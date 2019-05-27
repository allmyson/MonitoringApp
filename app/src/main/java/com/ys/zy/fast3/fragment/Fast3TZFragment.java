package com.ys.zy.fast3.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yanzhenjie.nohttp.rest.Response;
import com.ys.zy.R;
import com.ys.zy.bean.BaseBean;
import com.ys.zy.bean.OddsBean;
import com.ys.zy.fast3.Fast3Util;
import com.ys.zy.fast3.activity.Fast3Activity;
import com.ys.zy.activity.RechargeActivity;
import com.ys.zy.fast3.adapter.Fast3HistoryAdapter;
import com.ys.zy.fast3.adapter.Fast3SumAdapter;
import com.ys.zy.base.BaseFragment;
import com.ys.zy.fast3.bean.Fast3Bean;
import com.ys.zy.dialog.DialogUtil;
import com.ys.zy.dialog.TZTipFragment;
import com.ys.zy.dialog.TipFragment;
import com.ys.zy.http.HttpListener;
import com.ys.zy.racing.RacingUtil;
import com.ys.zy.racing.activity.RacingActivity;
import com.ys.zy.sp.UserSP;
import com.ys.zy.ssc.activity.SscActivity;
import com.ys.zy.ssc.bean.SscResultBean;
import com.ys.zy.util.HttpUtil;
import com.ys.zy.util.L;
import com.ys.zy.util.StringUtil;
import com.ys.zy.util.TimeUtil;
import com.ys.zy.util.YS;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.ys.zy.fast3.activity.Fast3Activity.TYPE_1FK3;
import static com.ys.zy.fast3.activity.Fast3Activity.TYPE_5FK3;
import static com.ys.zy.fast3.activity.Fast3Activity.TYPE_JSK3;

public class Fast3TZFragment extends BaseFragment implements View.OnClickListener {
    private GridView gv;
    private List<Fast3Bean> list;
    private Fast3SumAdapter fast3SumAdapter;
    private EditText priceET;
    private TextView clearTV;
    private LinearLayout priceLL;
    private TextView zhuAndPriceTV;
    private TextView tzTV;
    private int type;
    private String gameName;
    private String gameNo = "0214983";
    private Fast3HistoryAdapter fast3HistoryAdapter;
    private List<SscResultBean.DataBean> historyList;
    private ListView historyLV;
    private ImageView showHistoryIV;
    private boolean isShowHistory = false;
    private LinearLayout historyLL;
    private LinearLayout dataLL;
    private ImageView iv01, iv02, iv03;
    private TextView newResultTV, tzTipTV, djsTV;
    private int jgTime = 1;
    private int gameType = 1005;
    private boolean isStart = true;
    private String userId;

    public static Fast3TZFragment newInstance(int type) {
        Fast3TZFragment fast3TZFragment = new Fast3TZFragment();
        fast3TZFragment.setType(type);
        return fast3TZFragment;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
        if (type == Fast3Activity.TYPE_JSK3) {
            gameType = YS.CODE_JSK3;
        } else if (type == Fast3Activity.TYPE_1FK3) {
            gameType = YS.CODE_1FK3;
        } else {
            gameType = YS.CODE_5FK3;
        }
        jgTime = Fast3Util.getJGTime(type);
    }

