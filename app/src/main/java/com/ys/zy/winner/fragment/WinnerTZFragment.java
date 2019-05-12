package com.ys.zy.winner.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yanzhenjie.nohttp.rest.Response;
import com.ys.zy.R;
import com.ys.zy.base.BaseFragment;
import com.ys.zy.bean.BaseBean;
import com.ys.zy.http.HttpListener;
import com.ys.zy.racing.activity.RacingActivity;
import com.ys.zy.sp.UserSP;
import com.ys.zy.ssc.SscUtil;
import com.ys.zy.util.DateUtil;
import com.ys.zy.util.HttpUtil;
import com.ys.zy.util.L;
import com.ys.zy.util.StringUtil;
import com.ys.zy.util.TimeUtil;
import com.ys.zy.util.YS;
import com.ys.zy.winner.WinnerUtil;
import com.ys.zy.winner.activity.WinnerActivity;
import com.ys.zy.winner.adapter.BuyDataAdapter;
import com.ys.zy.winner.adapter.MySNAdapter;
import com.ys.zy.winner.bean.WinnerData;
import com.ys.zy.winner.bean.WinnerResult;
import com.ys.zy.winner.ui.CircleProgressBar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

public class WinnerTZFragment extends BaseFragment implements View.OnClickListener {
    private CircleProgressBar singlePriceCPB, totalMoneyCPB;
    private ListView buyDataLV;
    private List<String> buyDataList;
    private BuyDataAdapter buyDataAdapter;
    private RelativeLayout moreRL;
    private GridView mySnGV;
    private List<String> snList;
    private MySNAdapter mySNAdapter;
    private LinearLayout snLL;
    private RelativeLayout snRL;
    private ImageView buySnIV;
    private TextView nextQsTV, djsTV, currentQsTV;
    private String currentQs = "";
    private long startTime;
    private SimpleDateFormat formatter;
    private LinearLayout tzLL;
    private RelativeLayout waitKjRL;
    private LinearLayout finishKjLL;
    private CircleProgressBar waitKjCPB;
    private TextView tzTV;
    private int snNum = 0;
    private TextView currentQ0, currentQ1, currentQ2, currentQ3;
    private String userId;
    private TextView fhTV, payTV;
    private TextView slzUser,sj1User,sj2User,sj3User,slzSn,sj1Sn,sj2Sn,sj3Sn,slzMoney,sj1Money,sj2Money,sj3Money;
    public static WinnerTZFragment newInstance() {
        WinnerTZFragment winnerTZFragment = new WinnerTZFragment();
        return winnerTZFragment;
    }

    @Override
    protected void init() {
        slzUser = getView(R.id.tv_slz_user);
        sj1User = getView(R.id.tv_sj1_user);
        sj2User = getView(R.id.tv_sj2_user);
        sj3User = getView(R.id.tv_sj3_user);
        slzSn = getView(R.id.tv_slz_sn);
        sj1Sn = getView(R.id.tv_sj1_sn);
        sj2Sn = getView(R.id.tv_sj2_sn);
        sj3Sn = getView(R.id.tv_sj3_sn);
        slzMoney = getView(R.id.tv_slz_money);
        sj1Money = getView(R.id.tv_sj1_money);
        sj2Money = getView(R.id.tv_sj2_money);
        sj3Money = getView(R.id.tv_sj3_money);
        fhTV = getView(R.id.tv_fh);
        payTV = getView(R.id.tv_pay);
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
//        startTime = System.currentTimeMillis() - 119 * 60 * 1000 - 40 * 1000;
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
        singlePriceCPB.setPercent("0");
        singlePriceCPB.setDescription("当前单价");
        singlePriceCPB.setProgress(0);
        totalMoneyCPB.setText1Color("#ffffff");
        totalMoneyCPB.setText1Size(20);
        totalMoneyCPB.setText2Color("#4Dffffff");
        totalMoneyCPB.setText2Size(12);
        totalMoneyCPB.setPercent("0");
        totalMoneyCPB.setDescription("奖池金额");
        totalMoneyCPB.setProgress(0);
        buyDataLV = getView(R.id.lv_buyData);
        buyDataList = new ArrayList<>();
        buyDataAdapter = new BuyDataAdapter(mContext, buyDataList, R.layout.item_buy_data);
        buyDataLV.setAdapter(buyDataAdapter);
        mySnGV = getView(R.id.gv_sn);
        snList = new ArrayList<>();
//        for (int k = 0; k < 24; k++) {
//            snList.add("SN00" + k);
//        }
//        int size = snList.size();
//        if (size < 24) {
//            for (int i = 0; i < 24 - size; i++) {
//                snList.add(null);
//            }
//        } else if (size == 24) {
//
//        } else {
//            int yu = snList.size() % 4;
//            if (yu != 0) {
//                for (int j = 0; j < 4 - yu; j++) {
//                    snList.add(null);
//                }
//            }
//        }
        mySNAdapter = new MySNAdapter(mContext, snList, R.layout.item_my_sn);
        mySnGV.setAdapter(mySNAdapter);
        snLL = getView(R.id.ll_sn);
        snLL.setOnClickListener(this);
        snRL = getView(R.id.rl_sn);
        userId = UserSP.getUserId(mContext);
    }

