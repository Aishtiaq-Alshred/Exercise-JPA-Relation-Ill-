package com.example.jparelationi.Service;

import com.example.jparelationi.ApiResponse.ApiException;
import com.example.jparelationi.DTO.AddressDDTO;
import com.example.jparelationi.DTO.CourseDDTO;
import com.example.jparelationi.DTO.CourseDTO;
import com.example.jparelationi.DTO.TeacherDTO;
import com.example.jparelationi.Model.Address;
import com.example.jparelationi.Model.Course;
import com.example.jparelationi.Model.Teacher;
import com.example.jparelationi.Repository.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

    @Service
    @RequiredArgsConstructor
    public class TeacherService {

        private final TeacherRepository teacherRepository;


//        public List<Teacher> getTeachers(){
//
//            return teacherRepository.findAll();
//        }

//        //getDTO
        public List<TeacherDTO> getTeachers(){

            List<Teacher>teachers=teacherRepository.findAll();
            List<TeacherDTO>teacherDTOS=new ArrayList<>();
            List<CourseDDTO>courseDTOS=new ArrayList<>();
            for(Teacher t:teachers){
                for (Course c:t.getCourses()){
                    CourseDDTO courseDTO=new CourseDDTO(c.getName(), teachers.getName);
                    courseDTOS.add(courseDTO);
            }
                TeacherDTO teacherDTO=new TeacherDTO(t.getName(),courseDTOS,new AddressDDTO(t.getAddress().getArea(),t.getAddress().getStreet(),t.getAddress().getBuildingNumber()));
                teacherDTOS.add(teacherDTO);
            }
            return teacherDTOS;
        }



        public void addTeacher(Teacher teacher){

            teacherRepository.save(teacher);
        }


        public void updateTeacher(Integer id , Teacher teacher){
            Teacher teacher1 = teacherRepository.findTeacherById(id);
            if(teacher1==null){
                throw new ApiException("Teacher id not found");
            }
            teacher1.setName(teacher.getName());
            teacher1.setAge(teacher.getAge());
            teacher1.setEmail(teacher.getEmail());
            teacher1.setSalary(teacher.getSalary());
            teacherRepository.save(teacher1);
        }


        public void deleteTeacher(Integer id){
            Teacher teacher=teacherRepository.findTeacherById(id);
            if(teacher==null){
                throw new ApiException("Teacher id not found");
            }
            teacherRepository.delete(teacher);
        }

        public Address getAllTeacherDetails(Integer id){
            Teacher teacher = teacherRepository.findTeacherById(id);
            if(teacher==null){
                throw new ApiException("Teacher id not found");
            }
            if(teacher.getAddress()==null){
                throw new ApiException("address not found");
            }

            return teacher.getAddress();
        }


}
