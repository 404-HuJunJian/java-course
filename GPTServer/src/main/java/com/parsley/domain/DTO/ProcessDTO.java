package com.parsley.domain.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProcessDTO {

    private Long id;

    private String name;

    private List<ServerDTO> servers;

    private String descriptions;

}
