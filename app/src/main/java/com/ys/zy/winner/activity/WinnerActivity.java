package com.ys.zy.winner.activity;

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
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yanzhenjie.nohttp.rest.Response;
import com.ys.zy.R;
import com.ys.zy.base.BaseActivity;
import com.ys.zy.common.TZJLFragment;
import com.ys.zy.dialog.DialogUtil;
import com.ys.zy.dialog.GameFragment;
import com.ys.zy.fast3.activity.Fast3Activity;
import com.ys.zy.http.HttpListener;
import com.ys.zy.racing.activity.RacingActivity;
import com.ys.zy.roulette.activity.RouletteActivity;
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
import com.ys.zy.winner.adapter.SmAdapter;
import com.ys.zy.winner.fragment.WinnerTZFragment;
import com.ys.zy.winner.fragment.WinnerTZJLFragment;

import java.util.ArrayList;
import java.util.List;

//胜利者
public class WinnerActivity extends BaseActivity {
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
    private String money;
    private boolean isShowMoreGame = false;//是否显示其他游戏

    private LinearLayout gameLL;
    private LinearLayout smLL, smContentLL;
    private ListView smLV;
    private SmAdapter smAdapter;
    private List<String> smList;
    public static String content;
    private String userId;

    @Override
    public int getLayoutId() {
        return R.layout.activity_winner;
    }

    @Override
    public void initView() {
        regist();
        setBarColor("#f7f7f7", true);
        smContentLL = getView(R.id.ll_smContent);
        smLV = getView(R.id.lv_sm);
        smList = new ArrayList<>();
        smList.addAll(getSMList());
        smAdapter = new SmAdapter(mContext, smList, R.layout.item_text3);
        smLV.setAdapter(smAdapter);
        backRL = getView(R.id.rl_back);
        backRL.setOnClickListener(this);
        backIV = getView(R.id.iv_back);
        smIV = getView(R.id.iv_sm);
        gameMoreIV = getView(R.id.iv_gameMore);
        backIV.setColorFilter(Color.parseColor("#000000"));
        smIV.setColorFilter(Color.parseColor("#f86e00"));
        gameMoreIV.setColorFilter(Color.parseColor("#000000"));
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

    private void initContent() {
        content = String.format("猜任意位置上的大小单双，0-4为小，5-9为大，选号与相同位置上的开奖号码形态一致，奖金:<font color=\"#dd2230\">%s</font>" + YS.UNIT + "。", StringUtil.StringToDoubleStr(money));
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
                    tzTV.setTextColor(Color.parseColor("#f86e00"));
                    tzView.setVisibility(View.VISIBLE);
                    tzjlTV.setTextColor(Color.parseColor("#a5a5a5"));
                    tzjlView.setVisibility(View.INVISIBLE);
                    showFragment(tzFragment);
                }
                break;
            case R.id.rl_tzjl:
                if (currentType == TYPE_TZ) {
                    currentType = TYPE_JL;
                    tzjlTV.setTextColor(Color.parseColor("#f86e00"));
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
                                finish();
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
                if (smContentLL.getVisibility() == View.VISIBLE) {
                    smContentLL.setVisibility(View.GONE);
                    smIV.setImageResource(R.mipmap.top_btn_more);
                } else {
                    smContentLL.setVisibility(View.VISIBLE);
                    smIV.setImageResource(R.mipmap.bottom_btn_more);
                }
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
        tzFragment = WinnerTZFragment.newInstance();
//        jlFragment = WinnerTZJLFragment.newInstance();
        jlFragment = TZJLFragment.newInstance(YS.CODE_SLZ, 1);
    }

    public static void intentToWinner(Context context) {
        context.startActivity(new Intent(context, WinnerActivity.class));
    }

    private List<String> getSMList() {
        List<String> list = new ArrayList<>();
        list.add("玩家购买SN号,第一个SN001售价1元,以后每增加一个号加一元,总共500个SN。");
        list.add("每局游戏计时120分钟,每买一个SN倒计时增加10秒。");
        list.add("倒计时结束时最后一个SN购买者即中奖,游戏结束,奖金为奖池金额32%%,倒计时没结束前,若先购买到SN500,游戏结束,该号码SN500中奖。");
        list.add("玩家购买SN的10%%将作为红利均分给前面所有SN所有者。");
        list.add("SN售价超过200时,在SN46至SN199之间诞生随机大奖1（总奖池8%%）;SN售价超过300时,在SN200至SN299之间诞生随机大奖2（总奖池16%%）;SN售价超过400时,在SN300至SN399之间诞生随机大奖3（总奖池24%%）。");
        return list;
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
