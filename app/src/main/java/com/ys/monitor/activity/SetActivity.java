package com.ys.monitor.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ys.monitor.R;
import com.ys.monitor.base.BaseActivity;
import com.ys.monitor.sp.ContactSP;
import com.ys.monitor.sp.MsgSP;
import com.ys.monitor.util.Constant;
import com.ys.monitor.util.FileUtil;
import com.ys.monitor.util.L;
import com.ys.monitor.util.SPUtil;
import com.ys.monitor.util.StringUtil;

import java.io.File;

public class SetActivity extends BaseActivity {
    private LinearLayout updateLL;
    private TextView versionNameTV;
    private String size;

    @Override
    public int getLayoutId() {
        return R.layout.activity_set;
    }

    @Override
    public void initView() {
        setBarColor("#ffffff");
        titleView.setText("设置");
        size = FileUtil.getAutoFileOrFilesSize(Constant.getProjectRoot());
        ((TextView) getView(R.id.tv_content)).setText(size + "");
        getView(R.id.ll_clear).setOnClickListener(this);
    }

    @Override
    public void getData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_clear:
                showClearDilaog();
                break;
        }
    }

    private void showClearDilaog() {
        L.e("缓存目录大小=" + size);
        AlertDialog.Builder mDialog = new AlertDialog.Builder(mContext);
        mDialog.setTitle("提示");
        mDialog.setMessage("即将清除缓存文件的大小为" + StringUtil.valueOf(size));
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
                FileUtil.deleteFile2(new File(Constant.getProjectRoot()));
//                Constant.buildFile();
                SPUtil.deleteKey(mContext, MsgSP.KEY_MSG);
                SPUtil.deleteKey(mContext, MsgSP.KEY_FIRST_MSG);
                SPUtil.deleteKey(mContext, MsgSP.KEY_HELP);
                SPUtil.deleteKey(mContext, MsgSP.KEY_MSG_DETAIL);
                SPUtil.deleteKey(mContext, ContactSP.KEY_CONTACT);
                //指挥中心的数据也要删
                show("清除缓存成功");
                ((TextView) getView(R.id.tv_content)).setText("0B");
                dialog.dismiss();
            }
        });
        mDialog.create().show();
    }
}
