package com.stude.springbootdemo.mjtest;


import java.util.ArrayList;
import java.util.List;

/**
 * Created on 2018/8/26.
 * 检查手牌是否能胡
 * 如果能胡，返回变化的可能性
 * @author grayCat
 * @since 1.0
 */
public class HuBian {

    /*癞子数量*/
    protected int laiNumber;
    /*癞子牌*/
    protected List<Integer> laiPoker;
    /*手牌数量总计*/
    protected int pokerNumber;
    /*计算手牌*/
    protected int[] handPoker;
    /*手牌包含的颜色*/
    protected List<Integer> colors;
    /*将牌信息*/
    protected List<JiangInfo> jiangPoker;
    /*胡牌可能性*/
    protected List<TingPossible> tingPossibles = new ArrayList<>();
    /*一种将牌找到的*/
    protected List<TingPossible> tingPossibleOne = new ArrayList<>();
    /*鬼牌信息（听用）*/
    public MJConstant.GuiPoker guiMessage = new MJConstant.GuiPoker();
    /*碰杠吃的牌*/
    public List<AlertType> chunk = new ArrayList<>();
    public int handPokerSize = 4;

    protected HuInfo huInfo = new HuInfo();

    public HuBian(){

    }

    public static class AlertType{
        /*吃，碰，杠,投类型*/
        public int[] pokerIds;
        /*执行的牌*/
        public int corePoker;
        public int corePokerId;
        /*巴杠癞子牌*/
        public int baLaiPokerId;
        /*杠牌类型*/
        /*谁打的*/
        public long outPlayerId;
        /*特殊执行原因(过巴杠)*/
        /*使用癞子牌数量*/
        public int laiNum;
    }

    /**
     * 胡牌计算信息
     */
    public class HuInfo{
        //是否胡牌
        public boolean isHu;
        //胡牌可能性组合
        public List<int[]> possibility = new ArrayList<>();
    }

    /**
     * 癞子牌变牌信息
     */
    public class TingPossible{
        //剩余未变化癞子数量
        public int laiNumber;
        //当前变化下的手牌
        public int[] handPoker;
        //癞子已经变好的牌
        public List<Integer> changes = new ArrayList<>();

        public TingPossible (int laiNumber,int[] handPoker,List<Integer> changes){
            this.laiNumber = laiNumber;
            this.handPoker = handPoker;
            this.changes.addAll(changes);
        }
    }

    /**
     * 将牌信息
     */
    public class JiangInfo{
        /*将牌*/
        int jiangOne;
        int jiangTwo;
        /*其中癞子数量*/
        int tingNum;
    }

    /**
     * 计算胡牌变化可能性
     * 花猪，定缺等特殊在外部判断,这个类不考虑
     * @param onlyHu 仅判断胡牌
     * @return
     */
    public HuInfo huInfo(int[] handPoker,boolean onlyHu,MJConstant.GuiPoker guiMessage){
        this.handPoker = handPoker.clone();
        this.guiMessage = guiMessage;
        this.handPokerSize = handPoker.length;
        this.pokerNumber = GameUtil.pokerNumber(handPoker);
        this.laiPoker = GameUtil.removeLaiPoker(this.handPoker,guiMessage);
        this.laiNumber =  this.laiPoker.size();
        this.colors = GameUtil.includeColor(this.handPoker,chunk);

        //花猪，定缺检查
        if(!GameUtil.checkQueYiMen(handPoker,true,guiMessage,chunk)){
            return huInfo;
        }

        //小七对检查
        qiXiaoDui(onlyHu);
        if(onlyHu && huInfo.isHu){
            return huInfo;
        }

        //将牌信息
        getJiangPoker();
        //普通胡牌
        for(JiangInfo jiang : jiangPoker){
            //手牌移除选中将牌
            int haveNumber = laiNumber - jiang.tingNum;
            int[] handIds = this.handPoker.clone();
            if(handIds[(int)jiang.jiangOne] > 0){
                handIds[(int)jiang.jiangOne] --;
            }
            if(handIds[(int)jiang.jiangTwo] > 0){
                handIds[(int)jiang.jiangTwo] --;
            }

            boolean isHu = needTimgNumber(handIds,haveNumber);
            if(isHu){
                huInfo.isHu = true;
                if(onlyHu){
                    return huInfo;
                }

                //查找癞子牌变化（递归）
                tingPossibleOne.clear();
                List<TingPossible> allPossible = new ArrayList<>();
                TingPossible possible = new TingPossible((int) haveNumber,handIds.clone(),new ArrayList<>());
                allPossible.add(possible);
                searchPossible(allPossible);
                //添加将牌变化
                addPossible(jiang);
            }else {
                continue;
            }
        }

        //癞子不变，查看是否能胡
        addNoChange();
        //返回值添加非癞子牌
        addPuTongPoker();
        return huInfo;
    }

