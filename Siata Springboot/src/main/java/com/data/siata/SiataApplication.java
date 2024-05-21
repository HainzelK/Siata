package com.data.siata;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.data.siata.repository")
public class SiataApplication {
    public static void main(String[] args) {
        SpringApplication.run(SiataApplication.class, args);
    }
}
