package com.example.demo.student;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import java.time.LocalDate;
import java.util.Optional;

import static java.time.Month.JUNE;
import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
class StudentRepositoryTest {

    @Autowired
    private StudentRepository underTest;

    @AfterEach
    void tearDown() {
        underTest.deleteAll();
    }

    @Test
    void itShouldCheckWhenStudentEmailExists() {
        //given
        String email = "arya@gmail.com";
        Student student = new Student(
                "Arya",
                email,
                LocalDate.of(2000, JUNE, 27),
                "454788"
        );
        underTest.save(student);
        //when
        Optional<Student> exists = underTest.findByEmail(email);
        //then
        assertTrue(exists.isPresent());
    }

    @Test
    void itShouldCheckWhenStudentEmailDoesNotExists() {
        //given
        String email = "arya@gmail.com";
        //when
        Optional<Student> exists = underTest.findByEmail(email);
        //then
        assertTrue(exists.isEmpty());
    }
}