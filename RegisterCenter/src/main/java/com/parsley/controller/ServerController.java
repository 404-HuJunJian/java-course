package com.parsley.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.parsley.domain.DTO.ServerDTO;
import com.parsley.domain.entity.ResponseDTO;
import com.parsley.service.ServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ServerController {

    @Autowired
    private ServerService serverService;

    @GetMapping("/server/pageInfo")
    public ResponseDTO<Page<ServerDTO>> pageInfo(Integer pageIndex, Integer pageSize, boolean ascOrder, String columnName){
        return ResponseDTO.succData(serverService.pageInfo(pageIndex,pageSize,ascOrder,columnName));
    }

}
