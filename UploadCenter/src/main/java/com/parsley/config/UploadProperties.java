package com.parsley.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(
        prefix = "com.parsley.upload"
)
public class UploadProperties {

    private String path = "uploadFiles/";

}
