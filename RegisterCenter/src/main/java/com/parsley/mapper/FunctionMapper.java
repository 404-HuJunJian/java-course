package com.parsley.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.parsley.domain.entity.Function;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface FunctionMapper extends BaseMapper<Function> {

}
