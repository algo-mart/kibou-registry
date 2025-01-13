package com.algomart.kibouregistry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class KibouRegistryApplication {

    public static void main(String[] args) {
        SpringApplication.run(KibouRegistryApplication.class, args);
    }

}
