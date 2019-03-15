package com.ys.zy.fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;

import com.ys.zy.R;
import com.ys.zy.base.BaseFragment;


/**
 * @author lh
 * @version 1.0.0
 * @filename OneFragment
 * @description -------------------------------------------------------
 * @date 2018/10/23 17:09
 */
public class FourFragment extends BaseFragment implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {
    public static FourFragment newInstance() {
        return new FourFragment();
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    protected void init() {

    }

    @Override
    protected void getData() {

    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_four;
    }
}
