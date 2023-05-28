package com.parsley.service.Imp;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.parsley.domain.DTO.ProcessDTO;
import com.parsley.domain.DTO.ServerDTO;
import com.parsley.domain.entity.Process;
import com.parsley.domain.entity.Server;
import com.parsley.mapper.ProcessMapper;
import com.parsley.service.ProcessService;
import com.parsley.service.ServerService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProcessServiceImp implements ProcessService {

    @Autowired
    private ProcessMapper processMapper;

    @Autowired
    private ServerService serverService;

    public Page<ProcessDTO> pageInfo(Integer pageIndex, Integer pageSize, boolean ascOrder, String columnName) {
        Page<Process> page = new Page<>(pageIndex,pageSize);
        QueryWrapper<Process> wrapper = new QueryWrapper<>();
        if (!columnName.isEmpty()){
            if (ascOrder){
                wrapper.orderByAsc(columnName.replaceAll("[A-Z]", "_$0").toLowerCase());
            }else{
                wrapper.orderByDesc(columnName.replaceAll("[A-Z]", "_$0").toLowerCase());
            }
        }
        processMapper.selectPage(page,wrapper);
        Page<ProcessDTO> pageDTO = new Page<>();
        BeanUtils.copyProperties(page,pageDTO);
        List<ProcessDTO> processDTOS = new ArrayList<>();
        page.getRecords().forEach(process -> {
            processDTOS.add(process2DTO(process));
        });
        pageDTO.setRecords(processDTOS);
        return pageDTO;
    }

    public void addProcess(ProcessDTO processDTO){
        Process process = new Process();
        BeanUtils.copyProperties(processDTO,process);
        StringBuilder stringBuilder = new StringBuilder();
        processDTO.getServers().forEach(serverDTO -> {
            stringBuilder.append(serverDTO.getId()).append(",");
        });
        stringBuilder.deleteCharAt(stringBuilder.length()-1);
        process.setServers(stringBuilder.toString());
        processMapper.insert(process);
    }

    public boolean checkProcess(ProcessDTO processDTO){
        List<ServerDTO> serverDTOS = processDTO.getServers();
        return true;
    }

    public List<ProcessDTO> listAll(){
        List<Process> processes = processMapper.selectList(null);
        List<ProcessDTO> processDTOS = new ArrayList<>();
        processes.forEach(process -> {
            ProcessDTO processDTO = process2DTO(process);
            processDTOS.add(processDTO);
        });
        return processDTOS;
    }

    private ProcessDTO process2DTO(Process process){
        List<ServerDTO> serverDTOS = serverService.listServersById(process.getServers());
        ProcessDTO processDTO = new ProcessDTO();
        BeanUtils.copyProperties(process,processDTO);
        processDTO.setServers(serverDTOS);
        return processDTO;
    }

}
