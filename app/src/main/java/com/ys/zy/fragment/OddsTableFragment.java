package com.ys.zy.fragment;

import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.ys.zy.R;
import com.ys.zy.adapter.GameOrPlayAdapter;
import com.ys.zy.adapter.OddsTableAdapter;
import com.ys.zy.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

public class OddsTableFragment extends BaseFragment implements View.OnClickListener {
    private List<Object> list;
    private OddsTableAdapter oddsTableAdapter;
    private ListView oddsTableLV;


    private ListView gameLV, playLV;
    private GameOrPlayAdapter gameAdapter;
    private GameOrPlayAdapter playAdapter;
    private List<Object> gameList;
    private List<Object> playList;

    private LinearLayout gameLL, playLL;
    private TextView gameTV,playTV;
    private ImageView gameIV,playIV;
    public static OddsTableFragment newInstance() {
        return new OddsTableFragment();
    }

    @Override
    protected void init() {
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
        list.add(null);
        list.add(null);
        list.add(null);
        list.add(null);
        list.add(null);
        list.add(null);
        oddsTableAdapter = new OddsTableAdapter(mContext, list, R.layout.item_odds_table);
        oddsTableLV.setAdapter(oddsTableAdapter);
        gameList = new ArrayList<>();
        gameList.add(null);
        gameList.add(null);
        gameList.add(null);
        gameList.add(null);
        gameList.add(null);
        playList = new ArrayList<>();
        playList.add(null);
        playList.add(null);
        playList.add(null);
        playList.add(null);
        gameAdapter = new GameOrPlayAdapter(mContext, gameList, R.layout.item_game_or_play);
        playAdapter = new GameOrPlayAdapter(mContext, playList, R.layout.item_game_or_play);
        gameLV.setAdapter(gameAdapter);
        playLV.setAdapter(playAdapter);
        gameLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                gameLV.setVisibility(View.INVISIBLE);
                playLV.setVisibility(View.INVISIBLE);
                gameIV.setImageResource(R.mipmap.proxy_table_btn_more);
                playIV.setImageResource(R.mipmap.proxy_table_btn_more);
//                gameTV.setText();
            }
        });
        playLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                gameLV.setVisibility(View.INVISIBLE);
                playLV.setVisibility(View.INVISIBLE);
                gameIV.setImageResource(R.mipmap.proxy_table_btn_more);
                playIV.setImageResource(R.mipmap.proxy_table_btn_more);
//                playTV.setText();
            }
        });
    }

    @Override
    protected void getData() {

    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_odds_table;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_game:
                if(gameLV.getVisibility() == View.INVISIBLE){
                    gameLV.setVisibility(View.VISIBLE);
                    gameIV.setImageResource(R.mipmap.proxy_table_btn_up);
                }else {
                    gameLV.setVisibility(View.INVISIBLE);
                    gameIV.setImageResource(R.mipmap.proxy_table_btn_more);
                }
                break;
            case R.id.ll_play:
                if(playLV.getVisibility() == View.INVISIBLE){
                    playLV.setVisibility(View.VISIBLE);
                    playIV.setImageResource(R.mipmap.proxy_table_btn_up);
                }else {
                    playLV.setVisibility(View.INVISIBLE);
                    playIV.setImageResource(R.mipmap.proxy_table_btn_more);
                }
                break;
        }
    }
}