    /**
     * 癞子不变，查看是否能胡(小七对)
     * @return
     */
    protected void addNoChangeQXD(int laiNumber){
        MJConstant.GuiPoker guiMessage = this.guiMessage;
        if(guiMessage.isHave  && laiPoker.size() > 0){
            int[] handPoker = this.handPoker.clone();
            for(int lzPoker : laiPoker){
                handPoker[lzPoker] ++;
            }
            //花猪，定缺检查
            if(!GameUtil.checkQueYiMen(handPoker,true,guiMessage,chunk)){
                return ;
            }
            if(laiNumber == this.laiNumber){
                //对子数
                int duiZhiNumber = 0;
                //查找已有的牌组合
                for(int i = 1; i < handPokerSize; i ++){
                    int number = handPoker[(int)i];
                    if(number > 0){
                        if(number % 2 == 0){
                            duiZhiNumber += number / 2;
                        }
                    }
                }
                if(duiZhiNumber == 7){
                    TingPossible lzbbPossible = new TingPossible((int) 0,handPoker,new ArrayList<>(laiPoker));
                    tingPossibles.add(lzbbPossible);
                }
            }
        }
    }

    /**
     * 癞子不变，查看是否能胡
     */
    private void addNoChange(){
        MJConstant.GuiPoker guiMessage = this.guiMessage;
        if(guiMessage.isHave  && laiPoker.size() > 0){
            int[] handPoker = this.handPoker.clone();
            for(int lzPoker : laiPoker){
                handPoker[lzPoker] ++;
            }
            //花猪，定缺检查
            if(!GameUtil.checkQueYiMen(handPoker,true,guiMessage,chunk)){
                return ;
            }
            if(coomHu(handPoker)){
                TingPossible lzbbPossible = new TingPossible((int) 0,handPoker,new ArrayList<>(laiPoker));
                tingPossibles.add(lzbbPossible);
            }
        }
    }

    /**
     * 包含将牌，基础检查胡牌
     * @return
     */
    private boolean coomHu(int[] handPoker){
        //获取到可用的将牌
        List<JiangInfo> jiangPoker = new ArrayList<>();
        for(int i = 1; i < handPokerSize; i++){
            int number = handPoker[(int)i];
            JiangInfo jiang = new JiangInfo();
            if(number > 1){
                jiang.jiangOne = i;
                jiang.jiangTwo = i;
                jiangPoker.add(jiang);
            }
        }

        //查看某种将牌下是否可胡
        for(JiangInfo jiangInfo : jiangPoker){
            int[] huPoker = handPoker.clone();
            huPoker[(int)jiangInfo.jiangOne] --;
            huPoker[(int)jiangInfo.jiangTwo] --;
            if(needTimgNumber(huPoker,0)){
                return true;
            }
        }
        return false;
    }

    /**
     * 最后将牌变化添加
     */
    private void addPossible(JiangInfo jiang){
        if(jiang.tingNum == 1){
            addJiangOne(jiang);
        }else if(jiang.tingNum == 2){
            addJiangTwo();
        }else {
            tingPossibles.addAll(tingPossibleOne);
        }
    }

    /**
     * 2张牌都是癞子
     */
    private void addJiangTwo(){
        //碰牌添加
        int[] handPoker = this.handPoker.clone();
        for(AlertType alertType : chunk){
            handPoker[(int)alertType.corePoker] ++;
        }
        //变牌添加
        for(TingPossible possibl : tingPossibleOne){
            int[] pokers = handPoker.clone();
            for(int bian : possibl.changes){
                pokers[bian] ++;
            }

            for(int poker = 1; poker < pokers.length;poker ++){
                if(pokers[(int)poker] > 0){
                    List<Integer> changes = new ArrayList<>(possibl.changes);
                    changes.add(poker);
                    changes.add(poker);
                    tingPossibles.add(new TingPossible((int)0,new int[0],changes));
                }
            }
        }
    }

