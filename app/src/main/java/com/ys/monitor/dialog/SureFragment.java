package com.ys.monitor.dialog;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.ys.monitor.R;
import com.ys.monitor.util.StringUtil;


/**
 * @author lh
 * @version 1.0.0
 * @filename SureFragment
 * @description -------------------------------------------------------
 * @date 2017/12/13 16:44
 */
public class SureFragment extends DialogFragment {
    private int mTheme;
    private int mStyle;
    private ListView lv;
    private View mContentView;
    private ClickListener clickListener;
    private TextView contentTV,sureTV;
    private String content;



    public static SureFragment newInstance(int style, int theme) {
        SureFragment pFragment = new SureFragment();
        Bundle args = new Bundle();
        args.putInt("style", style);
        args.putInt("theme", theme);
        pFragment.setArguments(args);
        return pFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setCancelable(false);// 设置点击屏幕Dialog不消失
        mStyle = getArguments().getInt("style");
        mTheme = getArguments().getInt("theme");
        setStyle(mStyle, mTheme);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContentView = inflater.inflate(R.layout.fragment_sure, null, false);
        contentTV = (TextView) mContentView.findViewById(R.id.tv_content);
        contentTV.setText(StringUtil.valueOf(content));
        sureTV = (TextView) mContentView.findViewById(R.id.tv_sure);
        sureTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogUtil.removeDialog(getActivity());
            }
        });
        sureTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(clickListener!=null){
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

    public void setContent(String content) {
        this.content = content;
    }
}
