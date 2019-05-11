package com.ys.zy.ssc.fragment;

import android.text.Html;
import android.widget.ListView;
import android.widget.TextView;

import com.ys.zy.R;
import com.ys.zy.racing.fragment.RacingTZFragment;
import com.ys.zy.ssc.adapter.SscDwdAdapter;
import com.ys.zy.util.StringUtil;
import com.ys.zy.util.YS;

import java.util.ArrayList;
import java.util.List;

public class SscDWDFragment extends SscFragment {
    private TextView smTV;
    private ListView lv;
    private List<String> list;
    private SscDwdAdapter dwdAdapter;

    public static SscDWDFragment newInstance() {
        return new SscDWDFragment();
    }

    @Override
    protected void init() {
        smTV = getView(R.id.tv_sm);
        setSM(19.50);
        lv = getView(R.id.lv_);
        list = new ArrayList<>();
        list.add("个位");
        dwdAdapter = new SscDwdAdapter(mContext, list, R.layout.item_dwd);
        lv.setAdapter(dwdAdapter);
        dwdAdapter.setChangeListener(new SscDwdAdapter.ChangeListener() {
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
        String sm = String.format("选择一个号码作为个位开奖号码，所选号码与开奖结果一致，奖金:<font color=\"#dd2230\">%s</font>" + YS.UNIT + "。", StringUtil.StringToDoubleStr(money));
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
        if(dwdAdapter!=null){
            return dwdAdapter.getJsonResult();
        }
        return "";
    }

}
