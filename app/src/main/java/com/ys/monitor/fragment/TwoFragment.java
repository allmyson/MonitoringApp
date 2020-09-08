package com.ys.monitor.fragment;

import android.view.View;

import com.ys.monitor.R;
import com.ys.monitor.base.BaseFragment;

/**
 * @author lh
 * @version 1.0.0
 * @filename OneFragment
 * @description -------------------------------------------------------
 * @date 2018/10/23 17:09
 */
public class TwoFragment extends BaseFragment implements View.OnClickListener {

    public static TwoFragment newInstance() {
        return new TwoFragment();
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
        return R.layout.fragment_two;
    }


}
