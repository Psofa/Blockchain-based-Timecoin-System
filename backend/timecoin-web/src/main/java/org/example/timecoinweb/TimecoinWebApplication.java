package org.example.timecoinweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TimecoinWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(TimecoinWebApplication.class, args);
    }

}
