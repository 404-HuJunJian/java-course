package com.parsley.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.parsley.domain.entity.Register;
import com.parsley.domain.entity.ResponseDTO;
import com.parsley.service.MessageService;
import com.parsley.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class RegisterController {

    @Autowired
    private RegisterService registerService;

    @Autowired
    private MessageService messageService;

    @GetMapping("/testAddTopic")
    public void test(){
        messageService.addTopic("new_topic");
    }

    @PostMapping("/register")
    public ResponseDTO<String> register(@RequestBody String payload){
        registerService.register(payload);
        messageService.addTopic(JSONObject.parseObject(payload).getString("uuid")+"_hardware");
        return ResponseDTO.succ();
    }

    @GetMapping("/register/pageInfo")
    public ResponseDTO<Page<Register>> pageInfo(Integer pageIndex, Integer pageSize, boolean ascOrder, String columnName){
        return ResponseDTO.succData(registerService.pageInfo(pageIndex,pageSize,ascOrder,columnName));
    }



}
