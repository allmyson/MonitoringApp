package com.ys.zy.fragment;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.google.gson.Gson;
import com.yanzhenjie.nohttp.rest.Response;
import com.ys.zy.R;
import com.ys.zy.bean.GameJson;
import com.ys.zy.fast3.activity.Fast3Activity;
import com.ys.zy.adapter.GameAdapter;
import com.ys.zy.base.BaseFragment;
import com.ys.zy.bean.GameBean;
import com.ys.zy.http.HttpListener;
import com.ys.zy.racing.activity.RacingActivity;
import com.ys.zy.roulette.activity.RouletteActivity;
import com.ys.zy.ssc.activity.SscActivity;
import com.ys.zy.ttz.activity.TtzActivity;
import com.ys.zy.util.HttpUtil;
import com.ys.zy.util.YS;
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
                String code = gameAdapter.getItem(position).code;
                String status = gameAdapter.getItem(position).status;
                //1000 正常销售  1001维护中
                if ("1001".equals(status)) {
                    show("维护中");
                    return;
                }
                switch (code) {
                    case "1000":
                        SscActivity.intentToSSC(mContext, SscActivity.TYPE_SSC);
//                        Fast3Activity.intentToFast3(mContext, Fast3Activity.TYPE_JSK3);
                        break;
                    case "1001":
                        SscActivity.intentToSSC(mContext, SscActivity.TYPE_1FC);
//                        Fast3Activity.intentToFast3(mContext, Fast3Activity.TYPE_1FK3);
                        break;
                    case "1002":
                        WinnerActivity.intentToWinner(mContext);
//                        Fast3Activity.intentToFast3(mContext, Fast3Activity.TYPE_5FK3);
                        break;
                    case "1003":
                        startActivity(new Intent(mContext, RouletteActivity.class));
                        break;
                    case "1004":
                        startActivity(new Intent(mContext, TtzActivity.class));
                        break;
                    case "1005":
                        Fast3Activity.intentToFast3(mContext, Fast3Activity.TYPE_JSK3);
//                        WinnerActivity.intentToWinner(mContext);
                        break;
                    case "1006":
                        Fast3Activity.intentToFast3(mContext, Fast3Activity.TYPE_1FK3);
//                        RacingActivity.intentToRacing(mContext, RacingActivity.TYPE_BJSC);
                        break;
                    case "1007":
                        Fast3Activity.intentToFast3(mContext, Fast3Activity.TYPE_5FK3);
//                        RacingActivity.intentToRacing(mContext, RacingActivity.TYPE_1FSC);
                        break;
                    case "1008":
                        RacingActivity.intentToRacing(mContext, RacingActivity.TYPE_BJSC);
//                        RacingActivity.intentToRacing(mContext, RacingActivity.TYPE_5FSC);
                        break;
                    case "1009":
                        RacingActivity.intentToRacing(mContext, RacingActivity.TYPE_1FSC);
//                        SscActivity.intentToSSC(mContext, SscActivity.TYPE_1FC);
                        break;
                    case "1010":
                        RacingActivity.intentToRacing(mContext, RacingActivity.TYPE_5FSC);
//                        SscActivity.intentToSSC(mContext, SscActivity.TYPE_SSC);
                        break;
                }
            }
        });
    }

    @Override
    protected void getData() {
        HttpUtil.getGameList(mContext, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                list.clear();
                List<GameBean> newList = new ArrayList<>();
                GameJson gameJson = new Gson().fromJson(response.get(), GameJson.class);
                if (gameJson != null && gameJson.data != null && gameJson.data.size() > 0) {
                    for (GameJson.DataBean dataBean : gameJson.data) {
                        GameBean gameBean = new GameBean();
                        gameBean.status = dataBean.game_status;
                        gameBean.code = dataBean.game_code;
//                        gameBean.name = dataBean.game_name;
                        gameBean.name = GameBean.getNameByCode(dataBean.game_code);
                        gameBean.des = GameBean.getDesByCode(dataBean.game_code);
                        gameBean.drawableId = GameBean.getImageByCode(dataBean.game_code);
                        newList.add(gameBean);
                    }
                }
                list.addAll(newList);
                gameAdapter.refresh(list);
            }

            @Override
            public void onFailed(int what, Response<String> response) {

            }
        });
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_one;
    }
}
