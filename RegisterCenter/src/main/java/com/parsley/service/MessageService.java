package com.parsley.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



@FeignClient(value = "MessageCenter")
public interface MessageService {

    @GetMapping("/mqtt/addTopic")
    public void addTopic(@RequestParam("topic") String topic);

}
