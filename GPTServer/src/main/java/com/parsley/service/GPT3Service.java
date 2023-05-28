package com.parsley.service;

import com.parsley.domain.DTO.PostDataDTO;
import com.parsley.domain.entity.ResponseDTO;


public interface GPT3Service {

    public ResponseDTO<String> chat(PostDataDTO postDataDTO);

}
