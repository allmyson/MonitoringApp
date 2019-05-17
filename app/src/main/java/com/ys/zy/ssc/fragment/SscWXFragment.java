package com.ys.zy.ssc.fragment;

import android.text.Html;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yanzhenjie.nohttp.rest.Response;
import com.ys.zy.R;
import com.ys.zy.bean.OddsBean;
import com.ys.zy.http.HttpListener;
import com.ys.zy.sp.UserSP;
import com.ys.zy.ssc.adapter.SscDwdAdapter;
import com.ys.zy.ssc.adapter.SscWxAdapter;
import com.ys.zy.util.HttpUtil;
import com.ys.zy.util.StringUtil;
import com.ys.zy.util.YS;

import java.util.ArrayList;
import java.util.List;

public class SscWXFragment extends SscFragment {
    private TextView smTV;
    private ListView lv;
    private List<String> list;
    private SscWxAdapter wxAdapter;
    private String userId;
    public static SscWXFragment newInstance() {
        return new SscWXFragment();
    }

    @Override
    protected void init() {
        smTV = getView(R.id.tv_sm);
        setSM(0.00);
        lv = getView(R.id.lv_);
        list = new ArrayList<>();
        list.add("万位");
        list.add("千位");
        list.add("百位");
        list.add("十位");
        list.add("个位");
        wxAdapter = new SscWxAdapter(mContext, list, R.layout.item_wx);
        lv.setAdapter(wxAdapter);
        wxAdapter.setChangeListener(new SscWxAdapter.ChangeListener() {
            @Override
            public void getData(int tzNum, String showResult) {
                if (getParentFragment() instanceof SscTZFragment) {
                    ((SscTZFragment) getParentFragment()).change(tzNum, showResult);
                }
            }
        });
        userId = UserSP.getUserId(mContext);
    }

    @Override
    protected void getData() {
        getOdds();
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_ssc_dwd;
    }

    private void setSM(double money) {
        String sm = String.format("每位至少选择一个号码，与开奖号码和位置都对应即中奖，赔率:<font color=\"#dd2230\">%s</font>", StringUtil.StringToDoubleStr(money));
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            smTV.setText(Html.fromHtml(sm, Html.FROM_HTML_MODE_LEGACY));
        } else {
            smTV.setText(Html.fromHtml(sm));
        }
    }

    @Override
    public void clearData() {
        if (wxAdapter != null) {
            wxAdapter.clear();
        }
    }

    @Override
    public String getTZResult() {
        if (wxAdapter != null) {
            return wxAdapter.getShowResult();
        }
        return "";
    }

    @Override
    public String getJsonResult() {
        if (wxAdapter != null) {
            return wxAdapter.getJsonResult();
        }
        return "";
    }
    private void getOdds() {
        HttpUtil.getGameOdds(mContext, userId, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                OddsBean oddsBean = new Gson().fromJson(response.get(), OddsBean.class);
                if (oddsBean != null && YS.SUCCESE.equals(oddsBean.code) && oddsBean.data != null) {
                    setSM(StringUtil.StringToDoubleTwo(oddsBean.data.sscWxzx));
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {

            }
        });
    }
}
