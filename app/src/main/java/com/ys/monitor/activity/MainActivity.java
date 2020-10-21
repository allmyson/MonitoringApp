package com.ys.monitor.activity;

import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.huamai.poc.IPocEngineEventHandler;
import com.huamai.poc.PocEngineFactory;
import com.huamai.poc.greendao.MessageDialogue;
import com.ys.monitor.R;
import com.ys.monitor.adapter.MainFragmentAdapter;
import com.ys.monitor.base.BaseActivity;
import com.ys.monitor.bean.MainBean;
import com.ys.monitor.fragment.ThreeFragment;
import com.ys.monitor.sp.UserSP;
import com.ys.monitor.ui.LhViewPager;
import com.ys.monitor.update.UpdateManager;
import com.ys.monitor.util.L;
import com.ys.monitor.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    private TabLayout mTabLayout;
    private LhViewPager mViewPager;
    private MainFragmentAdapter mainFragmentAdapter;
    private List<MainBean> list;
    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        setTransparent();
        mTabLayout = getView(R.id.tabs);
        mViewPager = getView(R.id.vp_view);
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        list = new ArrayList<>();
        list.addAll(MainBean.getMainBeanList(mContext));
        mainFragmentAdapter = new MainFragmentAdapter(getSupportFragmentManager(), mContext, list);
        mViewPager.setAdapter(mainFragmentAdapter);
        mViewPager.setOffscreenPageLimit(5);
        mViewPager.setNoScroll(true);
        mTabLayout.setupWithViewPager(mViewPager);
        for (int i = 0; i < mainFragmentAdapter.getCount(); i++) {
            TabLayout.Tab tab = mTabLayout.getTabAt(i);//获得每一个tab
            tab.setCustomView(mainFragmentAdapter.getTabView(i));//给每一个tab设置view
        }
        mTabLayout.setSelectedTabIndicatorHeight(0);
        mTabLayout.setBackgroundColor(Color.WHITE);
        ((TextView) mTabLayout.getTabAt(0).getCustomView().findViewById(R.id.tv)).setTextColor(list.get(0)
                .selectTextColor);

        ((ImageView) mTabLayout.getTabAt(0).getCustomView().findViewById(R.id.iv)).setImageResource(list.get(0)
                .selectIconId);

        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                ((TextView) tab.getCustomView().findViewById(R.id.tv)).setTextColor(list.get(tab.getPosition())
                        .selectTextColor);
                ((ImageView) tab.getCustomView().findViewById(R.id.iv)).setImageResource(list.get(tab.getPosition())
                        .selectIconId);
                if (tab.getPosition() == 2) {
                    setBarColor("#0D87F8",false);
                } else {
                    setTransparent();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                ((TextView) tab.getCustomView().findViewById(R.id.tv)).setTextColor(list.get(tab.getPosition())
                        .unSelectTextColor);
                ((ImageView) tab.getCustomView().findViewById(R.id.iv)).setImageResource(list.get(tab.getPosition())
                        .unSelectIconId);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
//        new UpdateManager(mContext,false).checkUpdate();
        loginChat();
    }

    public void setPositionTab(int position) {
        if (position >= 0 && position <= 3) {
            mViewPager.setCurrentItem(position);
        }
    }

    @Override
    public void getData() {
        new UpdateManager(mContext, false).checkUpdate();
    }

    @Override
    public void onClick(View v) {

    }

    /**
     * 返回退出
     */
    private long firstTime = 0;

    public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            long time = System.currentTimeMillis();
            if (time - firstTime > 2000) {
                Toast.makeText(MainActivity.this, "再按一次退出", Toast.LENGTH_SHORT).show();
                firstTime = time;
                return true;
            } else {
                //退出相关操作
                PocEngineFactory.get().logout();
                System.exit(0);
                finish();
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    private void loginChat() {
        if (UserSP.isSoftPhone(mContext)) {
            String softPhoneId = UserSP.getSoftPhoneId(mContext);
            if (!StringUtil.isBlank(softPhoneId)) {
                if (!PocEngineFactory.get().hasServiceConnected()) {
                    L.e("chat", "未连接开始登陆...");
                    PocEngineFactory.get().addEventHandler(iPocEngineEventHandler);
                    PocEngineFactory.get().login(softPhoneId, "13579");
//                    PocEngineFactory.get().login("1111031", "13579");
//            PocEngineFactory.get().login("1111029", "13579");
                } else {
                    L.e("chat", "已登陆...");
                }
            }else {
                L.e("softPhoneId为空");
            }
        }else {
            L.e("当前用户不是云集讯用户");
        }
    }

    //登录时相关的回调，该过程可能需要几秒，可根据不同状态显示相应的UI
    IPocEngineEventHandler iPocEngineEventHandler = new IPocEngineEventHandler() {

        @Override
        public void onLoginStepProgress(int progress, String msg) {
            if (progress == LoginProgress.PRO_LOGIN_SUCCESS) {
                PocEngineFactory.get().removeEventHandler(iPocEngineEventHandler);
                L.e("chat", "Login success " + msg);
            } else if (progress == LoginProgress.PRO_BINDING_ACCOUNT_FAILED) {
                L.e("chat", "Login failed " + msg);
            } else if (progress == LoginProgress.PRO_BINDING_ACCOUNT_NOT_EXIST) {
                L.e("chat", "Login failed " + msg);
            } else if (progress == LoginProgress.PRO_BINDING_ACCOUNT_NOT_ACTIVE) {
                L.e("chat", "Login failed " + msg);
            } else if (progress == LoginProgress.PRO_LOGIN_FAILED) {
                L.e("chat", "Login failed " + msg);
            }
        }

        @Override
        public void onServiceConnected() {
            L.e("chat", "服务已连接");
        }

        @Override
        public void onServiceConnecting() {
            L.e("chat", "服务连接中......");
        }

        @Override
        public void onServiceConnectFailed(int reason) {
            L.e("chat", "服务连接失败" + reason);
        }

        @Override
        public void onServiceDisconnected(int reason) {
            L.e("chat", "服务断开连接,reason: " + reason);
        }

        @Override
        public void onNewConversationCreated(MessageDialogue messageDialogue) {
            ThreeFragment threeFragment = (ThreeFragment) mainFragmentAdapter.getItem(2);
            if (threeFragment != null) {
                threeFragment.addNewConversation(messageDialogue);
            }
        }
    };
}
