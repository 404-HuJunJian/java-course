package com.parsley.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.parsley.domain.DTO.ProcessDTO;

import java.util.List;

public interface ProcessService {

    public Page<ProcessDTO> pageInfo(Integer pageIndex, Integer pageSize, boolean ascOrder, String columnName);

    public void addProcess(ProcessDTO processDTO);

    public List<ProcessDTO> listAll();

    public boolean checkProcess(ProcessDTO processDTO);

}
