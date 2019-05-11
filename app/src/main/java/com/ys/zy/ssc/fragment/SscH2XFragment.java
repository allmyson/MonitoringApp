package com.ys.zy.ssc.fragment;

import android.text.Html;
import android.widget.ListView;
import android.widget.TextView;

import com.ys.zy.R;
import com.ys.zy.ssc.adapter.SscDwdAdapter;
import com.ys.zy.ssc.adapter.SscH2XAdapter;
import com.ys.zy.util.StringUtil;
import com.ys.zy.util.YS;

import java.util.ArrayList;
import java.util.List;

public class SscH2XFragment extends SscFragment {
    private TextView smTV;
    private ListView lv;
    private List<String> list;
    private SscH2XAdapter h2xAdapter;

    public static SscH2XFragment newInstance() {
        return new SscH2XFragment();
    }

    @Override
    protected void init() {
        smTV = getView(R.id.tv_sm);
        setSM(98.00);
        lv = getView(R.id.lv_);
        list = new ArrayList<>();
        list.add("组选");
        h2xAdapter = new SscH2XAdapter(mContext, list, R.layout.item_h2x);
        lv.setAdapter(h2xAdapter);
        h2xAdapter.setChangeListener(new SscH2XAdapter.ChangeListener() {
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
        String sm = String.format("从0-9中选择2个数字组成一注，所选号码与开奖号码后二位相同，顺序不限（不含对子）奖金:<font color=\"#dd2230\">%s</font>" + YS.UNIT + "。", StringUtil.StringToDoubleStr(money));
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            smTV.setText(Html.fromHtml(sm, Html.FROM_HTML_MODE_LEGACY));
        } else {
            smTV.setText(Html.fromHtml(sm));
        }
    }

    @Override
    public void clearData() {
        if (h2xAdapter != null) {
            h2xAdapter.clear();
        }
    }

    @Override
    public String getTZResult() {
        if (h2xAdapter != null) {
            return h2xAdapter.getShowResult();
        }
        return "";
    }

    @Override
    public String getJsonResult() {
        if (h2xAdapter != null) {
            return h2xAdapter.getJsonResult();
        }
        return "";
    }

}
