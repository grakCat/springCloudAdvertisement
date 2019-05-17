package com.stude.springbootdemo.pktest;

import java.util.LinkedList;
import java.util.List;

/**
 * Created on 2019/5/17.
 *
 * @author hy
 * @since 1.0
 */
public class DdzRobotCardType {

    /*当前牌类型*/
    public DdzCardsType cardsType;
    /*牌型价值*/
    public int cardsValue = 0;
    /*更想把这张牌打出去，补值*/
    public int outOtherValue = 0;
    /*包含的牌*/
    public List<DdzCard> cards = new LinkedList<>();
    /*带牌*/
    public List<DdzCard> daiCards;
    /*牌型里最大的一张牌，用于比较大小*/
    public DdzCard compareCard ;
}
