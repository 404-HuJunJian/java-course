package com.parsley.utils;

import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;


@MessagingGateway(defaultRequestChannel = "mqttOutputChannel")
public interface MqttTemplate {

    void send(String payload);
    void sendToTopic(String payload, @Header(MqttHeaders.TOPIC)String topic);
    void sendToTopic(String payload,@Header(MqttHeaders.TOPIC)String topic,@Header(MqttHeaders.QOS)int qos);

}
