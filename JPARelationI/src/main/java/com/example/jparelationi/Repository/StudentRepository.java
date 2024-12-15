package com.example.jparelationi.Repository;

import com.example.jparelationi.Model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student,Integer> {

    Student findStudentById(Integer id);
}
