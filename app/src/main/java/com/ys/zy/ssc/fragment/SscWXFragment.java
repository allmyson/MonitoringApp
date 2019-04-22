package com.ys.zy.ssc.fragment;

import android.text.Html;
import android.widget.ListView;
import android.widget.TextView;

import com.ys.zy.R;
import com.ys.zy.ssc.adapter.SscDwdAdapter;
import com.ys.zy.ssc.adapter.SscWxAdapter;
import com.ys.zy.util.StringUtil;
import com.ys.zy.util.YS;

import java.util.ArrayList;
import java.util.List;

public class SscWXFragment extends SscFragment {
    private TextView smTV;
    private ListView lv;
    private List<String> list;
    private SscWxAdapter wxAdapter;

    public static SscWXFragment newInstance() {
        return new SscWXFragment();
    }

    @Override
    protected void init() {
        smTV = getView(R.id.tv_sm);
        setSM(196000.00);
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
    }

    @Override
    protected void getData() {

    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_ssc_dwd;
    }

    private void setSM(double money) {
        String sm = String.format("每位至少选择一个号码，与开奖号码和位置都对应即中奖，奖金:<font color=\"#dd2230\">%s</font>" + YS.UNIT + "。", StringUtil.StringToDoubleStr(money));
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

}
