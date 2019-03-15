package com.upsoft.qrlibrary;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;



public class ShowActivity extends FragmentActivity {
    private String result;
    private SharedPreferences sp;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sp = getSharedPreferences("upsoft_sdk", Context.MODE_PRIVATE);
        String barColor = sp.getString("upsoft_color_bar", "#2196F3");
        initImmersionBar(barColor);
        result = getIntent().getStringExtra(CaptureActivity.SCAN_RESULT);
        TextView textView = new TextView(this);
        setContentView(textView);
        textView.setText("扫描结果：" + result);

    }

    public static void showQRResult(Context context, String result) {
        Intent intent = new Intent(context, ShowActivity.class);
        intent.putExtra(CaptureActivity.SCAN_RESULT, result);
        context.startActivity(intent);
    }

    protected void initImmersionBar(String barColor) {
        mImmersionBar = ImmersionBar.with(this);
        mImmersionBar
                .statusBarColor(barColor)   //状态栏颜色，不写默认透明色
                .fitsSystemWindows(true)//解决状态栏和布局重叠问题，任选其一，默认为false，当为true时一定要指定statusBarColor()，不然状态栏为透明色
                .init();   //所有子类都将继承这些相同的属性
    }

    protected ImmersionBar mImmersionBar;
    protected void setTransparent() {
        mImmersionBar
                .statusBarColor(android.R.color.transparent)     //状态栏颜色，不写默认透明色
                .fitsSystemWindows(true)//解决状态栏和布局重叠问题，任选其一，默认为false，当为true时一定要指定statusBarColor()，不然状态栏为透明色
                .init();   //所有子类都将继承这些相同的属性
    }


    protected void setBarColor(String color) {
        mImmersionBar
                .statusBarColor(color)     //状态栏颜色，不写默认透明色
                .fitsSystemWindows(true)//解决状态栏和布局重叠问题，任选其一，默认为false，当为true时一定要指定statusBarColor()，不然状态栏为透明色
                .init();   //所有子类都将继承这些相同的属性
    }

}
