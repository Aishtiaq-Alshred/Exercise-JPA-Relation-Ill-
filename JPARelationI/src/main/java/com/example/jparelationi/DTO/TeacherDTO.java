package com.example.jparelationi.DTO;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class TeacherDTO {

    private String name;
    private List<CourseDTO> courseDTOS;
    private AddressDDTO addressDDTOS;

}
