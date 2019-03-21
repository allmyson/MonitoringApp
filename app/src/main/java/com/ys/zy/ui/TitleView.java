package com.ys.zy.ui;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ys.zy.R;
import com.ys.zy.util.StringUtil;

public class TitleView extends LinearLayout {
    private View view;
    private Context context;
    private DoListener doListener;
    private Button btn;
    private LinearLayout backLL;
    private TextView tv;

    public TitleView(Context context) {
        this(context, null);
    }

    public TitleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TitleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(final Context context) {
        this.context = context;
        view = LayoutInflater.from(context).inflate(R.layout.layout_title, this);
        backLL = (LinearLayout) view.findViewById(R.id.ll_back);
        tv = (TextView) view.findViewById(R.id.tv_);
        btn = (Button) view.findViewById(R.id.btn_);
        backLL.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (context instanceof Activity) {
                    ((Activity) context).finish();
                }
            }
        });
        btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (doListener != null) {
                    doListener.finish();
                }
            }
        });
    }

    public interface DoListener {
        void finish();
    }

    public TitleView setDoListener(DoListener doListener) {
        this.doListener = doListener;
        return this;
    }

    public TitleView setText(String text) {
        tv.setText(StringUtil.valueOf(text));
        return this;
    }

    public TitleView setBtnText(String text) {
        btn.setText(StringUtil.valueOf(text));
        return this;
    }

    public TitleView showBtn(boolean isShow) {
        if (isShow) {
            btn.setVisibility(View.VISIBLE);
        } else {
            btn.setVisibility(View.GONE);
        }
        return this;
    }
}
