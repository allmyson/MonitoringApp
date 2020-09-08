package com.ys.monitor.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * @author lh
 * @version 1.0.0
 * @filename MyGridView
 * @description -------------------------------------------------------
 * @date 2018/11/8 18:58
 */
public class MyGridView extends GridView {
    public MyGridView(Context context) {
        super(context);
    }

    public MyGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec
                , MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST));
    }
}
