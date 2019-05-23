package com.ys.zy.roulette.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yanzhenjie.nohttp.rest.Response;
import com.ys.zy.R;
import com.ys.zy.base.BaseFragment;
import com.ys.zy.bean.BaseBean;
import com.ys.zy.bean.OddsBean;
import com.ys.zy.dialog.DialogUtil;
import com.ys.zy.dialog.TZTipFragment;
import com.ys.zy.http.HttpListener;
import com.ys.zy.roulette.activity.RouletteActivity;
import com.ys.zy.roulette.adapter.ChipAdapter;
import com.ys.zy.roulette.adapter.LpHistoryAdapter;
import com.ys.zy.roulette.bean.ChipBean;
import com.ys.zy.roulette.bean.LPBean;
import com.ys.zy.roulette.bean.LpTz;
import com.ys.zy.roulette.ui.LPView;
import com.ys.zy.sp.UserSP;
import com.ys.zy.ssc.SscUtil;
import com.ys.zy.ssc.activity.SscActivity;
import com.ys.zy.ssc.bean.SscResultBean;
import com.ys.zy.ui.HorizontalListView;
import com.ys.zy.util.GameUtil;
import com.ys.zy.util.HttpUtil;
import com.ys.zy.util.L;
import com.ys.zy.util.StringUtil;
import com.ys.zy.util.YS;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RouletteTZFragment extends BaseFragment implements View.OnClickListener {
    private TextView timeTV, statusTV, qsTV;
    private ImageView moreIV;
    private LinearLayout moreLL;
    private LinearLayout historyLL;
    private boolean isShowHistory = false;
    private ListView historyLV;
    private List<SscResultBean.DataBean> historyList;
    private List<SscResultBean.DataBean> allList;
    private LpHistoryAdapter historyAdapter;
    private HorizontalListView horizontalListView;
    private List<ChipBean> chipBeanList;
    private ChipAdapter chipAdapter;
    private Button clearBtn, sureBtn;
    private LPView lpView;
    private List<LPBean> lpBeanList;
    private String gameNo = "";
    private boolean isStart = true;
    private String userId;
    private boolean isLPAnimal = false;//是否开启过开奖动画，确保只执行一次

    //    private boolean isShowCurrentResult = false;//是否已显示当前期开奖结果
    public static RouletteTZFragment newInstance() {
        RouletteTZFragment rouletteTZFragment = new RouletteTZFragment();
        return rouletteTZFragment;
    }

    @Override
    protected void init() {
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
        allList = new ArrayList<>();
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
        lpView = getView(R.id.lpv_);
        lpBeanList = new ArrayList<>();
        lpBeanList.addAll(LPBean.getList());
        lpView.setData(lpBeanList);
        lpView.setClickListener(new LPView.ClickListener() {
            @Override
            public void click(int position, LPBean lpBean) {
                if (chipAdapter.getChooseData() != null && currentStatus == TYPE_TZ) {
                    lpBeanList.get(position).myValue += chipAdapter.getChooseData().money;
                    lpView.setData(lpBeanList);
                    setBtnClickable(true, sureBtn);
                }
            }
        });
        userId = UserSP.getUserId(mContext);
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
                if (second == 0) {
                    setCurrentGameNo();
                }
                break;
            case TYPE_STOP_TZ:
                DialogUtil.removeDialog(mContext);
                timeTV.setText(getTimeStr(50 - second));
                statusTV.setText("停止投注...");
                break;
            case TYPE_KJ:
                timeTV.setText(getTimeStr(55 - second));
                statusTV.setText("开奖中...");
                break;
            case TYPE_PJ:
                timeTV.setText(getTimeStr(60 - second));
                statusTV.setText("派奖中...");
                break;
        }
        if (currentStatus == TYPE_KJ || currentStatus == TYPE_PJ) {
            if (second < 55) {
                if (!lpView.isRamdomRunning() && !isLPAnimal) {
                    lpView.startRandomColor();
                    isLPAnimal = true;
                }
                if (hasResult(gameNo)) {
                    String result = getResult(gameNo);
                    L.e("lp", "second=" + second + "--result=" + result);
                    lpView.setResult(result);
                }
            } else if (second < 59) {
                if (hasResult(gameNo)) {
                    String result = getResult(gameNo);
                    lpView.setResultWithNoAnimal(result);
                }
            } else {
                lpView.closeRandomColor();
                lpView.clearColorAndResult();
                isLPAnimal = false;
            }
        }
    }

    @Override
    protected void getData() {
        start();
        getResult();
//        getTotalTz();
        getOdds();
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
                if (currentStatus != TYPE_TZ) {
                    show("当前时间段不能投注！");
                    return;
                }
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
                                tz();
//                                show("投注成功");
//                                lpView.clearColorAndResult();
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

    private void tz() {
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("gameCode", YS.CODE_LP);
        map.put("complantTypeCode", "1000");//手动
        map.put("periodsNum", gameNo);//手动
        List<Map<String, Object>> list = new ArrayList<>();
        List<LPBean> result = lpView.getTZList();
        if (result != null && result.size() > 0) {
            for (LPBean lpBean : result) {
                Map<String, Object> tzMap = new HashMap<>();
                tzMap.put("betsNum", lpBean.name);
                tzMap.put("payMoney", lpBean.myValue);
                tzMap.put("times", 1);
                list.add(tzMap);
            }
        }
        map.put("betDetail", list);
        String json = new Gson().toJson(map);
        L.e("tz内容:" + json);
        HttpUtil.tz(mContext, json, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                BaseBean baseBean = new Gson().fromJson(response.get(), BaseBean.class);
                if (baseBean != null) {
                    if (YS.SUCCESE.equals(baseBean.code)) {
                        show("投注成功");
                        lpView.clearColorAndResult();
                        //通知Activity刷新余额
                        ((RouletteActivity) getActivity()).getData();
                        sendMsg();//刷新投注记录
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
    public void onDestroyView() {
        super.onDestroyView();
        cancel();
        lpView.closeRandomColor();
        isStart = false;
    }

    private String getTimeStr(int num) {
        if (num > 9) {
            return "" + num;
        } else {
            return "0" + num;
        }
    }

    private void setCurrentGameNo() {
        gameNo = GameUtil.getCurrentLpPeriods();
        qsTV.setText(gameNo + "期");
    }


    private void getResult() {
        HttpUtil.getSscResult(mContext, YS.CODE_LP, 50, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                historyList.clear();
                allList.clear();
                SscResultBean sscResultBean = new Gson().fromJson(response.get(), SscResultBean.class);
                if (sscResultBean != null && YS.SUCCESE.equals(sscResultBean.code) && sscResultBean.data != null && sscResultBean.data.size() > 0) {
                    allList.addAll(sscResultBean.data);
                    historyList.addAll(sscResultBean.data);
                }
                if(currentStatus!=TYPE_PJ) {
                    for (SscResultBean.DataBean dataBean:historyList){
                        if(gameNo.equals(dataBean.periodsNum)){
                            historyList.remove(dataBean);
                            break;
                        }
                    }
                }
                historyAdapter.refresh(historyList);
                if (isStart) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            getResult();
                        }
                    }, 500);
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
        if (allList.size() > 0) {
            for (int i = 0; i < allList.size(); i++) {
                if (lastNo.equals(allList.get(i).periodsNum)) {
                    hasResult = true;
                    break;
                }
            }
        }
        return hasResult;
    }

    private String getResult(String lastNo) {
        String result = "";
        if (allList.size() > 0) {
            for (int i = 0; i < allList.size(); i++) {
                if (lastNo.equals(allList.get(i).periodsNum)) {
                    result = allList.get(i).lotteryNum;
                }
            }
        }
        return result;
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
                    ((TextView) getView(R.id.tv_odds)).setText(StringUtil.valueOf(oddsBean.data.lunpan));
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {

            }
        });
    }

    private void getTotalTz() {
        HttpUtil.getLpAllTz(mContext, gameNo, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                if (chipAdapter.getChooseData() != null && currentStatus == TYPE_TZ) {
                    LpTz lpTz = new Gson().fromJson(response.get(), LpTz.class);
                    if (lpTz != null && YS.SUCCESE.equals(lpTz.code) && lpTz.data != null) {
                        lpBeanList.get(0).totalValue = StringUtil.StringToDoubleTwo(lpTz.data.rabbitPayNum);
                        lpBeanList.get(1).totalValue = StringUtil.StringToDoubleTwo(lpTz.data.loongPayNum);
                        lpBeanList.get(2).totalValue = StringUtil.StringToDoubleTwo(lpTz.data.snakePayNum);
                        lpBeanList.get(3).totalValue = StringUtil.StringToDoubleTwo(lpTz.data.horsePayNum);
                        lpBeanList.get(4).totalValue = StringUtil.StringToDoubleTwo(lpTz.data.sheepPayNum);
                        lpBeanList.get(5).totalValue = StringUtil.StringToDoubleTwo(lpTz.data.monkeyPayNum);
                        lpBeanList.get(6).totalValue = StringUtil.StringToDoubleTwo(lpTz.data.chickenPayNum);
                        lpBeanList.get(7).totalValue = StringUtil.StringToDoubleTwo(lpTz.data.dogPayNum);
                        lpBeanList.get(8).totalValue = StringUtil.StringToDoubleTwo(lpTz.data.pigPayNum);
                        lpBeanList.get(9).totalValue = StringUtil.StringToDoubleTwo(lpTz.data.mousePayNum);
                        lpBeanList.get(10).totalValue = StringUtil.StringToDoubleTwo(lpTz.data.cattlePayNum);
                        lpBeanList.get(11).totalValue = StringUtil.StringToDoubleTwo(lpTz.data.tigerPayNum);
                        lpView.setData(lpBeanList);
                    }
                }
                if (isStart) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            getTotalTz();
                        }
                    }, 2000);
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                getTotalTz();
            }
        });
    }
}
