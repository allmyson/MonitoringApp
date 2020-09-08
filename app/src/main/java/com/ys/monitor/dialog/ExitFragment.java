package com.ys.monitor.dialog;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ys.monitor.R;
import com.ys.monitor.util.DensityUtil;


public class ExitFragment extends LhDialogFragment {
    private int mTheme;
    private int mStyle;
    private View mContentView;
    private ClickListener clickListener;
    private TextView cancelTV, sureTV, gameTV, gameNoTV, moneyTV, contentTV;
    private String game, gameNo, money, content;

    public static ExitFragment newInstance(int style, int theme) {
        ExitFragment pFragment = new ExitFragment();
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
        mContentView = inflater.inflate(R.layout.fragment_exit, null, false);
        cancelTV = (TextView) mContentView.findViewById(R.id.tv_cancel);
        sureTV = (TextView) mContentView.findViewById(R.id.tv_sure);
        cancelTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogUtil.removeDialog(getContext());
            }
        });
        sureTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickListener != null) {
                    clickListener.sure();
                }
            }
        });
        //去掉背景
        getDialog().getWindow().setBackgroundDrawable(new
                ColorDrawable(Color.TRANSPARENT));
        return mContentView;
    }

    public interface ClickListener {
        void sure();
    }

    public void setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @Override
    public void onResume() {
        super.onResume();
        getDialog().getWindow().setLayout(DensityUtil.dp2px(getContext(), 264), ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    public void setContent(String content) {
        this.content = content;
    }

}
