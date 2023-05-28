package com.parsley.service.Imp;

import com.parsley.domain.entity.Function;
import com.parsley.mapper.FunctionMapper;
import com.parsley.service.FunctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class FunctionServiceImp implements FunctionService {

    @Autowired
    private FunctionMapper functionMapper;

    @Override
    public List<Function> listFunctionById(String Id){
        List<String> Ids = Arrays.asList(Id.split(","));
        return functionMapper.selectBatchIds(Ids);
    }

    @Override
    public String FunctionName2Id(String Name) {
        return null;
    }

    @Override
    public String FunctionId2Name(String Id) {
        StringBuilder builder = new StringBuilder();
        listFunctionById(Id).forEach(f->{
            builder.append(f.getName()).append(",");
        });
        builder.deleteCharAt(builder.length()-1);
        return builder.toString();
    }


}
