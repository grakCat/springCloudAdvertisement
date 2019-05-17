package com.stude.springbootdemo.mjtest;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 公共方法调用
 */
public class GameUtil {

    /**
     * 指定范围随机数
     * @return
     */
    public static int randomNum(int start, int end){
        return new Random().nextInt(end + 1 - start) + start;
    }

    /**
     * 牌id转换用于计算的int
     */
    public static int getPokerInteger(int id){
        int pokerInteger = 0;
        if(id <= 108){
            //条筒万
            pokerInteger += (Math.ceil((double)id / 36) - 1) * 10;
            int value = (int) (id % 9);
            pokerInteger += value == 0 ? 9 : value;
        }else if(id <= 124){
            //风牌
            pokerInteger += 30;
            int value = (int) ((id - 108) % 4);
            pokerInteger += value == 0 ? 4 : value;
        }else if(id <= 136){
            //元牌
            pokerInteger += 40;
            int value = (int) ((id - 124) % 3);
            pokerInteger += value == 0 ? 3 : value;
        }
        return pokerInteger;
    }

    /**
     * 牌id转换用于计算的byte
     */
    public static int getPokerByte(int id){
        byte pokerByte = 0;
        if(id <= 108){
            //条筒万
            pokerByte += (Math.ceil((double)id / 36) - 1) * 10;
            byte value = (byte) (id % 9);
            pokerByte += value == 0 ? 9 : value;
        }else if(id <= 124){
            //风牌
            pokerByte += 30;
            byte value = (byte) ((id - 108) % 4);
            pokerByte += value == 0 ? 4 : value;
        }else if(id <= 136){
            //元牌
            pokerByte += 40;
            byte value = (byte) ((id - 124) % 3);
            pokerByte += value == 0 ? 3 : value;
        }
        return pokerByte;
    }

    /**
     * 牌类型，转换为牌id
     * 这个id会是当前类型牌，id的最前面的一个id
     * @param type
     * @return
     */
    public static int getPokerId(int type){
        int id = 0;
        int color = type / 10;
        int value = type % 10;
        if(type <= 30){
            id += color * 36;
            id += value;
        }else if(type <= 40){
            id += 108 + value ;
        }else if(type <= 50){
            id += 124 + value ;
        }
        return id;
    }

    /**
     * 查找当前位置，指定距离的牌（同花色下）
     * @param corePosition
     * @param change
     * @return
     */
    public static int getNextPoker(int corePosition,int change){
        int nextPoker = 0;
        int color = corePosition / 10;
        int value = (corePosition % 10) + change;
        int updateValue = 0;
        if(corePosition <= 30){
            updateValue = 9;
        }else if(corePosition <= 40){
            updateValue = 4;
        }else if(corePosition <= 50){
            updateValue = 3;
        }

        value = value > updateValue ? value - updateValue :
                value < 1 ? value + updateValue : value;
        nextPoker += value;
        nextPoker += color * 10;
        return nextPoker;
    }

    public static boolean containPoker(List<Integer> have,int chose){
        for(int type : have){
            if(type == chose){
                return true;
            }
        }
        return false;
    }

    public static boolean containPoker(List<Long> players,long playerId){
        for(long id : players){
            if(id == playerId){
                return true;
            }
        }
        return false;
    }

    /**
     * 判断是否包含，选出来的牌
     * @return
     */
    public static boolean containPoker(int[] handPoker,int pokerId){
        for(int choseInt : handPoker){
            if(choseInt == pokerId){
                return true;
            }
        }
        return false;
    }

    /**
     * 移除牌中的癞子牌
     * @return
     */
    public static int removeTingPoker(int[] handPokerQiDui,MJConstant.GuiPoker guiMessage){
        int guiNumber = 0;
        if(guiMessage.isHave){
            for(int poker : guiMessage.guiPoker){
                int haveNumber = handPokerQiDui[poker];
                if(haveNumber > 0){
                    guiNumber += haveNumber;
                    handPokerQiDui[poker] = 0;
                }
            }
        }
        return guiNumber;
    }

