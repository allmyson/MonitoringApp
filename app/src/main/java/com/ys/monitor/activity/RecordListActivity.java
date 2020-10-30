package com.ys.monitor.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.view.View;
import android.widget.Button;

import com.ys.monitor.R;
import com.ys.monitor.adapter.CommonFragmentAdapter;
import com.ys.monitor.base.BaseActivity;
import com.ys.monitor.bean.TabBean;
import com.ys.monitor.fragment.RecordFragment;
import com.ys.monitor.sp.RecordSP;
import com.ys.monitor.ui.LhViewPager;

import java.util.ArrayList;
import java.util.List;

public class RecordListActivity extends BaseActivity {
    private TabLayout tabLayout;
    private LhViewPager vp;
    private CommonFragmentAdapter mAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_record;
    }

    @Override
    public void initView() {
        setBarColor("#ffffff");
        titleView.setText("历史记录");
        tabLayout = getView(R.id.tl_);
        vp = getView(R.id.vp_);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        mAdapter = new CommonFragmentAdapter(getSupportFragmentManager(), getList());
        vp.setAdapter(mAdapter);
        vp.setOffscreenPageLimit(2);
        tabLayout.setupWithViewPager(vp);
        getView(R.id.rl_clear).setOnClickListener(this);
    }

    @Override
    public void getData() {

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.rl_clear) {
            showClearDilaog();
        }
    }

    private List<TabBean> getList() {
        List<TabBean> list = new ArrayList<>();
        list.add(new TabBean("上传失败", RecordFragment.newInstance(RecordFragment.TYPE_UNDO)));
        list.add(new TabBean("上传成功", RecordFragment.newInstance(RecordFragment.TYPE_FINISH)));
        return list;
    }

    private void showClearDilaog() {
        AlertDialog.Builder mDialog = new AlertDialog.Builder(mContext);
        mDialog.setTitle("提示");
        mDialog.setMessage("确定删除本地历史记录?");
        mDialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        //设置第二个按钮
        mDialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                RecordSP.clear(mContext);
                dialog.dismiss();
                mAdapter.getItem(0).onResume();
                mAdapter.getItem(1).onResume();
                show("删除成功");
            }
        });
        AlertDialog dialog = mDialog.create();
        dialog.show();
        Button btnPos = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
        Button btnNeg = dialog.getButton(DialogInterface.BUTTON_NEGATIVE);
        btnPos.setTextColor(Color.parseColor("#0D87F8"));
        btnNeg.setTextColor(Color.parseColor("#0D87F8"));
    }
}
