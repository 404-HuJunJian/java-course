package com.parsley.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.parsley.domain.entity.Server;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface ServerMapper extends BaseMapper<Server> {
}
