package com.parsley.config;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "mqtt")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MqttProperties {

    private String url;

    private String username;

    private String password;

    private String clientId;

}
