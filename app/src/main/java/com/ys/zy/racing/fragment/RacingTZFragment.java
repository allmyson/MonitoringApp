package com.ys.zy.racing.fragment;

import android.content.Intent;
import android.graphics.Color;
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
import com.ys.zy.util.YS;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RacingTZFragment extends BaseFragment implements View.OnClickListener, TextWatcher {
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

    public static RacingTZFragment newInstance(int type, int play) {
        RacingTZFragment racingTZFragment = new RacingTZFragment();
        racingTZFragment.setType(type);
        racingTZFragment.setPlay(play);
        return racingTZFragment;
    }

    @Override
    protected void init() {
        zhuAndPriceTV = getView(R.id.tv_zhuAndPrice);
        tzResultTV = getView(R.id.tv_tzResult);
        tzTV = getView(R.id.tv_tz);
        clearTV = getView(R.id.tv_clear);
        tzTV.setOnClickListener(this);
        clearTV.setOnClickListener(this);
        beiET = getView(R.id.et_bei);
        qiET = getView(R.id.et_qi);
        beiET.addTextChangedListener(this);
        qiET.addTextChangedListener(this);
        manager = getChildFragmentManager();
        resultGV = getView(R.id.gv_result);
        racingResultAdapter = new RacingResultAdapter(mContext, getResultList(), R.layout.item_text);
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

    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_racing_tz;
    }

    public void setType(int type) {
        this.type = type;
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
                    DialogUtil.showTZTip(mContext, ((RacingActivity) getActivity()).getGameName(), ((RacingActivity) getActivity()).getGameNo(), tzMoney, currentFragment.getTZResult(), new TZTipFragment.ClickListener() {
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
}
