package com.parsley.domain.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PostDataDTO {

    private ProcessDTO processDTO;

    private Map<String, Object> dataMap;

    private Integer processIndex;

}
