package com.ys.zy.racing.adapter;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.ys.zy.R;
import com.ys.zy.adapter.CommonAdapter;
import com.ys.zy.adapter.ViewHolder;
import com.ys.zy.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

public class DwdAdapter extends CommonAdapter<String> {
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


    public DwdAdapter(Context context, List<String> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
        initData();
    }

    @Override
    public void convert(ViewHolder helper, String item, final int position) {
        helper.setText(R.id.tv_, StringUtil.valueOf(item));
        GridView gv = helper.getView(R.id.gv_);
        final NumberAdapter numberAdapter = new NumberAdapter(mContext, getNumberList(), R.layout
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

    private List<String> getNumberList() {
        List<String> numberList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            numberList.add(String.valueOf(i));
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
            List<String> resultList = getResult();
            changeListener.getData(resultList.size(), getShowResult());
        }
    }


    //是否可以投注
    public boolean canTZ() {
        List<String> resultList = getResult();
        return resultList.size() > 0;
    }


    /**
     * 投注内容显示结果
     * 02 03,03,03,03,-,-,-,-,-,-
     */
    public String getShowResult() {
        String result = "";
        for (int i = 0; i < list.size(); i++) {
            String x = "";
            for (int j = 0; j < list.get(i).size(); j++) {
                if (list.get(i).get(j)) {
                    if (j == list.get(i).size() - 1) {
                        x += getNumber(j + 1);
                    } else {
                        x += getNumber(j + 1) + "\t";
                    }
                }
            }
            if (i == list.size() - 1) {
                result += (StringUtil.isBlank(x) ? "-" : x);
            } else {
                result += (StringUtil.isBlank(x) ? "-" : x) + ",";
            }
        }
        return result;
    }

    /**
     * 得到投注内容
     *
     * @return
     */
    public List<String> getResult() {
        List<String> resultList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < list.get(i).size(); j++) {
                String templete = "-,-,-,-,-,-,-,-,-,-";
                String x;
                if (list.get(i).get(j)) {
                    x = templete.replace(templete.charAt(2 * j), '*');
                    x = x.replace("*", getNumber(j + 1));
                    resultList.add(x);
                }
            }
        }
        return resultList;
    }

    public String getNumber(int i) {
        if (i < 10) {
            return "0" + i;
        } else {
            return "" + i;
        }
    }
}
