package com.example.jparelationi.Service;

import com.example.jparelationi.ApiResponse.ApiException;
import com.example.jparelationi.DTO.CourseDTO;
import com.example.jparelationi.Model.Course;
import com.example.jparelationi.Model.Student;
import com.example.jparelationi.Model.Teacher;
import com.example.jparelationi.Repository.CourseRepository;
import com.example.jparelationi.Repository.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;
    private  final TeacherRepository teacherRepository;


//      public List<Course> getCourse(){
//          return courseRepository.findAll();
//      }

    public List<CourseDTO> get (){
        List<Course> courses= courseRepository.findAll();
        List<CourseDTO> courseDTOS =new ArrayList<>();
        for (Course c : courses){
            CourseDTO courseDTO= new CourseDTO(c.getName(),c.getTeacher().getName());
            courseDTOS.add(courseDTO);
        }
        return courseDTOS ;
    }

        public  void addCourse(Integer teacher_id, Course course){
        Teacher teacher=teacherRepository.findTeacherById(teacher_id);
        if(teacher==null){
            throw  new ApiException("teacher not found");
        }

            course.setTeacher(teacher);
            courseRepository.save(course);
    }


    public void update(Integer id,Course course){
        Course course1=courseRepository.findCourseById(id);

        if(course1==null){
            throw new ApiException("Course not found id");
        }

        course1.setName(course.getName());
        courseRepository.save(course1);
    }


    public void delete(Integer id){
        Course course=courseRepository.findCourseById(id);
        if(course==null){
            throw new ApiException("Course not found id");
        }
        courseRepository.delete(course);

    }

    public String  getTeacherByCourse(Integer id){
        Course course=courseRepository.findCourseById(id);
        if(course==null){
            throw new ApiException("Course not found id");
        }
        return course.getTeacher().getName();
    }

    public Set<Student> getAllStudentByCourse(Integer id){

        Course course = courseRepository.findCourseById(id);
        if(course==null){
            throw new ApiException("course id not found");
        }

        return course.getStudents();

    }


}
