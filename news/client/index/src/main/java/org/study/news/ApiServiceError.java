package org.study.news;

import org.springframework.stereotype.Component;

/**
 * Created on 2019/6/6.
 *
 * @author Grak
 * @since 1.0
 */
@Component
public class ApiServiceError implements ApiService {

    @Override
    public String index() {
        return "服务发生故障！";
    }
}
