package com.stude.timer_task;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class TimerTaskApplication {

    public static void main(String[] args) {
        SpringApplication.run(TimerTaskApplication.class, args);
    }

}
