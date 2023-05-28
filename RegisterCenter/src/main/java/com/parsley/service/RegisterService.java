package com.parsley.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.parsley.domain.entity.Register;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RegisterService {

    public void register(String payload);

    public Page<Register> pageInfo(Integer pageIndex,Integer pageSize, boolean ascOrder, String columnName);

}
