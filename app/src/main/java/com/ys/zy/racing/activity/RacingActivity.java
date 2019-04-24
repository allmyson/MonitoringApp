package com.ys.zy.racing.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ys.zy.R;
import com.ys.zy.base.BaseActivity;
import com.ys.zy.dialog.DialogUtil;
import com.ys.zy.dialog.GameFragment;
import com.ys.zy.dialog.PlayFragment;
import com.ys.zy.fast3.activity.Fast3Activity;
import com.ys.zy.fast3.fragment.Fast3JLFragment;
import com.ys.zy.fast3.fragment.Fast3TZFragment;
import com.ys.zy.racing.RacingUtil;
import com.ys.zy.racing.fragment.RacingTZFragment;
import com.ys.zy.racing.fragment.RacingTZJLFragment;
import com.ys.zy.roulette.activity.RouletteActivity;
import com.ys.zy.ssc.activity.SscActivity;
import com.ys.zy.util.StringUtil;
import com.ys.zy.winner.activity.WinnerActivity;

import java.util.ArrayList;
import java.util.List;

//赛车
public class RacingActivity extends BaseActivity {

    public static final int TYPE_BJSC = 1000;//北京赛车
    public static final int TYPE_1FSC = 1001;//1分赛车
    public static final int TYPE_5FSC = 1002;//5分赛车
    private int type = TYPE_BJSC;
    public static final int PLAY_DWD = 50;//定位胆
    public static final int PLAY_DXDS = 51;//大小单双
    public static final int PLAY_LHD = 52;//龙虎斗
    private int play = PLAY_DWD;
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
    private boolean isShow = true;
    private String money = "19992.23";
    private ImageView gameMoreIV;
    private boolean isShowMoreGame = false;//是否显示其他游戏
    private LinearLayout playLL;
    private TextView playTV;
    private ImageView playIV;
    private String gameNo;

    @Override
    public int getLayoutId() {
        return R.layout.activity_racing;
    }

    @Override
    public void initView() {
        moneyTV = getView(R.id.tv_money);
        moneyTV.setText(money);
        showOrHideIV = getView(R.id.iv_showOrHide);
        showOrHideIV.setOnClickListener(this);
        manager = getSupportFragmentManager();
        type = getIntent().getIntExtra("type", TYPE_BJSC);
        initFragment();
        backRL = getView(R.id.rl_back);
        backRL.setOnClickListener(this);
        gameLL = getView(R.id.ll_game);
        gameLL.setOnClickListener(this);
        gameTV = getView(R.id.tv_gameName);
        gameMoreIV = getView(R.id.iv_gameMore);
        switch (type) {
            case TYPE_BJSC:
                gameTV.setText("北京赛车");
                break;
            case TYPE_1FSC:
                gameTV.setText("1分赛车");
                break;
            case TYPE_5FSC:
                gameTV.setText("5分赛车");
                break;
            default:
                gameTV.setText("北京赛车");
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
        playLL = getView(R.id.ll_play);
        playTV = getView(R.id.tv_play);
        playLL.setOnClickListener(this);
        playIV = getView(R.id.iv_play);
        playIV.setColorFilter(Color.parseColor("#dd2230"));
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
                                //分分彩
                                SscActivity.intentToSSC(mContext,SscActivity.TYPE_1FC);
                                break;
                            case 7:
                                //北京赛车
                                if (type != TYPE_BJSC) {
                                    RacingActivity.intentToRacing(mContext, TYPE_BJSC);
                                }
                                break;
                            case 8:
                                //时时彩
                                SscActivity.intentToSSC(mContext,SscActivity.TYPE_SSC);
                                break;
                            case 9:
                                //1分赛车
                                if (type != TYPE_1FSC) {
                                    RacingActivity.intentToRacing(mContext, TYPE_1FSC);
                                }
                                break;
                            case 10:
                                //更多
                                break;
                            case 11:
                                //5分赛车
                                if (type != TYPE_5FSC) {
                                    RacingActivity.intentToRacing(mContext, TYPE_5FSC);
                                }
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
            case R.id.ll_play:
                playIV.setImageResource(R.mipmap.bottom_btn_more);
                DialogUtil.showPlayDialog(mContext, getPlayList(), new PlayFragment.ClickListener() {
                    @Override
                    public void click(String name) {
                        playTV.setText(name);
                        if ("定位胆".equals(name)) {
                            play = PLAY_DWD;
                        } else if ("大小单双".equals(name)) {
                            play = PLAY_DXDS;
                        } else if ("龙虎斗".equals(name)) {
                            play = PLAY_LHD;
                        }
                        playIV.setImageResource(R.mipmap.top_btn_red_more_);
                        DialogUtil.removeDialog(mContext);
                        ((RacingTZFragment) tzFragment).showFragment(name);
                        ((RacingTZFragment) tzFragment).clearData();
                    }
                }, new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        playIV.setImageResource(R.mipmap.top_btn_red_more_);
                    }
                });
                break;
        }
    }

    public static void intentToRacing(Context context, int type) {
        Intent intent = new Intent(context, RacingActivity.class);
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
        tzFragment = RacingTZFragment.newInstance(type, play);
        jlFragment = RacingTZJLFragment.newInstance(type, play);
    }

    public double getMoney() {
        return StringUtil.StringToDoubleTwo(money);
    }

    private List<String> getPlayList() {
        List<String> list = new ArrayList<>();
        list.add("定位胆");
        list.add("大小单双");
        list.add("龙虎斗");
        return list;
    }

    public String getGameName() {
        String gameName = "北京赛车";
        switch (type) {
            case TYPE_BJSC:
                gameName = "北京赛车";
                break;
            case TYPE_1FSC:
                gameName = "1分赛车";
                break;
            case TYPE_5FSC:
                gameName = "5分赛车";
                break;
            default:
                gameName = "北京赛车";
                break;
        }
        switch (play){
            case PLAY_DWD:
                gameName+="(定位胆)";
                break;
            case PLAY_DXDS:
                gameName+="(大小单双)";
                break;
            case PLAY_LHD:
                gameName+="(龙虎斗)";
                break;
        }
        return gameName;
    }

    public String getGameNo() {
        String gameNo = "";
        switch (type) {
            case TYPE_BJSC:
                gameNo = RacingUtil.getCurrentBJSCPeriods();
                break;
            case TYPE_1FSC:
                gameNo = RacingUtil.getCurrent1FSCPeriods();
                break;
            case TYPE_5FSC:
                gameNo = RacingUtil.getCurrent5FSCPeriods();
                break;
            default:
                gameNo = "";
                break;
        }
        return gameNo;
    }
}
