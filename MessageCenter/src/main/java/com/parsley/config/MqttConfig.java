package com.parsley.config;

import com.parsley.utils.InputMessageHandler;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.MessageProducer;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

import java.time.Instant;

@Configuration
@IntegrationComponentScan
public class MqttConfig {

    @Autowired
    private MqttProperties prop;


    // 生产客户端
    @Bean
    public MqttPahoClientFactory mqttPahoClientFactory(){
        DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory(); //生产客户端类的工厂，以options为设置创建MqttClient
        MqttConnectOptions mqttConnectOptions = new MqttConnectOptions(); //配置客户端如何连接到mqtt服务器broker
        mqttConnectOptions.setServerURIs(new String[]{prop.getUrl()});
        mqttConnectOptions.setUserName(prop.getUsername());
        mqttConnectOptions.setPassword(prop.getPassword().toCharArray());

        mqttConnectOptions.setCleanSession(false);  // 重连后保持上次状态
        mqttConnectOptions.setAutomaticReconnect(true); //连接丢失时自动连接
        factory.setConnectionOptions(mqttConnectOptions);
        return factory;
    }

    // Mqtt 管道适配器 也就是在进入管道之前对数据流进行修改，比如把字节转对象之类的
    @Bean
    public MqttPahoMessageDrivenChannelAdapter adapter(MqttPahoClientFactory factory){
        return new MqttPahoMessageDrivenChannelAdapter(prop.getClientId(),factory,"register");
    }

    // 消息生产者 把mqtt的消息投递给入站管道
    @Bean
    public MessageProducer inbound(MqttPahoMessageDrivenChannelAdapter adapter){
        adapter.setCompletionTimeout(5000);
        adapter.setConverter(new DefaultPahoMessageConverter());
        adapter.setQos(1);
        adapter.setOutputChannel(mqttInputChannel());
        return adapter;
    }


    /* 入站 */

    @Bean
    public MessageChannel mqttInputChannel(){
        return new DirectChannel();
    }

    @Bean
    @ServiceActivator(inputChannel = "mqttInputChannel")
    public MessageHandler mqttInputHandler(InputMessageHandler inputMessageHandler){
        return inputMessageHandler;
    }

    /* 出站 */

    @Bean
    public MessageChannel mqttOutputChannel(){
        return new DirectChannel();
    }

    @Bean
    @ServiceActivator(inputChannel = "mqttOutputChannel")
    public MessageHandler mqttOutputHandler(MqttPahoClientFactory mqttPahoClientFactory){
        MqttPahoMessageHandler messageHandler = new MqttPahoMessageHandler(prop.getClientId()+"-pub-"+ Instant.now().toEpochMilli(),mqttPahoClientFactory);
        messageHandler.setAsync(true);
        messageHandler.setDefaultRetained(false);
        messageHandler.setAsyncEvents(false);
        messageHandler.setDefaultQos(2);
        DefaultPahoMessageConverter defaultPahoMessageConverter = new DefaultPahoMessageConverter();
        messageHandler.setConverter(defaultPahoMessageConverter);
        return messageHandler;
    }

}
