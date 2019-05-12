package com.ys.zy.racing.fragment;

import android.graphics.Color;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.ys.zy.R;
import com.ys.zy.util.StringUtil;
import com.ys.zy.util.YS;

import java.util.ArrayList;
import java.util.List;


public class RacingLHDFragment extends RacingFragment implements View.OnClickListener {
    private TextView smTV, longTV, huTV;
    private boolean isLong = false;
    private boolean isHu = false;

    public static RacingLHDFragment newInstance() {
        return new RacingLHDFragment();
    }

    @Override
    protected void init() {
        smTV = getView(R.id.tv_sm);
        setSM(3.916);
        longTV = getView(R.id.tv_long);
        huTV = getView(R.id.tv_hu);
        longTV.setOnClickListener(this);
        huTV.setOnClickListener(this);
    }

    @Override
    protected void getData() {

    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_racing_lhd;
    }

    @Override
    public void clearData() {
        isLong = false;
        isHu = false;
        if (longTV != null) {
            longTV.setTextColor(Color.parseColor("#dd2230"));
            longTV.setBackgroundResource(R.drawable.rect_cornor_gray3);
        }
        if (huTV != null) {
            huTV.setTextColor(Color.parseColor("#dd2230"));
            huTV.setBackgroundResource(R.drawable.rect_cornor_gray3);
        }
    }

    @Override
    public String getTZResult() {
        return getShowResult();
    }

    @Override
    public String getJsonResult() {
        List<String> list= new ArrayList<>();
        if(isLong){
            list.add("龙");
        }
        if(isHu){
            list.add("虎");
        }
        String json = new Gson().toJson(list);
        return json;
    }

    private void setSM(double money) {
        String sm = String.format("冠军与亚军车号比较，冠军车号大则为龙，反之则为虎，奖金:<font color=\"#dd2230\">%s</font>" + YS.UNIT + "。", StringUtil.StringToDoubleStr(money));
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            smTV.setText(Html.fromHtml(sm, Html.FROM_HTML_MODE_LEGACY));
        } else {
            smTV.setText(Html.fromHtml(sm));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_long:
                if (isLong) {
                    isLong = false;
                    longTV.setTextColor(Color.parseColor("#dd2230"));
                    longTV.setBackgroundResource(R.drawable.rect_cornor_gray3);
                } else {
                    isLong = true;
                    longTV.setTextColor(Color.parseColor("#f7f7f7"));
                    longTV.setBackgroundResource(R.drawable.rect_cornor_red3);
                }
                notifyData();
                break;
            case R.id.tv_hu:
                if (isHu) {
                    isHu = false;
                    huTV.setTextColor(Color.parseColor("#dd2230"));
                    huTV.setBackgroundResource(R.drawable.rect_cornor_gray3);
                } else {
                    isHu = true;
                    huTV.setTextColor(Color.parseColor("#f7f7f7"));
                    huTV.setBackgroundResource(R.drawable.rect_cornor_red3);
                }
                notifyData();
                break;
        }
    }

    private void notifyData() {
        if (getParentFragment() instanceof RacingTZFragment) {
            int tzNum = 0;
            if (isLong && isHu) {
                tzNum = 2;
            } else if (!isLong && !isHu) {
                tzNum = 0;
            } else {
                tzNum = 1;
            }
            ((RacingTZFragment) getParentFragment()).change(tzNum, getShowResult());
        }
    }

    private String getShowResult() {
        if (!isLong && !isHu) {
            return "-,-";
        } else if (isLong && !isHu) {
            return "龙,-";
        } else if (!isLong && isHu) {
            return "-,虎";
        } else if (isLong && isHu) {
            return "龙,虎";
        }
        return "-,-";
    }

}
