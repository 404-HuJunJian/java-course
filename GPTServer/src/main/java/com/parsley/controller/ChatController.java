package com.parsley.controller;

import com.parsley.domain.DTO.PostDataDTO;
import com.parsley.domain.entity.ResponseDTO;
import com.parsley.service.GPT3Service;
import com.parsley.service.Imp.GPT3ServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatController {


    @Autowired
    private GPT3Service gpt3Service;


    @PostMapping("/chat/gpt3")
    public ResponseDTO<String> Gpt3chat(@RequestBody PostDataDTO postDataDTO){
        return gpt3Service.chat(postDataDTO);
    }

    @GetMapping("/test")
    public String test(String message){
        return GPT3ServiceImp.requestGPT3(message);
    }



}
