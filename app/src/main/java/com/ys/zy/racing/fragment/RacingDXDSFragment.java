package com.ys.zy.racing.fragment;

import android.text.Html;
import android.widget.ListView;
import android.widget.TextView;

import com.ys.zy.R;
import com.ys.zy.racing.adapter.DwdAdapter;
import com.ys.zy.racing.adapter.DxdsAdapter;
import com.ys.zy.util.StringUtil;
import com.ys.zy.util.YS;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RacingDXDSFragment extends RacingFragment {
    private TextView smTV;
    private ListView lv;
    private List<String> list;
    private DxdsAdapter dxdsAdapter;

    public static RacingDXDSFragment newInstance() {
        return new RacingDXDSFragment();
    }

    @Override
    protected void init() {
        smTV = getView(R.id.tv_sm);
        setSM(19.50);
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
        dxdsAdapter = new DxdsAdapter(mContext, list, R.layout.item_dxds);
        lv.setAdapter(dxdsAdapter);
        dxdsAdapter.setChangeListener(new DwdAdapter.ChangeListener() {
            @Override
            public void getData(int tzNum, String showResult) {
                if (getParentFragment() instanceof RacingTZFragment) {
                    ((RacingTZFragment) getParentFragment()).change(tzNum, showResult);
                }
            }
        });
    }

    @Override
    protected void getData() {

    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_racing_dxds;
    }

    private void setSM(double money) {
        String sm = String.format("猜任意名次上的大小单双，01-05为小，06-10为大，选号与相同名次上的开奖号码形态一致，奖金:<font color=\"#dd2230\">%s</font>" + YS.UNIT + "。", StringUtil.StringToDoubleStr(money));
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

    @Override
    public String getJsonResult() {
        if (dxdsAdapter != null) {
            return dxdsAdapter.getJsonResult();
        }
        return "";
    }

}
