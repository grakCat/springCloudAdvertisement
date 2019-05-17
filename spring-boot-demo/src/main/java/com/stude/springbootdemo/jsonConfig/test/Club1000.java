package com.stude.springbootdemo.jsonConfig.test;

import com.stude.springbootdemo.jsonConfig.JsonConfigLoadBean;
import lombok.Data;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * Created on 2018/12/6.
 *
 * @author grayCat
 * @since 1.0
 */
@Data
@Component
@Order(Integer.MIN_VALUE)
public class Club1000 extends JsonConfigLoadBean<Club1000> {

    public static Club1000 club = new Club1000();

    private int maxNum = 10;
    private int closeOpenWaitDay = 1;
    private int minRoomCard = 10;
    private int insteadRoomNumMax = 10;
    private int totalRoomNumMax = 10;
    private boolean test = true;

    @Override
    protected Class<Club1000> getTClass() {
        return Club1000.class;
    }

    @Override
    protected void getJsonConfig(Club1000 club) {
        Club1000.club = club;
    }
}
