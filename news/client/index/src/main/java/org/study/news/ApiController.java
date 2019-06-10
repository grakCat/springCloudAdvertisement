package org.study.news;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created on 2019/6/6.
 *
 * @author Grak
 * @since 1.0
 */
@RestController
public class ApiController {

    @Autowired
    private ApiService apiService;

    @RequestMapping("indexApi")
    public String index(){
        return apiService.index();
    }
}
