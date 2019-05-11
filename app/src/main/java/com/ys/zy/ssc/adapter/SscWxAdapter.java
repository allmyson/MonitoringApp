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

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SscWxAdapter extends CommonAdapter<String> {
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


    public SscWxAdapter(Context context, List<String> mDatas, int itemLayoutId) {
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
        List<List<Integer>> aa = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            List<Integer> zz = new ArrayList<>();
            for (int j = 0; j < list.get(i).size(); j++) {
                if (list.get(i).get(j)) {
                    zz.add(j);
                }
            }
            aa.add(zz);
        }
        boolean isShow = true;
        for (int m = 0; m < aa.size(); m++) {
            if (aa.get(m).size() == 0) {
                num = 0;
                isShow = false;
                break;
            }
        }
        if(isShow){
            ArrayList<String> result = new ArrayList<>();
            if (mDatas.size() == 5) {
                for (int i = 0; i < aa.get(0).size(); i++) {
                    for (int j = 0; j < aa.get(1).size(); j++) {
                        for (int k = 0; k < aa.get(2).size(); k++) {
                            for (int m = 0; m < aa.get(3).size(); m++) {
                                for (int n = 0; n < aa.get(4).size(); n++) {
                                    StringBuilder sb = new StringBuilder();
                                    sb.append(aa.get(0).get(i));
                                    sb.append(",");
                                    sb.append(aa.get(1).get(j));
                                    sb.append(",");
                                    sb.append(aa.get(2).get(k));
                                    sb.append(",");
                                    sb.append(aa.get(3).get(m));
                                    sb.append(",");
                                    sb.append(aa.get(4).get(n));
                                    result.add(sb.toString());
                                }
                            }
                        }
                    }
                }
            }
            num = result.size();
        }
        return num;
    }

    /**
     * 投注内容显示结果
     * 02 03,03,03,03,-,-,-,-,-,-
     */
    public String getShowResult() {
        List<List<Integer>> aa = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            List<Integer> zz = new ArrayList<>();
            for (int j = 0; j < list.get(i).size(); j++) {
                if (list.get(i).get(j)) {
                    zz.add(j);
                }
            }
            aa.add(zz);
        }
        ArrayList<String> result = new ArrayList<>();
        if (mDatas.size() == 5) {
            for (int i = 0; i < aa.get(0).size(); i++) {
                for (int j = 0; j < aa.get(1).size(); j++) {
                    for (int k = 0; k < aa.get(2).size(); k++) {
                        for (int m = 0; m < aa.get(3).size(); m++) {
                            for (int n = 0; n < aa.get(4).size(); n++) {
                                StringBuilder sb = new StringBuilder();
                                sb.append(aa.get(0).get(i));
                                sb.append(",");
                                sb.append(aa.get(1).get(j));
                                sb.append(",");
                                sb.append(aa.get(2).get(k));
                                sb.append(",");
                                sb.append(aa.get(3).get(m));
                                sb.append(",");
                                sb.append(aa.get(4).get(n));
                                result.add(sb.toString());
                            }
                        }
                    }
                }
            }
        }
        String show = "";
        if (result.size() == 1) {
            show = "(" + result.get(0) + ")";
        } else {
            for (int m = 0; m < result.size(); m++) {
                if (m != result.size() - 1) {
                    show += "(" + result.get(m) + "),";
                } else {
                    show += "(" + result.get(m) + ")";
                }
            }
        }
        return show;
    }

    /**
     * 得到投注内容
     *
     * @return
     */
    public String getJsonResult() {
        List<List<Integer>> aa = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            List<Integer> zz = new ArrayList<>();
            for (int j = 0; j < list.get(i).size(); j++) {
                if (list.get(i).get(j)) {
                    zz.add(j);
                }
            }
            aa.add(zz);
        }
        ArrayList<String> result = new ArrayList<>();
        if (mDatas.size() == 5) {
            for (int i = 0; i < aa.get(0).size(); i++) {
                for (int j = 0; j < aa.get(1).size(); j++) {
                    for (int k = 0; k < aa.get(2).size(); k++) {
                        for (int m = 0; m < aa.get(3).size(); m++) {
                            for (int n = 0; n < aa.get(4).size(); n++) {
                                StringBuilder sb = new StringBuilder();
                                sb.append(aa.get(0).get(i));
                                sb.append(",");
                                sb.append(aa.get(1).get(j));
                                sb.append(",");
                                sb.append(aa.get(2).get(k));
                                sb.append(",");
                                sb.append(aa.get(3).get(m));
                                sb.append(",");
                                sb.append(aa.get(4).get(n));
                                result.add(sb.toString());
                            }
                        }
                    }
                }
            }
        }
        String json = new Gson().toJson(result);
        return json;
    }
}
