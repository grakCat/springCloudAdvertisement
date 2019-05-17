package com.stude.springbootdemo.mjtest;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 2018/8/24.
 *
 * @author grayCat
 * @since 1.0
 */
public class MJConstant {

    /**
     * 打牌（摸牌）信息
     */
    public static class OutPokerType{
        /*当前用户*/
        public long playerId;
        /*牌*/
        public byte poker;
        /*牌id*/
        public int pokerId;
        /*true打牌，false摸牌*/
        public boolean isOut;
        /*特殊胡牌信息*/
//        public Constant.Extreme extreme;
        /*杠上花，杠上炮，抢杠胡，抢提胡*/
//        public Constant.Special special;
        /*自抢杠,抢杠胡,抢提胡，抢的人点过使用*/
//        public Constant.Special qiangGang;
        /*自抢杠被抢标记*/
        public boolean beiQiangZQG;
        /*杠上花，杠上炮（杠牌类型）*/
//        public Constant.GameGang gangType;
        /*抢杠的是癞子牌*/
        public int baLaiPokerId;
        /*断杠每轮点击记录*/
        public volatile List<DGClick> buttonClick = new ArrayList<>();
        /*按钮点亮优先级信息*/
        public volatile List<ShowButtonComapre> buttonPriority = new ArrayList<>();
        /*不计算某些特殊牌型（提示的时候，实际结算才添加）*/
        public boolean buJinGouPao;
    }

    /**
     * 鬼牌信息
     */
    public static class GuiPoker{
        /*是否有鬼牌*/
        public boolean isHave;
        /*鬼牌类型*/
//        public Constant.GuiChose guiChose;
        /*鬼牌数量*/
        public byte guiNumber;
        /*鬼牌(实际鬼牌属性，并不能代表实际鬼牌数量)*/
        public int[] guiPoker;
        /*是否含有本金*/
        public boolean isBenJin;
        /*翻牌鬼翻中的牌（本金牌）*/
        public byte fanChose;
    }

    /**
     * 胡牌玩家信息
     */
    public static class HuPlayer {
        /*胡牌类型*/
//        public Constant.HuType huType;
        /*胡牌顺序*/
        public int huNum;
        /*胡的牌*/
        public byte huPoker;
        /*胡的牌*/
        public int huPokerId;
        /*Id*/
        public long playerId;
        /*胡牌分数*/
        public int huScore;
        /*胡牌翻数*/
        public int huFan;
        /*提牌*/
        public byte tiPoker;
        /*胡牌名堂*/
//        public List<Constant.MingTang> huMingTang = new ArrayList<>();
        /*点炮用户Id*/
        public long paoPlayerId;
        /*一炮多响*/
        public boolean duoXiang;
        /*抢杠胡/抢提胡*/
//        public Constant.Special special;
        /*自摸时，未胡用户（需要扣分的用户）*/
        public List<Long> beiMoPlayer;
    }

    /**
     * 每一个优先级保存信息
     */
    public static class ShowButtonComapre implements Comparable<ShowButtonComapre>{
        /*是否已经执行(用于，已经同意执行，但是还在等待其他玩家选择使用)*/
        public boolean isRun;
        /*执行任务类型*/
//        public Constant.AlertChose type;
//        /*执行状态*/
//        public Constant.ChoseType chose;
//        /*杠牌类型*/
//        public Constant.GameGang gangType;
        /*优先级*/
        public int priority;
        /*执行玩家Id*/
        public long playerId;
        /*执行的牌*/
        public int runPokerId;
        /*是否使用癞子牌*/
        public boolean isUseLaiZhi;
        /*癞子牌使用数量*/
        public int laiNumber;

        @Override
        public int compareTo(ShowButtonComapre next) {
            //执行状态（等待，执行，拒绝）
            int chose = 0;
            //已有优先级
            if(chose == 0){
                chose = this.priority - next.priority;
            }
            //执行类型（碰，杠，胡，飞）
            if(chose == 0){
                chose = 0;
                //是否使用癞子牌
                if(chose == 0){
//                    chose = GameUtil.useLaiZhi(next.isUseLaiZhi) - GameUtil.useLaiZhi(this.isUseLaiZhi);
                }
            }
            return chose;
        }
    }

    /**
     * 测试需要数据
     */
    public static class TestConfig{
        /*测试开关*/
        public boolean test ;
        /*玩家凑牌*/
        public String testPoker = "";
        /*翻牌鬼，翻中的牌*/
        public String fanChose = "";
        /*控制庄家*/
        public String zhuangId ="";
        /*控制底牌（由前向后添加）*/
        public String updateFreePoker ="";
        /*房间剩余底牌*/
        public int freePoker;
    }

    /**
     * 爆牌控制
     */
    public static class MJBaoPai {
        /*动态爆牌分数（封顶分数-2番）这个值大于零，爆牌分数以此为准
        * 杀爆通赔也会修改这个参数*/
        public int updateScore;
        /*一爆通爆*/
        public boolean tongBao;
        /*天胡算爆牌*/
        public boolean tianBao;
        /*关联爆牌*/
        public boolean relevance;
        /*杀爆通赔（多个用户爆牌，每多一个爆牌分数+1番）*/
        public boolean shaBao;
        /*爆牌放炮只胡一次*/
        public boolean onlyOne;
        /*爆牌升翻胡（整个回合）*/
        public boolean upgrade;
        /*天胡不算爆牌（开局，其他用户爆牌，天胡玩家不带爆牌名堂及分数）*/
        public boolean tianNoBao;
        /*顶爆（顶爆是，AB两人爆牌，两人之间互相放炮或者自摸，爆牌番数会X2）*/
        public boolean dingBao;
        /*爆牌杠属性（不变叫，变少）*/
//        public Constant.BaoGangType baoGangType;
    }

    /**
     * 多名堂组合特殊设置（提前找到一部分名堂，被占用后面就不在添加）
     */
    public static class BeforMingTang {
        /*清一色*/
        public boolean oneColor;
        /*爆牌*/
        public boolean baoPai;
        /*杠上花*/
        public boolean gsh;
        /*杠上炮*/
        public boolean gsp;
        /*抢杠胡*/
        public boolean qgh;
        /*抢提胡*/
        public boolean qth;
        /*归（会因为其他名堂，归数量减少）*/
        public byte guiNum;
    }

    /**
     * 特殊操作，提示执行原因
     */
    public static class PromptReason {
        /*原因*/
//        public Constant.Reason reason;
        /*执行用户*/
        public long playerId;
    }

    /**
     * 特殊名堂
     */
    public static class SpecialMingTang {
        /*连庄次数*/
        public int lianZhuangNum;
    }

    /**
     * 鸡属性
     */
    public static class JiScore{
        /*是鸡*/
        public boolean isJi;
        /*鸡类型*/
//        public Constant.HuType jiType;
        /*鸡分数*/
        public int score;
    }

    /**
     * 断杠点亮
     */
    public static class DGClick{
        /*最近点击标记*/
        public boolean presentClick;
        /*点亮用户id*/
        public long playerId;
        /*用户点击*/
//        public Constant.ChoseType chose = Constant.ChoseType.WAIT;

        public DGClick(long playerId){
            this.playerId = playerId;
        }
    }
}
