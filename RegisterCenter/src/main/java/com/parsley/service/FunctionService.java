package com.parsley.service;

import com.parsley.domain.entity.Function;

import java.util.List;

public interface FunctionService {

    public List<Function> listFunctionById(String Id);

    public String FunctionName2Id(String Name);

    public String FunctionId2Name(String Id);

}
