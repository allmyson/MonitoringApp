package com.ys.zy.activity;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.yanzhenjie.nohttp.rest.Response;
import com.ys.zy.R;
import com.ys.zy.adapter.PayAdapter;
import com.ys.zy.base.BaseActivity;
import com.ys.zy.bean.CzBean;
import com.ys.zy.bean.PayBean;
import com.ys.zy.bean.RechargePlatform;
import com.ys.zy.dialog.CzFragment;
import com.ys.zy.dialog.DialogUtil;
import com.ys.zy.http.HttpListener;
import com.ys.zy.sp.UserSP;
import com.ys.zy.ui.BlankView;
import com.ys.zy.ui.NoNetView;
import com.ys.zy.util.HttpUtil;
import com.ys.zy.util.NetWorkUtil;
import com.ys.zy.util.StringUtil;
import com.ys.zy.util.YS;
import com.ys.zy.web.CommonWebviewActivity;

import java.util.ArrayList;
import java.util.List;

//充值
public class RechargeActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, NoNetView.ClickListener {
    private ListView lv;
    private List<RechargePlatform.DataBean> list;
    private PayAdapter payAdapter;
    private String userId;
    private SwipeRefreshLayout srl;
    private NoNetView noNetView;
    private BlankView blankView;
    private String accountTypeCode;

    @Override
    public int getLayoutId() {
        return R.layout.activity_recharge;
    }

    @Override
    public void initView() {
        setBarColor("#ededed");
        titleView.setText("充值");
        noNetView = getView(R.id.nnv_);
        blankView = getView(R.id.bv_);
        blankView.setImage(R.mipmap.blank_inf_img).setText("暂无数据");
        noNetView.setClickListener(this);
        srl = getView(R.id.srl_);
        srl.setOnRefreshListener(this);
        srl.setColorSchemeColors(getResources().getColor(R.color.main_color));
        lv = getView(R.id.lv_);
        list = new ArrayList<>();
        payAdapter = new PayAdapter(mContext, list, R.layout.item_recharge);
        lv.setAdapter(payAdapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                accountTypeCode = payAdapter.getItem(position).accountTypeCode;
                if ("BANK".equals(accountTypeCode)) {
                    //一般充值
                    startActivity(new Intent(mContext, NormalCzActivity.class));
                } else {
                    final String accountId = payAdapter.getItem(position).accountId;
                    DialogUtil.showCZ(mContext, new CzFragment.ClickListener() {
                        @Override
                        public void sure(String money) {
                            DialogUtil.removeDialog(mContext);
                            cz(money, accountId);
                        }
                    });
                }
            }
        });
        lv.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                View firstView = view.getChildAt(firstVisibleItem);
                if (firstVisibleItem == 0 && (firstView == null || firstView.getTop() == 0)) {
                    /*上滑到listView的顶部时，下拉刷新组件可见*/
                    srl.setEnabled(true);
                } else {
                    /*不是listView的顶部时，下拉刷新组件不可见*/
                    srl.setEnabled(false);
                }
            }
        });
        userId = UserSP.getUserId(mContext);
    }

    private void cz(String money, String accountId) {
        HttpUtil.cz(mContext, userId, StringUtil.StringToDoubleStr(money), accountId, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                CzBean czBean = new Gson().fromJson(response.get(), CzBean.class);
                if (czBean != null) {
                    if (YS.SUCCESE.equals(czBean.code)) {
                        if ("WX".equals(accountTypeCode)) {
                            //微信生成支付二维码
                            EwmActivity.intentToEwm(mContext, czBean.data);
                        } else {
                            CommonWebviewActivity.openWebUrl(mContext, czBean.data);
                        }
                    } else {
                        show(StringUtil.valueOf(czBean.data));
                    }
                } else {
                    show(YS.HTTP_TIP);
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {

            }
        });
    }

    @Override
    public void getData() {
        if (NetWorkUtil.isNetworkAvailable(mContext)) {
            noNetView.setVisibility(View.GONE);
            lv.setVisibility(View.VISIBLE);
            HttpUtil.getCZList(mContext, new HttpListener<String>() {
                @Override
                public void onSucceed(int what, Response<String> response) {
                    list.clear();
                    RechargePlatform rechargePlatform = new Gson().fromJson(response.get(), RechargePlatform.class);
                    if (rechargePlatform != null && YS.SUCCESE.equals(rechargePlatform.code) && rechargePlatform.data != null && rechargePlatform.data.size() > 0) {
                        for (RechargePlatform.DataBean dataBean : rechargePlatform.data) {
                            if ("1000".equals(dataBean.accountStatus)) {
                                list.add(dataBean);
                            }
                        }
                    }
                    payAdapter.refresh(list);
                    if (payAdapter.getCount() > 0) {
                        blankView.setVisibility(View.GONE);
                    } else {
                        blankView.setVisibility(View.VISIBLE);
                    }
                    srl.setRefreshing(false);
                }

                @Override
                public void onFailed(int what, Response<String> response) {
                    srl.setRefreshing(false);
                    blankView.setVisibility(View.GONE);
                }
            });
        } else {
            noNetView.setVisibility(View.VISIBLE);
            blankView.setVisibility(View.GONE);
            lv.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onRefresh() {
        getData();
    }

    @Override
    public void reload() {
        srl.setRefreshing(true);
        onRefresh();
    }
}
