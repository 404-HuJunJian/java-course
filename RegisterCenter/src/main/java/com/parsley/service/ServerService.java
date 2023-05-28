package com.parsley.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.parsley.domain.DTO.ServerDTO;
import com.parsley.domain.entity.Register;
import com.parsley.domain.entity.Server;

import java.util.List;

public interface ServerService {

    public List<ServerDTO> listServersById(String Id);

    public Page<ServerDTO> pageInfo(Integer pageIndex, Integer pageSize, boolean ascOrder, String columnName);


}
