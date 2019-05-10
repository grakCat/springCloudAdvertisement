package com.imooc.ad.conf;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * Created by Qinyi.
 */
@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    /**
     * 设置通信SpringMVC，序列化方式配置
     * @param converters
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>>
                                                       converters) {
        //只添加了json格式化方式
        converters.clear();
        converters.add(new MappingJackson2HttpMessageConverter());
    }
}