    public static List<Integer> removeLaiPoker(int[] handPokerQiDui,MJConstant.GuiPoker guiMessage){
        List<Integer> guiPoker = new ArrayList<>();
        if(guiMessage.isHave){
            for(int poker : guiMessage.guiPoker){
                int haveNumber = handPokerQiDui[poker];
                if(haveNumber > 0){
                    for(int i = 0;i < haveNumber;i++){
                        guiPoker.add(poker);
                    }
                    handPokerQiDui[poker] = 0;
                }
            }
        }
        return guiPoker;
    }



    public static int getPokerId(int poker,List<Integer> pokers,MJConstant.OutPokerType present){
        for(int pokerId : pokers){
            if(getPokerInteger(pokerId) == poker){
                return pokerId;
            }
        }
        if(poker == present.poker){
            return present.pokerId;
        }
        return 0;
    }

    public static int getTingNum(int[] pokerIds,MJConstant.GuiPoker guiMessage){
        int guiNumber = 0;
        if(guiMessage.isHave){
            int[] guiPoker = guiMessage.guiPoker;
            for(int pokerId : pokerIds){
                int poker = getPokerInteger(pokerId);
                for(int gui : guiPoker){
                    if(gui == poker){
                        guiNumber ++;
                        break;
                    }
                }
            }
        }
        return guiNumber;
    }


