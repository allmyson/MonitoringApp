package com.ys.zy.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ys.zy.R;
import com.ys.zy.adapter.CommonFragmentAdapter;
import com.ys.zy.base.BaseActivity;
import com.ys.zy.bean.TabBean;
import com.ys.zy.fragment.Fast3JLFragment;
import com.ys.zy.fragment.Fast3TZFragment;
import com.ys.zy.fragment.HotGameFragment;
import com.ys.zy.fragment.WinnerListFragment;
import com.ys.zy.ui.LhViewPager;
import com.ys.zy.util.DensityUtil;
import com.ys.zy.util.StringUtil;
import com.ys.zy.util.TabUtil;

import java.util.ArrayList;
import java.util.List;

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
    private boolean isShow = true;
    private String money;
    @Override
    public int getLayoutId() {
        return R.layout.activity_fast3;
    }

    @Override
    public void initView() {
        moneyTV = getView(R.id.tv_money);
        money = moneyTV.getText().toString();
        showOrHideIV = getView(R.id.iv_showOrHide);
        showOrHideIV.setOnClickListener(this);
        manager = getSupportFragmentManager();
        type = getIntent().getIntExtra("type", TYPE_JSK3);
        initFragment();
        backRL = getView(R.id.rl_back);
        backRL.setOnClickListener(this);
        gameLL = getView(R.id.ll_game);
        gameTV = getView(R.id.tv_gameName);
        switch (type) {
            case TYPE_JSK3:
                gameTV.setText("江苏快3");
                break;
            case TYPE_1FK3:
                gameTV.setText("1分快3");
                break;
            case TYPE_5FK3:
                gameTV.setText("5分快3");
                break;
            default:
                gameTV.setText("江苏快3");
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
                if(isShow){
                    isShow = false;
                    showOrHideIV.setImageResource(R.mipmap.btn_hide);
                    moneyTV.setText(StringUtil.changeToX(money));
                }else {
                    isShow = true;
                    showOrHideIV.setImageResource(R.mipmap.btn_show);
                    moneyTV.setText(money);
                }
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
        jlFragment = Fast3JLFragment.newInstance(type);
    }
}
