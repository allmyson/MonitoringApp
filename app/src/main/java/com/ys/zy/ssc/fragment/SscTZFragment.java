package com.ys.zy.ssc.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.os.Handler;
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

import com.google.gson.Gson;
import com.yanzhenjie.nohttp.rest.Response;
import com.ys.zy.R;
import com.ys.zy.activity.RechargeActivity;
import com.ys.zy.base.BaseFragment;
import com.ys.zy.dialog.DialogUtil;
import com.ys.zy.dialog.TZTipFragment;
import com.ys.zy.dialog.TipFragment;
import com.ys.zy.http.HttpListener;
import com.ys.zy.racing.RacingUtil;
import com.ys.zy.racing.activity.RacingActivity;
import com.ys.zy.racing.adapter.RacingResultAdapter;
import com.ys.zy.racing.adapter.ScHistoryAdapter;
import com.ys.zy.racing.fragment.RacingDWDFragment;
import com.ys.zy.racing.fragment.RacingDXDSFragment;
import com.ys.zy.racing.fragment.RacingFragment;
import com.ys.zy.racing.fragment.RacingLHDFragment;
import com.ys.zy.ssc.SscUtil;
import com.ys.zy.ssc.activity.SscActivity;
import com.ys.zy.ssc.adapter.SscHistoryAdapter;
import com.ys.zy.ssc.adapter.SscResultAdapter;
import com.ys.zy.ssc.bean.SscResultBean;
import com.ys.zy.util.HttpUtil;
import com.ys.zy.util.L;
import com.ys.zy.util.StringUtil;
import com.ys.zy.util.TimeUtil;
import com.ys.zy.util.YS;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class SscTZFragment extends BaseFragment implements View.OnClickListener {
    private int type;
    private int play;
    private GridView resultGV;
    private SscResultAdapter sscResultAdapter;
    private ImageView showHistoryIV;
    private LinearLayout leftLL;
    private boolean isShowHistory = false;
    private LinearLayout historyLL;
    private List<SscResultBean.DataBean> historyList;
    private ListView historyLV;
    private SscHistoryAdapter sscHistoryAdapter;
    private LinearLayout dataLL;
    private SscFragment currentFragment;
    private SscFragment dwdFragment, dxdsFragment, h2xFragment, wxFragment;
    private FragmentManager manager;
    private TextView zhuAndPriceTV, tzResultTV;
    private EditText beiET, qiET;
    private int tzNum = 0;
    private TextView tzTV, clearTV;
    private TextView newResultTV, tzTipTV, djsTV;
    private int jgTime = 1;
    private List<Integer> resultList;
    private int gameType = 1000;
    private int year;

    public static SscTZFragment newInstance(int type, int play) {
        SscTZFragment sscTZFragment = new SscTZFragment();
        sscTZFragment.setType(type);
        sscTZFragment.setPlay(play);
        return sscTZFragment;
    }

    @Override
    protected void init() {
        Calendar now = Calendar.getInstance();
        year = now.get(Calendar.YEAR);
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
        sscResultAdapter = new SscResultAdapter(mContext, resultList, R.layout.item_text2);
        resultGV.setAdapter(sscResultAdapter);
        showHistoryIV = getView(R.id.iv_showHistory);
        showHistoryIV.setColorFilter(Color.parseColor("#a5a5a5"));
        leftLL = getView(R.id.ll_left);
        leftLL.setOnClickListener(this);
        historyLL = getView(R.id.ll_history);

        historyList = new ArrayList<>();
        sscHistoryAdapter = new SscHistoryAdapter(mContext, historyList, R.layout.item_ssc_history);
        historyLV = getView(R.id.lv_);
        historyLV.setAdapter(sscHistoryAdapter);

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
        return R.layout.fragment_ssc_tz;
    }

    public void setType(int type) {
        this.type = type;
        jgTime = SscUtil.getJGTime(type);
        if (type == SscActivity.TYPE_SSC) {
            gameType = 1000;
        } else {
            gameType = 1001;
        }
    }

    public void setPlay(int play) {
        this.play = play;
    }

    private List<Integer> getResultList() {
        List<Integer> list = new ArrayList<>();
        list.add(0);
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
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
                    String gameName = ((SscActivity) getActivity()).getGameName();
                    String gameNo = ((SscActivity) getActivity()).getGameNo();
                    int qi2 = qi;
                    if (qi > 1) {
                        qi2 = StringUtil.StringToInt(SscUtil.getSscPeriods(type, qi).substring(4)) - StringUtil.StringToInt(gameNo.substring(4)) + 1;
                        gameNo = "第" + gameNo + "期至第" + SscUtil.getSscPeriods(type, qi) + "期，共" + qi2;
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
        dwdFragment = SscDWDFragment.newInstance();
        dxdsFragment = SscDXDSFragment.newInstance();
        h2xFragment = SscH2XFragment.newInstance();
        wxFragment = SscWXFragment.newInstance();
    }

    /**
     * @param fragment
     */
    public void showFragment(SscFragment fragment) {
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
        } else if ("后二星组选".equals(type)) {
            showFragment(h2xFragment);
        } else if ("五星直选".equals(type)) {
            showFragment(wxFragment);
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
        double totalMoney = ((SscActivity) getActivity()).getMoney();
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
        sscResultAdapter.closeRandom();
    }

    private void setStatus() {
        String currentNo = SscUtil.getSscPeriods(type, 1);//当前期
        String lastNo = SscUtil.getSscPeriods(type, 0);//上一期
        String nextNo = SscUtil.getSscPeriods(type, 2);//下一期
        tzTipTV.setText(currentNo + "期投注截止");
        newResultTV.setText(lastNo + "期开奖号码");
        int totalSecond = (StringUtil.StringToInt(nextNo.substring(4)) - 1) * jgTime * 60 - 1;
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
            sscResultAdapter.startRandom();
            L.e("59当前期：" + currentNo);
            L.e("59上一期：" + lastNo);
        }
        if (historyList.size() > 0) {
            for (int i = 0; i < historyList.size(); i++) {
                if ((year + lastNo).equals(historyList.get(i).periodsNum)) {
                    sscResultAdapter.closeRandom();
                    break;
                }
            }
        }
        getResult();
    }


    private void getResult() {
        HttpUtil.getSscResult(mContext, gameType, 50, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                historyList.clear();
                SscResultBean sscResultBean = new Gson().fromJson(response.get(), SscResultBean.class);
                if (sscResultBean != null && YS.SUCCESE.equals(sscResultBean.code) && sscResultBean.data != null && sscResultBean.data.size() > 0) {
                    historyList.addAll(sscResultBean.data);
                }
                sscHistoryAdapter.refresh(historyList);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getResult();
                    }
                }, 3000);
            }

            @Override
            public void onFailed(int what, Response<String> response) {

            }
        });
    }
}
