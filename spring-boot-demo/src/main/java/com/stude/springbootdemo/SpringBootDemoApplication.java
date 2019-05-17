package com.stude.springbootdemo;

import com.stude.springbootdemo.xlsx.sample.lsyguimajiang.LGMJConfiginfo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootDemoApplication {

    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(SpringBootDemoApplication.class, args);
        for(int i=0;i<99;i++){
            LGMJConfiginfo configinfo = LGMJConfiginfo.getLGMJConfiginfo(202);
            Thread.sleep(5000);
            System.out.println(configinfo.name);
        }
    }

}
