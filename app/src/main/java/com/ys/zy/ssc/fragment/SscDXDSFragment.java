package com.ys.zy.ssc.fragment;

import android.text.Html;
import android.widget.ListView;
import android.widget.TextView;

import com.ys.zy.R;
import com.ys.zy.racing.adapter.DwdAdapter;
import com.ys.zy.racing.adapter.DxdsAdapter;
import com.ys.zy.racing.fragment.RacingFragment;
import com.ys.zy.racing.fragment.RacingTZFragment;
import com.ys.zy.ssc.adapter.SscDxdsAdapter;
import com.ys.zy.util.StringUtil;
import com.ys.zy.util.YS;

import java.util.ArrayList;
import java.util.List;

public class SscDXDSFragment extends SscFragment {
    private TextView smTV;
    private ListView lv;
    private List<String> list;
    private SscDxdsAdapter dxdsAdapter;

    public static SscDXDSFragment newInstance() {
        return new SscDXDSFragment();
    }

    @Override
    protected void init() {
        smTV = getView(R.id.tv_sm);
        setSM(3.92);
        lv = getView(R.id.lv_);
        list = new ArrayList<>();
        list.add("万位");
        list.add("千位");
        list.add("百位");
        list.add("十位");
        list.add("个位");
        dxdsAdapter = new SscDxdsAdapter(mContext, list, R.layout.item_dxds);
        lv.setAdapter(dxdsAdapter);
        dxdsAdapter.setChangeListener(new SscDxdsAdapter.ChangeListener() {
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
        return R.layout.fragment_ssc_dxds;
    }

    private void setSM(double money) {
        String sm = String.format("猜任意位置上的大小单双，0-4为小，5-9为大，选号与相同位置上的开奖号码形态一致，奖金:<font color=\"#dd2230\">%s</font>" + YS.UNIT + "。", StringUtil.StringToDoubleStr(money));
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            smTV.setText(Html.fromHtml(sm, Html.FROM_HTML_MODE_LEGACY));
        } else {
            smTV.setText(Html.fromHtml(sm));
        }
    }

    @Override
    public void clearData() {
        if (dxdsAdapter != null) {
            dxdsAdapter.clear();
        }
    }

    @Override
    public String getTZResult() {
        if (dxdsAdapter != null) {
            return dxdsAdapter.getShowResult();
        }
        return "";
    }

}
