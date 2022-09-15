package com.example.demo.student;

import com.example.demo.student.exception.BadRequestException;
import com.example.demo.student.exception.StudentNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getStudents() {
        return studentRepository.findAll();
    }

    public Student addStudent(Student student) {
        Optional<Student> studentByEmail = studentRepository.findByEmail(student.getEmail());
        if (studentByEmail.isPresent()) {
            throw new BadRequestException(
                    "Email " + student.getEmail() + " taken");
        }
        return studentRepository.save(student);
    }

    public void deleteStudent(String studentId) {
        if(!studentRepository.existsById(studentId)) {
            throw new StudentNotFoundException(
                    "Student with id " + studentId + " does not exists");
        }
        studentRepository.deleteById(studentId);
    }

    @Transactional
    public Student updateStudent(String studentId, Student student) {
        Student findStudent = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalStateException("Student with id " + studentId + " does not exists."));

        if (student.getName() != null && student.getName().length() > 0 && !Objects.equals(findStudent.getName(), student.getName())) {
            findStudent.setName(student.getName());
        }

        if (student.getEmail() != null && student.getEmail().length() > 0 && !Objects.equals(findStudent.getEmail(), student.getEmail())) {
            Optional<Student> studentOptional = studentRepository.findByEmail(student.getEmail());
            if (studentOptional.isPresent()) {
                throw new IllegalStateException("Email already taken.");
            }
            findStudent.setEmail(student.getEmail());
        }

        if(!Objects.equals(student.getPhone(), "")){
            findStudent.setPhone(student.getPhone());
        }
        // TODO : not necessary if transactional works
        return studentRepository.save(findStudent);
    }
}
