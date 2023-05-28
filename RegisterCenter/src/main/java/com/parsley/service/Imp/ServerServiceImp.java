package com.parsley.service.Imp;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.parsley.domain.DTO.ServerDTO;
import com.parsley.domain.entity.Function;
import com.parsley.domain.entity.Register;
import com.parsley.domain.entity.Server;
import com.parsley.mapper.ServerMapper;
import com.parsley.service.FunctionService;
import com.parsley.service.ServerService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

@Service
public class ServerServiceImp implements ServerService {

    @Autowired
    private ServerMapper serverMapper;

    @Autowired
    private FunctionService functionService;

    @Override
    public List<ServerDTO> listServersById(String Id) {
        List<String> Ids = Arrays.asList(Id.split(","));
        List<ServerDTO> servers = new ArrayList<>();
        Ids.forEach(i->{
            Server server = serverMapper.selectById(i);
            servers.add(server2DTO(server));
        });
        return servers;
    }

    @Override
    public Page<ServerDTO> pageInfo(Integer pageIndex, Integer pageSize, boolean ascOrder, String columnName) {
        Page<Server> page = new Page<>(pageIndex,pageSize);
        QueryWrapper<Server> wrapper = new QueryWrapper<>();
        if (!columnName.isEmpty()){
            if (ascOrder){
                wrapper.orderByAsc(columnName.replaceAll("[A-Z]", "_$0").toLowerCase());
            }else{
                wrapper.orderByDesc(columnName.replaceAll("[A-Z]", "_$0").toLowerCase());
            }
        }
        serverMapper.selectPage(page,wrapper);
        Page<ServerDTO> pageDTO = new Page<>();
        BeanUtils.copyProperties(page,pageDTO);
        List<ServerDTO> serverDTOS = new ArrayList<>();
        page.getRecords().forEach(server->{
            serverDTOS.add(server2DTO(server));
        });
        pageDTO.setRecords(serverDTOS);
        return pageDTO;
    }

    private ServerDTO server2DTO(Server server){
        List<Function> inputFunctions = functionService.listFunctionById(server.getInputFunctions());
        List<Function> outputFunctions =functionService.listFunctionById(server.getOutputFunctions());
        ServerDTO serverDTO = new ServerDTO();
        BeanUtils.copyProperties(server,serverDTO);
        serverDTO.setInputFunctions(inputFunctions);
        serverDTO.setOutputFunctions(outputFunctions);
        return serverDTO;
    }

}
