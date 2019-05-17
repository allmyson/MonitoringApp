package com.ys.zy.racing.fragment;

import android.text.Html;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yanzhenjie.nohttp.rest.Response;
import com.ys.zy.R;
import com.ys.zy.bean.OddsBean;
import com.ys.zy.http.HttpListener;
import com.ys.zy.racing.adapter.DwdAdapter;
import com.ys.zy.sp.UserSP;
import com.ys.zy.util.HttpUtil;
import com.ys.zy.util.StringUtil;
import com.ys.zy.util.YS;

import java.util.ArrayList;
import java.util.List;

public class RacingDWDFragment extends RacingFragment {
    private TextView smTV;
    private ListView lv;
    private List<String> list;
    private DwdAdapter dwdAdapter;
    private String userId;
    public static RacingDWDFragment newInstance() {
        return new RacingDWDFragment();
    }

    @Override
    protected void init() {
        smTV = getView(R.id.tv_sm);
        setSM(0.00);
        lv = getView(R.id.lv_);
        list = new ArrayList<>();
        list.add("冠军");
        list.add("亚军");
        list.add("季军");
        list.add("第四");
        list.add("第五");
        list.add("第六");
        list.add("第七");
        list.add("第八");
        list.add("第九");
        list.add("第十");
        dwdAdapter = new DwdAdapter(mContext, list, R.layout.item_dwd);
        lv.setAdapter(dwdAdapter);
        dwdAdapter.setChangeListener(new DwdAdapter.ChangeListener() {
            @Override
            public void getData(int tzNum, String showResult) {
                if (getParentFragment() instanceof RacingTZFragment) {
                    ((RacingTZFragment) getParentFragment()).change(tzNum, showResult);
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
        return R.layout.fragment_racing_dwd;
    }

    private void setSM(double money) {
        String sm = String.format("从任意名次上选择一个号码组成一组，每组<font color=\"#dd2230\">2</font>元，选号与相同名次的号码一致即中奖，赔率:<font color=\"#dd2230\">%s</font>", StringUtil.StringToDoubleStr(money));
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            smTV.setText(Html.fromHtml(sm, Html.FROM_HTML_MODE_LEGACY));
        } else {
            smTV.setText(Html.fromHtml(sm));
        }
    }

    @Override
    public void clearData() {
        if (dwdAdapter != null) {
            dwdAdapter.clear();
        }
    }

    @Override
    public String getTZResult() {
        if (dwdAdapter != null) {
            return dwdAdapter.getShowResult();
        }
        return "";
    }

    @Override
    public String getJsonResult() {
        if (dwdAdapter != null) {
            return dwdAdapter.getJsonResult();
        }
        return "";
    }
    private void getOdds() {
        HttpUtil.getGameOdds(mContext, userId, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                OddsBean oddsBean = new Gson().fromJson(response.get(), OddsBean.class);
                if (oddsBean != null && YS.SUCCESE.equals(oddsBean.code) && oddsBean.data != null) {
                    setSM(StringUtil.StringToDoubleTwo(oddsBean.data.scDwd));
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {

            }
        });
    }
}
