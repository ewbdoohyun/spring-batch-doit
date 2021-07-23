package com.example.batch.doit;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableBatchProcessing
@SpringBootApplication
public class BatchDoItApplication{

    public static void main(String[] args) {
        SpringApplication.run(BatchDoItApplication.class,args);
    }
}
