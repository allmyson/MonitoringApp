package com.ys.monitor.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.VideoView;

import com.ys.monitor.R;
import com.ys.monitor.base.BaseActivity;
import com.ys.monitor.util.L;
import com.ys.monitor.util.StringUtil;

public class RtspVideoActivity extends BaseActivity {
    VideoView videoView;

    @Override
    public int getLayoutId() {
        return R.layout.activity_video;
    }

    @Override
    public void initView() {
        setBarColor("#ffffff");
        videoView = getView(R.id.videoView);
        String path = getIntent().getStringExtra("path");
        L.e("path=" + path);
        if (StringUtil.isBlank(path)) {
            show("播放地址不能为空！");
            finish();
            return;
        }
        // 此时的f.getAbsolutePath()=/storage/emulated/0/DCIM//\Camera/test.mp4
        Uri uri = Uri.parse(path);
        videoView.setVideoURI(uri);
        // 开始播放视频
        videoView.start();


        // VideiView获焦点
        // videoView.requestFocus();


    }

    @Override
    public void getData() {

    }

    @Override
    public void onClick(View v) {
    }

    public static void playRtspVideo(Context context, String path) {
        Intent intent = new Intent(context, RtspVideoActivity.class);
        intent.putExtra("path", path);
        context.startActivity(intent);
    }
}
