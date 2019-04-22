package com.ys.zy.ssc.adapter;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.ys.zy.R;
import com.ys.zy.adapter.CommonAdapter;
import com.ys.zy.adapter.ViewHolder;
import com.ys.zy.racing.RacingUtil;
import com.ys.zy.util.StringUtil;

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
                callback();
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
        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < list.get(i).size(); j++) {
                if (list.get(i).get(j)) {
                    num++;
                }
            }
        }
        return num;
    }

    /**
     * 投注内容显示结果
     * 02 03,03,03,03,-,-,-,-,-,-
     */
    public String getShowResult() {
        String result = "";
        for (int i = 0; i < list.size(); i++) {
            String x = "";
            List<Integer> data = new ArrayList<>();
            for (int j = 0; j < list.get(i).size(); j++) {
                if (list.get(i).get(j)) {
                    data.add(j);
                }
            }
            if (data.size() == 0) {
                x = "-";
            } else if (data.size() == 1) {
                x = "" + data.get(0);
            } else {
                for (int j = 0; j < data.size(); j++) {
                    if (j == data.size() - 1) {
                        x += data.get(j);
                    } else {
                        x += (data.get(j) + " ");
                    }
                }
            }
            if (i == list.size() - 1) {
                result += x;
            } else {
                result += x + ",";
            }
        }
        return result;
    }

    /**
     * 得到投注内容
     *
     * @return
     */
    public Map<String, List<String>> getResult() {
        Map<String, List<String>> map = new LinkedHashMap<>();
        List<String> resultList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            List<String> data = new ArrayList<>();
            for (int j = 0; j < list.get(i).size(); j++) {
                String x = "";
                if (list.get(i).get(j)) {
                    for (int k = 0; k < list.get(i).size(); k++) {
                        if (k != j) {
                            x += "-,";
                        } else {
                            x += RacingUtil.getNumber(j + 1) + ",";
                        }
                    }
                    x = x.substring(0, x.length() - 1);
                    data.add(x);
                }
            }
            if (data.size() > 0) {
                map.put(RacingUtil.getNameByPosition(i), data);
            }
        }
        return map;
    }
}
