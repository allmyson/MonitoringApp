package com.ys.zy.roulette.fragment;

import android.graphics.Color;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.ys.zy.R;
import com.ys.zy.base.BaseFragment;
import com.ys.zy.dialog.DialogUtil;
import com.ys.zy.dialog.TZTipFragment;
import com.ys.zy.roulette.adapter.ChipAdapter;
import com.ys.zy.roulette.adapter.LpHistoryAdapter;
import com.ys.zy.roulette.bean.ChipBean;
import com.ys.zy.roulette.bean.LPBean;
import com.ys.zy.roulette.ui.LPView;
import com.ys.zy.ui.HorizontalListView;
import com.ys.zy.util.L;
import com.ys.zy.util.StringUtil;

import java.util.ArrayList;
import java.util.Calendar;
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
    private String gameNo = "0215096";

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
                if (chipAdapter.getChooseData() != null && currentStatus == TYPE_TZ) {
                    lpBeanList.get(position).myValue = chipAdapter.getChooseData().money;
                    lpView.setData(lpBeanList);
                    setBtnClickable(true, sureBtn);
                }
            }
        });

    }

    private void setStatus() {
        currentStatus = getGameStatus();
        int second = getCurrentSecond();
        switch (currentStatus) {
            case TYPE_TZ:
                if (chipAdapter.getChooseData() != null && lpView.getTZList().size() > 0) {
                    setBtnClickable(true, sureBtn);
                } else {
                    setBtnClickable(false, sureBtn);
                }
                timeTV.setText(getTimeStr(45 - second));
                statusTV.setText("投注中...");
                break;
            case TYPE_STOP_TZ:
                timeTV.setText(getTimeStr(50 - second));
                statusTV.setText("停止投注...");
                break;
            case TYPE_KJ:
                timeTV.setText(getTimeStr(55 - second));
                statusTV.setText("开奖中...");
                if (second != 54) {
                    if (!lpView.isRamdomRunning()) {
                        L.e("lpView.startRandomColor()执行次数");
                        lpView.startRandomColor();
                    }
                } else {
                    L.e("lpView.setRandomResult()执行次数");
                    lpView.setRandomResult();
                }
                break;
            case TYPE_PJ:
                timeTV.setText(getTimeStr(60 - second));
                statusTV.setText("派奖中...");
                if (second == 59) {
                    L.e("lpView.clearColorAndResult()执行次数");
                    lpView.clearColorAndResult();
                }
                break;
        }
    }

    @Override
    protected void getData() {
        start();
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
                List<LPBean> result = lpView.getTZList();
                if (result != null && result.size() > 0) {
                    double sum = 0;
                    String content = "";
                    for (int i = 0; i < result.size(); i++) {
                        sum += result.get(i).myValue;
                        if (i != result.size() - 1) {
                            content += result.get(i).name + ",";
                        } else {
                            content += result.get(i).name;
                        }
                    }
                    DialogUtil.showTZTip(mContext, "轮盘", gameNo, StringUtil.StringToDoubleStr(sum), content, new TZTipFragment.ClickListener() {
                        @Override
                        public void sure() {
                            if (currentStatus == TYPE_TZ) {
                                DialogUtil.removeDialog(mContext);
                                show("投注成功");
                                lpView.clearColorAndResult();
                            } else {
                                DialogUtil.removeDialog(mContext);
                                show("当前时间段不能投注！");
                            }
                        }
                    });
                }
                break;
        }
    }

    public static final int TYPE_TZ = 1000;//投注中
    public static final int TYPE_STOP_TZ = 1001;//停止投注
    public static final int TYPE_KJ = 1002;//开奖中
    public static final int TYPE_PJ = 1003;//派奖中
    private int currentStatus = TYPE_TZ;

    private int getGameStatus() {
        Calendar now = Calendar.getInstance();
        int second = now.get(Calendar.SECOND);
        if (second >= 0 && second < 45) {
            return TYPE_TZ;
        } else if (second >= 45 && second < 50) {
            return TYPE_STOP_TZ;
        } else if (second >= 50 && second < 55) {
            return TYPE_KJ;
        } else {
            return TYPE_PJ;
        }
    }

    private int getCurrentSecond() {
        Calendar now = Calendar.getInstance();
        int second = now.get(Calendar.SECOND);
        return second;
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
        cancel();
        lpView.closeRandomColor();
    }

    private String getTimeStr(int num) {
        if (num > 9) {
            return "" + num;
        } else {
            return "0" + num;
        }
    }
}
