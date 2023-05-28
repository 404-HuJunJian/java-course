package com.parsley.service.Imp;

import com.parsley.config.UploadProperties;
import com.parsley.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
public class UploadServiceImp implements UploadService {

//    @Value("${com.parsley.upload.path}")
//    private String path;

    @Autowired
    private UploadProperties uploadProperties;

    @Override
    public String uploadFile(MultipartFile multipartFile) {
        if(null == multipartFile){
            return "";
        }
        //获取文件的完整名字
        String originalFileName = multipartFile.getOriginalFilename();
        //生成文件的新名字
        String newFileName = generateFileName(originalFileName);
        File file = new File(new File(uploadProperties.getPath(),newFileName).getAbsolutePath());
        try {
            multipartFile.transferTo(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return newFileName;
    }


    private String generateFileName(String originalFileName){
        //当前时间戳
        String time = String.valueOf(System.currentTimeMillis());
        //获得uuid
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        //获得文件扩展名后缀
        String fileType = originalFileName.substring(originalFileName.lastIndexOf("."));
        return time +"_"+ uuid + fileType;
    }
}
