package com.ys.zy.ssc.adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.ys.zy.R;
import com.ys.zy.adapter.CommonAdapter;
import com.ys.zy.adapter.ViewHolder;
import com.ys.zy.racing.RacingUtil;
import com.ys.zy.ssc.SscUtil;
import com.ys.zy.util.StringUtil;

import java.util.List;

public class SscResultAdapter extends CommonAdapter<Integer> {
    public SscResultAdapter(Context context, List<Integer> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
    }

    @Override
    public void convert(ViewHolder helper, Integer item, int position) {
        helper.setText(R.id.tv_, StringUtil.valueOf(item));
    }

    private RandomThraed randomThraed;

    public void startRandom() {
        if (randomThraed != null) {
            randomThraed.setOver(true);
            randomThraed.interrupt();
            randomThraed = null;
        }
        randomThraed = new RandomThraed();
        randomThraed.start();
    }

    /**
     * 线程是否正在进行
     *
     * @return
     */
    public boolean isRamdomRunning() {
        return randomThraed != null && !randomThraed.isOver();
    }

    public void closeRandom() {
        if (randomThraed != null) {
            randomThraed.setOver(true);
            randomThraed.interrupt();
            randomThraed = null;
        }
    }

    class RandomThraed extends Thread {
        private volatile boolean isOver = false;

        public boolean isOver() {
            return isOver;
        }

        public void setOver(boolean over) {
            isOver = over;
        }

        @Override
        public void run() {
            while (!isOver) {
                try {
                    Thread.sleep(100);
                    handler.sendEmptyMessage(0);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {
                randomRssult();
            } else if (msg.what == 1) {

            }
        }
    };
    private int randomResult = 0;

    private void randomRssult() {
        mDatas.clear();
        mDatas.addAll(SscUtil.getRandomResultList(randomResult));
        refresh(mDatas);
        randomResult++;
        if (randomResult > 9) {
            randomResult = 0;
        }
    }
}
