package com.parsley.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.parsley.domain.entity.Process;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface ProcessMapper extends BaseMapper<Process> {

}
