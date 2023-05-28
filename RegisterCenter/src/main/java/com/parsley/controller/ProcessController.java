package com.parsley.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.parsley.domain.DTO.ProcessDTO;
import com.parsley.domain.entity.ResponseDTO;
import com.parsley.service.ProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProcessController {

    @Autowired
    private ProcessService processService;

    @GetMapping("/process/pageInfo")
    public ResponseDTO<Page<ProcessDTO>> pageInfo(Integer pageIndex, Integer pageSize, boolean ascOrder, String columnName){
        return ResponseDTO.succData(processService.pageInfo(pageIndex,pageSize,ascOrder,columnName));
    }

    @PostMapping("/process/add")
    public ResponseDTO<String> addNewProcess(@RequestBody ProcessDTO processDTO){
        processService.addProcess(processDTO);
        return ResponseDTO.succ();
    }

    @GetMapping("/process/listAll")
    public ResponseDTO<List<ProcessDTO>> listAll(){
        return ResponseDTO.succData(processService.listAll());
    }

}
