package org.study.news;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created on 2019/6/6.
 *
 * @author Grak
 * @since 1.0
 */
@FeignClient(value = "userClient",fallback = ApiServiceError.class)
public interface ApiService {

    @RequestMapping(value = "/user",method = RequestMethod.GET)
    String index();
}