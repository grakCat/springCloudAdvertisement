package com.stude.springbootdemo.mjtest;

import org.apache.commons.lang3.ArrayUtils;

import java.util.stream.Stream;

/**
 * Created on 2019/5/17.
 *
 * @author hy
 * @since 1.0
 */
public class ApplicationTest {

    public static void main(String[] args) {
        int number = 4000000;
        int[] allPoker = Stream.iterate(1,x -> x + 1).limit(108).mapToInt(x -> x).toArray();
        MJConstant.GuiPoker guiMessage = new MJConstant.GuiPoker();
        guiMessage.isHave = true;
        guiMessage.guiNumber = 12;
        guiMessage.fanChose = 2;
        long startTime = System.currentTimeMillis();    //获取开始时间

        for(int i = 0;i< number;i++){
            HuBian bian = new HuBian();
            ArrayUtils.shuffle(allPoker);
            int[] handPoker = ArrayUtils.subarray(allPoker,0,14);
            int[] hand = getHandPoker(handPoker);
            guiMessage.guiPoker = getHandPoker(ArrayUtils.subarray(allPoker,14,18));
            bian.huInfo(hand,false,guiMessage);
        }
        long endTime = System.currentTimeMillis();    //获取结束时间
        System.out.println("程序运行时间：" + (endTime - startTime) + "ms");
    }

    /**
     * 将获取到的手牌id转换为计算用的牌数组
     */
    protected static int[] getHandPoker(int[] handPokerss) {
        int[] handPoker = new int[50];
        for (int pokerId : handPokerss) {
            int poker = GameUtil.getPokerByte(pokerId);
            handPoker[poker]++;
        }
        return handPoker;
    }
}
