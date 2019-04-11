package com.ys.zy.roulette.fragment;

import android.graphics.Color;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.ys.zy.R;
import com.ys.zy.base.BaseFragment;
import com.ys.zy.roulette.adapter.ChipAdapter;
import com.ys.zy.roulette.adapter.LpHistoryAdapter;
import com.ys.zy.roulette.bean.ChipBean;
import com.ys.zy.roulette.bean.LPBean;
import com.ys.zy.roulette.ui.LPView;
import com.ys.zy.ui.HorizontalListView;

import java.util.ArrayList;
import java.util.List;

public class RouletteTZFragment extends BaseFragment implements View.OnClickListener {
    private TextView timeTV, statusTV, qsTV;
    private ImageView moreIV;
    private LinearLayout moreLL;
    private LinearLayout historyLL;
    private boolean isShowHistory = false;
    private ListView historyLV;
    private List<Object> historyList;
    private LpHistoryAdapter historyAdapter;
    private HorizontalListView horizontalListView;
    private List<ChipBean> chipBeanList;
    private ChipAdapter chipAdapter;
    private Button clearBtn, sureBtn;
    private LPView lpView;
    private List<LPBean> lpBeanList;

    public static RouletteTZFragment newInstance() {
        RouletteTZFragment rouletteTZFragment = new RouletteTZFragment();
        return rouletteTZFragment;
    }

    @Override
    protected void init() {
        timeTV = getView(R.id.tv_time);
        statusTV = getView(R.id.tv_status);
        qsTV = getView(R.id.tv_qs);
        moreIV = getView(R.id.iv_more);
        moreIV.setColorFilter(Color.parseColor("#a5a5a5"));
        moreLL = getView(R.id.ll_more);
        moreLL.setOnClickListener(this);
        historyLL = getView(R.id.ll_history);
        historyLV = getView(R.id.lv_history);
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
        historyAdapter = new LpHistoryAdapter(mContext, historyList, R.layout.item_lp_history);
        historyLV.setAdapter(historyAdapter);

        horizontalListView = getView(R.id.hlv_);
        chipBeanList = new ArrayList<>();
        chipBeanList.addAll(ChipBean.getList());
        chipAdapter = new ChipAdapter(mContext, chipBeanList, R.layout.item_iv);
        horizontalListView.setAdapter(chipAdapter);
        chipAdapter.setHorizontalListView(horizontalListView);
        horizontalListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                chipAdapter.setSelectItem(position);
//                setBtnClickable(true, sureBtn);
            }
        });

        clearBtn = getView(R.id.btn_clear);
        sureBtn = getView(R.id.btn_sure);
        clearBtn.setOnClickListener(this);
        sureBtn.setOnClickListener(this);
        setBtnClickable(false, sureBtn);
        lpView = getView(R.id.lpv_);
        lpBeanList = new ArrayList<>();
        lpBeanList.addAll(LPBean.getList());
        lpView.setData(lpBeanList);
        lpView.setClickListener(new LPView.ClickListener() {
            @Override
            public void click(int position, LPBean lpBean) {
                if (chipAdapter.getChooseData() != null) {
                    lpBeanList.get(position).myValue = chipAdapter.getChooseData().money;
                    lpView.setData(lpBeanList);
                    setBtnClickable(true, sureBtn);
                }
            }
        });
    }

    @Override
    protected void getData() {

    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_roulette_tz;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_more:
                if (isShowHistory) {
                    isShowHistory = false;
                    moreIV.setImageResource(R.mipmap.slz_notice_btn_more);
                    historyLL.setVisibility(View.GONE);
                } else {
                    isShowHistory = true;
                    moreIV.setImageResource(R.mipmap.slz_notice_btn_more_up);
                    historyLL.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.btn_clear:
//                chipAdapter.clear();
                for (int i = 0; i < lpBeanList.size(); i++) {
                    lpBeanList.get(i).myValue = 0;
                }
                lpView.setData(lpBeanList);
                setBtnClickable(false, sureBtn);
                break;
            case R.id.btn_sure:
                break;
        }
    }
}
