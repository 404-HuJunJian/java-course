package com.parsley.domain.entity;

//import com.baomidou.mybatisplus.annotation.IdType;
//import com.baomidou.mybatisplus.annotation.TableId;
//import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
//@TableName("functions")
public class Function {

//    @TableId(type = IdType.AUTO)
    private Long id;

    private String name;

    private String type;

    private String descriptions;


}
