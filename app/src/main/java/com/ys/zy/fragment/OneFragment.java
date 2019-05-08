package com.ys.zy.fragment;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.google.gson.Gson;
import com.yanzhenjie.nohttp.rest.Response;
import com.ys.zy.R;
import com.ys.zy.activity.MsgDetailActivity;
import com.ys.zy.adapter.ImageHolder;
import com.ys.zy.api.FunctionApi;
import com.ys.zy.bean.ADBean;
import com.ys.zy.bean.GameJson;
import com.ys.zy.bean.MsgBean;
import com.ys.zy.fast3.activity.Fast3Activity;
import com.ys.zy.adapter.GameAdapter;
import com.ys.zy.base.BaseFragment;
import com.ys.zy.bean.GameBean;
import com.ys.zy.http.HttpListener;
import com.ys.zy.racing.activity.RacingActivity;
import com.ys.zy.roulette.activity.RouletteActivity;
import com.ys.zy.ssc.activity.SscActivity;
import com.ys.zy.ttz.activity.TtzActivity;
import com.ys.zy.util.HttpUtil;
import com.ys.zy.util.StringUtil;
import com.ys.zy.util.YS;
import com.ys.zy.winner.activity.WinnerActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lh
 * @version 1.0.0
 * @filename OneFragment
 * @description -------------------------------------------------------
 * @date 2018/10/23 17:09
 */
public class OneFragment extends BaseFragment implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {
    private GridView gv;
    private GameAdapter gameAdapter;
    private List<GameBean> list;
    private TextView msgTV;
    private List<ADBean.DataBeanX.DataBean> adList;
    private SwipeRefreshLayout swipeRefreshLayout;
    private MsgBean.DataBeanX.DataBean dataBean;
    private ConvenientBanner convenientBanner;

    public static OneFragment newInstance() {
        return new OneFragment();
    }

    @Override
    public void onRefresh() {
        getData();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_msg:
                if (dataBean != null) {
                    MsgDetailActivity.intentToMsgDetail(mContext, dataBean);
                }
                break;
            case R.id.iv_kf:
                FunctionApi.contactKF(mContext);
                break;
        }
    }

    @Override
    protected void init() {
        convenientBanner = getView(R.id.cb_);
        getView(R.id.ll_msg).setOnClickListener(this);
        getView(R.id.iv_kf).setOnClickListener(this);
        swipeRefreshLayout = getView(R.id.srl_);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.main_color));
        adList = new ArrayList<>();
        msgTV = getView(R.id.tv_msg);
        gv = getView(R.id.gv_);
        list = new ArrayList<>();
