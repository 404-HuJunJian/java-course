package com.parsley;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.parsley")
public class RegisterCenter {
    public static void main(String[] args) {
        SpringApplication.run(RegisterCenter.class);
    }
}
