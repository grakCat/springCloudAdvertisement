package com.imooc.ad;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * Created on 2019/5/9.
 *
 * @author hy
 * @since 1.0
 */
@EnableZuulProxy
@SpringBootApplication
public class ZuuGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZuuGatewayApplication.class,args);
    }
}