//        list.addAll(GameBean.getDefaultList());
        gameAdapter = new GameAdapter(mContext, list, R.layout.item_game);
        gv.setAdapter(gameAdapter);
        gv.setOnScrollListener(new AbsListView.OnScrollListener() {
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
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String code = gameAdapter.getItem(position).code;
                String status = gameAdapter.getItem(position).status;
                //1000 正常销售  1001维护中
                if ("1001".equals(status)) {
                    show("维护中");
                    return;
                }
                switch (code) {
                    case "1000":
                        SscActivity.intentToSSC(mContext, SscActivity.TYPE_SSC);
//                        Fast3Activity.intentToFast3(mContext, Fast3Activity.TYPE_JSK3);
                        break;
                    case "1001":
                        SscActivity.intentToSSC(mContext, SscActivity.TYPE_1FC);
//                        Fast3Activity.intentToFast3(mContext, Fast3Activity.TYPE_1FK3);
                        break;
                    case "1002":
                        WinnerActivity.intentToWinner(mContext);
//                        Fast3Activity.intentToFast3(mContext, Fast3Activity.TYPE_5FK3);
                        break;
                    case "1003":
                        startActivity(new Intent(mContext, RouletteActivity.class));
                        break;
                    case "1004":
                        startActivity(new Intent(mContext, TtzActivity.class));
                        break;
                    case "1005":
                        Fast3Activity.intentToFast3(mContext, Fast3Activity.TYPE_JSK3);
//                        WinnerActivity.intentToWinner(mContext);
                        break;
                    case "1006":
                        Fast3Activity.intentToFast3(mContext, Fast3Activity.TYPE_1FK3);
//                        RacingActivity.intentToRacing(mContext, RacingActivity.TYPE_BJSC);
                        break;
                    case "1007":
                        Fast3Activity.intentToFast3(mContext, Fast3Activity.TYPE_5FK3);
//                        RacingActivity.intentToRacing(mContext, RacingActivity.TYPE_1FSC);
                        break;
                    case "1008":
                        RacingActivity.intentToRacing(mContext, RacingActivity.TYPE_BJSC);
//                        RacingActivity.intentToRacing(mContext, RacingActivity.TYPE_5FSC);
                        break;
                    case "1009":
                        RacingActivity.intentToRacing(mContext, RacingActivity.TYPE_1FSC);
//                        SscActivity.intentToSSC(mContext, SscActivity.TYPE_1FC);
                        break;
                    case "1010":
                        RacingActivity.intentToRacing(mContext, RacingActivity.TYPE_5FSC);
//                        SscActivity.intentToSSC(mContext, SscActivity.TYPE_SSC);
                        break;
                }
            }
        });
    }

    @Override
    protected void getData() {
        HttpUtil.getAD(mContext, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                adList.clear();
                ADBean adBean = new Gson().fromJson(response.get(), ADBean.class);
                if (adBean != null && adBean.data != null && adBean.data.data != null&& adBean.data.data.size() > 0) {
                    for (ADBean.DataBeanX.DataBean dataBean : adBean.data.data) {
                        if ("1001".equals(dataBean.activity_status)) {
                            //只取进行中的活动
                            adList.add(dataBean);
                        }
                    }
                }
                setPage();
                //刷新广告
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        HttpUtil.selectMsg(mContext, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                MsgBean msgBean = new Gson().fromJson(response.get(), MsgBean.class);
                if (msgBean != null && msgBean.data != null&& msgBean.data.data != null && msgBean.data.data.size() > 0) {
                    dataBean = msgBean.data.data.get(0);
                    msgTV.setText(StringUtil.valueOf(msgBean.data.data.get(0).notice_content));
                }
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        HttpUtil.getGameList(mContext, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                list.clear();
                List<GameBean> newList = new ArrayList<>();
                GameJson gameJson = new Gson().fromJson(response.get(), GameJson.class);
                if (gameJson != null && gameJson.data != null && gameJson.data.size() > 0) {
                    for (GameJson.DataBean dataBean : gameJson.data) {
                        GameBean gameBean = new GameBean();
                        gameBean.status = dataBean.gameStatus;
                        gameBean.code = dataBean.gameCode;
//                        gameBean.name = dataBean.game_name;
                        gameBean.name = GameBean.getNameByCode(dataBean.gameCode);
                        gameBean.des = GameBean.getDesByCode(dataBean.gameCode);
                        gameBean.drawableId = GameBean.getImageByCode(dataBean.gameCode);
                        newList.add(gameBean);
                    }
                }
                list.addAll(newList);
                gameAdapter.refresh(list);
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_one;
    }


    private void setPage() {
        convenientBanner.setPages(new CBViewHolderCreator<ImageHolder>() {
            @Override
            public ImageHolder createHolder() {
                return new ImageHolder();
            }
        }, adList)//设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
                .setPageIndicator(new int[]{R.mipmap.ic_page_indicator, R.mipmap.ic_page_indicator_focused});
                //设置指示器的方向
//                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.ALIGN_PARENT_RIGHT)
//                .setOnPageChangeListener(this)//监听翻页事件
//                .setOnItemClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        convenientBanner.startTurning(5000);
    }

    @Override
    public void onPause() {
        super.onPause();
        convenientBanner.stopTurning();
    }
}
