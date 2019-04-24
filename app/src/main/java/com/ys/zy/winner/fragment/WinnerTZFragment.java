package com.ys.zy.winner.fragment;

import android.graphics.Color;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ys.zy.R;
import com.ys.zy.base.BaseFragment;
import com.ys.zy.ssc.SscUtil;
import com.ys.zy.util.L;
import com.ys.zy.util.StringUtil;
import com.ys.zy.util.TimeUtil;
import com.ys.zy.winner.WinnerUtil;
import com.ys.zy.winner.adapter.BuyDataAdapter;
import com.ys.zy.winner.adapter.MySNAdapter;
import com.ys.zy.winner.ui.CircleProgressBar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class WinnerTZFragment extends BaseFragment implements View.OnClickListener {
    private CircleProgressBar singlePriceCPB, totalMoneyCPB;
    private ListView buyDataLV;
    private List<Object> buyDataList;
    private BuyDataAdapter buyDataAdapter;
    private RelativeLayout moreRL;
    private GridView mySnGV;
    private List<String> snList;
    private MySNAdapter mySNAdapter;
    private LinearLayout snLL;
    private RelativeLayout snRL;
    private ImageView buySnIV;
    private TextView nextQsTV, djsTV, currentQsTV;
    private String currentQs = "1900001";
    private long startTime;
    private SimpleDateFormat formatter;
    private LinearLayout tzLL;
    private RelativeLayout waitKjRL;
    private LinearLayout finishKjLL;
    private CircleProgressBar waitKjCPB;
    private TextView tzTV;
    private int snNum = 0;
    private TextView currentQ0, currentQ1, currentQ2, currentQ3;

    public static WinnerTZFragment newInstance() {
        WinnerTZFragment winnerTZFragment = new WinnerTZFragment();
        return winnerTZFragment;
    }

    @Override
    protected void init() {
        currentQ0 = getView(R.id.tv_currentQ0);
        currentQ1 = getView(R.id.tv_currentQ1);
        currentQ2 = getView(R.id.tv_currentQ2);
        currentQ3 = getView(R.id.tv_currentQ3);
        tzTV = getView(R.id.tv_tz);
        setBtnClickable(false, tzTV);
        tzTV.setOnClickListener(this);
        tzLL = getView(R.id.ll_tz);
        waitKjRL = getView(R.id.rl_wait_kj);
        finishKjLL = getView(R.id.ll_finish_kj);
        waitKjCPB = getView(R.id.cpb_wait_kj);
        waitKjCPB.setText1Color("#DEffffff");
        waitKjCPB.setText1Size(20);
        waitKjCPB.setText2Color("#26ffffff");
        waitKjCPB.setText2Size(16);
        waitKjCPB.setPercent("开奖核算中");
        waitKjCPB.setDescription("1900001期");
        waitKjCPB.setText2ScaleY(0.8);
        waitKjCPB.setProgress(0);
        formatter = new SimpleDateFormat("HH:mm:ss");//初始化Formatter的转换格式。
        formatter.setTimeZone(TimeZone.getTimeZone("GMT+00:00"));
        startTime = System.currentTimeMillis() - 119 * 60 * 1000 - 40 * 1000;
        nextQsTV = getView(R.id.tv_nextQs);
        djsTV = getView(R.id.tv_djs);
        currentQsTV = getView(R.id.tv_currentQS);
        setCurrentQ(currentQs);
        nextQsTV.setText(StringUtil.StringToInt(currentQs) + 1 + "期投注倒计时");
        buySnIV = getView(R.id.iv_buySn);
        buySnIV.setColorFilter(Color.parseColor("#ffffff"));
        moreRL = getView(R.id.rl_more);
        moreRL.setOnClickListener(this);
        singlePriceCPB = getView(R.id.cpb_singlePrice);
        totalMoneyCPB = getView(R.id.cpb_totalMoney);
        singlePriceCPB.setText1Color("#ffffff");
        singlePriceCPB.setText1Size(20);
        singlePriceCPB.setText2Color("#4Dffffff");
        singlePriceCPB.setText2Size(12);
        singlePriceCPB.setPercent("240");
        singlePriceCPB.setDescription("当前单价");
        singlePriceCPB.setProgress(45);
        totalMoneyCPB.setText1Color("#ffffff");
        totalMoneyCPB.setText1Size(20);
        totalMoneyCPB.setText2Color("#4Dffffff");
        totalMoneyCPB.setText2Size(12);
        totalMoneyCPB.setPercent("28635");
        totalMoneyCPB.setDescription("奖池金额");
        totalMoneyCPB.setProgress(25);
        buyDataLV = getView(R.id.lv_buyData);
        buyDataList = new ArrayList<>();
        buyDataList.add(null);
        buyDataList.add(null);
        buyDataList.add(null);
        buyDataList.add(null);
        buyDataAdapter = new BuyDataAdapter(mContext, buyDataList, R.layout.item_buy_data);
        buyDataLV.setAdapter(buyDataAdapter);
        mySnGV = getView(R.id.gv_sn);
        snList = new ArrayList<>();
        for (int k = 0; k < 24; k++) {
            snList.add("SN00" + k);
        }
        int size = snList.size();
        if (size < 24) {
            for (int i = 0; i < 24 - size; i++) {
                snList.add(null);
            }
        } else if (size == 24) {

        } else {
            int yu = snList.size() % 4;
            if (yu != 0) {
                for (int j = 0; j < 4 - yu; j++) {
                    snList.add(null);
                }
            }
        }
        mySNAdapter = new MySNAdapter(mContext, snList, R.layout.item_my_sn);
        mySnGV.setAdapter(mySNAdapter);
        snLL = getView(R.id.ll_sn);
        snLL.setOnClickListener(this);
        snRL = getView(R.id.rl_sn);
    }

    @Override
    protected void getData() {
        start();
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_winner_tz;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_more:
                buyDataList.add(null);
                buyDataList.add(null);
                buyDataList.add(null);
                buyDataList.add(null);
                buyDataList.add(null);
                buyDataAdapter.refresh(buyDataList);
                moreRL.setVisibility(View.GONE);
                break;
            case R.id.ll_sn:
                if (snRL.getVisibility() == View.VISIBLE) {
                    snRL.setVisibility(View.GONE);
                    buySnIV.setImageResource(R.mipmap.slz_sn_btn_more);
                } else {
                    snRL.setVisibility(View.VISIBLE);
                    buySnIV.setImageResource(R.mipmap.slz_notice_btn_more_up);
                }
                break;
            case R.id.tv_tz:
                snNum++;
                show("投注成功");
                break;
        }
    }

    private CountDownTimer countDownTimer;

    private void initTimer() {
        countDownTimer = new CountDownTimer(24 * 60 * 60 * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                setStatus();
            }

            @Override
            public void onFinish() {

            }
        };
    }

    /**
     * 开启倒计时
     */
    public void start() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        initTimer();
        countDownTimer.start();
    }


    /**
     * destroy
     */
    public void cancel() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        cancel();
    }

    private void setStatus() {
        int type = WinnerUtil.getType(startTime, snNum);
        setCurrentQ(currentQs);
        nextQsTV.setText(StringUtil.StringToInt(currentQs) + 1 + "期投注倒计时");
        long endTime = WinnerUtil.getEndTime(type, startTime, snNum);
        djsTV.setText(formatter.format(endTime - System.currentTimeMillis()));
        switch (type) {
            case WinnerUtil.TYPE_TZ:
                tzLL.setVisibility(View.VISIBLE);
                djsTV.setVisibility(View.VISIBLE);
                waitKjRL.setVisibility(View.GONE);
                finishKjLL.setVisibility(View.GONE);
                nextQsTV.setVisibility(View.GONE);
                setBtnClickable(true, tzTV);
                break;
            case WinnerUtil.TYPE_WAIT_KJ:
                //系统结算中
                tzLL.setVisibility(View.GONE);
                nextQsTV.setVisibility(View.GONE);
                djsTV.setVisibility(View.GONE);
                waitKjRL.setVisibility(View.VISIBLE);
                finishKjLL.setVisibility(View.GONE);
                waitKjCPB.setDescription(currentQs + "期");
                waitKjCPB.setProgress(100 - (int) ((endTime - System.currentTimeMillis()) * 100 / (60 * 1000)));
                setBtnClickable(false, tzTV);
                break;
            case WinnerUtil.TYPE_FINISH_KJ:
                //已开奖
                tzLL.setVisibility(View.GONE);
                nextQsTV.setVisibility(View.VISIBLE);
                djsTV.setVisibility(View.VISIBLE);
                waitKjRL.setVisibility(View.GONE);
                finishKjLL.setVisibility(View.VISIBLE);
                setBtnClickable(false, tzTV);
                break;
            case WinnerUtil.TYPE_END:
                //本期游戏结束，期号加1，下期游戏开始
                startTime = System.currentTimeMillis() - 119 * 60 * 1000 - 40 * 1000;
                currentQs = StringUtil.StringToInt(currentQs) + 1 + "";
                snNum = 0;//snNum清0
                tzLL.setVisibility(View.VISIBLE);
                djsTV.setVisibility(View.VISIBLE);
                waitKjRL.setVisibility(View.GONE);
                finishKjLL.setVisibility(View.GONE);
                nextQsTV.setVisibility(View.GONE);
                setBtnClickable(false, tzTV);
                break;
        }
    }

    @Override
    protected void setBtnClickable(boolean canClick, View view) {
        TextView tv;
        if (view instanceof TextView) {
            tv = (TextView) view;
            if (canClick) {
                tv.setClickable(true);
                tv.setTextColor(Color.parseColor("#f86e00"));
                tv.setBackgroundResource(R.drawable.rect_cornor_yellow1);
//                tv.setAlpha(1f);
            } else {
                tv.setClickable(false);
                tv.setTextColor(Color.parseColor("#a5a5a5"));
                tv.setBackgroundResource(R.drawable.rect_cornor_gray7);
//                tv.setAlpha(0.3f);
            }
        }
    }

    private void setCurrentQ(String currentQ) {
        currentQsTV.setText(currentQ + "期");
        currentQ0.setText(currentQ + "期");
        currentQ1.setText(currentQ + "期");
        currentQ2.setText(currentQ + "期");
        currentQ3.setText(currentQ + "期");
    }
}
