package com.example.demo.student;

import com.example.demo.config.RabbitMQMessageConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping(path = "api/v1/student")
public class StudentController {

    private final StudentService studentService;

    @Autowired
    KafkaTemplate<String, Student> kafkaTemplate;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    private static final String TOPIC = "Kafka_Example_json";

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public ResponseEntity<List<Student>> getStudents() {
        List<Student> students = studentService.getStudents();
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Student> addStudent(@RequestBody Student student) {
        Student newStudent = studentService.addStudent(student);

        kafkaTemplate.send(TOPIC, newStudent);
        return new ResponseEntity<>(newStudent, HttpStatus.OK);
    }

    @DeleteMapping(path = "{studentId}")
    public ResponseEntity<?> deleteStudent(@PathVariable("studentId") String studentId) {
        studentService.deleteStudent(studentId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(path = "{studentId}")
    public ResponseEntity<Student> updateStudent(
            @PathVariable("studentId") String studentId,
            @RequestBody Student student) {
        Student updatedStudent = studentService.updateStudent(studentId, student);
        return new ResponseEntity<>(updatedStudent, HttpStatus.OK);
    }

    @GetMapping(path = "/{message}")
    public String testRabbitMq(@PathVariable("message") String message) {

        HashMap<String, String> data = new HashMap<String, String>();
        data.put("message", message);
        rabbitTemplate.convertAndSend(RabbitMQMessageConfig.EXCHANGE, RabbitMQMessageConfig.ROUTING_KEY, data);
        return "Message sent";
    }

}
