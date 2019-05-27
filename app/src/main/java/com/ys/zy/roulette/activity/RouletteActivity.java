package com.ys.zy.roulette.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yanzhenjie.nohttp.rest.Response;
import com.ys.zy.R;
import com.ys.zy.common.TZJLFragment;
import com.ys.zy.fast3.activity.Fast3Activity;
import com.ys.zy.base.BaseActivity;
import com.ys.zy.dialog.DialogUtil;
import com.ys.zy.dialog.GameFragment;
import com.ys.zy.http.HttpListener;
import com.ys.zy.racing.activity.RacingActivity;
import com.ys.zy.roulette.fragment.RouletteJLFragment;
import com.ys.zy.roulette.fragment.RouletteTZFragment;
import com.ys.zy.sp.User;
import com.ys.zy.sp.UserSP;
import com.ys.zy.ssc.activity.SscActivity;
import com.ys.zy.ttz.activity.TtzActivity;
import com.ys.zy.util.HttpUtil;
import com.ys.zy.util.L;
import com.ys.zy.util.StringUtil;
import com.ys.zy.util.YS;
import com.ys.zy.winner.activity.WinnerActivity;

//轮盘
public class RouletteActivity extends BaseActivity {
    private RelativeLayout backRL;
    private ImageView backIV, smIV, gameMoreIV;
    private TextView gameNameTV;
    private RelativeLayout tzRL, tzjlRL;
    private TextView tzTV, tzjlTV;
    private View tzView, tzjlView;
    private Fragment tzFragment, jlFragment;
    public static final int TYPE_TZ = 100;
    public static final int TYPE_JL = 101;
    private int currentType = TYPE_TZ;

    private TextView moneyTV;
    private ImageView showOrHideIV;
    private boolean isShow = false;
    private String money = "0.00";
    private boolean isShowMoreGame = false;//是否显示其他游戏

    private LinearLayout gameLL;
    private LinearLayout smLL;
    private String userId;
//    public static final String content = "投注时间里从12个生肖里选一个下注，开奖结果与所选生肖相同即中奖；红色数字代表赔率，蓝色是自己投注的金额，白色是所有玩家投注总额";
    public static final String content = "投注时间里从12个生肖里选一个下注，开奖结果与所选生肖相同即中奖；红色数字代表赔率，蓝色是自己投注的金额";

    @Override
    public int getLayoutId() {
        return R.layout.activity_roulette;
    }

    @Override
    public void initView() {
        regist();
        setBarColor("#1e1e1e", false);
        backRL = getView(R.id.rl_back);
        backRL.setOnClickListener(this);
        backIV = getView(R.id.iv_back);
        smIV = getView(R.id.iv_sm);
        gameMoreIV = getView(R.id.iv_gameMore);
        backIV.setColorFilter(Color.parseColor("#a5a5a5"));
        smIV.setColorFilter(Color.parseColor("#f7f7f7"));
        gameMoreIV.setColorFilter(Color.parseColor("#a5a5a5"));
        gameNameTV = getView(R.id.tv_gameName);

        moneyTV = getView(R.id.tv_money);
        showOrHideIV = getView(R.id.iv_showOrHide);
        showOrHideIV.setOnClickListener(this);

        gameLL = getView(R.id.ll_game);
        gameLL.setOnClickListener(this);

        smLL = getView(R.id.ll_sm);
        smLL.setOnClickListener(this);

        tzRL = getView(R.id.rl_wytz);
        tzjlRL = getView(R.id.rl_tzjl);
        tzTV = getView(R.id.tv_wytz);
        tzjlTV = getView(R.id.tv_tzjl);
        tzView = getView(R.id.view_wytz);
        tzjlView = getView(R.id.view_tzjl);
        tzRL.setOnClickListener(this);
        tzjlRL.setOnClickListener(this);
        manager = getSupportFragmentManager();
        initFragment();
        showFragment(tzFragment);
        if (!isShow) {
            showOrHideIV.setImageResource(R.mipmap.btn_hide);
            moneyTV.setText(StringUtil.changeToX(money));
        } else {
            showOrHideIV.setImageResource(R.mipmap.btn_show);
            moneyTV.setText(money);
        }
        userId = UserSP.getUserId(mContext);
    }

