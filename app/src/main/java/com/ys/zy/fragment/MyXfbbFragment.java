package com.ys.zy.fragment;

import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.google.gson.Gson;
import com.yanzhenjie.nohttp.rest.Response;
import com.ys.zy.R;
import com.ys.zy.adapter.MyJyjlAdapter;
import com.ys.zy.adapter.MyXfjlAdapter;
import com.ys.zy.base.BaseFragment;
import com.ys.zy.bean.MyXfjlBean;
import com.ys.zy.bean.SubJYJL;
import com.ys.zy.bean.TzjlBean;
import com.ys.zy.http.HttpListener;
import com.ys.zy.sp.UserSP;
import com.ys.zy.ui.BlankView;
import com.ys.zy.ui.NoNetView;
import com.ys.zy.util.HttpUtil;
import com.ys.zy.util.NetWorkUtil;
import com.ys.zy.util.StringUtil;
import com.ys.zy.util.YS;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyXfbbFragment extends BaseFragment implements NoNetView.ClickListener {
    private ListView lv;
    private List<MyXfjlBean.DataBeanX.DataBean> list;
    private MyXfjlAdapter adapter;
    private String userId;
    private NoNetView noNetView;
    private BlankView blankView;
    private LinearLayout dataLL;

    public static Fragment newInstance() {
        return new MyXfbbFragment();
    }

    @Override
    protected void init() {
        dataLL = getView(R.id.ll_data);
        noNetView = getView(R.id.nnv_);
        blankView = getView(R.id.bv_);
        blankView.setImage(R.mipmap.blank_inf_img).setText("暂无记录");
        noNetView.setClickListener(this);
        lv = getView(R.id.lv_);
        list = new ArrayList<>();
        adapter = new MyXfjlAdapter(mContext, list, R.layout.item_xfjj);
        lv.setAdapter(adapter);
        userId = UserSP.getUserId(mContext);
    }

    @Override
    public void getData() {
        if (NetWorkUtil.isNetworkAvailable(mContext)) {
            noNetView.setVisibility(View.GONE);
            dataLL.setVisibility(View.VISIBLE);
            HttpUtil.getMyXFJL(mContext, userId, new HttpListener<String>() {
                @Override
                public void onSucceed(int what, Response<String> response) {
                    list.clear();
                    MyXfjlBean xfjlBean = new Gson().fromJson(response.get(), MyXfjlBean.class);
                    List<MyXfjlBean.DataBeanX.DataBean> tempList = new ArrayList<>();
                    if (xfjlBean != null) {
                        if (YS.SUCCESE.equals(xfjlBean.code) && xfjlBean.data != null && xfjlBean.data.data != null && xfjlBean.data.data.size() > 0) {
                            tempList.addAll(xfjlBean.data.data);
//                            list.addAll(xfjlBean.data.data);
                        }
                    } else {
                        show(YS.HTTP_TIP);
                    }
                    //先对游戏分组
                    Map<String, List<MyXfjlBean.DataBeanX.DataBean>> map = new HashMap<>();
                    List<MyXfjlBean.DataBeanX.DataBean> listTmp;
                    for (MyXfjlBean.DataBeanX.DataBean val : tempList) {
                        listTmp = map.get(val.game_code);
                        if (null == listTmp) {
                            listTmp = new ArrayList<>();
                            map.put(val.game_code, listTmp);
                        }
                        listTmp.add(val);
                    }
                    for (String key : map.keySet()) {
                        List<MyXfjlBean.DataBeanX.DataBean> periodsList = map.get(key);
                        //再对期号分组
                        Map<String, List<MyXfjlBean.DataBeanX.DataBean>> periodsMap = new HashMap<>();
                        List<MyXfjlBean.DataBeanX.DataBean> periodsListTmp;
                        for (MyXfjlBean.DataBeanX.DataBean val : periodsList) {
                            periodsListTmp = periodsMap.get(val.periods_num);
                            if (null == periodsListTmp) {
                                periodsListTmp = new ArrayList<>();
                                periodsMap.put(val.periods_num, periodsListTmp);
                            }
                            periodsListTmp.add(val);
                        }

                        for (String periodsKey : periodsMap.keySet()) {
                            List<MyXfjlBean.DataBeanX.DataBean> pTempList = periodsMap.get(periodsKey);
                            MyXfjlBean.DataBeanX.DataBean dataBean = new MyXfjlBean.DataBeanX.DataBean();
                            dataBean.periods_num = periodsKey;
                            if (pTempList != null && pTempList.size() > 0) {
                                dataBean.game_name = pTempList.get(0).game_name;
                                dataBean.game_code = pTempList.get(0).game_code;
                                double betMoney = 0;
                                double winMoney = 0;
                                for(MyXfjlBean.DataBeanX.DataBean bean:pTempList){
                                    betMoney += StringUtil.StringToDouble(bean.bets_money);
                                    winMoney += StringUtil.StringToDouble(bean.win_money);
                                }
                                dataBean.bets_money = StringUtil.valueOf(betMoney);
                                dataBean.win_money = StringUtil.valueOf(winMoney);
                            }
                            list.add(dataBean);
                        }
                    }
                    Collections.sort(list);
                    adapter.refresh(list);
                    if (adapter.getCount() > 0) {
                        blankView.setVisibility(View.GONE);
                    } else {
                        blankView.setVisibility(View.VISIBLE);
                    }
                }

                @Override
                public void onFailed(int what, Response<String> response) {
                    blankView.setVisibility(View.GONE);
                }
            });
        } else {
            noNetView.setVisibility(View.VISIBLE);
            blankView.setVisibility(View.GONE);
            dataLL.setVisibility(View.GONE);
        }
    }


    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_my_xfbb;
    }

    @Override
    public void reload() {
        getData();
    }
}
