package com.ilpalazzo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;

@SpringBootApplication(scanBasePackages = "com.ilpalazzo")
@EnableJpaRepositories(basePackages = "com.ilpalazzo.repository")
@EntityScan(basePackages = "com.ilpalazzo.model.entity")
@EnableRabbit
public class ilpalazzoApplication {
    public static void main(String[] args) {
        SpringApplication.run(ilpalazzoApplication.class, args);
    }
}
