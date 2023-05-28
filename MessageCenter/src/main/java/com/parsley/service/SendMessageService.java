package com.parsley.service;

import com.parsley.domain.entity.ResponseDTO;

public interface SendMessageService {

    public ResponseDTO<String> sendMessage(String topic, String payload);

}