    private void addJiangOne(JiangInfo jiang){
        int poker = 0;
        if(jiang.jiangOne > 0){
            poker = jiang.jiangOne;
        }else if(jiang.jiangTwo > 0) {
            poker = jiang.jiangTwo;
        }
        for(TingPossible possibl : tingPossibleOne){
            possibl.changes.add(poker);
            tingPossibles.add(possibl);
        }
    }

    /**
     * 返回值添加非癞子牌
     */
    private void addPuTongPoker(){
        for(TingPossible possible : tingPossibles){
            int[] handPoker = this.handPoker.clone();
            for(int poker : possible.changes){
                handPoker[poker] ++;
            }
            huInfo.possibility.add(handPoker);
        }
    }

    /**
     * 计算癞子可能性core类
     * @param allPossible
     * @return
     */
    private void searchPossible(List<TingPossible> allPossible){
        List<TingPossible> presentPossible = new ArrayList<>();
        for(TingPossible possible : allPossible){
            //查找一组变化
            int[] handPoker = possible.handPoker;
            int position = 0;
            for(int i = 1; i < handPokerSize; i ++){
                int num = handPoker[i];
                if(num > 0){
                    position = i;
                    break;
                }
            }
            //靠近的连牌
            int core = handPoker[position];
            if(core > 2){
                handPoker[position] -= 3;
                presentPossible.add(possible);
            }else if(core > 0){
                //当前选中最前面的牌，只有2种可能，刻子或者顺子
                addKeZhi(possible,position,presentPossible);
                //123
                addShunZhi(possible,position,presentPossible,-1,-2);
                //234
                addShunZhi(possible,position,presentPossible,-1,1);
                //345
                addShunZhi(possible,position,presentPossible,1,2);
            }else {
                if(possible.laiNumber == 0){
                    //手牌组合完，说明当前牌型可以胡
                    tingPossibleOne.add(possible);
                }else {
                    //说明已经找完，考虑剩余全是癞子牌
                    addDuoTing(possible);
                }
            }
        }

        //继续下一级递归
        if(presentPossible.size() > 0){
            searchPossible(presentPossible);
        }
    }

    /**
     * 剩余全癞子，变化添加
     * 1.已有牌，（排除已经组合4张牌的）
     * 2.保证至少4张不同牌
     * 3.对已有牌4种可能性组合
     * @param possible
     */
    private void addDuoTing(TingPossible possible){
        int[] havePoker = this.handPoker.clone();
        //添加碰的牌
        for(AlertType alert : chunk){

        }
        //牌过少添加相同颜色的牌
        addSameColor(havePoker);
        List<TingPossible> possibles = new ArrayList<>();
        possibles.add(possible);
        addDuoTingRun(possibles,havePoker);
    }

    /**
     * 牌过少添加相同颜色的牌
     * @param possibles
     * @param havePoker
     */
    private void addDuoTingRun(List<TingPossible> possibles,int[] havePoker){
        List<TingPossible> possibleBase = new ArrayList<>();

       for(TingPossible possible : possibles){
           if(possible.laiNumber > 2){
               for(int i = 1; i < handPokerSize; i++){
                   int num = havePoker[i];
                   if(num > 0){
                       addMostType((int) i,possible,possibleBase);
                   }
               }
           }else if(possible.laiNumber == 0){
               tingPossibleOne.add(possible);
           }
       }

        if(possibleBase.size() > 0){
            addDuoTingRun(possibleBase,havePoker);
        }
    }

    /**
     * 三张癞子牌变化
     * 123，345，234，333
     */
    private void addMostType(int corePoker,TingPossible possible,List<TingPossible> possibleBase){
        int up2 = realNextPoker(corePoker + 2);
        int up1 = realNextPoker(corePoker + 1);
        int down1 = realNextPoker(corePoker - 1);
        int down2 = realNextPoker(corePoker - 2);

        if(down2 > 0 && down1 > 0){
            possibleBase.add(addTingBian(corePoker,down1,down2,possible));
        }
        if(up1 > 0 && down1 > 0){
            possibleBase.add(addTingBian(corePoker,down1,up1,possible));
        }
        if(up1 > 0 && up2 > 0){
            possibleBase.add(addTingBian(corePoker,up2,up1,possible));
        }
        possibleBase.add(addTingBian(corePoker,corePoker,corePoker,possible));
    }

