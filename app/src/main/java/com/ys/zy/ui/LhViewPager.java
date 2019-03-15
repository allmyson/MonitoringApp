package com.ys.zy.ui;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

/**
 * @author lh
 * @version 1.0.0
 * @filename MyViewPager
 * @description -------------------------------------------------------
 * @date 2017/5/11 10:55
 */
public class LhViewPager extends ViewPager {
    public LhViewPager(Context context) {
        super(context);
    }

    public LhViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void setCurrentItem(int item) {
        setCurrentItem(item, false);
    }
}
