
 package com.stude.springbootdemo.xlsx.sample.lsyguimajiang;

import com.dyuproject.protostuff.Tag;
import com.stude.springbootdemo.xlsx.Sample;
import com.stude.springbootdemo.xlsx.SampleFactory;
import com.stude.springbootdemo.xlsx.SampleFactoryImpl;

 import javax.annotation.Generated;

/**
 * Auto generate by "Python tools"
 * 
 * @Date 2019-05-17 12:07:07
 */
 @Generated("Python tools")
 public class LGMJConfiginfo extends Sample{
    
    public static SampleFactory<LGMJConfiginfo> factory = new SampleFactoryImpl<>();
    
    public static LGMJConfiginfo getLGMJConfiginfo(int sid) {
        return (LGMJConfiginfo)factory.getSample(sid);
    }
    
    public static LGMJConfiginfo newLGMJConfiginfo(int sid) {
        return (LGMJConfiginfo)factory.newSample(sid);
    }
 	@Tag(3)
	// 游戏类型
	public int gameType;
	@Tag(4)
	// 游戏脚本
	public String controller;
	@Tag(5)
	// 最小开始人数
	public int startNum;
	@Tag(6)
	// 最大人数
	public int maxNum;
	@Tag(7)
	// 发牌数量
	public int pokerLenth;
	@Tag(8)
	// 名堂
	public String mingTang;
	@Tag(9)
	// 封顶选择(番数fan,分数score)
	public String fengDing;
	@Tag(10)
	// 坏牌率(相同花色大于6或癞子数量大于3
	public int badProbability;
	@Tag(11)
	// 测试开关
	public int testGame;
	@Tag(12)
	// 庄家控制
	public String testZhuang;
	@Tag(13)
	// 翻中的牌(翻牌鬼)
	public String fanChose;
	@Tag(14)
	// 指定发牌
	public String testPoker;
	@Tag(15)
	// 控制底牌(up最开始摸的牌，down最后几张摸的牌)（同一房间，一次只能设置一种）
	public String testMoPoker;
	@Tag(16)
	// 房间剩余底牌数量
	public int freePokerNum;
	@Tag(17)
	// 胡牌提示
	public int huPrompt;
	@Tag(18)
	// 下叫提示
	public int jiaoPrompt;
	@Tag(19)
	// 翻鸡
	public int fanJi;

 }
