package com.parsley.utils;

import com.parsley.domain.entity.ResponseDTO;
import com.parsley.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.util.concurrent.TimeUnit;


@Component
public class InputMessageHandler implements MessageHandler {

//    @Autowired
//    private RestTemplate restTemplate;
//
//    private static final String REGISTER_URL_PREFIX = "http://RegisterCenter";

    @Autowired
    private RegisterService registerService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public void handleMessage(Message<?> message) throws MessagingException {
        String topic = message.getHeaders().get("mqtt_receivedTopic").toString();
        String payload = message.getPayload().toString();
        if(topic.equals("register")){
//            restTemplate.postForObject(REGISTER_URL_PREFIX+"/register",payload, ResponseDTO.class);
            registerService.register(payload);
        }else {
            redisTemplate.opsForValue().set(topic,payload,60, TimeUnit.SECONDS);
        }
        // 1.存到redis里设置过期时间***（这个可以在多个用户请求的情况下加快访问速度，减少硬件响应）
        // 2.持久化到mysql（排除吧）
        // 3.使用websocket向前端发送消息？（这样必须知道前端是谁）（实时性高）
    }
}
