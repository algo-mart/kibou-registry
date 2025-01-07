package com.algomart.kibouregistry;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class KibouRegistryApplication {

    public static void main(String[] args){
        Dotenv dotenv = Dotenv.configure()
                .directory("./") // Ensure this is the directory where .env is located
                .load();

        // Optionally, print out the loaded variables for debugging
        System.out.println("Database URL: " + dotenv.get("SPRING_DATASOURCE_URL"));

        SpringApplication.run(KibouRegistryApplication.class, args);
    }

}
