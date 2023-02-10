package com.exam.stream;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafkaStreams;

@SpringBootApplication
@EnableKafkaStreams
public class ExamManagementStreamApplication {
    public static void main(String[] args) {
        SpringApplication.run(ExamManagementStreamApplication.class, args);
    }
}