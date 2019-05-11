package com.ys.zy.ssc.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.ys.zy.R;
import com.ys.zy.adapter.CommonAdapter;
import com.ys.zy.adapter.ViewHolder;
import com.ys.zy.racing.RacingUtil;
import com.ys.zy.racing.adapter.DwdAdapter;
import com.ys.zy.util.StringUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SscDxdsAdapter extends CommonAdapter<String> {
    private List<List<Boolean>> list;
    private int numberSize = 4;
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


    public SscDxdsAdapter(Context context, List<String> mDatas, int itemLayoutId) {
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
        numberList.add("大");
        numberList.add("小");
        numberList.add("单");
        numberList.add("双");
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
        List<String> numberList = getNumberList();
        String result = "";
        for (int i = 0; i < list.size(); i++) {
            String x = "";
            List<String> data = new ArrayList<>();
            for (int j = 0; j < list.get(i).size(); j++) {
                if (list.get(i).get(j)) {
                    data.add(numberList.get(j));
                }
            }
            if (data.size() == 0) {
                x = "-";
            } else if (data.size() == 1) {
                x = data.get(0);
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
    public String getJsonResult() {
        List<String> numberList = getNumberList();
        String result = "";
        List<Map<String, String>> data = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < list.get(i).size(); j++) {
                if (list.get(i).get(j)) {
                    Map<String, String> map = new HashMap<>();
                    map.put("bit", ""+getBit(i));
                    map.put("data", numberList.get(j));
                    data.add(map);
                }
            }
        }
        result = new Gson().toJson(data);
        return result;
    }

    public int getBit(int position) {
        int result = 1000;
        switch (position) {
            case 0:
                result = 1004;
                break;
            case 1:
                result = 1003;
                break;
            case 2:
                result = 1002;
                break;
            case 3:
                result = 1001;
                break;
            case 4:
                result = 1000;
                break;
        }
        return result;
    }


    class NumberAdapter extends CommonAdapter<String> {
        private List<Boolean> list;

        public NumberAdapter(Context context, List<String> mDatas, int itemLayoutId) {
            super(context, mDatas, itemLayoutId);
            list = new ArrayList<>();
            for (int i = 0; i < mDatas.size(); i++) {
                list.add(false);
            }
        }

        @Override
        public void convert(ViewHolder helper, String item, int position) {
            helper.setText(R.id.tv_number, item);
            TextView tv = helper.getView(R.id.tv_number);
            if (list.get(position)) {
                tv.setBackgroundResource(R.drawable.circle8);
                tv.setTextColor(Color.WHITE);
            } else {
                tv.setBackgroundResource(R.drawable.circle9);
                tv.setTextColor(mContext.getResources().getColor(R.color.main_color));
            }
        }

        public void setList(List<Boolean> list) {
            this.list = list;
        }

        public void cancelChooseOne(int position) {
            for (int i = 0; i < list.size(); i++) {
                if (i == position) {
                    list.set(i, false);
                    break;
                }
            }
            notifyDataSetInvalidated();
        }

        public void chooseOne(int position) {
            for (int i = 0; i < list.size(); i++) {
                if (i == position) {
                    list.set(i, true);
                    break;
                }
            }
            notifyDataSetInvalidated();
        }

        public void chooseAll() {
            for (int i = 0; i < list.size(); i++) {
                list.set(i, true);
            }
            notifyDataSetInvalidated();
        }

        public void chooseBig() {
            for (int i = 0; i < list.size(); i++) {
                if (i >= 5) {
                    list.set(i, true);
                } else {
                    list.set(i, false);
                }
            }
            notifyDataSetInvalidated();
        }

        public void chooseSmall() {
            for (int i = 0; i < list.size(); i++) {
                if (i < 5) {
                    list.set(i, true);
                } else {
                    list.set(i, false);
                }
            }
            notifyDataSetInvalidated();
        }

        public void chooseSingle() {
            for (int i = 0; i < list.size(); i++) {
                if (i % 2 != 0) {
                    list.set(i, true);
                } else {
                    list.set(i, false);
                }
            }
            notifyDataSetInvalidated();
        }

        public void chooseDouble() {
            for (int i = 0; i < list.size(); i++) {
                if (i % 2 == 0) {
                    list.set(i, true);
                } else {
                    list.set(i, false);
                }
            }
            notifyDataSetInvalidated();
        }

        public void clear() {
            for (int i = 0; i < mDatas.size(); i++) {
                list.set(i, false);
            }
            notifyDataSetInvalidated();
        }

        public String getNumber(int i) {
            if (i < 10) {
                return "0" + i;
            } else {
                return "" + i;
            }
        }

        public String getShowChooseResult() {
            String result = "";
            List<String> data = new ArrayList<>();
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i)) {
                    data.add(getItem(i));
                }
            }
            if (data.size() == 0) {
                result = "-";
            } else if (data.size() == 1) {
                result = data.get(0);
            } else {
                for (int j = 0; j < data.size(); j++) {
                    if (j == data.size() - 1) {
                        result += data.get(j);
                    } else {
                        result += (data.get(j) + "\t");
                    }
                }
            }
            return result;
        }
    }

}
