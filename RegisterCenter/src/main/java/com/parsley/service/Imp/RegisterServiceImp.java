package com.parsley.service.Imp;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.parsley.domain.entity.Function;
import com.parsley.domain.entity.Register;
import com.parsley.mapper.FunctionMapper;
import com.parsley.mapper.RegisterMapper;
import com.parsley.service.FunctionService;
import com.parsley.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.*;

@Service
public class RegisterServiceImp implements RegisterService {

    private static HashMap<String, Function> mapFunction = new HashMap<>();

    @Autowired
    private RegisterMapper registerMapper;

    @Autowired
    private FunctionMapper functionMapper;

    @Autowired
    private FunctionService functionService;

    @Override // 防止二次注册
    public void register(String payload) {
        JSONObject jsonObject = JSONObject.parseObject(payload);
        String uuid = jsonObject.getString("uuid");
        QueryWrapper<Register> registerQueryWrapper = new QueryWrapper<>();
        registerQueryWrapper.eq("uuid",uuid);
        Register register = registerMapper.selectOne(registerQueryWrapper);
        if(register == null){
            JSONArray functions = jsonObject.getJSONArray("functions");
            StringBuilder builder = new StringBuilder();
            functions.forEach(function->{
                QueryWrapper<Function> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("name",function);
                Function Selectfunction = functionMapper.selectOne(queryWrapper);
                builder.append(Selectfunction.getId().toString()).append(",");
            });
            builder.deleteCharAt(builder.length()-1);
            String functionIndex = builder.toString();
            register = new Register();
            register.setUuid(uuid);
            register.setFunctions(functionIndex);
            register.setDescriptions("default");
            register.setName("ReName");
            register.setCreateTime(new Timestamp(System.currentTimeMillis()));
            register.setLastTime(new Timestamp(System.currentTimeMillis()));
            registerMapper.insert(register);
        }else {
            register.setLastTime(new Timestamp(System.currentTimeMillis()));
            registerMapper.updateById(register);
        }

    }

    @Override
    public Page<Register> pageInfo(Integer pageIndex, Integer pageSize, boolean ascOrder, String columnName) {
        Page<Register> page = new Page<>(pageIndex,pageSize);
        QueryWrapper<Register> wrapper = new QueryWrapper<>();
        if (!columnName.isEmpty()){
            if (ascOrder){
                wrapper.orderByAsc(columnName.replaceAll("[A-Z]", "_$0").toLowerCase());
            }else{
                wrapper.orderByDesc(columnName.replaceAll("[A-Z]", "_$0").toLowerCase());
            }
        }
        registerMapper.selectPage(page,wrapper);
        page.getRecords().forEach(item ->{
            item.setFunctions(functionService.FunctionId2Name(item.getFunctions()));
        });
        return page;
    }


}
