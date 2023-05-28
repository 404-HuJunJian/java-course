package com.parsley.domain.DTO;

import com.parsley.domain.entity.Function;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ServerDTO {

    private Long id;

    private String name;

    private String path;

    private String descriptions;

    private List<Function> inputFunctions;

    private List<Function> outputFunctions;

}
