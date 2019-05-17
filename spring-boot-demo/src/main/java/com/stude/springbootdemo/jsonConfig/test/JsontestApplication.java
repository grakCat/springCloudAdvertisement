package com.stude.springbootdemo.jsonConfig.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.stude.springbootdemo"})
public class JsontestApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(JsontestApplication.class, args);
        Club club = Club.club;
        try {
            for (int i = 0; i < 50; i++) {
                Thread.sleep(2000);
                club = Club.club;
                System.out.println(club.getMaxNum());
                System.out.println("two" + Club1000.club.getMaxNum());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ctx.close();
    }


}
