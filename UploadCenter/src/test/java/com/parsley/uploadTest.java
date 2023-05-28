package com.parsley;

import com.parsley.service.UploadService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;

import java.io.InputStream;

@SpringBootTest
public class uploadTest {

    @Autowired
    UploadService uploadService;

    @Test
    public void test01(){
    }

}
