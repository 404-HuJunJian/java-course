package com.parsley.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@TableName("servers")
public class Server {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String name;

    private String path;

    private String descriptions;

    private String inputFunctions;

    private String outputFunctions;


}
