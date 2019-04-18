package com.ys.zy.racing.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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
import com.ys.zy.activity.RechargeActivity;
import com.ys.zy.base.BaseFragment;
import com.ys.zy.dialog.DialogUtil;
import com.ys.zy.dialog.TZTipFragment;
import com.ys.zy.dialog.TipFragment;
import com.ys.zy.fast3.activity.Fast3Activity;
import com.ys.zy.racing.RacingUtil;
import com.ys.zy.racing.activity.RacingActivity;
import com.ys.zy.racing.adapter.DwdAdapter;
import com.ys.zy.racing.adapter.RacingResultAdapter;
import com.ys.zy.racing.adapter.ScHistoryAdapter;
import com.ys.zy.util.L;
import com.ys.zy.util.StringUtil;
import com.ys.zy.util.TimeUtil;
import com.ys.zy.util.YS;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class RacingTZFragment extends BaseFragment implements View.OnClickListener {
    private int type;
    private int play;
    private GridView resultGV;
    private RacingResultAdapter racingResultAdapter;
    private ImageView showHistoryIV;
    private LinearLayout leftLL;
    private boolean isShowHistory = false;
    private LinearLayout historyLL;
    private List<Object> historyList;
    private ListView historyLV;
    private ScHistoryAdapter scHistoryAdapter;
    private LinearLayout dataLL;
    private RacingFragment currentFragment;
    private RacingFragment dwdFragment, dxdsFragment, lhdFragment;
    private FragmentManager manager;
    private TextView zhuAndPriceTV, tzResultTV;
    private EditText beiET, qiET;
    private int tzNum = 0;
    private TextView tzTV, clearTV;
    private TextView newResultTV, tzTipTV, djsTV;
    private int jgTime = 1;
    private List<String> resultList;

    public static RacingTZFragment newInstance(int type, int play) {
        RacingTZFragment racingTZFragment = new RacingTZFragment();
        racingTZFragment.setType(type);
        racingTZFragment.setPlay(play);
        return racingTZFragment;
    }

    @Override
    protected void init() {
        resultList = new ArrayList<>();
        resultList.addAll(getResultList());
        newResultTV = getView(R.id.tv_newResult);
        tzTipTV = getView(R.id.tv_tzTip);
        djsTV = getView(R.id.tv_djs);
        zhuAndPriceTV = getView(R.id.tv_zhuAndPrice);
        tzResultTV = getView(R.id.tv_tzResult);
        tzTV = getView(R.id.tv_tz);
        clearTV = getView(R.id.tv_clear);
        tzTV.setOnClickListener(this);
        clearTV.setOnClickListener(this);
        beiET = getView(R.id.et_bei);
        qiET = getView(R.id.et_qi);
        beiET.addTextChangedListener(new TextWatcher1());
        qiET.addTextChangedListener(new TextWatcher2());
        manager = getChildFragmentManager();
        resultGV = getView(R.id.gv_result);
        racingResultAdapter = new RacingResultAdapter(mContext, resultList, R.layout.item_text);
        resultGV.setAdapter(racingResultAdapter);
        showHistoryIV = getView(R.id.iv_showHistory);
        showHistoryIV.setColorFilter(Color.parseColor("#a5a5a5"));
        leftLL = getView(R.id.ll_left);
        leftLL.setOnClickListener(this);
        historyLL = getView(R.id.ll_history);

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
        scHistoryAdapter = new ScHistoryAdapter(mContext, historyList, R.layout.item_sc_history);
        historyLV = getView(R.id.lv_);
        historyLV.setAdapter(scHistoryAdapter);

        dataLL = getView(R.id.ll_data);
        initFragment();
        showFragment(dwdFragment);
        isCanTZ();
    }

    @Override
    protected void getData() {
        start();
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_racing_tz;
    }

    public void setType(int type) {
        this.type = type;
        jgTime = RacingUtil.getJGTime(type);
    }

    public void setPlay(int play) {
        this.play = play;
    }

    private List<String> getResultList() {
        List<String> list = new ArrayList<>();
        list.add("01");
        list.add("02");
        list.add("03");
        list.add("04");
        list.add("05");
        list.add("06");
        list.add("07");
        list.add("08");
        list.add("09");
        list.add("10");
        return list;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
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
            case R.id.tv_clear:
                clearData();
                break;
            case R.id.tv_tz:
                String result = currentFragment.getTZResult();
                L.e(result);
                if (!isMoneyEnough()) {
                    DialogUtil.showTip(mContext, "温馨提示", "您的余额不足！", "去充值", new TipFragment.ClickListener() {
                        @Override
                        public void sure() {
                            DialogUtil.removeDialog(mContext);
                            startActivity(new Intent(mContext, RechargeActivity.class));
                        }
                    });
                } else {
                    int bei = StringUtil.StringToInt(beiET.getText().toString());
                    int qi = StringUtil.StringToInt(qiET.getText().toString());
                    String tzMoney = StringUtil.StringToDoubleStr("" + tzNum * bei * qi * YS.SINGLE_PRICE);
                    String gameName = ((RacingActivity) getActivity()).getGameName();
                    String gameNo = ((RacingActivity) getActivity()).getGameNo();
                    int qi2 = qi;
                    if (qi > 1) {
                        qi2 = StringUtil.StringToInt(RacingUtil.getSCPeriods(type, qi).substring(4)) - StringUtil.StringToInt(gameNo.substring(4)) + 1;
                        gameNo = "第" + gameNo + "期至第" + RacingUtil.getSCPeriods(type, qi) + "期，共" + qi2;
                        tzMoney = StringUtil.StringToDoubleStr("" + tzNum * bei * qi2 * YS.SINGLE_PRICE);
                    }
                    zhuAndPriceTV.setText("共" + tzNum + "注" + bei + "倍" + qi2 + "期" + tzMoney + YS.UNIT);
                    qiET.setText("" + qi2);
                    qiET.setSelection(qiET.getText().length());
                    DialogUtil.showTZTip(mContext, gameName, gameNo, tzMoney, currentFragment.getTZResult(), new TZTipFragment.ClickListener() {
                        @Override
                        public void sure() {
                            DialogUtil.removeDialog(mContext);
                            show("投注成功");
                        }
                    });
                }
                break;
        }
    }

    /**
     * 初始化Fragment
     */
    private void initFragment() {
        dwdFragment = RacingDWDFragment.newInstance();
        dxdsFragment = RacingDXDSFragment.newInstance();
        lhdFragment = RacingLHDFragment.newInstance();
    }

    /**
     * @param fragment
     */
    public void showFragment(RacingFragment fragment) {
        try {
            if (currentFragment != fragment) {
                FragmentTransaction transaction = manager.beginTransaction();
                if (currentFragment != null) {
                    transaction.hide(currentFragment);
                }
                currentFragment = fragment;
                if (!fragment.isAdded()) {
                    transaction.add(R.id.ll_data, fragment).show(fragment).commitAllowingStateLoss();
                } else {
                    transaction.show(fragment).commitAllowingStateLoss();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param type
     */
    public void showFragment(String type) {
        if ("定位胆".equals(type)) {
            showFragment(dwdFragment);
        } else if ("大小单双".equals(type)) {
            showFragment(dxdsFragment);
        } else if ("龙虎斗".equals(type)) {
            showFragment(lhdFragment);
        }
    }

    public void change(int tzNum, String showResult) {
        this.tzNum = tzNum;
        if (tzNum == 0) {
            tzResultTV.setVisibility(View.GONE);
        } else {
            tzResultTV.setVisibility(View.VISIBLE);
        }
        tzResultTV.setText(showResult);
        int bei = StringUtil.StringToInt(beiET.getText().toString());
        int qi = StringUtil.StringToInt(qiET.getText().toString());
        zhuAndPriceTV.setText("共" + tzNum + "注" + bei + "倍" + qi + "期" + StringUtil.StringToDoubleStr(tzNum * bei * qi * YS.SINGLE_PRICE) + YS.UNIT);
        isCanTZ();
    }

    public void clearData() {
        currentFragment.clearData();
        this.tzNum = 0;
        beiET.setText("");
        qiET.setText("");
        tzResultTV.setVisibility(View.GONE);
        isCanTZ();
    }

    private void isCanTZ() {
        int bei = StringUtil.StringToInt(beiET.getText().toString());
        int qi = StringUtil.StringToInt(qiET.getText().toString());
        if (tzNum == 0 || bei == 0 || qi == 0) {
            tzTV.setTextColor(Color.parseColor("#a5a5a5"));
            tzTV.setBackgroundResource(R.drawable.rect_cornor_gray5);
            tzTV.setClickable(false);
        } else {
            tzTV.setTextColor(Color.parseColor("#dd2230"));
            tzTV.setBackgroundResource(R.drawable.rect_cornor_red4);
            tzTV.setClickable(true);
        }
    }

    //是否余额不足
    private boolean isMoneyEnough() {
        int bei = StringUtil.StringToInt(beiET.getText().toString());
        int qi = StringUtil.StringToInt(qiET.getText().toString());
        double tzMoney = StringUtil.StringToDouble("" + tzNum * bei * qi * YS.SINGLE_PRICE);
        double totalMoney = ((RacingActivity) getActivity()).getMoney();
        if (tzMoney <= totalMoney) {
            return true;
        }
        return false;
    }

    public class TextWatcher1 implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            int bei = StringUtil.StringToInt(beiET.getText().toString());
            int qi = StringUtil.StringToInt(qiET.getText().toString());
            zhuAndPriceTV.setText("共" + tzNum + "注" + bei + "倍" + qi + "期" + StringUtil.StringToDoubleStr(tzNum * bei * qi * YS.SINGLE_PRICE) + YS.UNIT);
            isCanTZ();
        }
    }

    public class TextWatcher2 implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            int bei = StringUtil.StringToInt(beiET.getText().toString());
            int qi = StringUtil.StringToInt(qiET.getText().toString());
            zhuAndPriceTV.setText("共" + tzNum + "注" + bei + "倍" + qi + "期" + StringUtil.StringToDoubleStr(tzNum * bei * qi * YS.SINGLE_PRICE) + YS.UNIT);
            isCanTZ();
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
        racingResultAdapter.closeRandom();
    }

    private void setStatus() {
        if (RacingUtil.isScRunning(type)) {
            String currentNo = RacingUtil.getSCPeriods(type, 1);//当前期
            String lastNo = RacingUtil.getSCPeriods(type, 0);//上一期
            String nextNo = RacingUtil.getSCPeriods(type, 2);//下一期
            tzTipTV.setText(currentNo + "期投注截止");
            newResultTV.setText(lastNo + "期开奖号码");
            int totalSecond = (StringUtil.StringToInt(nextNo.substring(4)) - 1) * jgTime * 60 - 1;
            if (type == RacingActivity.TYPE_BJSC) {
                totalSecond = (StringUtil.StringToInt(nextNo.substring(4)) - 1) * jgTime * 60 - 1 + (9 * 60 + 10) * 60;
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
                racingResultAdapter.startRandom();
                L.e("59当前期：" + currentNo);
                L.e("59上一期：" + lastNo);
            }
        } else {
            //赛车游戏不在游戏时间段内
        }
    }

}
