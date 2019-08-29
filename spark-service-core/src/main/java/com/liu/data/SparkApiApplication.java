package com.liu.data;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = {"com.liu.data"})
public class SparkApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(SparkApiApplication.class, args);
    }
}
