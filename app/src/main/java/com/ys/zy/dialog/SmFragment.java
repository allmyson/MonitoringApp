package com.ys.zy.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.ys.zy.R;
import com.ys.zy.adapter.CommonAdapter;
import com.ys.zy.adapter.ViewHolder;
import com.ys.zy.bean.BankBean;
import com.ys.zy.util.DensityUtil;
import com.ys.zy.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

public class SmFragment extends LhDialogFragment {
    private int mTheme;
    private int mStyle;
    private View mContentView;
    private TextView contentTV;
    private String content;
    public static SmFragment newInstance(int style, int theme) {
        SmFragment pFragment = new SmFragment();
        Bundle args = new Bundle();
        args.putInt("style", style);
        args.putInt("theme", theme);
        pFragment.setArguments(args);
        return pFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setCancelable(true);// 设置点击屏幕Dialog消失
        mStyle = getArguments().getInt("style");
        mTheme = getArguments().getInt("theme");
        setStyle(mStyle, mTheme);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContentView = inflater.inflate(R.layout.fragment_sm, null, false);
        contentTV = (TextView) mContentView.findViewById(R.id.tv_content);
        contentTV.setText(StringUtil.valueOf(content));
        //去掉背景
        //去掉背景
        getDialog().getWindow().setBackgroundDrawable(new
                ColorDrawable(Color.TRANSPARENT));
        Dialog dialog = getDialog();
        if (dialog != null) {
            DisplayMetrics dm = new DisplayMetrics();
            getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
            WindowManager.LayoutParams attributes = dialog.getWindow().getAttributes();
            attributes.gravity = Gravity.TOP | Gravity.LEFT;//对齐方式
//            attributes.width = DensityUtil.dp2px(getContext(),100);
//            attributes.x = DensityUtil.dp2px(getContext(), 0);
            attributes.width = WindowManager.LayoutParams.MATCH_PARENT;
            attributes.horizontalMargin = 0;
            attributes.y = DensityUtil.dp2px(getContext(), 45);//具体头部距离
            dialog.getWindow().setAttributes(attributes);
            dialog.getWindow().getDecorView().setPadding(0, 0, 0, 0);
//            dialog.getWindow().setBackgroundDrawable( new ColorDrawable( 0xff000000 ) );
            dialog.getWindow().getDecorView().setMinimumWidth(getResources().getDisplayMetrics().widthPixels);
            dialog.getWindow().setLayout(dm.widthPixels, getDialog().getWindow().getAttributes().height);
        }
        return mContentView;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
