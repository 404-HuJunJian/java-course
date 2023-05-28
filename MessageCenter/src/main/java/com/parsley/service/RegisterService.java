package com.parsley.service;

import com.parsley.domain.entity.ResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

@Service
@FeignClient(value = "RegisterCenter")
public interface RegisterService {

    @PostMapping("/register")
    public ResponseDTO<String> register(String payload);


}