    private TingPossible addTingBian(int corePoker,int upPoker,int downPoker,TingPossible possible){
        int laiNumber = possible.laiNumber;
        int[] handPoker = possible.handPoker.clone();
        List<Integer> changes = new ArrayList<>(possible.changes);
        //信息修改
        laiNumber -= 3;
        changes.add(corePoker);
        changes.add(upPoker);
        changes.add(downPoker);
        return new TingPossible(laiNumber,handPoker,changes);
    }

    private int realNextPoker(int position){
        if(position > 0 && position < handPoker.length){
            return (int) position;
        }
        return 0;
    }

    /**
     * 牌过少添加相同颜色的牌
     */
    private void addSameColor(int[] havePoker){
        int needNum = 4;
        for(int i = 1; i < handPokerSize; i++){
            int num = havePoker[i];
            if(num > 0){
                needNum --;
            }
            if(needNum <= 0){
                return;
            }
        }

        //优先添加本金牌
        MJConstant.GuiPoker guiMessage = this.guiMessage;
        if(guiMessage.isBenJin){
            int num = havePoker[(int)guiMessage.fanChose];
            if(num <= 0){
                havePoker[(int)guiMessage.fanChose] ++;
                needNum --;
            }
        }

        //用相同颜色补位
        if(needNum > 0){
            for(int color : colors){
                int value =  color * 10;
                for(int i = 1;i <= 9; i++){
                    value += i;
                    int num = havePoker[value];
                    if(num <= 0){
                        havePoker[value] ++;
                        needNum --;
                    }
                    if(needNum <= 0){
                        return;
                    }
                }
            }
        }
    }

    /**
     * 刻子处理
     * @param possible
     * @param position
     * @param presentPossible
     */
    private void addKeZhi(TingPossible possible,int position,List<TingPossible> presentPossible){
        int core = possible.handPoker[position];
        int needTing = 3 - core;
        if(possible.laiNumber >= needTing){
            int[] handKeZhi = possible.handPoker.clone();
            List<Integer> changes = new ArrayList<>(possible.changes);
            //清除用到的牌
            handKeZhi[position] = 0;
            for(int i = 0;i < needTing;i++){
                changes.add((int)position);
            }
            TingPossible nextPossible = new TingPossible((int) (possible.laiNumber - needTing),handKeZhi,changes);
            //当前癞子牌数量是否够胡牌
            if(needTimgNumber(nextPossible.handPoker,nextPossible.laiNumber)){
                presentPossible.add(nextPossible);
            }
        }
    }

    /**
     * 检查位置合理性
     * @return
     */
    private boolean checkPosition(int update,int position,TingPossible possible){
        if(position + update >0 && position + update < possible.handPoker.length){
            return true;
        }
        return false;
    }

    /**
     * 顺子处理
     * @param possible
     * @param position
     * @param presentPossible
     */
    private void addShunZhi(TingPossible possible,int position,List<TingPossible> presentPossible,int oneUpdate,int twoUpdate){
        //检查位置参数是否合理
        if(!checkPosition(oneUpdate,position,possible) || !checkPosition(twoUpdate,position,possible)){
            return;
        }
        //准备保存的变化参数
        int laiNumber = possible.laiNumber;
        int[] handShunZhi = possible.handPoker.clone();
        List<Integer> changes = new ArrayList<>(possible.changes);
        //连着的三张牌
        int core = handShunZhi[position];
        int one = handShunZhi[oneUpdate + position];
        int two = handShunZhi[twoUpdate + position];
        //检查癞子牌是否足够
        int needTing = one > 0 ? 0 : 1;
        needTing = two > 0 ? needTing : needTing + 1;
        if(needTing > laiNumber){
            return;
        }

        //修改变化参数
        handShunZhi[position] --;
        if(one > 0){
            handShunZhi[position + oneUpdate] --;
        }else {
            laiNumber --;
            changes.add((int)(position + oneUpdate));
        }
        if(two > 0){
            handShunZhi[position + twoUpdate] --;
        }else {
            laiNumber --;
            changes.add((int)(position + twoUpdate));
        }

        TingPossible nextPossible = new TingPossible(laiNumber,handShunZhi,changes);
        //当前癞子牌数量是否够胡牌
        if(needTimgNumber(nextPossible.handPoker,nextPossible.laiNumber)){
            presentPossible.add(nextPossible);
        }
    }

