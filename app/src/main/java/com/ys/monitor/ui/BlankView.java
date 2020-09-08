package com.ys.monitor.ui;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ys.monitor.R;
import com.ys.monitor.util.StringUtil;

/**
 * @author lh
 * @version 1.0.0
 * @filename NoNetView
 * @description -------------------------------------------------------
 * @date 2019/3/18 14:02
 */
public class BlankView extends RelativeLayout {
    private Context context;
    private View view;
    private ImageView iv;
    private TextView tv;

    public BlankView(Context context) {
        this(context, null);
    }

    public BlankView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BlankView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.context = context;
        view = LayoutInflater.from(context).inflate(R.layout.layout_blank, this);
        iv = (ImageView) view.findViewById(R.id.iv_);
        tv = (TextView) view.findViewById(R.id.tv_);
    }

    public BlankView setImage(int drawableId) {
        iv.setImageResource(drawableId);
        return this;
    }

    public BlankView setText(String text) {
        tv.setText(StringUtil.valueOf(text));
        return this;
    }
}
