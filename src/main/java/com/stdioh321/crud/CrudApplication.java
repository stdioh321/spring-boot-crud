package com.stdioh321.crud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import pl.powermilk.jpa.soft.delete.repository.EnableJpaSoftDeleteRepositories;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class CrudApplication {

    public static void main(String[] args) {
        SpringApplication.run(CrudApplication.class, args);
    }

}
