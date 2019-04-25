package com.ys.zy.ttz.fragment;

import android.graphics.Color;
import android.os.CountDownTimer;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ys.zy.R;
import com.ys.zy.base.BaseFragment;
import com.ys.zy.roulette.adapter.ChipAdapter;
import com.ys.zy.roulette.adapter.LpHistoryAdapter;
import com.ys.zy.roulette.bean.ChipBean;
import com.ys.zy.ttz.adapter.PaiAdapter;
import com.ys.zy.ui.HorizontalListView;
import com.ys.zy.util.DensityUtil;
import com.ys.zy.util.GameUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class TtzTZFragment extends BaseFragment implements View.OnClickListener, View.OnTouchListener {
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
    //    private LPView lpView;
//    private List<LPBean> lpBeanList;
    private String gameNo = "0215096";
    private GridView paiGV;
    private List<Object> paiList;
    private PaiAdapter paiAdater;
    private TextView total_z_1TV, total_p_1TV, total_x_1TV;
    private TextView my_z_1TV, my_p_1TV, my_x_1TV;
    private RelativeLayout z1RL, p1RL, x1Rl;

    public static TtzTZFragment newInstance() {
        TtzTZFragment ttzTZFragment = new TtzTZFragment();
        return ttzTZFragment;
    }

    @Override
    protected void init() {
        total_z_1TV = getView(R.id.tv_1_total_z);
        total_p_1TV = getView(R.id.tv_1_total_p);
        total_x_1TV = getView(R.id.tv_1_total_x);
        my_z_1TV = getView(R.id.tv_1_my_z);
        my_p_1TV = getView(R.id.tv_1_my_p);
        my_x_1TV = getView(R.id.tv_1_my_x);
        z1RL = getView(R.id.rl_1_z);
        p1RL = getView(R.id.rl_1_p);
        x1Rl = getView(R.id.rl_1_x);
        z1RL.setOnTouchListener(this);

        paiGV = getView(R.id.gv_);
        paiList = new ArrayList<>();
        paiList.add(null);
        paiList.add(null);
        paiList.add(null);
        paiList.add(null);
        paiAdater = new PaiAdapter(mContext, paiList, R.layout.item_ttz_pai);
        paiGV.setAdapter(paiAdater);
        timeTV = getView(R.id.tv_time);
        statusTV = getView(R.id.tv_status);
        qsTV = getView(R.id.tv_qs);
        setCurrentGameNo();
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
        chipAdapter.setSelectItem(0);
        clearBtn = getView(R.id.btn_clear);
        sureBtn = getView(R.id.btn_sure);
        clearBtn.setOnClickListener(this);
        sureBtn.setOnClickListener(this);
        setBtnClickable(false, sureBtn);
//        lpView = getView(R.id.lpv_);
//        lpBeanList = new ArrayList<>();
//        lpBeanList.addAll(LPBean.getList());
//        lpView.setData(lpBeanList);
//        lpView.setClickListener(new LPView.ClickListener() {
//            @Override
//            public void click(int position, LPBean lpBean) {
//                if (chipAdapter.getChooseData() != null && currentStatus == TYPE_TZ) {
//                    lpBeanList.get(position).myValue += chipAdapter.getChooseData().money;
//                    lpView.setData(lpBeanList);
//                    setBtnClickable(true, sureBtn);
//                }
//            }
//        });
    }

    private void setStatus() {
        currentStatus = getGameStatus();
        int second = getCurrentSecond();
        switch (currentStatus) {
            case TYPE_TZ:
//                if (chipAdapter.getChooseData() != null && lpView.getTZList().size() > 0) {
                if (chipAdapter.getChooseData() != null) {
                    setBtnClickable(true, sureBtn);
                } else {
                    setBtnClickable(false, sureBtn);
                }
                timeTV.setText(getTimeStr(45 - second));
                statusTV.setText("投注中...");
                if (second == 0) {
                    setCurrentGameNo();
                }
                break;
            case TYPE_STOP_TZ:
                timeTV.setText(getTimeStr(50 - second));
                statusTV.setText("停止投注...");
                break;
            case TYPE_FP:
                timeTV.setText(getTimeStr(55 - second));
                statusTV.setText("发牌中...");
                if (second != 54) {
//                    if (!lpView.isRamdomRunning()) {
//                        L.e("lpView.startRandomColor()执行次数");
//                        lpView.startRandomColor();
//                    }
                } else {
//                    L.e("lpView.setRandomResult()执行次数");
//                    lpView.setRandomResult();
                }
                break;
            case TYPE_PJ:
                timeTV.setText(getTimeStr(60 - second));
                statusTV.setText("派奖中...");
                if (second == 59) {
                    //恢复初始状态
//                    L.e("lpView.clearColorAndResult()执行次数");
//                    lpView.clearColorAndResult();
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
        return R.layout.fragment_ttz_tz;
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
                //清空投注
//                for (int i = 0; i < lpBeanList.size(); i++) {
//                    lpBeanList.get(i).myValue = 0;
//                }
//                lpView.setData(lpBeanList);
                setBtnClickable(false, sureBtn);
                break;
            case R.id.btn_sure:
                if (currentStatus != TYPE_TZ) {
                    show("当前时间段不能投注！");
                    return;
                }
//                List<LPBean> result = lpView.getTZList();
//                if (result != null && result.size() > 0) {
//                    double sum = 0;
//                    String content = "";
//                    for (int i = 0; i < result.size(); i++) {
//                        sum += result.get(i).myValue;
//                        if (i != result.size() - 1) {
//                            content += result.get(i).name + ",";
//                        } else {
//                            content += result.get(i).name;
//                        }
//                    }
//                    DialogUtil.showTZTip(mContext, "轮盘", gameNo, StringUtil.StringToDoubleStr(sum), content, new TZTipFragment.ClickListener() {
//                        @Override
//                        public void sure() {
//                            if (currentStatus == TYPE_TZ) {
//                                DialogUtil.removeDialog(mContext);
//                                show("投注成功");
//                                lpView.clearColorAndResult();
//                            } else {
//                                DialogUtil.removeDialog(mContext);
//                                show("当前时间段不能投注！");
//                            }
//                        }
//                    });
//                }
                break;
        }
    }

    public static final int TYPE_TZ = 1000;//投注中
    public static final int TYPE_STOP_TZ = 1001;//停止投注
    public static final int TYPE_FP = 1002;//发牌中
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
            return TYPE_FP;
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
//        lpView.closeRandomColor();
    }

    private String getTimeStr(int num) {
        if (num > 9) {
            return "" + num;
        } else {
            return "0" + num;
        }
    }

    private void setCurrentGameNo() {
        gameNo = GameUtil.getCurrentTtzPeriods();
        qsTV.setText(gameNo + "期");
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            /**
             * 点击的开始位置
             */
            case MotionEvent.ACTION_DOWN:
//                tv.setText("起始位置：(" + event.getX() + "," + event.getY());
                break;
            /**
             * 触屏实时位置
             */
            case MotionEvent.ACTION_MOVE:
//                tv.setText("实时位置：(" + event.getX() + "," + event.getY());
                break;
            /**
             * 离开屏幕的位置
             */
            case MotionEvent.ACTION_UP:
//                tv.setText("结束位置：(" + event.getX() + "," + event.getY());
                addView(event.getX(), event.getY());
                break;
            default:
                break;
        }
        /**
         *  注意返回值
         *  true：view继续响应Touch操作；
         *  false：view不再响应Touch操作，故此处若为false，只能显示起始位置，不能显示实时位置和结束位置
         */
        return true;
    }


    private void addView(float x, float y) {
        ImageView imageView = new ImageView(mContext);
        imageView.setImageResource(R.mipmap.lp_icon_long);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(DensityUtil.dp2px(mContext, 30), DensityUtil.dp2px(mContext, 30));
        layoutParams.leftMargin = (int) (x - DensityUtil.dp2px(mContext, 15));
        layoutParams.topMargin = (int) (y - DensityUtil.dp2px(mContext, 15));
        getActivity().addContentView(imageView, layoutParams);
        TranslateAnimation animation = new TranslateAnimation(DensityUtil.dp2px(mContext, 15)-x, -x + x - DensityUtil.dp2px(mContext, 15), DensityUtil.dp2px(mContext, 15)-y, -y + y - DensityUtil.dp2px(mContext, 15));
        animation.setDuration(2000);
        animation.setFillAfter(true);
        imageView.startAnimation(animation);
    }
}
