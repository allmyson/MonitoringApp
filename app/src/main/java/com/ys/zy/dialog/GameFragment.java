package com.ys.zy.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yanzhenjie.nohttp.rest.Response;
import com.ys.zy.R;
import com.ys.zy.bean.GameBean;
import com.ys.zy.bean.GameJson;
import com.ys.zy.http.HttpListener;
import com.ys.zy.util.DensityUtil;
import com.ys.zy.util.HttpUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameFragment extends LhDialogFragment {
    private int mTheme;
    private int mStyle;
    private View mContentView;
    private ClickListener clickListener;
    private TextView tv0, tv1, tv2, tv3, tv4, tv5, tv6, tv7, tv8, tv9, tv10, tv11;
    private Map<String, String> map;

    public static GameFragment newInstance(int style, int theme) {
        GameFragment pFragment = new GameFragment();
        Bundle args = new Bundle();
        args.putInt("style", style);
        args.putInt("theme", theme);
        pFragment.setArguments(args);
        return pFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setCancelable(true);// 设置点击屏幕Dialog不消失
        mStyle = getArguments().getInt("style");
        mTheme = getArguments().getInt("theme");
        setStyle(mStyle, mTheme);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContentView = inflater.inflate(R.layout.fragment_game, null, false);
        map = new HashMap<>();
        tv0 = (TextView) mContentView.findViewById(R.id.tv0);
        tv1 = (TextView) mContentView.findViewById(R.id.tv1);
        tv2 = (TextView) mContentView.findViewById(R.id.tv2);
        tv3 = (TextView) mContentView.findViewById(R.id.tv3);
        tv4 = (TextView) mContentView.findViewById(R.id.tv4);
        tv5 = (TextView) mContentView.findViewById(R.id.tv5);
        tv6 = (TextView) mContentView.findViewById(R.id.tv6);
        tv7 = (TextView) mContentView.findViewById(R.id.tv7);
        tv8 = (TextView) mContentView.findViewById(R.id.tv8);
        tv9 = (TextView) mContentView.findViewById(R.id.tv9);
        tv10 = (TextView) mContentView.findViewById(R.id.tv10);
        tv11 = (TextView) mContentView.findViewById(R.id.tv11);
        tv0.setOnClickListener(this);
        tv1.setOnClickListener(this);
        tv2.setOnClickListener(this);
        tv3.setOnClickListener(this);
        tv4.setOnClickListener(this);
        tv5.setOnClickListener(this);
        tv6.setOnClickListener(this);
        tv7.setOnClickListener(this);
        tv8.setOnClickListener(this);
        tv9.setOnClickListener(this);
        tv10.setOnClickListener(this);
        tv11.setOnClickListener(this);
        //去掉背景
        getDialog().getWindow().setBackgroundDrawable(new
                ColorDrawable(Color.TRANSPARENT));
        Dialog dialog = getDialog();
        if (dialog != null) {
            DisplayMetrics dm = new DisplayMetrics();
            getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
            WindowManager.LayoutParams attributes = dialog.getWindow().getAttributes();
            attributes.gravity = Gravity.TOP | Gravity.RIGHT;//对齐方式
//            attributes.width = DensityUtil.dp2px(getContext(),100);
//            attributes.x = DensityUtil.dp2px(getContext(), 10);
            attributes.y = DensityUtil.dp2px(getContext(), 40);//具体头部距离
            dialog.getWindow().setAttributes(attributes);
            dialog.getWindow().setLayout((int) (dm.widthPixels * 0.5), ViewGroup.LayoutParams
                    .WRAP_CONTENT);
        }
        getData();
        return mContentView;
    }

    private void getData() {
        HttpUtil.getGameList(getContext(), new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                map.clear();
                GameJson gameJson = new Gson().fromJson(response.get(), GameJson.class);
                if (gameJson != null && gameJson.data != null && gameJson.data.size() > 0) {
                    for (GameJson.DataBean dataBean : gameJson.data) {
                        map.put(GameBean.getNameByCode(dataBean.gameCode), dataBean.gameStatus);
                    }
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {

            }
        });
    }


    public interface ClickListener {
        void click(int position);
    }

    public void setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @Override
    public void onClick(View arg0) {
        super.onClick(arg0);
        switch (arg0.getId()) {
            case R.id.tv0:
                click(tv0, 0);
                break;
            case R.id.tv1:
                click(tv1, 1);
                break;
            case R.id.tv2:
                click(tv2, 2);
                break;
            case R.id.tv3:
                click(tv3, 3);
                break;
            case R.id.tv4:
                click(tv4, 4);
                break;
            case R.id.tv5:
                click(tv5, 5);
                break;
            case R.id.tv6:
                click(tv6, 6);
                break;
            case R.id.tv7:
                click(tv7, 7);
                break;
            case R.id.tv8:
                click(tv8, 8);
                break;
            case R.id.tv9:
                click(tv9, 9);
                break;
            case R.id.tv10:
                click(tv10, 10);
                break;
            case R.id.tv11:
                click(tv11, 11);
                break;
        }
    }

    private void click(TextView tv, int position) {
        if (clickListener != null) {
            if (tv.getId() == R.id.tv10) {
                show("敬请期待");
                DialogUtil.removeDialog(getContext());
                return;
            }
            if ("1000".equals(map.get(tv.getText().toString()))) {
                clickListener.click(position);
            } else {
                show(tv.getText().toString() + "维护中");
                DialogUtil.removeDialog(getContext());
            }
        }
    }
}