    @Override
    protected void init() {
        switch (type) {
            case TYPE_JSK3:
                gameName = "10分快3";
                break;
            case TYPE_1FK3:
                gameName = "1分快3";
                break;
            case TYPE_5FK3:
                gameName = "5分快3";
                break;
        }
        iv01 = getView(R.id.iv01);
        iv02 = getView(R.id.iv02);
        iv03 = getView(R.id.iv03);
        newResultTV = getView(R.id.tv_newResult);
        tzTipTV = getView(R.id.tv_tzTip);
        djsTV = getView(R.id.tv_djs);
        zhuAndPriceTV = getView(R.id.tv_zhuAndPrice);
        tzTV = getView(R.id.tv_tz);
        tzTV.setOnClickListener(this);
        gv = getView(R.id.gv_);
        list = new ArrayList<>();
        list.addAll(Fast3Bean.getList());
        fast3SumAdapter = new Fast3SumAdapter(mContext, list, R.layout.item_fast3);
        gv.setAdapter(fast3SumAdapter);
        fast3SumAdapter.setChooseListener(new Fast3SumAdapter.ChooseListener() {
            @Override
            public void chooseResult(List<Fast3Bean> list) {
                zhuAndPriceTV.setText("共" + list.size() + "注," + list.size() * StringUtil.StringToDoubleTwo(priceET.getText().toString()) + "元");
                if (list.size() > 0) {
                    priceLL.setVisibility(View.VISIBLE);
                } else {
                    priceLL.setVisibility(View.GONE);
                }
                isCanTZ();
            }
        });
        priceET = getView(R.id.et_price);
        priceET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (priceET.getText().toString().matches("^0")) {//判断当前的输入第一个数是不是为0
                    priceET.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                zhuAndPriceTV.setText("共" + fast3SumAdapter.getTZList().size() + "注," + StringUtil.StringToDoubleStr(fast3SumAdapter.getTZList().size() * StringUtil.StringToDoubleTwo(priceET.getText().toString())) + YS.UNIT);
                isCanTZ();
            }
        });
        clearTV = getView(R.id.tv_clear);
        clearTV.setOnClickListener(this);
        priceLL = getView(R.id.ll_price);
        historyList = new ArrayList<>();
        fast3HistoryAdapter = new Fast3HistoryAdapter(mContext, historyList, R.layout.item_fast3_history);
        historyLV = getView(R.id.lv_);
        historyLV.setAdapter(fast3HistoryAdapter);
        showHistoryIV = getView(R.id.iv_showHistory);
        getView(R.id.ll_left).setOnClickListener(this);
        historyLL = getView(R.id.ll_history);
        dataLL = getView(R.id.ll_data);
        userId = UserSP.getUserId(mContext);
    }

    @Override
    protected void getData() {
        getOdds();
        startRandomText();
        start();
        getResult();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        closeRandomText();
        cancel();
        isStart = false;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_fast3_tz;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_clear:
                clear();
                break;
            case R.id.tv_tz:
                if (fast3SumAdapter.getTZList().size() == 0) {
                    DialogUtil.showTip(mContext, "请至少选择一注号码投注", new TipFragment.ClickListener() {
                        @Override
                        public void sure() {
                            DialogUtil.removeDialog(mContext);
                        }
                    });
                } else if (StringUtil.isBlank(priceET.getText().toString())) {
                    DialogUtil.showTip(mContext, "请填写您要投注的金额", new TipFragment.ClickListener() {
                        @Override
                        public void sure() {
                            DialogUtil.removeDialog(mContext);
                        }
                    });
                } else if (!isMoneyEnough()) {
                    DialogUtil.showTip(mContext, "温馨提示", "您的余额不足！", "去充值", new TipFragment.ClickListener() {
                        @Override
                        public void sure() {
                            DialogUtil.removeDialog(mContext);
                            startActivity(new Intent(mContext, RechargeActivity.class));
                        }
                    });
                } else {
                    DialogUtil.showTZTip(mContext, gameName, gameNo, StringUtil.StringToDoubleStr(fast3SumAdapter.getTZList().size() * StringUtil.StringToDoubleTwo(priceET.getText().toString())), fast3SumAdapter.getTZResult(), new TZTipFragment.ClickListener() {
                        @Override
                        public void sure() {
                            DialogUtil.removeDialog(mContext);
                            tz();
//                            show("投注成功");
                        }
                    });
                }
                break;
            case R.id.ll_left:
                if (isShowHistory) {
                    isShowHistory = false;
                    showHistoryIV.setImageResource(R.mipmap.top_btn_more);
                    historyLL.setVisibility(View.GONE);
                    dataLL.setVisibility(View.VISIBLE);
                } else {
                    isShowHistory = true;
                    showHistoryIV.setImageResource(R.mipmap.bottom_btn_more);
                    historyLL.setVisibility(View.VISIBLE);
                    dataLL.setVisibility(View.GONE);
                }
                break;
        }
    }

    private void clear() {
        fast3SumAdapter.clear();
    }

    private void tz() {
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("gameCode", gameType);
        map.put("complantTypeCode", "1000");//手动
        map.put("periodsNum", gameNo);
        List<Map<String, Object>> list = new ArrayList<>();
        List<Fast3Bean> tzList = fast3SumAdapter.getTZList();
        double payMoney = StringUtil.StringToDoubleTwo(priceET.getText().toString());
        if (tzList != null && tzList.size() > 0) {
            for (Fast3Bean fast3Bean : tzList) {
                Map<String, Object> tzMap = new HashMap<>();
                tzMap.put("times", 1);
                tzMap.put("payMoney", payMoney);
                tzMap.put("betsNum", fast3Bean.value);
                list.add(tzMap);
            }
        }
        map.put("betDetail", list);
        String json = new Gson().toJson(map);
        HttpUtil.tz(mContext, json, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                BaseBean baseBean = new Gson().fromJson(response.get(), BaseBean.class);
                if (baseBean != null) {
                    if (YS.SUCCESE.equals(baseBean.code)) {
                        show("投注成功");
                        //通知Activity刷新余额
                        ((Fast3Activity) getActivity()).getData();
                        sendMsg();//刷新投注记录
                        clear();
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

    //是否余额不足
    private boolean isMoneyEnough() {
        double tzMoney = fast3SumAdapter.getTZList().size() * StringUtil.StringToDoubleTwo(priceET.getText().toString());
        double totalMoney = ((Fast3Activity) getActivity()).getMoney();
        if (tzMoney <= totalMoney) {
            return true;
        }
        return false;
    }

    private RandomThraed randomThraed;
    private int[] drawables = new int[]{R.mipmap.k3_s1_icon, R.mipmap.k3_s2_icon, R.mipmap.k3_s3_icon, R.mipmap.k3_s4_icon, R.mipmap.k3_s5_icon, R.mipmap.k3_s6_icon};

    /**
     * 线程是否正在进行
     *
     * @return
     */
    public boolean isRamdomRunning() {
        return randomThraed != null && !randomThraed.isOver();
    }

    private void startRandomText() {
        if (randomThraed != null) {
            randomThraed.setOver(true);
            randomThraed.interrupt();
            randomThraed = null;
        }
        randomThraed = new RandomThraed();
        randomThraed.start();
    }

    private void closeRandomText() {
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

    private int i = 0;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {
                iv01.setImageResource(drawables[i]);
                iv02.setImageResource(drawables[i]);
                iv03.setImageResource(drawables[i]);
                i++;
                if (i > 5) {
                    i = 0;
                }
            } else if (msg.what == 1) {

            }
        }
    };


    private CountDownTimer countDownTimer;

    private void initTimer() {
        countDownTimer = new CountDownTimer(24 * 60 * 60 * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                YS.add();
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

    private void setStatus() {
        if (Fast3Util.isK3Running(type)) {
            String currentNo = Fast3Util.getK3Periods(type, 1);//当前期
            String lastNo = Fast3Util.getK3Periods(type, 0);//上一期
            String nextNo = Fast3Util.getK3Periods(type, 2);//下一期
            gameNo = currentNo;
            tzTipTV.setText(currentNo + "期投注截止");
            newResultTV.setText(lastNo + "期开奖号码");
            int totalSecond = (StringUtil.StringToInt(nextNo.substring(4)) - 1) * jgTime * 60 - 1;
            if (type == Fast3Activity.TYPE_JSK3) {
                if (StringUtil.StringToInt(currentNo.substring(4)) == 41) {
                    totalSecond = 41 * jgTime * 60 - 1 + (8 * 60 + 30) * 60;
                } else {
                    totalSecond = (StringUtil.StringToInt(nextNo.substring(4)) - 1) * jgTime * 60 - 1 + (8 * 60 + 30) * 60;
                }
            }
            Calendar now = Calendar.getInstance();
            now.setTime(YS.getCurrentDate());
            int hour = now.get(Calendar.HOUR_OF_DAY);
            int minute = now.get(Calendar.MINUTE);
            int second = now.get(Calendar.SECOND);
            int differenceSecond = totalSecond - ((hour * 60 + minute) * 60 + second);
            L.e("differenceSecond=" + differenceSecond);
            djsTV.setText(TimeUtil.getTime(differenceSecond));
            if (differenceSecond == 0) {
                DialogUtil.removeDialog(mContext);
            }
            if (type == TYPE_JSK3) {
                if (differenceSecond == 599) {
                    //请求lastNo的开奖数据，取服务器最新一期的开奖结果，如果不是lastNo的就一直转圈圈。
                    if (!isRamdomRunning()) {
                        startRandomText();
                        L.e("599当前期：" + currentNo);
                        L.e("599上一期：" + lastNo);
                    }
                }
            } else if (type == TYPE_5FK3) {
                if (differenceSecond == 299) {
                    //请求lastNo的开奖数据，取服务器最新一期的开奖结果，如果不是lastNo的就一直转圈圈。
                    if (!isRamdomRunning()) {
                        startRandomText();
                        L.e("299当前期：" + currentNo);
                        L.e("299上一期：" + lastNo);
                    }
                }
            } else if (type == TYPE_1FK3) {
                if (differenceSecond == 59) {
                    //请求lastNo的开奖数据，取服务器最新一期的开奖结果，如果不是lastNo的就一直转圈圈。
                    if (!isRamdomRunning()) {
                        startRandomText();
                        L.e("59当前期：" + currentNo);
                        L.e("59上一期：" + lastNo);
                    }
                }
            }

            if (hasResult(lastNo)) {
                closeRandomText();
                List<Integer> list = getResult(lastNo);
                if (list.size() == 3) {
                    iv01.setImageResource(drawables[list.get(0) - 1]);
                    iv02.setImageResource(drawables[list.get(1) - 1]);
                    iv03.setImageResource(drawables[list.get(2) - 1]);
                }
            }
        } else {
            //快3游戏不在游戏时间段内
            L.e("快3游戏不在游戏时间段内");
            gameNo = Fast3Util.getNextJSK3Periods();
            tzTipTV.setText(gameNo + "期投注截止");
            djsTV.setText("预售中");
            String lastN = Fast3Util.getLastJSK3Periods();
            newResultTV.setText(lastN + "期开奖号码");
            if (hasResult(lastN)) {
                closeRandomText();
                List<Integer> list = getResult(lastN);
                if (list.size() == 3) {
                    iv01.setImageResource(drawables[list.get(0) - 1]);
                    iv02.setImageResource(drawables[list.get(1) - 1]);
                    iv03.setImageResource(drawables[list.get(2) - 1]);
                }
            }
        }
    }


    private void isCanTZ() {
        int tzNum = fast3SumAdapter.getTZList().size();
        double price = StringUtil.StringToDouble(priceET.getText().toString());
        if (tzNum == 0 || price == 0) {
            tzTV.setTextColor(Color.parseColor("#a5a5a5"));
            tzTV.setBackgroundResource(R.drawable.rect_cornor_gray5);
//            tzTV.setClickable(false);
        } else {
            tzTV.setTextColor(Color.parseColor("#dd2230"));
            tzTV.setBackgroundResource(R.drawable.rect_cornor_red4);
//            tzTV.setClickable(true);
        }
    }

    private void getResult() {
        HttpUtil.getSscResult(mContext, gameType, 50, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                historyList.clear();
                SscResultBean sscResultBean = new Gson().fromJson(response.get(), SscResultBean.class);
                if (sscResultBean != null) {
                    if (!isTB) {
                        YS.setCurrentTimeMillis(sscResultBean.systemDate + YS.TIME_DELAY);
                        isTB = true;
                    }
                }
                if (sscResultBean != null && YS.SUCCESE.equals(sscResultBean.code) && sscResultBean.data != null && sscResultBean.data.size() > 0) {
                    historyList.addAll(sscResultBean.data);
                }
                fast3HistoryAdapter.refresh(historyList);
                if (isStart) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            getResult();
                        }
                    }, 3000);
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                getResult();
            }
        });
    }

    /**
     * 是否已经开出这期结果
     *
     * @param lastNo
     * @return
     */
    private boolean hasResult(String lastNo) {
        boolean hasResult = false;
        if (historyList.size() > 0) {
            for (int i = 0; i < historyList.size(); i++) {
                if (lastNo.equals(historyList.get(i).periodsNum)) {
                    hasResult = true;
                    break;
                }
            }
        }
        return hasResult;
    }

    private List<Integer> getResult(String lastNo) {
        List<Integer> list = new ArrayList<>();
        if (historyList.size() > 0) {
            for (int i = 0; i < historyList.size(); i++) {
                if (lastNo.equals(historyList.get(i).periodsNum)) {
                    String result = historyList.get(i).lotteryNum;
                    String[] str = result.split(",");
                    if (str != null & str.length > 0) {
                        for (String s : str) {
                            list.add(StringUtil.StringToInt(s));
                        }
                    }
                }
            }
        }
        return list;
    }

    private void sendMsg() {
        Intent intent = new Intent(YS.ACTION_TZ_SUCCESS);
        getActivity().sendBroadcast(intent);
    }

    private void getOdds() {
        HttpUtil.getGameOdds(mContext, userId, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                OddsBean oddsBean = new Gson().fromJson(response.get(), OddsBean.class);
                if (oddsBean != null && YS.SUCCESE.equals(oddsBean.code) && oddsBean.data != null) {
                    list.clear();
                    List<Fast3Bean> tempList = new ArrayList<>();
                    tempList.add(new Fast3Bean("大", oddsBean.data.ksDxds));
                    tempList.add(new Fast3Bean("小", oddsBean.data.ksDxds));
                    tempList.add(new Fast3Bean("单", oddsBean.data.ksDxds));
                    tempList.add(new Fast3Bean("双", oddsBean.data.ksDxds));
                    tempList.add(new Fast3Bean("3", oddsBean.data.ksHz318));
                    tempList.add(new Fast3Bean("4", oddsBean.data.ksHz417));
                    tempList.add(new Fast3Bean("5", oddsBean.data.ksHz516));
                    tempList.add(new Fast3Bean("6", oddsBean.data.ksHz615));
                    tempList.add(new Fast3Bean("7", oddsBean.data.ksHz714));
                    tempList.add(new Fast3Bean("8", oddsBean.data.ksHz813));
                    tempList.add(new Fast3Bean("9", oddsBean.data.ksHz912));
                    tempList.add(new Fast3Bean("10", oddsBean.data.ksHz1011));
                    tempList.add(new Fast3Bean("11", oddsBean.data.ksHz1011));
                    tempList.add(new Fast3Bean("12", oddsBean.data.ksHz912));
                    tempList.add(new Fast3Bean("13", oddsBean.data.ksHz813));
                    tempList.add(new Fast3Bean("14", oddsBean.data.ksHz714));
                    tempList.add(new Fast3Bean("15", oddsBean.data.ksHz615));
                    tempList.add(new Fast3Bean("16", oddsBean.data.ksHz516));
                    tempList.add(new Fast3Bean("17", oddsBean.data.ksHz417));
                    tempList.add(new Fast3Bean("18", oddsBean.data.ksHz318));
                    list.addAll(tempList);
                    fast3SumAdapter.refresh(list);
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {

            }
        });
    }


}
