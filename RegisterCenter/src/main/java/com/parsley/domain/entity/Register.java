package com.parsley.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Register {

    @TableId(type = IdType.AUTO)
    private Long id;
    private String name;
    private String uuid;
    private Timestamp createTime;
    private Timestamp lastTime;
    private String functions;
    private String descriptions;

}
