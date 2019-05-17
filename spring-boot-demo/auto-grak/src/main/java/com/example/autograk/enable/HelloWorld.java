package com.example.autograk.enable;

import org.springframework.context.annotation.Bean;

/**
 * Created on 2019/5/17.
 *
 * @author hy
 * @since 1.0
 */
public class HelloWorld {

    @Bean
    public String helloWorld(){
        return "Hello World";
    }
}
