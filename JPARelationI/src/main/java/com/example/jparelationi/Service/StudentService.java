package com.example.jparelationi.Service;


import com.example.jparelationi.ApiResponse.ApiException;
import com.example.jparelationi.DTO.CourseDDTO;
import com.example.jparelationi.DTO.CourseDTO;
import com.example.jparelationi.DTO.StudentDDTO;
import com.example.jparelationi.DTO.StudentDTO;
import com.example.jparelationi.Model.Course;
import com.example.jparelationi.Model.Student;
import com.example.jparelationi.Model.Teacher;
import com.example.jparelationi.Repository.CourseRepository;
import com.example.jparelationi.Repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;


//    public List<Student> getStudents(){
//        return studentRepository.findAll();
//    }

    public List<StudentDDTO> get() {
        List<Student> students = studentRepository.findAll();
        List<StudentDDTO> studentDTOS = new ArrayList<>();

        for (Student student : students) {
            List<CourseDDTO> courseDTOS = new ArrayList<>();

            for (Course course : student.getCourses()) {
                Teacher teacher = course.getTeacher();
                CourseDDTO courseDTO = new CourseDDTO(course.getName(), teacher.getName());
                courseDTOS.add(courseDTO);
            }

            StudentDDTO studentDTO = new StudentDDTO(
                    student.getName(),
                    student.getAge(),
                    student.getMajor(),
                    courseDTOS
            );

            studentDTOS.add(studentDTO);
        }

        return studentDTOS;
    }


    public void addStudent(Student student){
        studentRepository.save(student);
    }

    public void updateStudent(Integer id , Student student){
        Student student1 = studentRepository.findStudentById(id);
        if(student1==null){
            throw new ApiException("student id not found");
        }
        student1.setName(student.getName());
        student1.setAge(student.getAge());
        student1.setMajor(student.getMajor());
        studentRepository.save(student1);
    }


    public void deleteStudent(Integer id){
        Student student = studentRepository.findStudentById(id);
        if(student==null){
            throw new ApiException("student id not found");
        }
        studentRepository.delete(student);
    }


    public void assignStudentToCourse(Integer student_id,Integer course_id){
        Student student = studentRepository.findStudentById(student_id);
        Course course =courseRepository.findCourseById(course_id);

        if(student==null){
            throw new ApiException("student id not found");
        }
        if(course==null){
            throw new ApiException("course id not found");
        }

        student.getCourses().add(course);
        course.getStudents().add(student);
        studentRepository.save(student);
        courseRepository.save(course);
    }


    public void changeMajor(Integer id , String major){
        Student student = studentRepository.findStudentById(id);

        if(student==null){
            throw new ApiException("student id not found");
        }

        for (Course course: student.getCourses()){
            course.getStudents().remove(student);
            courseRepository.save(course);
        }

        student.setMajor(major);
        student.setCourses(null);
        studentRepository.save(student);
    };


}
