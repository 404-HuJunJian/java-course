package com.parsley.controller;

import com.parsley.domain.entity.ResponseDTO;
import com.parsley.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;

@RestController
public class UploadController {

    @Autowired
    private UploadService uploadService;

    @PostMapping("/upload")
    public ResponseDTO<String> uploadTest(HttpServletRequest request){
        StandardMultipartHttpServletRequest req = (StandardMultipartHttpServletRequest) request;;
        return ResponseDTO.succData(uploadService.uploadFile(req.getFile("file")));
    }

}
