package com.parsley.service.Imp;

import com.alibaba.fastjson.JSONObject;
import com.parsley.constant.OrdinaryResponseCodeConst;
import com.parsley.domain.entity.ResponseDTO;
import com.parsley.service.SendMessageService;
import com.parsley.utils.MqttTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class SendMessageServiceImp implements SendMessageService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private MqttTemplate template;

    @Override
    public ResponseDTO<String> sendMessage(String topic, String payload) {
        String receiveTopic = topic.replace("_server","_hardware");
        for(int i=0 ;i<5;i++){
            if(redisTemplate.hasKey(receiveTopic)){
                String result = (String) redisTemplate.opsForValue().get(receiveTopic);
                JSONObject jsonObject = JSONObject.parseObject(result);
                AtomicBoolean hasAllFunc = new AtomicBoolean(true);
                Arrays.asList(payload.split(",")).forEach(func->{
                    if(!jsonObject.keySet().contains(func)){
                        hasAllFunc.set(false);
                        return;
                    }
                });
                if(hasAllFunc.get()){
                    return ResponseDTO.succData(jsonObject.toJSONString());
                }
            }else{
                if(i==0){
                    template.sendToTopic(payload, topic);
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    System.out.println(e);
                }
            }
        }
        return ResponseDTO.wrap(OrdinaryResponseCodeConst.TIME_OUT,"硬件响应超时大于5s");
    }
}