    public static boolean isTing(int[] pokerIds,MJConstant.GuiPoker guiMessage){
        if(guiMessage.isHave){
            int[] guiPoker = guiMessage.guiPoker;
            for(int pokerId : pokerIds){
                for(int poker : guiPoker){
                    if(getPokerInteger(pokerId) == poker){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * 判断手中是否有癞子牌
     * @return
     */
    public static boolean isTing(int handPoker,MJConstant.GuiPoker guiMessage){
        if(guiMessage.isHave){
            for(int poker : guiMessage.guiPoker){
                if(poker ==  handPoker){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 计算牌数量
     * @param handPoker
     * @return
     */
    public static int pokerNumber(int[] handPoker){
        int number = 0;
        for(int count : handPoker){
            number += count;
        }
        return number;
    }

    /**
     * 查找手牌包含的颜色
     * @param handPoker
     * @return
     */
    public static List<Integer> includeColor(int[] handPoker,List<HuBian.AlertType> chunk){
        List<Integer> colors = new ArrayList<>();
        for(int i = 1; i< 50;i ++){
            if(handPoker[i] > 0){
                int color = (int) (i / 10);
                if(!containPoker(colors,color)){
                    colors.add(color);
                }
            }
        }
        for(HuBian.AlertType alert : chunk){
            int haveColor = (int)(alert.corePoker / 10);
            if(!containPoker(colors, haveColor)){
                colors.add(haveColor);
            }
        }
        return colors;
    }

    /**
     * 添加发送鬼牌消息
     * @param guiMessage
     * @return
     */
//    public static MJInfo.laiZhiGui getSendTing(MJConstant.GuiPoker guiMessage){
//        MJInfo.laiZhiGui sendTing = new MJInfo.laiZhiGui();
//        if(guiMessage.isHave){
//            sendTing.guiChose = guiMessage.guiChose;
//            sendTing.fanPoker = GameUtil.getPokerId(guiMessage.fanChose);
//            sendTing.guiPoker = GameUtil.getPokerIds(guiMessage.guiPoker);
//        }
//        return sendTing;
//    }


    public static List<Integer> getPokerIds(int[] pokers){
        List<Integer> pokerIds = new ArrayList<>();
        for(int value : pokers){
            pokerIds.add(getPokerId(value));
        }
        return pokerIds;
    }

    public static List<Integer> getPokerIds(List<Integer> pokers){
        List<Integer> pokerIds = new ArrayList<>();
        for(int value : pokers){
            pokerIds.add(getPokerId(value));
        }
        return pokerIds;
    }

//    /**
//     * 获取下一个阶段
//     * @return
//     */
//    public static Constant.GamePhase getInitPhase(MJGame mjGame){
//        Constant.GamePhase nextPhase = null;
//        //当前阶段
//        Constant.GamePhase phase = mjGame.phase();
//        //获取当前阶段等级
//        int phaseValue = (phase.ordinal() + 1) % 7;
//
//        //找到高于当前等级的阶段，按小到大判断
//        GameRule rule = mjGame.gameRule;
//        if(phaseValue < 1 && rule.piao != 3 && rule.piao != 0){
//            nextPhase = Constant.GamePhase.PIAOGAME;
//        }else if(phaseValue < 2 && rule.huanSan){
//            nextPhase = Constant.GamePhase.HUANSANGAME;
//        }else if(phaseValue < 3 && rule.queYiMen == MJMessageConst.START.DING_QUE){
//            nextPhase = Constant.GamePhase.DINGQUE;
//        }else if(phaseValue < 4 && rule.baoJiao){
//            nextPhase = Constant.GamePhase.BAOPAI;
//        }else if(phaseValue < 5){
//            nextPhase = Constant.GamePhase.DAPAI;
//        }
//        return nextPhase;
//    }
//
//    /**
//     * 自动飘检查
//     * @return
//     */
//    public static boolean autoPiao(MJGame mjGame){
//        int piao = mjGame.gameRule.piao;
//        if(piao == 2 || piao == 4 || piao == 5){
//            return true;
//        }else if(piao == 6 && mjGame.specialCore.currentNum > 1){
//            return true;
//        }
//        return false;
//    }
//
//    /**
//     * 获取指定胡牌信息
//     * @return
//     */
//    public static MJConstant.HuPlayer getHuPlayer(MJGame mjGame,long playerId){
//        for(MJConstant.HuPlayer huPlayer : mjGame.specialClearn.huGameMess){
//            if(huPlayer.playerId == playerId){
//                return huPlayer;
//            }
//        }
//        return null;
//    }
//
//    /**
//     * 下一个用户
//     * @param mjGame
//     * @param playerId
//     * @return
//     */
//    public static GamePlayer nextPlayer(MJGame mjGame,long playerId){
//        //当前用户
//        int length = mjGame.gamePlayers.size();
//        for(int position = 0;position < length;position ++){
//            GamePlayer player = mjGame.gamePlayers.get(position);
//            if(player.playerId == playerId){
//                for(int addValue = 1;addValue <= length;addValue ++){
//                    int need = position + addValue;
//                    if(need >= length){
//                        need -= length;
//                    }
//                    GamePlayer nextPlayer = mjGame.gamePlayers.get(need);
//                    //确保找到的玩家不能是已经胡牌用户
//                    if(nextPlayer != null && mjGame.huPlayer(nextPlayer.playerId) == null){
//                        return nextPlayer;
//                    }
//                }
//                break;
//            }
//        }
//        return null;
//    }
//
//    /**
//     * 碰,杠，胡，飞优先级排序
//     * @param type
//     * @return
//     */
//    public static int comparePGH(Constant.AlertChose type){
//        int compare=0;
//        if(type == Constant.AlertChose.HUALERT){
//            compare=4;
//        }else if(type == Constant.AlertChose.GANGALERT){
//            compare=3;
//        }else if(type == Constant.AlertChose.PENGALERT ){
//            compare=2;
//        }else if(type == Constant.AlertChose.FEIGAME){
//            compare=1;
//        }
//        return compare;
//    }
//
//    /**
//     * 癞子牌是否使用
//     * @return
//     */
//    public static int useLaiZhi(boolean use){
//        int compare=0;
//        if(!use){
//            compare = 1;
//        }
//        return compare;
//    }
//
//    /**
//     * 执行状态（等待，执行，拒绝)排序
//     * @param chose
//     * @return
//     */
//    public static int compareChose(Constant.ChoseType chose){
//        int compare=0;
//        if (chose == Constant.ChoseType.AGREE) {
//            compare = 2;
//        } else if (chose == Constant.ChoseType.WAIT) {
//            compare = 2;
//        } else if (chose == Constant.ChoseType.REFUSE ) {
//            compare = 1;
//        } else if (chose == Constant.ChoseType.SPECIAL) {
//            compare = 0;
//        }
//        return compare;
//    }
//
//    /**
//     * 动态爆牌检查
//     * @return
//     */
//    public static boolean checkDongBao(MJGame mjGame, GamePlayer player){
//        if (mjGame.specialCore.mjBaoPai.relevance && // 关联爆开关，开启
//                player.playerSpecialClearn.limit.baoChose.get() != MJMessageConst.CHOSE.AGREE ){ //胡牌玩家自己没爆牌
//            return true;
//        }
//        return false;
//    }
//
//    /**
//     * 顶爆检查
//     * @param huPlayer
//     * @param player
//     * @return
//     */
//    public static boolean checkDingBao(GamePlayer huPlayer,GamePlayer player){
//        if( huPlayer.playerSpecialClearn.limit.baoChose.get() == MJMessageConst.CHOSE.AGREE &&
//                player.playerSpecialClearn.limit.baoChose.get() == MJMessageConst.CHOSE.AGREE ){
//            return true;
//        }
//        return false;
//    }
//
//    /**
//     * 通过牌类型，找到手牌中对应的一张牌
//     * @return
//     */
//    public static int getRealPokerId(GamePlayer player,int corePoker,MJGame mjGame){
//        for(int pokerId : player.playerSpecialClearn.handPokerId){
//            if(getPokerInteger(pokerId) == corePoker){
//                return pokerId;
//            }
//        }
//        MJConstant.OutPokerType present = mjGame.present();
//        if(present != null && present.poker == corePoker){
//            return present.pokerId;
//        }
//        return 0;
//    }
//
//    /**
//     * 通过牌类型，找到手牌中对应的一张牌
//     * @return
//     */
//    public static int getRealPokerIdGX(GamePlayer player,int corePoker,MJGame mjGame){
//        if(mjGame.specialClearn.freePoker.size() != 0){
//            for(int pokerId : player.playerSpecialClearn.handPokerId){
//                if(getPokerInteger(pokerId) == corePoker){
//                    return pokerId;
//                }
//            }
//        }
//        MJConstant.OutPokerType present = mjGame.present();
//        if(present != null && present.poker == corePoker){
//            return present.pokerId;
//        }
//        return 0;
//    }
//
//    /**
//     * 胡牌提示
//     * @return
//     */
//    public static List<MJInfo.HuPokers> getHuPrompt(List<EverHuPlayer> huPlayers){
//        List<MJInfo.HuPokers> huMessage = new ArrayList<>();
//        for(EverHuPlayer huPlayer : huPlayers){
//            MJInfo.HuPokers huPokers = new MJInfo.HuPokers();
//            huPokers.pokerId = huPlayer.pokerId;
//            huPokers.bet = huPlayer.bet;
//            huPokers.huType = huPlayer.huType;
//            huMessage.add(huPokers);
//        }
//        return huMessage;
//    }

    /**
     * 花猪，定缺检查
     * @return
     */
    public static boolean checkQueYiMen(int[] handPoker, boolean removeTing,MJConstant.GuiPoker guiMessage,List<HuBian.AlertType> chunk){

        int[] checkPoker = handPoker.clone();
        //去癞子
        if(removeTing){
            GameUtil.removeTingPoker(checkPoker,guiMessage);
        }
        //查看颜色
        List<Integer> colors = GameUtil.includeColor(checkPoker,chunk);
        if(colors.size() >= 3){
            return false;
        }
        //查看定缺
//        Constant.QueChose que = gamePlayer.playerSpecialClearn.limit.queChose.get();
//        if(que != null){
//            int queColor = 0;
//            if(que == Constant.QueChose.TONGCHOSE){
//                queColor = 1;
//            }else if(que == Constant.QueChose.WANCHOSE){
//                queColor = 2;
//            }
//            for(Integer color : colors){
//                if(color == queColor){
//                    return false;
//                }
//            }
//        }
        return true;
    }

//    /**
//     * 删除杠牌分
//     */
//    public static void removeGangScore(GamePlayer player,MJGangDetail detail,MJGame mjGame){
//        //移除自己获得的杠分
//        player.totalScore -= detail.score;
//        player.playerSpecialClearn.score -= detail.score;
//        //还回其他被杠玩家分
//        int repayScore = detail.score / detail.playerId.size();
//        for(long beiGangId : detail.playerId){
//            GamePlayer repayPlayer = mjGame.player(beiGangId);
//            if(repayPlayer != null){
//                repayPlayer.totalScore += repayScore;
//                repayPlayer.playerSpecialClearn.score += repayScore;
//                for(Iterator<MJGangDetail> it = repayPlayer.playerSpecialClearn.gangDetail.iterator(); it.hasNext();) {
//                    MJGangDetail byDetail = it.next();
//                    if(byDetail.gangPokerId == detail.gangPokerId){
//                        it.remove();
//                        break;
//                    }
//                }
//            }
//        }
//    }
//
//    /**
//     * 删除杠牌分
//     */
//    public static void removeGangScoreXS(GamePlayer player,MJGangDetail detail,MJGame mjGame){
//        int winScore = 0;
//        //还回其他被杠玩家分
//        int repayScore = detail.score;
//        for(long beiGangId : detail.playerId){
//            GamePlayer repayPlayer = mjGame.player(beiGangId);
//            if(repayPlayer != null){
//                winScore += repayScore;
//                repayPlayer.totalScore += repayScore;
//                repayPlayer.playerSpecialClearn.score += repayScore;
//                for(Iterator<MJGangDetail> it = repayPlayer.playerSpecialClearn.gangDetail.iterator(); it.hasNext();) {
//                    MJGangDetail byDetail = it.next();
//                    if(byDetail.gangPokerId == detail.gangPokerId){
//                        it.remove();
//                        break;
//                    }
//                }
//            }
//        }
//
//        //移除自己获得的杠分
//        player.totalScore -= winScore;
//        player.playerSpecialClearn.score -= winScore;
//    }
//
//    /**
//     * 删除所有杠牌分
//     */
//    public static void removeGangScore(GamePlayer player,MJGame mjGame,int pokerId){
//        if(player != null){
//            for(Iterator<MJGangDetail> it = player.playerSpecialClearn.gangDetail.iterator(); it.hasNext();){
//                MJGangDetail detail = it.next();
//                if(pokerId > 0 && detail.gangPokerId != pokerId){
//                    continue;
//                }
//                if(detail.type == Constant.GangScoreType.GANG_DETAIL || detail.type == Constant.GangScoreType.BY_ZHUAN_YI){
//                    removeGangScore(player,detail,mjGame);
//                    it.remove();
//                }
//            }
//        }
//    }
//
//    /**
//     * 删除所有杠牌分
//     */
//    public static void removeGangScoreXS(GamePlayer player,MJGame mjGame,int pokerId){
//        if(player != null){
//            for(Iterator<MJGangDetail> it = player.playerSpecialClearn.gangDetail.iterator(); it.hasNext();){
//                MJGangDetail detail = it.next();
//                if(pokerId > 0 && detail.gangPokerId != pokerId){
//                    continue;
//                }
//                if(detail.type == Constant.GangScoreType.GANG_DETAIL || detail.type == Constant.GangScoreType.BY_ZHUAN_YI){
//                    removeGangScoreXS(player,detail,mjGame);
//                    it.remove();
//                }
//            }
//        }
//    }
//
//    /**
//     * 相同用户
//     */
//    public static boolean samePlayerId(List<Long> playerIds,List<Long> comparePlayerIds){
//        if(playerIds != null && comparePlayerIds != null && playerIds.size() == comparePlayerIds.size()){
//            for(long playerId : playerIds){
//                if(!containPoker(comparePlayerIds,playerId)){
//                    return false;
//                }
//            }
//            return true;
//        }
//        return false;
//    }
}
