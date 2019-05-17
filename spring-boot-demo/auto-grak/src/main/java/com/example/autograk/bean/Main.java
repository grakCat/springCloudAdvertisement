package com.example.autograk.bean;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ImportResource;

/**
 * Created on 2019/5/17.
 *
 * @author hy
 * @since 1.0
 */
//@ComponentScan("com.example.autograk.bean")
@ImportResource("classpath:spring.xml")
public class Main {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = new SpringApplicationBuilder(Main.class)
                .web(WebApplicationType.NONE)
                .run(args);

        System.out.println(context.getBean("controllerDemo").toString());
        //有添加bean的名字，设置转换的类型
        ConfigurationDemo demo = context.getBean("MyConfiguration",ConfigurationDemo.class);
        System.out.println(demo.toString());
        context.close();
    }
}
