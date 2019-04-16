package com.ys.zy.racing.fragment;

import android.graphics.Color;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.ys.zy.R;
import com.ys.zy.base.BaseFragment;
import com.ys.zy.racing.adapter.RacingResultAdapter;
import com.ys.zy.racing.adapter.ScHistoryAdapter;

import java.util.ArrayList;
import java.util.List;

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

    public static RacingTZFragment newInstance(int type, int play) {
        RacingTZFragment racingTZFragment = new RacingTZFragment();
        racingTZFragment.setType(type);
        racingTZFragment.setPlay(play);
        return racingTZFragment;
    }

    @Override
    protected void init() {
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
}
