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
import com.ys.zy.activity.SafeActivity;
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
                        break;
                    case 3:
                        break;
                    case 4:
                        break;
                }
            }
        });
        srl = (SwipeRefreshLayout) mView.findViewById(R.id.srl);
        srl.setOnRefreshListener(this);
        srl.setColorSchemeColors(getResources().getColor(R.color.main_color));
    }

    @Override
    protected void getData() {

    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_four;
    }
}
