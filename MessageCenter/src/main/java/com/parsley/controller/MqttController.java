package com.parsley.controller;

import com.alibaba.fastjson.JSONObject;
import com.parsley.constant.OrdinaryResponseCodeConst;
import com.parsley.domain.entity.ResponseDTO;
import com.parsley.service.RegisterService;
import com.parsley.service.SendMessageService;
import com.parsley.utils.MqttTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;

@RestController
public class MqttController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private RegisterService registerService;

    @Autowired
    private MqttPahoMessageDrivenChannelAdapter adapter;

    @Autowired
    private SendMessageService sendMessageService;


    @GetMapping("/mqtt/addTopic")
    public void addTopic(String topic){
        String[] topics = adapter.getTopic();
        if(!Arrays.asList(topics).contains(topic)){
            adapter.addTopic(topic);
        }
    }

    /* 输入topic 和payload 目前payload只能是单功能的东西*/
    @PostMapping("/mqtt/sendMessage")
    public ResponseDTO<String> sendMessage(String topic,String payload){
        return sendMessageService.sendMessage(topic,payload);
    }

    @GetMapping("/test")
    public ResponseDTO<String> testForRestTemplate(){
        restTemplate.postForObject("http://RegisterCenter/register","{uuid:'test_payload',functions:['humid','temperature']}", ResponseDTO.class);
        return ResponseDTO.succ();
    }

    @GetMapping("/test2")
    public ResponseDTO<String> testForFeign(){
        registerService.register("{uuid:'test_payload',functions:['humid','temperature']}");
        return ResponseDTO.succ();
    }

}
