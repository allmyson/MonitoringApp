package com.ys.zy.fast3.activity;

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
import com.ys.zy.base.BaseActivity;
import com.ys.zy.common.TZJLFragment;
import com.ys.zy.dialog.DialogUtil;
import com.ys.zy.dialog.GameFragment;
import com.ys.zy.fast3.fragment.Fast3JLFragment;
import com.ys.zy.fast3.fragment.Fast3TZFragment;
import com.ys.zy.http.HttpListener;
import com.ys.zy.racing.activity.RacingActivity;
import com.ys.zy.roulette.activity.RouletteActivity;
import com.ys.zy.sp.User;
import com.ys.zy.sp.UserSP;
import com.ys.zy.ssc.activity.SscActivity;
import com.ys.zy.ttz.activity.TtzActivity;
import com.ys.zy.util.HttpUtil;
import com.ys.zy.util.L;
import com.ys.zy.util.StringUtil;
import com.ys.zy.util.YS;
import com.ys.zy.winner.activity.WinnerActivity;

//快3
public class Fast3Activity extends BaseActivity {
    public static final int TYPE_JSK3 = 1000;
    public static final int TYPE_1FK3 = 1001;
    public static final int TYPE_5FK3 = 1002;
    private int type = TYPE_JSK3;
    private RelativeLayout backRL;
    private LinearLayout gameLL;
    private TextView gameTV;

    private RelativeLayout tzRL, tzjlRL;
    private TextView tzTV, tzjlTV;
    private View tzView, tzjlView;
    public static final int TYPE_TZ = 100;
    public static final int TYPE_JL = 101;
    private int currentType = TYPE_TZ;
    private Fragment tzFragment, jlFragment;
    private TextView moneyTV;
    private ImageView showOrHideIV;
    private boolean isShow = false;
    private String money = "0.00";
    private ImageView gameMoreIV;
    private boolean isShowMoreGame = false;//是否显示其他游戏
    private String userId;
    @Override
    public int getLayoutId() {
        return R.layout.activity_fast3;
    }

    @Override
    public void initView() {
        setBarColor2("#f7f7f7",true);
        regist();
        moneyTV = getView(R.id.tv_money);
        moneyTV.setText(money);
        showOrHideIV = getView(R.id.iv_showOrHide);
        showOrHideIV.setOnClickListener(this);
        manager = getSupportFragmentManager();
        type = getIntent().getIntExtra("type", TYPE_JSK3);
        initFragment();
        backRL = getView(R.id.rl_back);
        backRL.setOnClickListener(this);
        gameLL = getView(R.id.ll_game);
        gameLL.setOnClickListener(this);
        gameTV = getView(R.id.tv_gameName);
        gameMoreIV = getView(R.id.iv_gameMore);
        switch (type) {
            case TYPE_JSK3:
                gameTV.setText("10分快3");
                break;
            case TYPE_1FK3:
                gameTV.setText("1分快3");
                break;
            case TYPE_5FK3:
                gameTV.setText("5分快3");
                break;
            default:
                gameTV.setText("10分快3");
                break;
        }
        tzRL = getView(R.id.rl_wytz);
        tzjlRL = getView(R.id.rl_tzjl);
        tzTV = getView(R.id.tv_wytz);
        tzjlTV = getView(R.id.tv_tzjl);
        tzView = getView(R.id.view_wytz);
        tzjlView = getView(R.id.view_tzjl);
        tzRL.setOnClickListener(this);
        tzjlRL.setOnClickListener(this);
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
                                startActivity(new Intent(mContext, RouletteActivity.class));
                                break;
                            case 1:
                                //1分快3
                                if (type != TYPE_1FK3) {
                                    Fast3Activity.intentToFast3(mContext, TYPE_1FK3);
                                }
                                break;
                            case 2:
                                //推筒子
                                startActivity(new Intent(mContext, TtzActivity.class));
                                break;
                            case 3:
                                //5分快3
                                if (type != TYPE_5FK3) {
                                    Fast3Activity.intentToFast3(mContext, TYPE_5FK3);
                                }
                                break;
                            case 4:
                                //最后胜利者
                                WinnerActivity.intentToWinner(mContext);
                                break;
                            case 5:
                                //江苏快3
                                if (type != TYPE_JSK3) {
                                    Fast3Activity.intentToFast3(mContext, TYPE_JSK3);
                                }
                                break;
                            case 6:
                                //1分彩
                                SscActivity.intentToSSC(mContext, SscActivity.TYPE_1FC);
                                break;
                            case 7:
                                //北京赛车
                                RacingActivity.intentToRacing(mContext, RacingActivity.TYPE_BJSC);
                                break;
                            case 8:
                                //时时彩
                                SscActivity.intentToSSC(mContext, SscActivity.TYPE_SSC);
                                break;
                            case 9:
                                //1分赛车
                                RacingActivity.intentToRacing(mContext, RacingActivity.TYPE_1FSC);
                                break;
                            case 10:
                                //更多
                                break;
                            case 11:
                                //5分赛车
                                RacingActivity.intentToRacing(mContext, RacingActivity.TYPE_5FSC);
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
        }
    }

    public static void intentToFast3(Context context, int type) {
        Intent intent = new Intent(context, Fast3Activity.class);
        intent.putExtra("type", type);
        context.startActivity(intent);
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
        tzFragment = Fast3TZFragment.newInstance(type);
        if(type==TYPE_JSK3){
            jlFragment = TZJLFragment.newInstance(YS.CODE_JSK3,1);
        }else if(type==TYPE_1FK3){
            jlFragment = TZJLFragment.newInstance(YS.CODE_1FK3,1);
        }else {
            jlFragment = TZJLFragment.newInstance(YS.CODE_5FK3,1);
        }
//        jlFragment = Fast3JLFragment.newInstance(type);
    }

    public double getMoney() {
        return StringUtil.StringToDoubleTwo(money);
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
