package com.stude.springbootdemo.comfig;

/**
 * Created on 2019/5/17.
 *
 * @author hy
 * @since 1.0
 */
//@Data

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.List;

@Data
@Configuration
@PropertySource(value = "file:${user.dir}/config/tree.properties")
@ConfigurationProperties(prefix = "tree")
public class OtherPath {

    private String whit;

    private int age;

    private boolean boos;

    private String color;

    private List<String> list;

    @Value("file:${user.dir}")
    private String topPath;
}
