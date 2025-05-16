package com.ilpalazzo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;


@SpringBootApplication
@EnableRabbit
public class ilpalazzoApplication {
    public static void main(String[] args) {
        SpringApplication.run(ilpalazzoApplication.class, args);
    }
}
