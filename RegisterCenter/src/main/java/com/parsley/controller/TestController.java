package com.parsley.controller;

import com.parsley.domain.entity.ResponseDTO;
import com.parsley.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;

@RestController
public class TestController {

    @Autowired
    private RegisterService registerService;

    @GetMapping("/test")
    public String test(){
        return "RegisterCenterTest";
    }

    @PostMapping("/upload")
    public ResponseDTO<String> uploadTest(HttpServletRequest request){
        StandardMultipartHttpServletRequest req = (StandardMultipartHttpServletRequest) request;
        System.out.println(req);
        return ResponseDTO.succData("后端返回data中的文件url");
    }


//    @PostMapping("/register")
//    public

//    @GetMapping("/get")
//    public Register get(){
//        return registerService.getRegister(new Long(1));
//    }

}
