package com.ys.zy.fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.GridView;

import com.ys.zy.R;
import com.ys.zy.adapter.GameAdapter;
import com.ys.zy.base.BaseFragment;
import com.ys.zy.bean.GameBean;
import com.ys.zy.ui.NoNetView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lh
 * @version 1.0.0
 * @filename OneFragment
 * @description -------------------------------------------------------
 * @date 2018/10/23 17:09
 */
public class OneFragment extends BaseFragment implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {
    private GridView gv;
    private GameAdapter gameAdapter;
    private List<GameBean> list;
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
        gv = getView(R.id.gv_);
        list = new ArrayList<>();
        list.addAll(GameBean.getDefaultList());
        gameAdapter = new GameAdapter(mContext,list,R.layout.item_game);
        gv.setAdapter(gameAdapter);
    }

    @Override
    protected void getData() {

    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_one;
    }
}
