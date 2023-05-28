package com.parsley;

import com.parsley.config.UploadProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({UploadProperties.class})
public class UploadCenter {

    public static void main(String[] args) {
        SpringApplication.run(UploadCenter.class);
    }

}
