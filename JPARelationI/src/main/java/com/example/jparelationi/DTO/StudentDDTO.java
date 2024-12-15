package com.example.jparelationi.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class StudentDDTO {

    private String name;
    private Integer age;
    private String major;
    private List<CourseDDTO> courseDTOS;
}
