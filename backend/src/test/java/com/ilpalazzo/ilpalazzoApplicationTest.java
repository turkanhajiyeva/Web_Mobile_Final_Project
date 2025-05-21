package com.ilpalazzo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;

@SpringBootApplication
@EnableRabbit
public class ilpalazzoApplicationTest {
    public static void main(String[] args) {
        SpringApplication.run(ilpalazzoApplicationTest.class, args);
    }
}