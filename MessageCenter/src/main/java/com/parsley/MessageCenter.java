package com.parsley;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableCaching
@EnableFeignClients(basePackages = {"com.parsley"})
public class MessageCenter {

    public static void main(String[] args) {
        SpringApplication.run(MessageCenter.class);
    }

}
