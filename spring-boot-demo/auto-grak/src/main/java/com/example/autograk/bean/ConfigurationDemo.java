package com.example.autograk.bean;

import lombok.Data;
import org.springframework.context.annotation.Configuration;

/**
 * Created on 2019/5/17.
 *
 * @author hy
 * @since 1.0
 */
@Data
@Configuration("MyConfiguration")
public class ConfigurationDemo {

    private String name = "Configuration";
}
