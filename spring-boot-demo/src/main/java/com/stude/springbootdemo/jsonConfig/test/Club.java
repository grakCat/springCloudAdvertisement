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
public class Club extends JsonConfigLoadBean<Club> {

    public static Club club = new Club();

    private int maxNum = 10;
    private int closeOpenWaitDay = 1;
    private int minRoomCard = 10;
    private int insteadRoomNumMax = 10;
    private int totalRoomNumMax = 10;
    private boolean test = true;
    private String defaultNotice = "欢迎来到%s！";
    private boolean checkOnlinePlayer = false;

    @Override
    protected Class<Club> getTClass() {
        return Club.class;
    }

    @Override
    protected void getJsonConfig(Club club) {
        Club.club = club;
    }
}
