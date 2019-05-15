package com.ys.zy.ttz.adapter;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.ys.zy.R;
import com.ys.zy.adapter.CommonAdapter;
import com.ys.zy.adapter.ViewHolder;
import com.ys.zy.ssc.SscUtil;
import com.ys.zy.ssc.adapter.SscResultAdapter;
import com.ys.zy.ttz.TtzUtil;
import com.ys.zy.ttz.bean.PaiBean;
import com.ys.zy.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

public class PaiAdapter extends CommonAdapter<PaiBean> {
    private Animation animation;
    private int currentAnimaItem = -1;

    public PaiAdapter(Context context, List<PaiBean> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
        animation = AnimationUtils.loadAnimation(mContext, R.anim.gridview_item_anim);
    }

    @Override
    public void convert(final ViewHolder helper, PaiBean item, int position) {
        helper.setText(R.id.tv_, StringUtil.valueOf(item.name));
        if (position == 0) {
            ((TextView) helper.getView(R.id.tv_)).setTextColor(Color.parseColor("#dd2230"));
        } else {
            ((TextView) helper.getView(R.id.tv_)).setTextColor(Color.parseColor("#29abe2"));
        }
        helper.setImageResource(R.id.iv01, PaiBean.getDrawableId(item.firstValue));
        helper.setImageResource(R.id.iv02, PaiBean.getDrawableId(item.secondValue));
        if (currentAnimaItem != -1) {
            if (position == currentAnimaItem) {
                helper.getView(R.id.iv01).startAnimation(animation);
                helper.getView(R.id.iv02).startAnimation(animation);
            } else {
                helper.getView(R.id.iv01).clearAnimation();
                helper.getView(R.id.iv02).clearAnimation();
            }
        } else {
            helper.getView(R.id.iv01).startAnimation(animation);
            helper.getView(R.id.iv02).startAnimation(animation);
        }
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
        List<PaiBean> list1 = new ArrayList<>();
        List<PaiBean> list2 = new ArrayList<>();
        List<PaiBean> list3 = new ArrayList<>();
        List<PaiBean> list4 = new ArrayList<>();

        list1.add(new PaiBean("庄", list.get(0), list.get(1)));
        list1.add(new PaiBean("闲1", 0, 0));
        list1.add(new PaiBean("闲2", 0, 0));
        list1.add(new PaiBean("闲3", 0, 0));

        list2.add(new PaiBean("庄", list.get(0), list.get(1)));
        list2.add(new PaiBean("闲1", list.get(2), list.get(3)));
        list2.add(new PaiBean("闲2", 0, 0));
        list2.add(new PaiBean("闲3", 0, 0));

        list3.add(new PaiBean("庄", list.get(0), list.get(1)));
        list3.add(new PaiBean("闲1", list.get(2), list.get(3)));
        list3.add(new PaiBean("闲2", list.get(4), list.get(5)));
        list3.add(new PaiBean("闲3", 0, 0));


        list4.add(new PaiBean("庄", list.get(0), list.get(1)));
        list4.add(new PaiBean("闲1", list.get(2), list.get(3)));
        list4.add(new PaiBean("闲2", list.get(4), list.get(5)));
        list4.add(new PaiBean("闲3", list.get(6), list.get(7)));
        refreshDelay(list1, 0, 0);
        refreshDelay(list2, 500, 1);
        refreshDelay(list3, 1000, 2);
        refreshDelay(list4, 1500, 3);
    }

    //延迟刷新
    private void refreshDelay(final List<PaiBean> list, long delay, final int position) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                currentAnimaItem = position;
                refresh(list);
//                currentAnimaItem = -1;
            }
        }, delay);
    }

    public int getCurrentAnimaItem() {
        return currentAnimaItem;
    }

    public void setCurrentAnimaItem(int currentAnimaItem) {
        this.currentAnimaItem = currentAnimaItem;
    }
}
