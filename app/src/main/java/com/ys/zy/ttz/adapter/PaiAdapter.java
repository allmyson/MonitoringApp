package com.ys.zy.ttz.adapter;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

import com.ys.zy.R;
import com.ys.zy.adapter.CommonAdapter;
import com.ys.zy.adapter.ViewHolder;
import com.ys.zy.ssc.SscUtil;
import com.ys.zy.ssc.adapter.SscResultAdapter;
import com.ys.zy.ttz.TtzUtil;
import com.ys.zy.ttz.bean.PaiBean;
import com.ys.zy.util.StringUtil;

import java.util.List;

public class PaiAdapter extends CommonAdapter<PaiBean> {
    public PaiAdapter(Context context, List<PaiBean> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
    }

    @Override
    public void convert(ViewHolder helper, PaiBean item, int position) {
        helper.setText(R.id.tv_, StringUtil.valueOf(item.name));
        if (position == 0) {
            ((TextView) helper.getView(R.id.tv_)).setTextColor(Color.parseColor("#dd2230"));
        } else {
            ((TextView) helper.getView(R.id.tv_)).setTextColor(Color.parseColor("#29abe2"));
        }
        helper.setImageResource(R.id.iv01, PaiBean.getDrawableId(item.firstValue));
        helper.setImageResource(R.id.iv02, PaiBean.getDrawableId(item.secondValue));
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

    public void toDefault() {
        randomResult = 1;
//        closeRandom();
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
                    Thread.sleep(300);
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
//                closeRandom();
            }
        }
    };
    private int randomResult = 1;

    private void randomRssult() {
        mDatas.clear();
        mDatas.addAll(TtzUtil.getRandomList(randomResult));
        refresh(mDatas);
        randomResult++;
        if (randomResult > 4) {
            randomResult = 4;
        }
    }

    public void setRandomResult() {
        closeRandom();
        mDatas.clear();
        mDatas.addAll(TtzUtil.getRandomList(4));
        refresh(mDatas);
    }

    public void faPai() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mDatas.clear();
                mDatas.addAll(TtzUtil.getRandomList(randomResult));
                refresh(mDatas);
                randomResult++;
                if (randomResult < 5) {
                    faPai();
                } else {
                    randomResult = 1;
                }
            }
        }, 500);
    }


    public void faPai(final List<Integer> list) {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mDatas.clear();
                mDatas.addAll(TtzUtil.getRandomList(randomResult, list));
                refresh(mDatas);
                randomResult++;
                if (randomResult < 5) {
                    faPai();
                } else {
                    randomResult = 1;
                }
            }
        }, 500);
    }
}
