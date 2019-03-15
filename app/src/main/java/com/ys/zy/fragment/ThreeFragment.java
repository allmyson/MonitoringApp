package com.ys.zy.fragment;

import android.support.v4.widget.SwipeRefreshLayout;

import com.ys.zy.R;
import com.ys.zy.base.BaseFragment;

/**
 * @author lh
 * @version 1.0.0
 * @filename OneFragment
 * @description -------------------------------------------------------
 * @date 2018/10/23 17:09
 */
public class ThreeFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener{
    public static ThreeFragment newInstance() {
        return new ThreeFragment();
    }
    @Override
    public void onRefresh() {

    }

    @Override
    protected void init() {

    }

    @Override
    protected void getData() {

    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_three;
    }
}
