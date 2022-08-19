package com.example.demo.student;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.List;

import static java.time.Month.JUNE;

@Configuration
public class StudentConfig {

    @Bean
    CommandLineRunner commandLineRunner(StudentRepository repository) {
        return args -> {
            Student umesh = new Student(
                    "Umesh",
                    "sujakhu.umesh@gmail.com",
                    LocalDate.of(1993, JUNE, 27),
                    "123456"
            );

            Student john = new Student(
                    "John",
                    "john@gmail.com",
                    LocalDate.of(2000, JUNE, 27),
                    "454788"
            );

//            repository.saveAll(List.of(umesh, john));
        };
    }
}