    /**
     * 将牌组合
     * @return
     */
    private void getJiangPoker(){
        List<JiangInfo> jiangPoker = new ArrayList<>();
        for(int i = 1; i < handPokerSize; i++){
            int number = handPoker[(int)i];
            JiangInfo jiang = new JiangInfo();
            if(number == 1 && laiNumber > 0){
                jiang.jiangOne = i;
                jiang.tingNum = 1;
                jiangPoker.add(jiang);
            }else if(number > 1){
                jiang.jiangOne = i;
                jiang.jiangTwo = i;
                jiangPoker.add(jiang);
            }
        }

        if(laiNumber > 1){
            JiangInfo jiang = new JiangInfo();
            jiang.tingNum = 2;
            jiangPoker.add(jiang);
        }
        this.jiangPoker = jiangPoker;
    }

    /**
     * 小七对检查
     */
    protected void qiXiaoDui(boolean onlyHu){
        if(pokerNumber == 14){
            //对子数
            int duiZhiNumber = 0;
            //癞子数量
            int laiNumber = this.laiNumber;
            TingPossible possible = new TingPossible((int) 0,null,new ArrayList<>());

            //查找已有的牌组合
            for(int i = 1; i < handPokerSize; i ++){
                int number = handPoker[(int)i];
                if(number > 0){
                    boolean dan = number % 2 == 1;//单双判断
                    double duoDui = Math.ceil((double) number/2);
                    duiZhiNumber += duoDui;
                    if(dan){
                        laiNumber --;
                        //用一张癞子牌固定变成该张单牌
                        possible.changes.add(i);
                    }
                }
            }

            if(laiNumber > 0){
                duiZhiNumber += laiNumber / 2;
            }
            if(duiZhiNumber == 7){
                huInfo.isHu = true;
                if(!onlyHu){
                    //癞子不变，查看是否能胡
                    addNoChangeQXD(laiNumber);
                    //查询多余癞子牌组合
                    moreTing(possible,laiNumber);
                    tingPossibles.add(possible);
                }
            }
        }
    }

    /**
     * 普通牌添加完，还有多余癞子牌
     * @param possible
     * @param laiNumber
     * @return
     */
    protected void moreTing(TingPossible possible,int laiNumber){
        //优先考虑本金牌
        MJConstant.GuiPoker guiMessage = this.guiMessage;
        if(laiNumber > 1){
            laiNumber -= addMoreTing(possible,guiMessage.fanChose);
        }

        //变已有的牌，数量小于等于2的牌
        if(laiNumber > 1){
            for(int i = 1; i < handPokerSize; i ++){
                if(i != guiMessage.fanChose && laiNumber > 1){
                    laiNumber -= addMoreTing(possible,i);
                }
            }
        }

        //癞子牌仍然有剩余,变已有颜色的牌（排除已有的牌）
        if(laiNumber > 1){
            for(int pokerValue = 1; pokerValue < handPokerSize; pokerValue ++){
                if(handPoker[pokerValue] == 0){
                    possible.changes.add(pokerValue);
                    possible.changes.add(pokerValue);
                    laiNumber -= 2;
                    if(laiNumber > 1){
                        possible.changes.add(pokerValue);
                        possible.changes.add(pokerValue);
                        laiNumber -= 2;
                    }
                }
            }
        }
    }

    protected int addMoreTing(TingPossible possible,int position){
        int num = handPoker[(int)position];
        //本金数只在这个范围考虑添加
        if(num == 1 || num == 2){
            possible.changes.add(position);
            possible.changes.add(position);
            return 2;
        }
        return 0;
    }

