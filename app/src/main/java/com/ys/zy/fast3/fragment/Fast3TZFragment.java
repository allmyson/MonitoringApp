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

import com.ys.zy.R;
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
import com.ys.zy.racing.RacingUtil;
import com.ys.zy.racing.activity.RacingActivity;
import com.ys.zy.util.L;
import com.ys.zy.util.StringUtil;
import com.ys.zy.util.TimeUtil;
import com.ys.zy.util.YS;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
    private List<Object> historyList;
    private ListView historyLV;
    private ImageView showHistoryIV;
    private boolean isShowHistory = false;
    private LinearLayout historyLL;
    private LinearLayout dataLL;
    private ImageView iv01, iv02, iv03;
    private TextView newResultTV, tzTipTV, djsTV;
    private int jgTime = 1;

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
        jgTime = Fast3Util.getJGTime(type);
    }

    @Override
    protected void init() {
        switch (type) {
            case TYPE_JSK3:
                gameName = "江苏快3";
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
        historyList.add(null);
        historyList.add(null);
        historyList.add(null);
        historyList.add(null);
        historyList.add(null);
        historyList.add(null);
        historyList.add(null);
        historyList.add(null);
        historyList.add(null);
        historyList.add(null);
        fast3HistoryAdapter = new Fast3HistoryAdapter(mContext, historyList, R.layout.item_fast3_history);
        historyLV = getView(R.id.lv_);
        historyLV.setAdapter(fast3HistoryAdapter);
        showHistoryIV = getView(R.id.iv_showHistory);
        showHistoryIV.setOnClickListener(this);
        historyLL = getView(R.id.ll_history);
        dataLL = getView(R.id.ll_data);
    }

    @Override
    protected void getData() {
        start();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        closeRandomText();
        cancel();
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_fast3_tz;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_clear:
                fast3SumAdapter.clear();
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
                            show("投注成功");
                        }
                    });
                }
                break;
            case R.id.iv_showHistory:
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
            tzTipTV.setText(currentNo + "期投注截止");
            newResultTV.setText(lastNo + "期开奖号码");
            int totalSecond = (StringUtil.StringToInt(nextNo.substring(4)) - 1) * jgTime * 60 - 1;
            if (type == Fast3Activity.TYPE_JSK3) {
                totalSecond = (StringUtil.StringToInt(nextNo.substring(4)) - 1) * jgTime * 60 - 1 + (8 * 60 + 30) * 60;
            }
            Calendar now = Calendar.getInstance();
            now.setTime(new Date());
            int hour = now.get(Calendar.HOUR_OF_DAY);
            int minute = now.get(Calendar.MINUTE);
            int second = now.get(Calendar.SECOND);
            int differenceSecond = totalSecond - ((hour * 60 + minute) * 60 + second);
            L.e("differenceSecond=" + differenceSecond);
            djsTV.setText(TimeUtil.getTime(differenceSecond));
            if (differenceSecond == 59) {
                //请求lastNo的开奖数据，取服务器最新一期的开奖结果，如果不是lastNo的就一直转圈圈。
                startRandomText();
                L.e("59当前期：" + currentNo);
                L.e("59上一期：" + lastNo);
            }
        } else {
            //快3游戏不在游戏时间段内
            L.e("快3游戏不在游戏时间段内");
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
}
