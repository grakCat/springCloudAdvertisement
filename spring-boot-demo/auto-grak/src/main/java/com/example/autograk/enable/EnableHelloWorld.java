package com.example.autograk.enable;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * Created on 2019/5/17.
 *
 * @author hy
 * @since 1.0
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({HelloWorld.class,HelloWorld2.class})
public @interface EnableHelloWorld {
}
