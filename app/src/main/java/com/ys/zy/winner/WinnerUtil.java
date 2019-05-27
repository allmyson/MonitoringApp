package com.ys.zy.winner;

import com.ys.zy.util.YS;

public class WinnerUtil {
    public static final int TYPE_TZ = 100;//投注中
    public static final int TYPE_WAIT_KJ = 101;//开奖核算中
    public static final int TYPE_FINISH_KJ = 102;//已经开奖
    public static final int TYPE_END = 103;//本期结束，下期开始
    public static final int WAIT_KJ_MINUTE = 5;//等待5分钟开奖
    public static final int FINISH_KJ_MINUTE = 15;//等待+展示结果的时间=15分钟

    /**
     * 返回胜利者投注结束时间
     *
     * @param startTime 该期胜利者开始时间，默认2小时投注时间
     * @param snNo      该期投注的SN的数量，每投注一个，时间延长10秒,当SN数量500的时候，游戏结束。
     * @return
     */
    public static long getEndTime(int type, long startTime, int snNo) {
        long endTime = startTime + 2 * 60 * 60 * 1000 + 10 * 1000 * snNo;
        switch (type) {
            case TYPE_TZ:
                endTime = startTime + 2 * 60 * 60 * 1000 + 10 * 1000 * snNo;
                break;
            case TYPE_WAIT_KJ:
                //暂定模拟5*60秒开奖
                endTime = startTime + 2 * 60 * 60 * 1000 + 10 * 1000 * snNo + 5 * 60 * 1000;
                break;
            case TYPE_FINISH_KJ:
                endTime = startTime + 2 * 60 * 60 * 1000 + 10 * 1000 * snNo + 15 * 60 * 1000;
                //为方便测试将15分钟等待时间改为70秒
//                endTime = startTime + 2 * 60 * 60 * 1000 + 10 * 1000 * snNo + 1 * 70 * 1000;
                break;
            case TYPE_END:
                endTime = startTime + 2 * 60 * 60 * 1000 + 10 * 1000 * snNo + 15 * 60 * 1000;
//                endTime = startTime + 2 * 60 * 60 * 1000 + 10 * 1000 * snNo + 1 * 70 * 1000;
                break;
        }
        return endTime;
    }

    public static int getType(String status, long endTime) {
        int type = TYPE_TZ;
        if ("进行中".equals(status)) {
            if (endTime >= YS.currentTimeMillis()) {
                type = TYPE_TZ;
            }else {
                if (YS.currentTimeMillis() <= endTime + WAIT_KJ_MINUTE * 60 * 1000) {
                    type = TYPE_WAIT_KJ;
                } else {
                    type = TYPE_FINISH_KJ;
                }
            }
        } else {
            if (YS.currentTimeMillis() <= endTime + WAIT_KJ_MINUTE * 60 * 1000) {
                type = TYPE_WAIT_KJ;
            } else {
                type = TYPE_FINISH_KJ;
            }
        }
        return type;
    }

//    public static int getType(long startTime, int snNo) {
//        long endTime = startTime + 2 * 60 * 60 * 1000 + 10 * 1000 * snNo;
//        long currentTime = System.currentTimeMillis();
//        if (snNo > 500) {
//            return TYPE_WAIT_KJ;
//        }
//        if (currentTime <= endTime) {
//            return TYPE_TZ;
//        } else {
//            //暂定5*60秒等待开奖时间
//            if (currentTime - endTime < 5 * 60 * 1000) {
//                return TYPE_WAIT_KJ;
////            } else if (currentTime-endTime < 15 * 60 * 1000) {
//            } else if (currentTime - endTime < 15 * 60 * 1000) {
//                return TYPE_FINISH_KJ;
//            } else if (currentTime - endTime >= 15 * 60 * 1000) {
//                return TYPE_END;
//            } else {
//                return TYPE_TZ;
//            }
//        }
//    }
}
