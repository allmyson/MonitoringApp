package com.ys.zy.ttz.activity;

import android.content.DialogInterface;
import android.content.Intent;
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

import com.ys.zy.R;
import com.ys.zy.base.BaseActivity;
import com.ys.zy.dialog.DialogUtil;
import com.ys.zy.dialog.GameFragment;
import com.ys.zy.fast3.activity.Fast3Activity;
import com.ys.zy.racing.activity.RacingActivity;
import com.ys.zy.roulette.activity.RouletteActivity;
import com.ys.zy.roulette.fragment.RouletteJLFragment;
import com.ys.zy.roulette.fragment.RouletteTZFragment;
import com.ys.zy.ssc.activity.SscActivity;
import com.ys.zy.ttz.fragment.TtzJLFragment;
import com.ys.zy.ttz.fragment.TtzTZFragment;
import com.ys.zy.util.StringUtil;
import com.ys.zy.winner.activity.WinnerActivity;
import com.ys.zy.winner.adapter.SmAdapter;

import java.util.ArrayList;
import java.util.List;

//推筒子
public class TtzActivity extends BaseActivity {
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
    private boolean isShow = true;
    private String money;
    private boolean isShowMoreGame = false;//是否显示其他游戏

    private LinearLayout gameLL;
    private LinearLayout smLL, smContentLL;
    private ListView smLV;
    private SmAdapter smAdapter;
    private List<String> smList;
    public static String content;


    @Override
    public int getLayoutId() {
        return R.layout.activity_ttz;
    }

    @Override
    public void initView() {
        smContentLL = getView(R.id.ll_smContent);
        smLV = getView(R.id.lv_sm);
        smList = new ArrayList<>();
        smList.addAll(getSMList());
        smAdapter = new SmAdapter(mContext, smList, R.layout.item_text3);
        smLV.setAdapter(smAdapter);
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
        money = moneyTV.getText().toString();
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
    }

    @Override
    public void getData() {

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
                                Fast3Activity.intentToFast3(mContext, Fast3Activity.TYPE_1FK3);
                                break;
                            case 2:
                                //推筒子
                                break;
                            case 3:
                                //5分快3
                                Fast3Activity.intentToFast3(mContext, Fast3Activity.TYPE_5FK3);
                                break;
                            case 4:
                                //最后胜利者
                                WinnerActivity.intentToWinner(mContext);
                                break;
                            case 5:
                                //江苏快3
                                Fast3Activity.intentToFast3(mContext, Fast3Activity.TYPE_JSK3);
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
        tzFragment = TtzTZFragment.newInstance();
        jlFragment = TtzJLFragment.newInstance();
    }

    private List<String> getSMList() {
        List<String> list = new ArrayList<>();
        list.add("一筒到九筒共36张牌,每次给庄和闲多发两张,玩家对庄和闲的输赢情况进行下注。");
        list.add("两张筒子相加取个位,对子大于单点,点数相同比较单牌,单排大赢,牌型完全一样为平;对9>对8>...>对1>9点>8点>...>0点。");
        return list;
    }
}
