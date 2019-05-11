package com.ys.zy.common;

import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.google.gson.Gson;
import com.yanzhenjie.nohttp.rest.Response;
import com.ys.zy.R;
import com.ys.zy.base.BaseFragment;
import com.ys.zy.bean.TzjlBean;
import com.ys.zy.http.HttpListener;
import com.ys.zy.sp.UserSP;
import com.ys.zy.ssc.activity.SscActivity;
import com.ys.zy.ssc.adapter.SscTZJLAdapter;
import com.ys.zy.ui.BlankView;
import com.ys.zy.ui.NoNetView;
import com.ys.zy.util.HttpUtil;
import com.ys.zy.util.L;
import com.ys.zy.util.NetWorkUtil;
import com.ys.zy.util.StringUtil;
import com.ys.zy.util.YS;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TZJLFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, NoNetView.ClickListener {
    private int type;
    private int play;
    private ListView lv;
    private List<TzjlBean.DataBeanX.DataBean> allList;
    private List<TzjlBean.DataBeanX.DataBean> list;
    private TZJLAdapter adapter;
    private LinearLayout dataLL;
    private BlankView blankView;
    private NoNetView noNetView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private String userId;

    public static TZJLFragment newInstance(int type, int play) {
        TZJLFragment sscTZJLFragment = new TZJLFragment();
        sscTZJLFragment.setType(type);
        sscTZJLFragment.setPlay(play);
        return sscTZJLFragment;
    }

    @Override
    protected void init() {
        swipeRefreshLayout = getView(R.id.srl_);
        swipeRefreshLayout.setOnRefreshListener(this);
        dataLL = getView(R.id.ll_data);
        noNetView = getView(R.id.nnv_);
        blankView = getView(R.id.bv_);
        noNetView.setClickListener(this);
        blankView.setImage(R.mipmap.blank_inf_img2).setText("暂无记录");
        lv = getView(R.id.lv_);
        allList = new ArrayList<>();
        list = new ArrayList<>();
        adapter = new TZJLAdapter(mContext, list, R.layout.item_ssc_tzjl);
        lv.setAdapter(adapter);
        checkList();
        lv.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                View firstView = view.getChildAt(firstVisibleItem);
                if (firstVisibleItem == 0 && (firstView == null || firstView.getTop() == 0)) {
                    /*上滑到listView的顶部时，下拉刷新组件可见*/
                    swipeRefreshLayout.setEnabled(true);
                } else {
                    /*不是listView的顶部时，下拉刷新组件不可见*/
                    swipeRefreshLayout.setEnabled(false);
                }
            }
        });
        userId = UserSP.getUserId(mContext);
    }

    @Override
    protected void getData() {
        if (NetWorkUtil.isNetworkAvailable(mContext)) {
            noNetView.setVisibility(View.GONE);
            dataLL.setVisibility(View.VISIBLE);
            HttpUtil.getTZJL(mContext, userId, "" + type, new HttpListener<String>() {
                @Override
                public void onSucceed(int what, Response<String> response) {
                    allList.clear();
                    list.clear();
                    List<TzjlBean.DataBeanX.DataBean> tempList = new ArrayList<>();
                    TzjlBean bean = new Gson().fromJson(response.get(), TzjlBean.class);
                    if (bean != null && YS.SUCCESE.equals(bean.code) && bean.data != null && bean.data.data
                            != null && bean.data.data.size() > 0) {
                        for (int i = 0; i < bean.data.data.size(); i++) {
                            tempList.add(bean.data.data.get(i));
                        }
                    }
                    //分组
                    Map<String, List<TzjlBean.DataBeanX.DataBean>> map = new HashMap<>();
                    List<TzjlBean.DataBeanX.DataBean> listTmp;
                    for (TzjlBean.DataBeanX.DataBean val : tempList) {
                        listTmp = map.get(val.periods_num);
                        if (null == listTmp) {
                            listTmp = new ArrayList<>();
                            map.put(val.periods_num, listTmp);
                        }
                        listTmp.add(val);
                    }
                    for (String key : map.keySet()) {
                        List<TzjlBean.DataBeanX.DataBean> list = map.get(key);
                        TzjlBean.DataBeanX.DataBean dataBean = new TzjlBean.DataBeanX.DataBean();
                        dataBean.periods_num = key;
                        if (list != null && list.size() > 0) {
                            dataBean.complant_type_name = list.get(0).complant_type_name;
                            dataBean.game_name = list.get(0).game_name;
                            dataBean.lottery_type_code = list.get(0).game_code;
                            dataBean.bets_time = list.get(0).bets_time;
                            dataBean.complant_type_code = list.get(0).complant_type_code;
                            double betMoney = 0;
                            double winMoney = 0;
                            for (TzjlBean.DataBeanX.DataBean data : list) {
                                betMoney += StringUtil.StringToDouble(data.bets_money) * StringUtil
                                        .StringToDouble(data.times);
                                winMoney += StringUtil.StringToDouble(data.win_money);
                            }
                            dataBean.bets_money = StringUtil.valueOf(betMoney);
                            dataBean.win_money = StringUtil.valueOf(winMoney);
                            if ("1002".equals(list.get(0).is_win_code)) {
                                dataBean.is_win_code = list.get(0).is_win_code;
                                dataBean.is_win_name = list.get(0).is_win_name;
                            } else {
                                if (winMoney == 0) {
                                    dataBean.is_win_code = "1001";
                                    dataBean.is_win_name = "未中奖";
                                } else {
                                    dataBean.is_win_code = "1000";
                                    dataBean.is_win_name = "中奖";
                                }
                            }
                        }
                        allList.add(dataBean);
                    }
                    Collections.sort(allList);
                    selectData();
                    if (adapter.getCount() > 0) {
                        blankView.setVisibility(View.GONE);
                    } else {
                        blankView.setVisibility(View.VISIBLE);
                    }
                    swipeRefreshLayout.setRefreshing(false);
                }

                @Override
                public void onFailed(int what, Response<String> response) {
                    swipeRefreshLayout.setRefreshing(false);
                    blankView.setVisibility(View.GONE);
                    swipeRefreshLayout.setRefreshing(false);
                }
            });
        } else {
            noNetView.setVisibility(View.VISIBLE);
            blankView.setVisibility(View.GONE);
            dataLL.setVisibility(View.GONE);
        }

    }

    private void selectData() {
        if (allList.size() > 0) {
            list.addAll(allList);
        }
        adapter.refresh(list);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_ssc_tzjl;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setPlay(int play) {
        this.play = play;
    }

    @Override
    public void onRefresh() {
       getData();
    }

    private void checkList() {
        if (adapter.getCount() == 0) {
            blankView.setVisibility(View.VISIBLE);
            dataLL.setVisibility(View.GONE);
        } else {
            blankView.setVisibility(View.GONE);
            dataLL.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void reload() {
        L.e("刷新了");
        swipeRefreshLayout.setRefreshing(true);
        getData();
    }
}
