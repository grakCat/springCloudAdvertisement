package org.study.news;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created on 2019/6/10.
 *
 * @author Grak
 * @since 1.0
 */
@RestController
@RefreshScope
public class HelloController {

    @Value("${server.port}")
    private int port;

    @RequestMapping("user")
    public String index(){
        return "Hello World!,user端口："+port;
    }


}
