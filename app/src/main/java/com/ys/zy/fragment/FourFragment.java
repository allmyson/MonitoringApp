package com.ys.zy.fragment;

import android.content.Intent;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ys.zy.R;
import com.ys.zy.activity.AboutActivity;
import com.ys.zy.activity.MyFormActivity;
import com.ys.zy.activity.ProxyCenterActivity;
import com.ys.zy.activity.RechargeActivity;
import com.ys.zy.activity.SafeActivity;
import com.ys.zy.activity.TXActivity;
import com.ys.zy.activity.UserInfoActivity;
import com.ys.zy.adapter.MyAdapter;
import com.ys.zy.base.BaseFragment;
import com.ys.zy.bean.FourBean;
import com.ys.zy.ui.MyListView;

import java.util.ArrayList;
import java.util.List;


/**
 * @author lh
 * @version 1.0.0
 * @filename OneFragment
 * @description -------------------------------------------------------
 * @date 2018/10/23 17:09
 */
public class FourFragment extends BaseFragment implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {
    private MyListView mlv;
    private List<FourBean> myList;
    private MyAdapter myAdapter;
    private ImageView headIV;
    private SwipeRefreshLayout srl;
    private RelativeLayout rechargeRL, txRL;

    public static FourFragment newInstance() {
        return new FourFragment();
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                srl.setRefreshing(false);
            }
        }, 2000);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_recharge:
                startActivity(new Intent(mContext, RechargeActivity.class));
                break;
            case R.id.rl_tx:
                startActivity(new Intent(mContext, TXActivity.class));
                break;
        }
    }

    @Override
    protected void init() {
        mlv = getView(R.id.mlv_my);
        myList = new ArrayList<>();
        myList.addAll(FourBean.getDefaultList());
        myAdapter = new MyAdapter(mContext, myList, R.layout.item_my);
        mlv.setAdapter(myAdapter);
        mlv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        startActivity(new Intent(mContext, UserInfoActivity.class));
                        break;
                    case 1:
                        startActivity(new Intent(mContext, SafeActivity.class));
                        break;
                    case 2:
                        startActivity(new Intent(mContext, ProxyCenterActivity.class));
                        break;
                    case 3:
                        startActivity(new Intent(mContext, MyFormActivity.class));
                        break;
                    case 4:
                        startActivity(new Intent(mContext, AboutActivity.class));
                        break;
                }
            }
        });
        srl = (SwipeRefreshLayout) mView.findViewById(R.id.srl);
        srl.setOnRefreshListener(this);
        srl.setColorSchemeColors(getResources().getColor(R.color.main_color));
        rechargeRL = getView(R.id.rl_recharge);
        txRL = getView(R.id.rl_tx);
        rechargeRL.setOnClickListener(this);
        txRL.setOnClickListener(this);
    }

    @Override
    protected void getData() {

    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_four;
    }

}
