package com.parsley.service;

import com.parsley.domain.DTO.PostDataDTO;
import com.parsley.domain.entity.ResponseDTO;

public interface WeatherService {



    public ResponseDTO<String> predictWeather(PostDataDTO postDataDTO);
}
