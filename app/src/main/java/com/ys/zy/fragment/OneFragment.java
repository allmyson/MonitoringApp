package com.ys.zy.fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;

import com.ys.zy.R;
import com.ys.zy.base.BaseFragment;
import com.ys.zy.ui.NoNetView;

/**
 * @author lh
 * @version 1.0.0
 * @filename OneFragment
 * @description -------------------------------------------------------
 * @date 2018/10/23 17:09
 */
public class OneFragment extends BaseFragment implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {
    private NoNetView noNetView;

    public static OneFragment newInstance() {
        return new OneFragment();
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    protected void init() {
        noNetView = getView(R.id.nnv_);
        noNetView.setClickListener(new NoNetView.ClickListener() {
            @Override
            public void reload() {
                show("重新加载");
            }
        });
    }

    @Override
    protected void getData() {

    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_one;
    }
}
