package com.ys.zy.fragment;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.ys.zy.R;
import com.ys.zy.fast3.activity.Fast3Activity;
import com.ys.zy.adapter.GameAdapter;
import com.ys.zy.base.BaseFragment;
import com.ys.zy.bean.GameBean;
import com.ys.zy.racing.activity.RacingActivity;
import com.ys.zy.roulette.activity.RouletteActivity;
import com.ys.zy.ssc.activity.SscActivity;
import com.ys.zy.ttz.activity.TtzActivity;
import com.ys.zy.winner.activity.WinnerActivity;

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
        gameAdapter = new GameAdapter(mContext, list, R.layout.item_game);
        gv.setAdapter(gameAdapter);
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        Fast3Activity.intentToFast3(mContext, Fast3Activity.TYPE_JSK3);
                        break;
                    case 1:
                        Fast3Activity.intentToFast3(mContext, Fast3Activity.TYPE_1FK3);
                        break;
                    case 2:
                        Fast3Activity.intentToFast3(mContext, Fast3Activity.TYPE_5FK3);
                        break;
                    case 3:
                        startActivity(new Intent(mContext, RouletteActivity.class));
                        break;
                    case 4:
                        startActivity(new Intent(mContext, TtzActivity.class));
                        break;
                    case 5:
                        WinnerActivity.intentToWinner(mContext);
                        break;
                    case 6:
                        RacingActivity.intentToRacing(mContext, RacingActivity.TYPE_BJSC);
                        break;
                    case 7:
                        RacingActivity.intentToRacing(mContext, RacingActivity.TYPE_1FSC);
                        break;
                    case 8:
                        RacingActivity.intentToRacing(mContext, RacingActivity.TYPE_5FSC);
                        break;
                    case 9:
                        SscActivity.intentToSSC(mContext, SscActivity.TYPE_1FC);
                        break;
                    case 10:
                        SscActivity.intentToSSC(mContext, SscActivity.TYPE_SSC);
                        break;
                }
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