    /**
     * 检查至少需要几张癞子牌才能胡
     * 检查到当前已超出，已拥有癞子数量，停止检查返回false
     * @param handPoker 手牌
     * @return
     */
    public boolean needTimgNumber(int[] handPoker,int haveNumber){
        int needCount = 0;
        int[] handPokerCopy = handPoker.clone();

        for(int i = 1;i < handPoker.length;i++){
            //四种可能性判断
            for(int count = 0;count < 4;count ++){
                int num = handPokerCopy[i];
                int numUpOne = i >= (handPoker.length -1) ? 0 : handPokerCopy[i + 1];
                int numUpTwo = i >= (handPoker.length -2) ? 0 : handPokerCopy[i + 2];
                //每10个数是一组牌，增加的不能超过一组牌
                int value = i % 10;
                if(value == 8){
                    numUpTwo = 0;
                }else if(value == 9){
                    numUpOne = 0;
                    numUpTwo = 0;
                }
                if(num > 0){
                    //1:优先组合不需要听用的组合
                    if(num > 2){
                        handPokerCopy[i] -= 3;
                    }else if(num == 2 && numUpOne == 1 && numUpTwo >= 1 && chose(handPokerCopy)){
                        //特殊模式211，只能取2
                        handPokerCopy[i] = 0;
                        needCount += 1;
                    }else if(numUpOne > 0 && numUpTwo > 0){
                        //连着三张牌都有
                        handPokerCopy[i] --;
                        handPokerCopy[i + 1] --;
                        handPokerCopy[i + 2] --;
                    }else if(num > 1){
                        handPokerCopy[i] = 0;
                        needCount += 1;
                    }else if(numUpOne > 0){
                        handPokerCopy[i] --;
                        handPokerCopy[i + 1] --;
                        needCount += 1;
                    }else if(numUpTwo > 0){
                        handPokerCopy[i] --;
                        handPokerCopy[i + 2] --;
                        needCount += 1;
                    }else {
                        handPokerCopy[i] --;
                        needCount += 2;
                    }
                    if(needCount > haveNumber){
                        return false;
                    }
                }
            }
        }
        return haveNumber >= needCount;
    }

    private boolean chose(int[] handPokerCopy){
        int jianChose = needTimgNumber(handPokerCopy,true);
        int coomChose = needTimgNumber(handPokerCopy,false);
        if(jianChose <= coomChose){
            return true;
        }
        return false;
    }

    /**
     * 检查至少需要几张癞子牌才能胡
     * 检查到当前已超出，已拥有癞子数量，停止检查返回false
     * @param handPoker 手牌
     * @return
     */
    public int needTimgNumber(int[] handPoker,boolean chose){
        int needCount = 0;
        boolean use = true;
        int[] handPokerCopy = handPoker.clone();

        for(int i = 1;i < handPoker.length;i++){
            //四种可能性判断
            for(int count = 0;count < 4;count ++){
                int num = handPokerCopy[i];
                int numUpOne = i >= (handPoker.length -1) ? 0 : handPokerCopy[i + 1];
                int numUpTwo = i >= (handPoker.length -2) ? 0 : handPokerCopy[i + 2];
                //每10个数是一组牌，增加的不能超过一组牌
                int value = i % 10;
                if(value == 8){
                    numUpTwo = 0;
                }else if(value == 9){
                    numUpOne = 0;
                    numUpTwo = 0;
                }
                if(num > 0){
                    //1:优先组合不需要听用的组合
                    if(num > 2){
                        handPokerCopy[i] -= 3;
                    }else if(num == 2 && numUpOne == 1 && numUpTwo >= 1 && (use ? chose : chose(handPokerCopy))){
                        use = false;
                        //特殊模式211，只能取2
                        handPokerCopy[i] = 0;
                        needCount += 1;
                    }else if(numUpOne > 0 && numUpTwo > 0){
                        //连着三张牌都有
                        handPokerCopy[i] --;
                        handPokerCopy[i + 1] --;
                        handPokerCopy[i + 2] --;
                    }else if(num > 1){
                        handPokerCopy[i] = 0;
                        needCount += 1;
                    }else if(numUpOne > 0){
                        handPokerCopy[i] --;
                        handPokerCopy[i + 1] --;
                        needCount += 1;
                    }else if(numUpTwo > 0){
                        handPokerCopy[i] --;
                        handPokerCopy[i + 2] --;
                        needCount += 1;
                    }else {
                        handPokerCopy[i] --;
                        needCount += 2;
                    }
                }
            }
        }
        return needCount;
    }

    /**
     * 孤牌判断
     * @param handPokerCopy
     * @param core
     * @return
     */
    private boolean checkGu(int[] handPokerCopy,int core){
        for(int i = -2 ; i<= 2;i++){
            if(i == 0){
                continue;
            }
            int numDown = handPokerCopy[realNextPoker(core + i)];
            if(numDown > 0){
                return false;
            }
        }
        return true;
    }
}
