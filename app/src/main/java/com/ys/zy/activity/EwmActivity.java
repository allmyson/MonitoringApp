package com.ys.zy.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.ImageView;

import com.upsoft.qrlibrary.QRCodeUtil;
import com.ys.zy.R;
import com.ys.zy.base.BaseActivity;
import com.ys.zy.util.Constant;
import com.ys.zy.util.DensityUtil;
import com.ys.zy.util.L;

import java.io.File;

public class EwmActivity extends BaseActivity {
    private ImageView ewmIV;
    private String url;
    private String type;

    @Override
    public int getLayoutId() {
        return R.layout.activity_ewm;
    }

    @Override
    public void initView() {
        setBarColor("#ededed");
        type = getIntent().getStringExtra("type");
        titleView.setText(type + "充值二维码");
        ewmIV = getView(R.id.iv_ewm);
        url = getIntent().getStringExtra("url");
        L.e("url=" + url);
        String path = Constant.EWM_PATH + File.separator + "wx_zf.png";
        QRCodeUtil.createQRImage(url, DensityUtil.dp2px(mContext, 200), DensityUtil.dp2px(mContext, 200), null, path);
        Bitmap bitmap = BitmapFactory.decodeFile(path);
        if (bitmap != null) {
            ewmIV.setImageBitmap(bitmap);
        }
    }


    @Override
    public void getData() {
    }

    @Override
    public void onClick(View v) {

    }

    public static void intentToEwm(Context context, String type, String url) {
        Intent intent = new Intent(context, EwmActivity.class);
        intent.putExtra("url", url);
        intent.putExtra("type", type);
        context.startActivity(intent);
    }
}
