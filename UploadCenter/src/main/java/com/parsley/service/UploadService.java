package com.parsley.service;

import org.springframework.web.multipart.MultipartFile;

public interface UploadService {

    public String uploadFile(MultipartFile multipartFile);

}