    @Override
    protected void getData() {
        getWinnerInfo();
        //延迟2秒开启倒计时，等待接口返回结果
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                start();
            }
        }, 2000);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_winner_tz;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_more:
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
//                snNum++;
//                show("投注成功");
                tz();
                break;
        }
    }

    private void tz() {
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("gameCode", YS.CODE_SLZ);
        map.put("complantTypeCode", "1000");//手动
        map.put("periodsNum", currentQs);
        String json = new Gson().toJson(map);
        HttpUtil.tz(mContext, json, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                BaseBean baseBean = new Gson().fromJson(response.get(), BaseBean.class);
                if (baseBean != null) {
                    if (YS.SUCCESE.equals(baseBean.code)) {
                        show("投注成功");
                        //通知Activity刷新余额
                        ((WinnerActivity) getActivity()).getData();
                        sendMsg();//刷新投注记录
                    } else {
                        show(StringUtil.valueOf(baseBean.msg));
                    }
                } else {
                    show(YS.HTTP_TIP);
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {

            }
        });
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
        isStart = false;
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
                getWinnerResult();
                break;
            case WinnerUtil.TYPE_END:
                //本期游戏结束，期号加1，下期游戏开始
//                startTime = System.currentTimeMillis() - 119 * 60 * 1000 - 40 * 1000;
//                currentQs = StringUtil.StringToInt(currentQs) + 1 + "";
//                snNum = 0;//snNum清0
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

    private boolean isStart = true;

    private void getWinnerInfo() {
        HttpUtil.getWinnerInfo(mContext, userId, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                snList.clear();
                buyDataList.clear();
                WinnerData winnerData = new Gson().fromJson(response.get(), WinnerData.class);
                if (winnerData != null && YS.SUCCESE.equals(winnerData.code) && winnerData.data != null) {
                    if (winnerData.data.lastWinnerBaseVo != null) {
                        snNum = StringUtil.StringToInt(winnerData.data.lastWinnerBaseVo.snprice) - 1;
                        currentQs = StringUtil.valueOf(winnerData.data.lastWinnerBaseVo.periodNum);
                        setCurrentQ(currentQs);
                        startTime = DateUtil.changeTimeToLong(winnerData.data.lastWinnerBaseVo.gameStartTime);
                        L.e("winner", "startTime=" + startTime);
                        fhTV.setText(StringUtil.valueOf(winnerData.data.lastWinnerBaseVo.earnMoney));
                        payTV.setText(StringUtil.valueOf(winnerData.data.lastWinnerBaseVo.payMoney));
                        totalMoneyCPB.setPercent(StringUtil.valueOf(winnerData.data.lastWinnerBaseVo.totleMoney));
                        totalMoneyCPB.setDescription("奖池金额");
                        totalMoneyCPB.setProgress((int) (StringUtil.StringToDouble(winnerData.data.lastWinnerBaseVo.totleMoney)*100/YS.MAX_MONEY));
                        singlePriceCPB.setPercent(StringUtil.valueOf(winnerData.data.lastWinnerBaseVo.snprice));
                        singlePriceCPB.setDescription("当前单价");
                        singlePriceCPB.setProgress(StringUtil.StringToInt(winnerData.data.lastWinnerBaseVo.snprice) * 100 / YS.MAX_SN_PRICE);
                    }
                    if (winnerData.data.listSn != null && winnerData.data.listSn.size() > 0) {
                        snList.addAll(winnerData.data.listSn);
                        Collections.reverse(snList);
                    }
                    if (winnerData.data.listRecord != null && winnerData.data.listRecord.size() > 0) {
                        buyDataList.addAll(winnerData.data.listRecord);
                    }
                }
                mySNAdapter.refresh(snList);
                buyDataAdapter.refresh(buyDataList);
                if (isStart) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            getWinnerInfo();
                        }
                    }, 1000);
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {

            }
        });
    }

    private void sendMsg() {
        Intent intent = new Intent(YS.ACTION_TZ_SUCCESS);
        getActivity().sendBroadcast(intent);
    }

    private void getWinnerResult() {
        HttpUtil.getWinnerResult(mContext, currentQs, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                WinnerResult winnerResult = new Gson().fromJson(response.get(), WinnerResult.class);
                if (winnerResult != null && YS.SUCCESE.equals(winnerResult.code) && winnerResult.data != null) {
                    slzUser.setText(StringUtil.valueOf(winnerResult.data.lastUserName));
                    slzSn.setText(StringUtil.valueOf(winnerResult.data.snNum));
                    slzMoney.setText(StringUtil.valueOf(winnerResult.data.lastMoney));
                    sj1User.setText(StringUtil.valueOf(winnerResult.data.incentiveUserName1));
                    sj1Sn.setText(StringUtil.valueOf(winnerResult.data.incentiveSnNum1));
                    sj1Money.setText(StringUtil.valueOf(winnerResult.data.incentive_money1));
                    sj2User.setText(StringUtil.valueOf(winnerResult.data.incentiveUserName2));
                    sj2Sn.setText(StringUtil.valueOf(winnerResult.data.incentiveSnNum2));
                    sj2Money.setText(StringUtil.valueOf(winnerResult.data.incentive_money2));
                    sj3User.setText(StringUtil.valueOf(winnerResult.data.incentiveUserName3));
                    sj3Sn.setText(StringUtil.valueOf(winnerResult.data.incentiveSnNum3));
                    sj3Money.setText(StringUtil.valueOf(winnerResult.data.incentive_money3));
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {

            }
        });
    }
}
