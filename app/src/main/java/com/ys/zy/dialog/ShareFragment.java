package com.ys.zy.dialog;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.upsoft.qrlibrary.QRCodeUtil;
import com.ys.zy.R;
import com.ys.zy.util.Constant;
import com.ys.zy.util.DensityUtil;
import com.ys.zy.util.L;

import java.io.File;

public class ShareFragment extends LhDialogFragment {
    private int mTheme;
    private int mStyle;
    private View mContentView;
    private ClickListener clickListener;
    private TextView shareTV, saveTV;
    private ImageView iv;


    public static ShareFragment newInstance(int style, int theme) {
        ShareFragment pFragment = new ShareFragment();
        Bundle args = new Bundle();
        args.putInt("style", style);
        args.putInt("theme", theme);
        pFragment.setArguments(args);
        return pFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setCancelable(true);// 设置点击屏幕Dialog消失
        mStyle = getArguments().getInt("style");
        mTheme = getArguments().getInt("theme");
        setStyle(mStyle, mTheme);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContentView = inflater.inflate(R.layout.fragment_share, null, false);
        shareTV = (TextView) mContentView.findViewById(R.id.tv_share);
        saveTV = (TextView) mContentView.findViewById(R.id.tv_save);
        iv = (ImageView) mContentView.findViewById(R.id.iv_);
        shareTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickListener != null) {
                    clickListener.shareToWX();
                }
            }
        });
        saveTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickListener != null) {
                    clickListener.saveToLocal();
                }
            }
        });
        //去掉背景
        getDialog().getWindow().setBackgroundDrawable(new
                ColorDrawable(Color.TRANSPARENT));
        //二维码
//        Bitmap log = BitmapFactory.decodeResource(getResources(), R.mipmap.icon_launcher);
        String path = Constant.EWM_PATH + File.separator + "ewm.png";
//        L.e("二维码地址：" + path);
        String shareUrl = "http://www.baidu.com";
        L.e("二维码地址：" + shareUrl);
        QRCodeUtil.createQRImage(shareUrl, 200, 200, null, path);
        Bitmap bitmap = BitmapFactory.decodeFile(path);
        if (bitmap != null) {
            iv.setImageBitmap(bitmap);
        }
        return mContentView;
    }

    public interface ClickListener {
        void shareToWX();

        void saveToLocal();
    }

    public void setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @Override
    public void onResume() {
        super.onResume();
        getDialog().getWindow().setLayout(DensityUtil.dp2px(getContext(),304), DensityUtil.dp2px(getContext(),300));
    }
}