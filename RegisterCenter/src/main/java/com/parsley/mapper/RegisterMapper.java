package com.parsley.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.parsley.domain.entity.Register;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface RegisterMapper extends BaseMapper<Register> {

//    public void addRegister(Register register);
//
//    public Register getRegisterById(Integer id);
//
//    public Register getRegisterByUuid(String uuid);
//
//    public List<Register> listRegister();
//
//    public List<Register> listOnlineRegister();

}
