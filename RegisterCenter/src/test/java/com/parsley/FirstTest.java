package com.parsley;

import com.parsley.service.ProcessService;
import com.parsley.service.RegisterService;
import com.parsley.service.ServerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class FirstTest {

    @Autowired
    RegisterService registerService;

    @Autowired
    ServerService serverService;

    @Autowired
    ProcessService processService;

    @Test
    public void test1(){
        registerService.register("{'uuid':'abcdefghi','functions':['humid','light']}");
    }

    @Test
    public void test2(){
        registerService.pageInfo(1,2,true,"");
    }

    @Test
    public void test3(){
        serverService.listServersById("2,1,4");
        serverService.pageInfo(1,3,false,"name");
    }

    @Test
    public void test4(){
        processService.pageInfo(1,3,false,"name");
    }

}
