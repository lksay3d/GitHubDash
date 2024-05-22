package com.github.lksay3d.GHADash;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class GhaDashApplication {

    public static void main(String[] args) {
        SpringApplication.run(GhaDashApplication.class, args);
    }
}