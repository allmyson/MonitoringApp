package com.ys.zy.racing.fragment;

import android.text.Html;
import android.widget.ListView;
import android.widget.TextView;

import com.ys.zy.R;
import com.ys.zy.racing.adapter.DwdAdapter;
import com.ys.zy.util.StringUtil;
import com.ys.zy.util.YS;

import java.util.ArrayList;
import java.util.List;

public class RacingDWDFragment extends RacingFragment {
    private TextView smTV;
    private ListView lv;
    private List<String> list;
    private DwdAdapter dwdAdapter;

    public static RacingDWDFragment newInstance() {
        return new RacingDWDFragment();
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
        dwdAdapter = new DwdAdapter(mContext, list, R.layout.item_dwd);
        lv.setAdapter(dwdAdapter);
        dwdAdapter.setChangeListener(new DwdAdapter.ChangeListener() {
            @Override
            public void getData(int tzNum, String showResult) {
                show("共" + tzNum + "注" + "\n" + showResult);
            }
        });
    }

    @Override
    protected void getData() {

    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_racing_dwd;
    }

    private void setSM(double money) {
        String sm = String.format("从任意名次上选择一个号码组成一组，选号与相同名次的号码一致，奖金:<font color=\"#dd2230\">%s</font>" + YS.UNIT + "。", StringUtil.StringToDoubleStr(money));
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            smTV.setText(Html.fromHtml(sm, Html.FROM_HTML_MODE_LEGACY));
        } else {
            smTV.setText(Html.fromHtml(sm));
        }
    }
}
