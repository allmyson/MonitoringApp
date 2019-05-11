package com.ys.zy.ssc.adapter;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.google.gson.Gson;
import com.ys.zy.R;
import com.ys.zy.adapter.CommonAdapter;
import com.ys.zy.adapter.ViewHolder;
import com.ys.zy.racing.RacingUtil;
import com.ys.zy.util.StringUtil;
import com.ys.zy.util.ZhUtil;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SscH2XAdapter extends CommonAdapter<String> {
    private List<List<Boolean>> list;
    private int numberSize = 10;
    private ChangeListener changeListener;

    private void initData() {
        list = new ArrayList<>();
        if (mDatas != null && mDatas.size() > 0) {
            for (int i = 0; i < mDatas.size(); i++) {
                List<Boolean> ll = new ArrayList<>();
                for (int j = 0; j < numberSize; j++) {
                    ll.add(false);
                }
                list.add(ll);
            }
        }
    }


    public SscH2XAdapter(Context context, List<String> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
        initData();
    }

    public void clear() {
        initData();
        notifyDataSetChanged();
    }

    @Override
    public void convert(ViewHolder helper, String item, final int position) {
        helper.setText(R.id.tv_, StringUtil.valueOf(item));
        GridView gv = helper.getView(R.id.gv_);
        final SscNumberAdapter numberAdapter = new SscNumberAdapter(mContext, getNumberList(), R.layout
                .item_number);
        numberAdapter.setList(list.get(position));
        gv.setAdapter(numberAdapter);
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int p, long id) {
                if (list.get(position).get(p)) {
                    list.get(position).set(p, false);
                    numberAdapter.cancelChooseOne(p);
                } else {
                    numberAdapter.chooseOne(p);
                    list.get(position).set(p, true);
                }
//                if (numberAdapter.getChooseNum() > 1) {
                callback();
//                }
            }
        });
    }

    private List<Integer> getNumberList() {
        List<Integer> numberList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            numberList.add(i);
        }
        return numberList;
    }

    public interface ChangeListener {
        void getData(int tzNum, String showResult);
    }

    public ChangeListener getChangeListener() {
        return changeListener;
    }

    public void setChangeListener(ChangeListener changeListener) {
        this.changeListener = changeListener;
    }

    public void callback() {
        if (changeListener != null) {
            changeListener.getData(getTZNum(), getShowResult());
        }
    }


    //是否可以投注
    public boolean canTZ() {
        return getTZNum() > 0;
    }

    public int getTZNum() {
        int num = 0;
        List<Integer> aa = new ArrayList<>();
        for (int j = 0; j < list.get(0).size(); j++) {
            if (list.get(0).get(j)) {
                aa.add(j);
            }
        }
        if (aa.size() < 2) {
            num = 0;
        } else {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < aa.size(); i++) {
                sb.append(aa.get(i));
            }
            List<String> cr = ZhUtil.getCombinationResult(2, ZhUtil.stringFilter(sb.toString()));
            num = cr.size();
        }
        return num;
    }

    /**
     * 投注内容显示结果
     * 02 03,03,03,03,-,-,-,-,-,-
     */
    public String getShowResult() {
        List<Integer> aa = new ArrayList<>();
        for (int j = 0; j < list.get(0).size(); j++) {
            if (list.get(0).get(j)) {
                aa.add(j);
            }
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < aa.size(); i++) {
            sb.append(aa.get(i));
        }
        List<String> cr = ZhUtil.getCombinationResult(2, ZhUtil.stringFilter(sb.toString()));
        List<String> dr = new ArrayList<>();
        for (int j = 0; j < cr.size(); j++) {
            StringBuilder sb1 = new StringBuilder(cr.get(j));
            if (sb1.length() > 0) {
                sb1.insert(1, ",");
                dr.add(sb1.toString());
            }
        }
        String result = "";
        if (dr.size() == 1) {
            result = "(" + dr.get(0) + ")";
        } else {
            for (int m = 0; m < dr.size(); m++) {
                if (m != dr.size() - 1) {
                    result += "(" + dr.get(m) + "),";
                } else {
                    result += "(" + dr.get(m) + ")";
                }
            }
        }
        return result;
    }

    /**
     * 得到投注内容
     *
     * @return
     */
    public String getJsonResult() {
        List<Integer> aa = new ArrayList<>();
        for (int j = 0; j < list.get(0).size(); j++) {
            if (list.get(0).get(j)) {
                aa.add(j);
            }
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < aa.size(); i++) {
            sb.append(aa.get(i));
        }
        List<String> cr = ZhUtil.getCombinationResult(2, ZhUtil.stringFilter(sb.toString()));
        List<String> dr = new ArrayList<>();
        for (int j = 0; j < cr.size(); j++) {
            StringBuilder sb1 = new StringBuilder(cr.get(j));
            if (sb1.length() > 0) {
                sb1.insert(1, ",");
                dr.add(sb1.toString());
            }
        }
        String json = new Gson().toJson(dr);
        return json;
    }
}
