package com.ys.monitor.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ys.monitor.R;
import com.ys.monitor.api.FunctionApi;
import com.ys.monitor.base.BaseActivity;

public class WelcomeActivity extends BaseActivity {


    private RelativeLayout bgRL;
    private TextView delayTV;
    private int total = 2;
    public static final int UPDATE = 222;
    public static final int FINISH = 223;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPDATE:
                    delayTV.setText(total + "s");
                    break;
                case FINISH:
                    doDelay();
                    break;
            }
        }
    };


    @Override
    public int getLayoutId() {
        return R.layout.activity_welcome;
    }

    @Override
    public void initView() {
        setTransparent();
        delayTV = getView(R.id.tv_delay);
        mContext = this;
        delayTV.setText("2s");
        update();
    }

    @Override
    public void getData() {

    }


    private void update() {
        new Thread() {
            @Override
            public void run() {
                try {
                    while (total > 0) {
                        Thread.sleep(1000);
                        handler.sendEmptyMessage(UPDATE);
                        total--;
                    }
                    handler.sendEmptyMessage(FINISH);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    public void doDelay() {
        if (FunctionApi.isLogin(mContext)) {
            Intent intent = new Intent(mContext, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            startActivity(new Intent(mContext, LoginActivity.class));
            finish();
        }
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    protected boolean isShowPermission() {
        return false;
    }
}
