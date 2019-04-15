package com.ys.zy.racing.fragment;

import android.widget.GridView;

import com.ys.zy.R;
import com.ys.zy.base.BaseFragment;
import com.ys.zy.racing.adapter.RacingResultAdapter;

import java.util.ArrayList;
import java.util.List;

public class RacingTZFragment extends BaseFragment {
    private int type;
    private int play;
    private GridView resultGV;
    private RacingResultAdapter racingResultAdapter;

    public static RacingTZFragment newInstance(int type, int play) {
        RacingTZFragment racingTZFragment = new RacingTZFragment();
        racingTZFragment.setType(type);
        racingTZFragment.setPlay(play);
        return racingTZFragment;
    }

    @Override
    protected void init() {
        resultGV = getView(R.id.gv_result);
        racingResultAdapter = new RacingResultAdapter(mContext, getResultList(), R.layout.item_text);
        resultGV.setAdapter(racingResultAdapter);
    }

    @Override
    protected void getData() {

    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_racing_tz;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setPlay(int play) {
        this.play = play;
    }

    private List<String> getResultList() {
        List<String> list = new ArrayList<>();
        list.add("01");
        list.add("02");
        list.add("03");
        list.add("04");
        list.add("05");
        list.add("06");
        list.add("07");
        list.add("08");
        list.add("09");
        list.add("10");
        return list;
    }
}
