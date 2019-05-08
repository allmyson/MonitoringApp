package com.ys.zy.fragment;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yanzhenjie.nohttp.rest.Response;
import com.ys.zy.R;
import com.ys.zy.adapter.GameOrPlayAdapter;
import com.ys.zy.adapter.OddsTableAdapter;
import com.ys.zy.base.BaseFragment;
import com.ys.zy.bean.KeyValue;
import com.ys.zy.bean.OddBean;
import com.ys.zy.http.HttpListener;
import com.ys.zy.util.HttpUtil;
import com.ys.zy.util.OddUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OddsTableFragment extends BaseFragment implements View.OnClickListener {
    private List<KeyValue> list;
    private OddsTableAdapter oddsTableAdapter;
    private ListView oddsTableLV;


    private ListView gameLV, playLV;
    private GameOrPlayAdapter gameAdapter;
    private GameOrPlayAdapter playAdapter;
    private List<String> gameList;
    private List<String> playList;

    private LinearLayout gameLL, playLL;
    private TextView gameTV, playTV;
    private ImageView gameIV, playIV;
    private List<OddBean.DataBean> dataBeanList;

    public static OddsTableFragment newInstance() {
        return new OddsTableFragment();
    }

    @Override
    protected void init() {
        dataBeanList = new ArrayList<>();
        gameTV = getView(R.id.tv_game);
        playTV = getView(R.id.tv_play);
        gameIV = getView(R.id.iv_game);
        playIV = getView(R.id.iv_play);
        oddsTableLV = getView(R.id.lv_);
        gameLV = getView(R.id.lv_game);
        playLV = getView(R.id.lv_play);
        gameLL = getView(R.id.ll_game);
        playLL = getView(R.id.ll_play);
        gameLL.setOnClickListener(this);
        playLL.setOnClickListener(this);
        list = new ArrayList<>();
        oddsTableAdapter = new OddsTableAdapter(mContext, list, R.layout.item_odds_table);
        oddsTableLV.setAdapter(oddsTableAdapter);
        gameList = new ArrayList<>();
        gameList.addAll(OddUtil.getGameList());
        playList = new ArrayList<>();
        playList.addAll(OddUtil.getPlayList(gameList.get(0)));
        gameAdapter = new GameOrPlayAdapter(mContext, gameList, R.layout.item_game_or_play);
        playAdapter = new GameOrPlayAdapter(mContext, playList, R.layout.item_game_or_play);
        gameLV.setAdapter(gameAdapter);
        playLV.setAdapter(playAdapter);
        gameTV.setText(gameAdapter.getItem(0));
        playTV.setText(playAdapter.getItem(0));
        gameLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                gameLV.setVisibility(View.INVISIBLE);
                playLV.setVisibility(View.INVISIBLE);
                gameIV.setImageResource(R.mipmap.proxy_table_btn_more);
                playIV.setImageResource(R.mipmap.proxy_table_btn_more);
                gameTV.setText(gameAdapter.getItem(position));
                playList.clear();
                playList.addAll(OddUtil.getPlayList(gameAdapter.getItem(position)));
                playAdapter.refresh(playList);
                playTV.setText(playAdapter.getItem(0));
                if (dataBeanList != null && dataBeanList.size() > 0) {
                    list.clear();
                    list.addAll(OddUtil.getList(dataBeanList, gameTV.getText().toString().trim(), playTV.getText().toString().trim()));
                    Collections.sort(list);
                    oddsTableAdapter.refresh(list);
                }
            }
        });
        playLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                gameLV.setVisibility(View.INVISIBLE);
                playLV.setVisibility(View.INVISIBLE);
                gameIV.setImageResource(R.mipmap.proxy_table_btn_more);
                playIV.setImageResource(R.mipmap.proxy_table_btn_more);
                playTV.setText(playAdapter.getItem(position));
                if (dataBeanList != null && dataBeanList.size() > 0) {
                    list.clear();
                    list.addAll(OddUtil.getList(dataBeanList, gameTV.getText().toString().trim(), playTV.getText().toString().trim()));
                    Collections.sort(list);
                    oddsTableAdapter.refresh(list);
                }
            }
        });
    }

    @Override
    protected void getData() {
        HttpUtil.getOddList(mContext, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                list.clear();
                dataBeanList.clear();
                OddBean oddBean = new Gson().fromJson(response.get(), OddBean.class);
                if (oddBean != null && oddBean.data != null && oddBean.data.size() > 0) {
                    dataBeanList.addAll(oddBean.data);
                    list.addAll(OddUtil.getList(dataBeanList, gameTV.getText().toString().trim(), playTV.getText().toString().trim()));
                }
                Collections.sort(list);
                oddsTableAdapter.refresh(list);
            }

            @Override
            public void onFailed(int what, Response<String> response) {

            }
        });
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_odds_table;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_game:
                if (gameLV.getVisibility() == View.INVISIBLE) {
                    gameLV.setVisibility(View.VISIBLE);
                    gameIV.setImageResource(R.mipmap.proxy_table_btn_up);
                } else {
                    gameLV.setVisibility(View.INVISIBLE);
                    gameIV.setImageResource(R.mipmap.proxy_table_btn_more);
                }
                break;
            case R.id.ll_play:
                if (playLV.getVisibility() == View.INVISIBLE) {
                    playLV.setVisibility(View.VISIBLE);
                    playIV.setImageResource(R.mipmap.proxy_table_btn_up);
                } else {
                    playLV.setVisibility(View.INVISIBLE);
                    playIV.setImageResource(R.mipmap.proxy_table_btn_more);
                }
                break;
        }
    }
}