    @Override
    public void getData() {
        HttpUtil.getUserInfoById(mContext, userId, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                User user = new Gson().fromJson(response.get(), User.class);
                if (user != null && YS.SUCCESE.equals(user.code) && user.data != null) {
                    money = StringUtil.StringToDoubleStr(user.data.balance);
//                    moneyTV.setText(money);
                    if (!isShow) {
                        showOrHideIV.setImageResource(R.mipmap.btn_hide);
                        moneyTV.setText(StringUtil.changeToX(money));
                    } else {
                        showOrHideIV.setImageResource(R.mipmap.btn_show);
                        moneyTV.setText(money);
                    }
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.rl_wytz:
                if (currentType == TYPE_JL) {
                    currentType = TYPE_TZ;
                    tzTV.setTextColor(Color.parseColor("#dd2230"));
                    tzView.setVisibility(View.VISIBLE);
                    tzjlTV.setTextColor(Color.parseColor("#a5a5a5"));
                    tzjlView.setVisibility(View.INVISIBLE);
                    showFragment(tzFragment);
                }
                break;
            case R.id.rl_tzjl:
                if (currentType == TYPE_TZ) {
                    currentType = TYPE_JL;
                    tzjlTV.setTextColor(Color.parseColor("#dd2230"));
                    tzjlView.setVisibility(View.VISIBLE);
                    tzTV.setTextColor(Color.parseColor("#a5a5a5"));
                    tzView.setVisibility(View.INVISIBLE);
                    showFragment(jlFragment);
                }
                break;
            case R.id.iv_showOrHide:
                if (isShow) {
                    isShow = false;
                    showOrHideIV.setImageResource(R.mipmap.btn_hide);
                    moneyTV.setText(StringUtil.changeToX(money));
                } else {
                    isShow = true;
                    showOrHideIV.setImageResource(R.mipmap.btn_show);
                    moneyTV.setText(money);
                }
                break;
            case R.id.ll_game:
                gameMoreIV.setImageResource(R.mipmap.bottom_btn_more);
                DialogUtil.showGameFragment(mContext, new GameFragment.ClickListener() {
                    @Override
                    public void click(int position) {
                        gameMoreIV.setImageResource(R.mipmap.top_btn_more);
                        DialogUtil.removeDialog(mContext);
                        switch (position) {
                            case 0:
                                //轮盘
                                break;
                            case 1:
                                //1分快3
                                Fast3Activity.intentToFast3(mContext, Fast3Activity.TYPE_1FK3);
                                finish();
                                break;
                            case 2:
                                //推筒子
                                startActivity(new Intent(mContext, TtzActivity.class));
                                finish();
                                break;
                            case 3:
                                //5分快3
                                Fast3Activity.intentToFast3(mContext, Fast3Activity.TYPE_5FK3);
                                finish();
                                break;
                            case 4:
                                //最后胜利者
                                WinnerActivity.intentToWinner(mContext);
                                finish();
                                break;
                            case 5:
                                //江苏快3
                                Fast3Activity.intentToFast3(mContext, Fast3Activity.TYPE_JSK3);
                                finish();
                                break;
                            case 6:
                                //1分彩
                                SscActivity.intentToSSC(mContext, SscActivity.TYPE_1FC);
                                finish();
                                break;
                            case 7:
                                //北京赛车
                                RacingActivity.intentToRacing(mContext, RacingActivity.TYPE_BJSC);
                                finish();
                                break;
                            case 8:
                                //时时彩
                                SscActivity.intentToSSC(mContext, SscActivity.TYPE_SSC);
                                finish();
                                break;
                            case 9:
                                //1分赛车
                                RacingActivity.intentToRacing(mContext, RacingActivity.TYPE_1FSC);
                                finish();
                                break;
                            case 10:
                                //更多
                                break;
                            case 11:
                                //5分赛车
                                RacingActivity.intentToRacing(mContext, RacingActivity.TYPE_5FSC);
                                finish();
                                break;

                        }
                    }
                }, new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        gameMoreIV.setImageResource(R.mipmap.top_btn_more);
                    }
                });
                break;
            case R.id.ll_sm:
                smIV.setImageResource(R.mipmap.bottom_btn_more);
                DialogUtil.showSmDialog(mContext, content, new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        smIV.setImageResource(R.mipmap.top_btn_more);
                    }
                });
                break;
        }
    }

    private Fragment currentFragment;
    private FragmentManager manager;

    private void showFragment(Fragment fragment) {
        try {
            if (currentFragment != fragment) {
                FragmentTransaction transaction = manager.beginTransaction();
                if (currentFragment != null) {
                    transaction.hide(currentFragment);
                }
                currentFragment = fragment;
                if (!fragment.isAdded()) {
                    transaction.add(R.id.fl_, fragment).show(fragment).commitAllowingStateLoss();
                } else {
                    transaction.show(fragment).commitAllowingStateLoss();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initFragment() {
        tzFragment = RouletteTZFragment.newInstance();
//        jlFragment = RouletteJLFragment.newInstance();
        jlFragment = TZJLFragment.newInstance(YS.CODE_LP, 2);
    }


    private TZSuccReceiver tzSuccReceiver;

    private class TZSuccReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            L.e("收到TZFragment的投注成功广播！");
            if(jlFragment.isAdded()) {
                ((TZJLFragment) jlFragment).reload();
            }
        }
    }
    private void regist() {
        IntentFilter intentFilter = new IntentFilter(YS.ACTION_TZ_SUCCESS);
        tzSuccReceiver = new TZSuccReceiver();
        registerReceiver(tzSuccReceiver, intentFilter);
    }

    private void unRegist() {
        if (tzSuccReceiver != null) {
            unregisterReceiver(tzSuccReceiver);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unRegist();
    }
}
