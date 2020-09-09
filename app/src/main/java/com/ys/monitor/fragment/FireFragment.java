package com.ys.monitor.fragment;

import android.support.v4.app.Fragment;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.ys.monitor.R;
import com.ys.monitor.adapter.FireAdapter;
import com.ys.monitor.base.BaseFragment;
import com.ys.monitor.sp.UserSP;
import com.ys.monitor.ui.BlankView;
import com.ys.monitor.ui.NoNetView;

import java.util.ArrayList;
import java.util.List;

public class FireFragment extends BaseFragment implements NoNetView.ClickListener {
    private ListView lv;
    private List<Object> list;
    private FireAdapter adapter;
    private String userId;
    private NoNetView noNetView;
    private BlankView blankView;
    private LinearLayout dataLL;

    public static Fragment newInstance() {
        return new FireFragment();
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
        list.add(null);
        list.add(null);
        list.add(null);
        adapter = new FireAdapter(mContext, list, R.layout.item_fire);
        lv.setAdapter(adapter);
        userId = UserSP.getUserId(mContext);
    }

    @Override
    public void getData() {
//        if (NetWorkUtil.isNetworkAvailable(mContext)) {
//            noNetView.setVisibility(View.GONE);
//            dataLL.setVisibility(View.VISIBLE);
//            HttpUtil.getMyJYJL(mContext, userId, new HttpListener<String>() {
//                @Override
//                public void onSucceed(int what, Response<String> response) {
//                    list.clear();
//                    MyJYBB myJybb = new Gson().fromJson(response.get(), MyJYBB.class);
//                    if (myJybb != null) {
//                        if (YS.SUCCESE.equals(myJybb.code) && myJybb.data != null && myJybb.data.data != null && myJybb.data.data.size() > 0) {
//                            list.addAll(myJybb.data.data);
//                        }
//                    } else {
//                        show(YS.HTTP_TIP);
//                    }
//                    adapter.refresh(list);
//                    if (adapter.getCount() > 0) {
//                        blankView.setVisibility(View.GONE);
//                    } else {
//                        blankView.setVisibility(View.VISIBLE);
//                    }
//                }
//
//                @Override
//                public void onFailed(int what, Response<String> response) {
//                    blankView.setVisibility(View.GONE);
//                }
//            });
//        } else {
//            noNetView.setVisibility(View.VISIBLE);
//            blankView.setVisibility(View.GONE);
//            dataLL.setVisibility(View.GONE);
//        }
    }


    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_fire;
    }

    @Override
    public void reload() {
        getData();
    }
}
