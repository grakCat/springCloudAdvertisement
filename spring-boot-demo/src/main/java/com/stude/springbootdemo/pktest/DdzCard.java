package com.stude.springbootdemo.pktest;

/**
 * Created on 2019/5/17.
 *
 * @author hy
 * @since 1.0
 */
public enum DdzCard {

    /**
     * 方块 A - K
     */
    FANG_KUAI_A(1,14, CardSuit.FANG_KUAI),
    FANG_KUAI_ER(2,15, CardSuit.FANG_KUAI),
    FANG_KUAI_SAN(3,3, CardSuit.FANG_KUAI),
    FANG_KUAI_SI(4,4, CardSuit.FANG_KUAI),
    FANG_KUAI_WU(5,5, CardSuit.FANG_KUAI),
    FANG_KUAI_LIU(6,6, CardSuit.FANG_KUAI),
    FANG_KUAI_QI(7,7, CardSuit.FANG_KUAI),
    FANG_KUAI_BA(8,8, CardSuit.FANG_KUAI),
    FANG_KUAI_JIU(9,9, CardSuit.FANG_KUAI),
    FANG_KUAI_SHI(10,10, CardSuit.FANG_KUAI),
    FANG_KUAI_J(11,11, CardSuit.FANG_KUAI),
    FANG_KUAI_Q(12,12, CardSuit.FANG_KUAI),
    FANG_KUAI_K(13,13, CardSuit.FANG_KUAI),

    /**
     * 梅花 A - K
     */
    MEI_HUA_A(14,14, CardSuit.MEI_HUA),
    MEI_HUA_ER(15,15, CardSuit.MEI_HUA),
    MEI_HUA_SAN(16,3, CardSuit.MEI_HUA),
    MEI_HUA_SI(17,4, CardSuit.MEI_HUA),
    MEI_HUA_WU(18,5, CardSuit.MEI_HUA),
    MEI_HUA_LIU(19,6, CardSuit.MEI_HUA),
    MEI_HUA_QI(20,7, CardSuit.MEI_HUA),
    MEI_HUA_BA(21,8, CardSuit.MEI_HUA),
    MEI_HUA_JIU(22,9, CardSuit.MEI_HUA),
    MEI_HUA_SHI(23,10, CardSuit.MEI_HUA),
    MEI_HUA_J(24,11, CardSuit.MEI_HUA),
    MEI_HUA_Q(25,12, CardSuit.MEI_HUA),
    MEI_HUA_K(26,13, CardSuit.MEI_HUA),

    /**
     * 红桃 A - K
     */
    HONG_TAO_A(27,14, CardSuit.HONG_TAO),
    HONG_TAO_ER(28,15, CardSuit.HONG_TAO),
    HONG_TAO_SAN(29,3, CardSuit.HONG_TAO),
    HONG_TAO_SI(30,4, CardSuit.HONG_TAO),
    HONG_TAO_WU(31,5, CardSuit.HONG_TAO),
    HONG_TAO_LIU(32,6, CardSuit.HONG_TAO),
    HONG_TAO_QI(33,7, CardSuit.HONG_TAO),
    HONG_TAO_BA(34,8, CardSuit.HONG_TAO),
    HONG_TAO_JIU(35,9, CardSuit.HONG_TAO),
    HONG_TAO_SHI(36,10, CardSuit.HONG_TAO),
    HONG_TAO_J(37,11, CardSuit.HONG_TAO),
    HONG_TAO_Q(38,12, CardSuit.HONG_TAO),
    HONG_TAO_K(39,13, CardSuit.HONG_TAO),

    //黑桃 A - K
    HEI_TAO_A(40,14, CardSuit.HEI_TAO),
    HEI_TAO_ER(41,15, CardSuit.HEI_TAO),
    HEI_TAO_SAN(42,3, CardSuit.HEI_TAO),
    HEI_TAO_SI(43,4, CardSuit.HEI_TAO),
    HEI_TAO_WU(44,5, CardSuit.HEI_TAO),
    HEI_TAO_LIU(45,6, CardSuit.HEI_TAO),
    HEI_TAO_QI(46,7, CardSuit.HEI_TAO),
    HEI_TAO_BA(47,8, CardSuit.HEI_TAO),
    HEI_TAO_JIU(48,9, CardSuit.HEI_TAO),
    HEI_TAO_SHI(49,10, CardSuit.HEI_TAO),
    HEI_TAO_J(50,11, CardSuit.HEI_TAO),
    HEI_TAO_Q(51,12, CardSuit.HEI_TAO),
    HEI_TAO_K(52,13, CardSuit.HEI_TAO),

    /**
     * 小王,大王
     */
    XIAO_WANG(53,16, null),
    DA_WANG(54,17, null);

    public final int id;
    /**
     * 3-k(3-13),A:14,2:15,王:16
     * */
    public final int num;
    /**
     * 牌的类型(0-5)
     * 0:方块,1:梅花,2:红桃,3:黑桃,4:小王,5:大王
     * */
    public final CardSuit type;

    private DdzCard(int id, int num, CardSuit type) {
        this.id = id;
        this.num = num;
        this.type = type;
    }

    /**
     * @Description:根据牌的id获取牌
     * @param cardId 1-54
     * @return
     */
    public static DdzCard card(int cardId) {
        if (cardId <= 0 || cardId > values().length) {
            throw new IllegalArgumentException("carId必须在[1,54]范围内");
        }
        return values()[cardId-1];
    }


    public static enum CardSuit {
        FANG_KUAI, MEI_HUA, HONG_TAO, HEI_TAO;
    }
}
