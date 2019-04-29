package com.ys.zy.ttz.fragment;

import android.animation.Animator;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yongchun.library.utils.ScreenUtils;
import com.ys.zy.R;
import com.ys.zy.base.BaseFragment;
import com.ys.zy.roulette.adapter.ChipAdapter;
import com.ys.zy.roulette.adapter.LpHistoryAdapter;
import com.ys.zy.roulette.bean.ChipBean;
import com.ys.zy.ttz.TtzUtil;
import com.ys.zy.ttz.activity.TtzActivity;
import com.ys.zy.ttz.adapter.PaiAdapter;
import com.ys.zy.ttz.adapter.TtzHistoryAdapter;
import com.ys.zy.ttz.bean.MoneyBean;
import com.ys.zy.ttz.bean.PaiBean;
import com.ys.zy.ttz.ui.ParticleView;
import com.ys.zy.ui.HorizontalListView;
import com.ys.zy.util.DensityUtil;
import com.ys.zy.util.GameUtil;
import com.ys.zy.util.L;
import com.ys.zy.util.StringUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class TtzTZFragment extends BaseFragment implements View.OnClickListener, View.OnTouchListener {
    public final static int TYPE_Z1 = 100;//点击区域
    public final static int TYPE_P1 = 101;
    public final static int TYPE_X1 = 102;
    public final static int TYPE_Z2 = 200;
    public final static int TYPE_P2 = 201;
    public final static int TYPE_X2 = 202;
    public final static int TYPE_Z3 = 300;
    public final static int TYPE_P3 = 301;
    public final static int TYPE_X3 = 302;
    public final static int TYPE_NULL = 502;
    private TextView timeTV, statusTV, qsTV;
    private ImageView moreIV;
    private LinearLayout moreLL;
    private LinearLayout historyLL;
    private boolean isShowHistory = false;
    private ListView historyLV;
    private List<Object> historyList;
    private TtzHistoryAdapter historyAdapter;
    private HorizontalListView horizontalListView;
    private List<ChipBean> chipBeanList;
    private ChipAdapter chipAdapter;
    private Button clearBtn, sureBtn;
    private String gameNo = "0215096";
    private GridView paiGV;
    private List<PaiBean> paiList;
    private PaiAdapter paiAdater;
    private TextView total_z_1TV, total_p_1TV, total_x_1TV;
    private TextView my_z_1TV, my_p_1TV, my_x_1TV;
    private TextView total_z_2TV, total_p_2TV, total_x_2TV;
    private TextView my_z_2TV, my_p_2TV, my_x_2TV;
    private TextView total_z_3TV, total_p_3TV, total_x_3TV;
    private TextView my_z_3TV, my_p_3TV, my_x_3TV;
    private RelativeLayout z1RL, p1RL, x1RL;
    private RelativeLayout z2RL, p2RL, x2RL;
    private RelativeLayout z3RL, p3RL, x3RL;
    private int[] locationZ1;
    private int[] locationP1;
    private int[] locationX1;
    private int[] locationZ2;
    private int[] locationP2;
    private int[] locationX2;
    private int[] locationZ3;
    private int[] locationP3;
    private int[] locationX3;
    private List<MoneyBean> moneyBeanList;
    private LinearLayout bottomLL;
    private int[] bottom;
    private Map<Integer, TextView> myTVMap;
    private ParticleView particleAnimator;
    private List<ImageView> imageViewList;
    private TextView z1ResultTV, z2ResultTV, z3ResultTV;
    private TextView p1ResultTV, p2ResultTV, p3ResultTV;
    private TextView x1ResultTV, x2ResultTV, x3ResultTV;
    private List<TextView> result1TVList;
    private List<TextView> result2TVList;
    private List<TextView> result3TVList;
    private RelativeLayout z1CircleRL, z2CircleRL, z3CircleRL;
    private RelativeLayout p1CircleRL, p2CircleRL, p3CircleRL;
    private RelativeLayout x1CircleRL, x2CircleRL, x3CircleRL;

    public static TtzTZFragment newInstance() {
        TtzTZFragment ttzTZFragment = new TtzTZFragment();
        return ttzTZFragment;
    }

    @Override
    protected void init() {
        z1CircleRL = getView(R.id.rl_1_z_circle);
        z2CircleRL = getView(R.id.rl_2_z_circle);
        z3CircleRL = getView(R.id.rl_3_z_circle);
        p1CircleRL = getView(R.id.rl_1_p_circle);
        p2CircleRL = getView(R.id.rl_2_p_circle);
        p3CircleRL = getView(R.id.rl_3_p_circle);
        x1CircleRL = getView(R.id.rl_1_x_circle);
        x2CircleRL = getView(R.id.rl_2_x_circle);
        x3CircleRL = getView(R.id.rl_3_x_circle);
        result1TVList = new ArrayList<>();
        result2TVList = new ArrayList<>();
        result3TVList = new ArrayList<>();
        z1ResultTV = getView(R.id.tv_1_result_z);
        z2ResultTV = getView(R.id.tv_2_result_z);
        z3ResultTV = getView(R.id.tv_3_result_z);
        p1ResultTV = getView(R.id.tv_1_result_p);
        p2ResultTV = getView(R.id.tv_2_result_p);
        p3ResultTV = getView(R.id.tv_3_result_p);
        x1ResultTV = getView(R.id.tv_1_result_x);
        x2ResultTV = getView(R.id.tv_2_result_x);
        x3ResultTV = getView(R.id.tv_3_result_x);
        result1TVList.add(z1ResultTV);
        result1TVList.add(p1ResultTV);
        result1TVList.add(x1ResultTV);
        result2TVList.add(z2ResultTV);
        result2TVList.add(p2ResultTV);
        result2TVList.add(x2ResultTV);
        result3TVList.add(z3ResultTV);
        result3TVList.add(p3ResultTV);
        result3TVList.add(x3ResultTV);
        imageViewList = new ArrayList<>();
        bottomLL = getView(R.id.ll_bottom);
        moneyBeanList = new ArrayList<>();
        moneyBeanList.addAll(MoneyBean.getList());
        total_z_1TV = getView(R.id.tv_1_total_z);
        total_z_2TV = getView(R.id.tv_2_total_z);
        total_z_3TV = getView(R.id.tv_3_total_z);
        total_p_1TV = getView(R.id.tv_1_total_p);
        total_p_2TV = getView(R.id.tv_2_total_p);
        total_p_3TV = getView(R.id.tv_3_total_p);
        total_x_1TV = getView(R.id.tv_1_total_x);
        total_x_2TV = getView(R.id.tv_2_total_x);
        total_x_3TV = getView(R.id.tv_3_total_x);
        my_z_1TV = getView(R.id.tv_1_my_z);
        my_z_2TV = getView(R.id.tv_2_my_z);
        my_z_3TV = getView(R.id.tv_3_my_z);
        my_p_1TV = getView(R.id.tv_1_my_p);
        my_p_2TV = getView(R.id.tv_2_my_p);
        my_p_3TV = getView(R.id.tv_3_my_p);
        my_x_1TV = getView(R.id.tv_1_my_x);
        my_x_2TV = getView(R.id.tv_2_my_x);
        my_x_3TV = getView(R.id.tv_3_my_x);
        myTVMap = new HashMap<>();
        myTVMap.put(TYPE_Z1, my_z_1TV);
        myTVMap.put(TYPE_Z2, my_z_2TV);
        myTVMap.put(TYPE_Z3, my_z_3TV);
        myTVMap.put(TYPE_P1, my_p_1TV);
        myTVMap.put(TYPE_P2, my_p_2TV);
        myTVMap.put(TYPE_P3, my_p_3TV);
        myTVMap.put(TYPE_X1, my_x_1TV);
        myTVMap.put(TYPE_X2, my_x_2TV);
        myTVMap.put(TYPE_X3, my_x_3TV);
        z1RL = getView(R.id.rl_1_z);
        p1RL = getView(R.id.rl_1_p);
        x1RL = getView(R.id.rl_1_x);
        z2RL = getView(R.id.rl_2_z);
        p2RL = getView(R.id.rl_2_p);
        x2RL = getView(R.id.rl_2_x);
        z3RL = getView(R.id.rl_3_z);
        p3RL = getView(R.id.rl_3_p);
        x3RL = getView(R.id.rl_3_x);
        ((ViewGroup) getActivity().getWindow().getDecorView().findViewById(android.R.id.content)).getChildAt(0).setOnTouchListener(this);
        paiGV = getView(R.id.gv_);
        paiList = new ArrayList<>();
        paiList.addAll(TtzUtil.getDefaultList());
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
        historyAdapter = new TtzHistoryAdapter(mContext, historyList, R.layout.item_ttz_history);
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
    }

    private void initLocation() {
        locationZ1 = new int[2];
        z1RL.getLocationOnScreen(locationZ1);
        locationP1 = new int[2];
        p1RL.getLocationOnScreen(locationP1);
        locationX1 = new int[2];
        x1RL.getLocationOnScreen(locationX1);
        locationZ2 = new int[2];
        z2RL.getLocationOnScreen(locationZ2);
        locationP2 = new int[2];
        p2RL.getLocationOnScreen(locationP2);
        locationX2 = new int[2];
        x2RL.getLocationOnScreen(locationX2);
        locationZ3 = new int[2];
        z3RL.getLocationOnScreen(locationZ3);
        locationP3 = new int[2];
        p3RL.getLocationOnScreen(locationP3);
        locationX3 = new int[2];
        x3RL.getLocationOnScreen(locationX3);
        bottom = new int[2];
        bottomLL.getLocationOnScreen(bottom);
    }

    private void setStatus() {
        currentStatus = getGameStatus();
        int second = getCurrentSecond();
//        isShowChip();
        switch (currentStatus) {
            case TYPE_TZ:
                if (chipAdapter.getChooseData() != null && getCurrentTzMoney() != 0) {
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
//                if (second != 54) {
//                    if (!paiAdater.isRamdomRunning()) {
//                        paiAdater.startRandom();
//                    }
//                } else {
//                    paiAdater.toDefault();
//                    setRandomResultColor();
//                }
                if (second == 51) {
                    paiAdater.faPai();
                }
                break;
            case TYPE_PJ:
                timeTV.setText(getTimeStr(60 - second));
                statusTV.setText("派奖中...");
                if (second == 59) {
                    //恢复初始状态
                    startClearAnima();
                    setBtnClickable(false, sureBtn);
                    paiList.clear();
                    paiList.addAll(TtzUtil.getDefaultList());
                    paiAdater.refresh(paiList);
                    setDefaultResultColor();
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
                startClearAnima();
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
//                statusTV.setText("起始位置：(" + event.getRawX() + "," + event.getRawY());
                break;
            /**
             * 触屏实时位置
             */
            case MotionEvent.ACTION_MOVE:
//                statusTV.setText("实时位置：(" + event.getRawX() + "," + event.getRawY());
//                statusTV.setText("实时位置：" + getTouchType((int) event.getRawX(), (int) event.getRawY()));
                break;
            /**
             * 离开屏幕的位置
             */
            case MotionEvent.ACTION_UP:
//                statusTV.setText("结束位置：(" + event.getRawX() + "," + event.getRawY());
                if (currentStatus != TYPE_TZ) {
                    show("当前时间段不能投注！");
                } else {
                    int type = getTouchType((int) event.getRawX(), (int) event.getRawY());
                    if (type != TYPE_NULL) {
                        ChipBean chipBean = chipAdapter.getChooseData();
                        if (chipBean != null) {
                            int selectItem = chipAdapter.getSelectItem();
                            L.e("selectItem=" + selectItem);
                            if (selectItem != -1) {
//                            int[] point = getLocation(horizontalListView.getChildAt(chipAdapter.getSelectItem() - horizontalListView.getFirstVisiblePosition()));
//                            addView(point[0] - event.getRawX(), point[1] - event.getRawY(), event.getX(), event.getY(), chipBean.selectDrawableId);
                                addView(ScreenUtils.getScreenWidth(mContext) / 2 - event.getRawX(), bottom[1] - event.getRawY(), event.getX(), event.getY(), chipBean.selectDrawableId);
                                for (MoneyBean moneyBean : moneyBeanList) {
                                    if (moneyBean.type == type) {
                                        moneyBean.chipBeanList.add(chipBean);
                                        myTVMap.get(type).setText(getMoney(moneyBean.chipBeanList));
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
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


    private void addView(float fromX, float fromY, float x, float y, int drawableId) {
        ImageView imageView = new ImageView(mContext);
        imageView.setImageResource(drawableId);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(DensityUtil.dp2px(mContext, 20), DensityUtil.dp2px(mContext, 20));
        layoutParams.leftMargin = (int) (x - DensityUtil.dp2px(mContext, 10));
        layoutParams.topMargin = (int) (y - DensityUtil.dp2px(mContext, 10));
        getActivity().addContentView(imageView, layoutParams);
        imageViewList.add(imageView);
//        TranslateAnimation animation = new TranslateAnimation(-x - DensityUtil.dp2px(mContext, 10), 0 - DensityUtil.dp2px(mContext, 10), -y - DensityUtil.dp2px(mContext, 10), 0 - DensityUtil.dp2px(mContext, 10));
        TranslateAnimation animation = new TranslateAnimation(fromX - DensityUtil.dp2px(mContext, 10), 0 - DensityUtil.dp2px(mContext, 10), fromY - DensityUtil.dp2px(mContext, 10), 0 - DensityUtil.dp2px(mContext, 10));
        animation.setDuration(1000);
        animation.setFillAfter(true);
        imageView.startAnimation(animation);
    }

    private int getTouchType(int rawX, int rawY) {
        if (locationZ1 == null) {
            initLocation();
        }
        if (isInRect(rawX, rawY, locationZ1[0], locationZ1[1], z1RL.getWidth(), z1RL.getHeight())) {
            return TYPE_Z1;
        } else if (isInRect(rawX, rawY, locationP1[0], locationP1[1], p1RL.getWidth(), p1RL.getHeight())) {
            return TYPE_P1;
        } else if (isInRect(rawX, rawY, locationX1[0], locationX1[1], x1RL.getWidth(), x1RL.getHeight())) {
            return TYPE_X1;
        } else if (isInRect(rawX, rawY, locationZ2[0], locationZ2[1], z2RL.getWidth(), z2RL.getHeight())) {
            return TYPE_Z2;
        } else if (isInRect(rawX, rawY, locationP2[0], locationP2[1], p2RL.getWidth(), p2RL.getHeight())) {
            return TYPE_P2;
        } else if (isInRect(rawX, rawY, locationX2[0], locationX2[1], x2RL.getWidth(), x2RL.getHeight())) {
            return TYPE_X2;
        } else if (isInRect(rawX, rawY, locationZ3[0], locationZ3[1], z3RL.getWidth(), z3RL.getHeight())) {
            return TYPE_Z3;
        } else if (isInRect(rawX, rawY, locationP3[0], locationP3[1], p3RL.getWidth(), p3RL.getHeight())) {
            return TYPE_P3;
        } else if (isInRect(rawX, rawY, locationX3[0], locationX3[1], x3RL.getWidth(), x3RL.getHeight())) {
            return TYPE_X3;
        } else {
            return TYPE_NULL;
        }
    }

    private boolean isInRect(int rawX, int rawY, int left, int top, int width, int height) {
        if (rawX >= left && rawX <= left + width && rawY >= top && rawY <= top + height) {
            return true;
        }
        return false;
    }

    private int[] getLocation(View view) {
        int[] points = new int[2];
        view.getLocationOnScreen(points);
        return points;
    }

    private String getMoney(List<ChipBean> list) {
        double money = 0;
        for (ChipBean chipBean : list) {
            money += chipBean.money;
        }
        return StringUtil.StringToDoubleStr(money);
    }

    private void startClearAnima() {
        if (imageViewList.size() > 0) {
            for (ImageView imageView : imageViewList) {
                particleAnimator = new ParticleView(mContext, 3000);//3000为动画持续时间
                particleAnimator.setOnAnimationListener(new ParticleView.OnAnimationListener() {
                    @Override
                    public void onAnimationStart(View v, Animator animation) {
                        v.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onAnimationEnd(View v, Animator animation) {

                    }
                });
                particleAnimator.boom(imageView);
            }
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    FrameLayout group = (FrameLayout) getActivity().getWindow().getDecorView().findViewById(android.R.id.content);
                    for (ImageView imageView : imageViewList) {
                        group.removeView(imageView);
                    }
                    imageViewList.clear();
                    for (TextView value : myTVMap.values()) {
                        value.setText("0");
                    }
                    for (MoneyBean moneyBean : moneyBeanList) {
                        moneyBean.chipBeanList.clear();
                    }
                }
            }, 1000);
        }
    }

    /**
     * 获取本次投注的钱
     *
     * @return
     */
    private double getCurrentTzMoney() {
        double money = 0;
        if (moneyBeanList != null && moneyBeanList.size() > 0) {
            for (MoneyBean moneyBean : moneyBeanList) {
                double childMoney = 0;
                if (moneyBean.chipBeanList != null && moneyBean.chipBeanList.size() > 0) {
                    for (ChipBean chipBean : moneyBean.chipBeanList) {
                        childMoney += chipBean.money;
                    }
                }
                money += childMoney;
            }
        }
        return money;
    }

    private void setRandomResultColor() {
        Random random = new Random();
        int r1 = random.nextInt(3);
        int r2 = random.nextInt(3);
        int r3 = random.nextInt(3);
        if (r1 == 0) {
            z1ResultTV.setTextColor(Color.parseColor("#dd2230"));
            p1ResultTV.setTextColor(Color.parseColor("#4da5a5a5"));
            x1ResultTV.setTextColor(Color.parseColor("#4d29abe2"));
            z1CircleRL.setBackgroundResource(R.drawable.circle11);
            p1CircleRL.setBackgroundResource(R.drawable.circle10);
            x1CircleRL.setBackgroundResource(R.drawable.circle10);
        } else if (r1 == 1) {
            z1ResultTV.setTextColor(Color.parseColor("#4ddd2230"));
            p1ResultTV.setTextColor(Color.parseColor("#a5a5a5"));
            x1ResultTV.setTextColor(Color.parseColor("#4d29abe2"));
            z1CircleRL.setBackgroundResource(R.drawable.circle10);
            p1CircleRL.setBackgroundResource(R.drawable.circle11);
            x1CircleRL.setBackgroundResource(R.drawable.circle10);
        } else {
            z1ResultTV.setTextColor(Color.parseColor("#4ddd2230"));
            p1ResultTV.setTextColor(Color.parseColor("#4da5a5a5"));
            x1ResultTV.setTextColor(Color.parseColor("#29abe2"));
            z1CircleRL.setBackgroundResource(R.drawable.circle10);
            p1CircleRL.setBackgroundResource(R.drawable.circle10);
            x1CircleRL.setBackgroundResource(R.drawable.circle11);
        }


        if (r2 == 0) {
            z2ResultTV.setTextColor(Color.parseColor("#dd2230"));
            p2ResultTV.setTextColor(Color.parseColor("#4da5a5a5"));
            x2ResultTV.setTextColor(Color.parseColor("#4d29abe2"));
            z2CircleRL.setBackgroundResource(R.drawable.circle11);
            p2CircleRL.setBackgroundResource(R.drawable.circle10);
            x2CircleRL.setBackgroundResource(R.drawable.circle10);
        } else if (r2 == 1) {
            z2ResultTV.setTextColor(Color.parseColor("#4ddd2230"));
            p2ResultTV.setTextColor(Color.parseColor("#a5a5a5"));
            x2ResultTV.setTextColor(Color.parseColor("#4d29abe2"));
            z2CircleRL.setBackgroundResource(R.drawable.circle10);
            p2CircleRL.setBackgroundResource(R.drawable.circle11);
            x2CircleRL.setBackgroundResource(R.drawable.circle10);
        } else {
            z2ResultTV.setTextColor(Color.parseColor("#4ddd2230"));
            p2ResultTV.setTextColor(Color.parseColor("#4da5a5a5"));
            x2ResultTV.setTextColor(Color.parseColor("#29abe2"));
            z2CircleRL.setBackgroundResource(R.drawable.circle10);
            p2CircleRL.setBackgroundResource(R.drawable.circle10);
            x2CircleRL.setBackgroundResource(R.drawable.circle11);
        }

        if (r3 == 0) {
            z3ResultTV.setTextColor(Color.parseColor("#dd2230"));
            p3ResultTV.setTextColor(Color.parseColor("#4da5a5a5"));
            x3ResultTV.setTextColor(Color.parseColor("#4d29abe2"));
            z3CircleRL.setBackgroundResource(R.drawable.circle11);
            p3CircleRL.setBackgroundResource(R.drawable.circle10);
            x3CircleRL.setBackgroundResource(R.drawable.circle10);
        } else if (r3 == 1) {
            z3ResultTV.setTextColor(Color.parseColor("#4ddd2230"));
            p3ResultTV.setTextColor(Color.parseColor("#a5a5a5"));
            x3ResultTV.setTextColor(Color.parseColor("#4d29abe2"));
            z3CircleRL.setBackgroundResource(R.drawable.circle10);
            p3CircleRL.setBackgroundResource(R.drawable.circle11);
            x3CircleRL.setBackgroundResource(R.drawable.circle10);
        } else {
            z3ResultTV.setTextColor(Color.parseColor("#4ddd2230"));
            p3ResultTV.setTextColor(Color.parseColor("#4da5a5a5"));
            x3ResultTV.setTextColor(Color.parseColor("#29abe2"));
            z3CircleRL.setBackgroundResource(R.drawable.circle10);
            p3CircleRL.setBackgroundResource(R.drawable.circle10);
            x3CircleRL.setBackgroundResource(R.drawable.circle11);
        }
    }


    private void setDefaultResultColor() {
        z1ResultTV.setTextColor(Color.parseColor("#4ddd2230"));
        p1ResultTV.setTextColor(Color.parseColor("#4da5a5a5"));
        x1ResultTV.setTextColor(Color.parseColor("#4d29abe2"));
        z2ResultTV.setTextColor(Color.parseColor("#4ddd2230"));
        p2ResultTV.setTextColor(Color.parseColor("#4da5a5a5"));
        x2ResultTV.setTextColor(Color.parseColor("#4d29abe2"));
        z3ResultTV.setTextColor(Color.parseColor("#4ddd2230"));
        p3ResultTV.setTextColor(Color.parseColor("#4da5a5a5"));
        x3ResultTV.setTextColor(Color.parseColor("#4d29abe2"));
        z1CircleRL.setBackgroundResource(R.drawable.circle10);
        z2CircleRL.setBackgroundResource(R.drawable.circle10);
        z3CircleRL.setBackgroundResource(R.drawable.circle10);
        p1CircleRL.setBackgroundResource(R.drawable.circle10);
        p2CircleRL.setBackgroundResource(R.drawable.circle10);
        p3CircleRL.setBackgroundResource(R.drawable.circle10);
        x1CircleRL.setBackgroundResource(R.drawable.circle10);
        x2CircleRL.setBackgroundResource(R.drawable.circle10);
        x3CircleRL.setBackgroundResource(R.drawable.circle10);
    }

    private void isShowChip() {
        if (imageViewList != null && imageViewList.size() > 0) {
            if (this == ((TtzActivity) getActivity()).getCurrentFragment()) {
                if (historyLL.getVisibility() == View.VISIBLE) {
                    for (ImageView iv : imageViewList) {
                        iv.setVisibility(View.GONE);
                    }
                } else {
                    for (ImageView iv : imageViewList) {
                        iv.setVisibility(View.VISIBLE);
                    }
                }
            } else {
                for (ImageView iv : imageViewList) {
                    iv.setVisibility(View.GONE);
                }
            }
        }
    }
}
