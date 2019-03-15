package com.upsoft.qrlibrary;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.DecodeHintType;
import com.google.zxing.Result;
import com.gyf.barlibrary.ImmersionBar;
import com.upsoft.qrlibrary.camera.CameraManager;
import com.upsoft.qrlibrary.control.AmbientLightManager;
import com.upsoft.qrlibrary.control.BeepManager;
import com.upsoft.qrlibrary.decode.CaptureActivityHandler;
import com.upsoft.qrlibrary.decode.Constant;
import com.upsoft.qrlibrary.decode.InactivityTimer;
import com.upsoft.qrlibrary.util.CodeUtils;
import com.upsoft.qrlibrary.util.QrcodeImageUtil;
import com.upsoft.qrlibrary.view.PromptAlertDialog;
import com.upsoft.qrlibrary.view.ViewfinderView;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;


public final class CaptureActivity extends FragmentActivity implements SurfaceHolder.Callback, OnClickListener {
    private boolean isTorchOn = false;
    private CameraManager cameraManager;
    private CaptureActivityHandler handler;
    private Result savedResultToShow;
    private ViewfinderView viewfinderView;
    private boolean hasSurface;
    private Collection<BarcodeFormat> decodeFormats;
    private Map<DecodeHintType, ?> decodeHints;
    private String characterSet;
    private InactivityTimer inactivityTimer;
    private BeepManager beepManager;
    private AmbientLightManager ambientLightManager;

    public ViewfinderView getViewfinderView() {
        return viewfinderView;
    }

    public Handler getHandler() {
        return handler;
    }

    private Context mContext;

    public CameraManager getCameraManager() {
        return cameraManager;
    }

    //切换扫描类型
    private TextView changeTV;
    //扫描类型
    private TextView typeTV;
    //1 二维码  2图片
    private int type = 1;
    //是否开灯 默认关闭
    private boolean isOpen = false;
    private final int GET_QRCODE_PIC = 1009;
    private String photoPath;

    private RelativeLayout titleRL;
    private SharedPreferences sp;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        getExtraIntent();
        mContext = this;
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_capture);
        changeTV = (TextView) this.findViewById(R.id.tv_change);
        typeTV = (TextView) this.findViewById(R.id.tv_typetx);
        typeTV.setText("将二维码放入框内即可自动扫描");
        changeTV.setOnClickListener(this);
        this.findViewById(R.id.common_im_leftImage).setOnClickListener(this);
        this.findViewById(R.id.common_im_rightImage).setOnClickListener(this);
        hasSurface = false;
        inactivityTimer = new InactivityTimer(this);
        beepManager = new BeepManager(this);
        ambientLightManager = new AmbientLightManager(this);
        titleRL = (RelativeLayout) findViewById(R.id.rl_title);
        sp = getSharedPreferences("upsoft_sdk", Context.MODE_PRIVATE);
        String titleColor = sp.getString("upsoft_color_title", "#2196F3");
        titleRL.setBackgroundColor(Color.parseColor(titleColor));
        String buttonColor = sp.getString("upsoft_color_btn", "#2196F3");
        shapeSolid(changeTV, buttonColor);
        String barColor = sp.getString("upsoft_color_bar", "#2196F3");
        initImmersionBar(barColor);
    }


    protected void initImmersionBar(String barColor) {
        mImmersionBar = ImmersionBar.with(this);
        mImmersionBar
                .statusBarColor(barColor)   //状态栏颜色，不写默认透明色
                .fitsSystemWindows(true)//解决状态栏和布局重叠问题，任选其一，默认为false，当为true时一定要指定statusBarColor()，不然状态栏为透明色
                .init();   //所有子类都将继承这些相同的属性
    }

    protected ImmersionBar mImmersionBar;
    protected void setTransparent() {
        mImmersionBar
                .statusBarColor(android.R.color.transparent)     //状态栏颜色，不写默认透明色
                .fitsSystemWindows(true)//解决状态栏和布局重叠问题，任选其一，默认为false，当为true时一定要指定statusBarColor()，不然状态栏为透明色
                .init();   //所有子类都将继承这些相同的属性
    }


    protected void setBarColor(String color) {
        mImmersionBar
                .statusBarColor(color)     //状态栏颜色，不写默认透明色
                .fitsSystemWindows(true)//解决状态栏和布局重叠问题，任选其一，默认为false，当为true时一定要指定statusBarColor()，不然状态栏为透明色
                .init();   //所有子类都将继承这些相同的属性
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onResume() {
        super.onResume();

        cameraManager = new CameraManager(getApplication());

        viewfinderView = (ViewfinderView) findViewById(R.id.viewfinder_view);
        viewfinderView.setCameraManager(cameraManager);

        handler = null;
        resetStatusView();

        SurfaceView surfaceView = (SurfaceView) findViewById(R.id.preview_view);
        SurfaceHolder surfaceHolder = surfaceView.getHolder();
        if (hasSurface) {
            initCamera(surfaceHolder);
        } else {
            surfaceHolder.addCallback(this);
            surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        }

        beepManager.updatePrefs();
        ambientLightManager.start(cameraManager);

        inactivityTimer.onResume();

        decodeFormats = null;
        characterSet = null;
    }

    @Override
    public void onPause() {
        if (handler != null) {
            handler.quitSynchronously();
            handler = null;
        }
        inactivityTimer.onPause();
        ambientLightManager.stop();
        cameraManager.closeDriver();
        if (!hasSurface) {
            SurfaceView surfaceView = (SurfaceView) findViewById(R.id.preview_view);
            SurfaceHolder surfaceHolder = surfaceView.getHolder();
            surfaceHolder.removeCallback(this);
        }
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        inactivityTimer.shutdown();
        viewfinderView.recycleLineDrawable();
        super.onDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_CAMERA:// 拦截相机键
                return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void decodeOrStoreSavedBitmap(Bitmap bitmap, Result result) {
        if (handler == null) {
            savedResultToShow = result;
        } else {
            if (result != null) {
                savedResultToShow = result;
            }
            if (savedResultToShow != null) {
                Message message = Message.obtain(handler,
                        Constant.R.decode_succeeded, savedResultToShow);
                handler.sendMessage(message);
            }
            savedResultToShow = null;
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (!hasSurface) {
            hasSurface = true;
            initCamera(holder);
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        hasSurface = false;
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    /**
     * 结果处理
     */
    public void handleDecode(Result rawResult, Bitmap barcode, float scaleFactor) {
        inactivityTimer.onActivity();
        beepManager.playBeepSoundAndVibrate();

        String msg = rawResult.getText();
        if (msg == null || "".equals(msg)) {
            msg = "无法识别";
        }
        //直接返回结果
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putString(SCAN_RESULT, msg);
        intent.putExtras(bundle);
        this.setResult(RESULT_OK, intent);
        finish();
    }

    private void initCamera(SurfaceHolder surfaceHolder) {
        if (surfaceHolder == null) {
            return;
        }
        if (cameraManager.isOpen()) {
            return;
        }
        try {
            cameraManager.openDriver(surfaceHolder);
            if (handler == null) {
                handler = new CaptureActivityHandler(this, decodeFormats, decodeHints, characterSet, cameraManager);
            }
            decodeOrStoreSavedBitmap(null, null);
        } catch (IOException ioe) {
            displayFrameworkBugMessageAndExit();
        } catch (RuntimeException e) {
            displayFrameworkBugMessageAndExit();
        }
    }

    private void displayFrameworkBugMessageAndExit() {

        showLogOutAlertDialog(CaptureActivity.this);
    }

    public void showLogOutAlertDialog(final Context context) {
        final PromptAlertDialog dialog = new PromptAlertDialog(context);
        dialog.setTitle("警告");
        dialog.setMessage("抱歉，相机出现问题，您可能需要重启设备");
        dialog.setNegativeButton(R.id.text_ok, "确定", new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                dialog.dismiss();
                CaptureActivity.this.finish();
            }
        }, View.VISIBLE);
        dialog.setNegativeButton(R.id.text_cancle, "取消", new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                dialog.dismiss();
                CaptureActivity.this.finish();
            }
        }, View.VISIBLE);
    }

    public void restartPreviewAfterDelay(long delayMS) {
        if (handler != null) {
            handler.sendEmptyMessageDelayed(Constant.R.restart_preview, delayMS);
        }
        resetStatusView();
    }

    private void resetStatusView() {
        viewfinderView.setVisibility(View.VISIBLE);
    }

    public void drawViewfinder() {
        viewfinderView.drawViewfinder();
    }

    public static final String KEY_NEED = "needResult";
    public static final String KEY_TYPE = "scanType";
    public static final String SCAN_RESULT = "result";
    public static final int DIRECT_VALUE = 1;
    private String[] scanType;//预留功能["qrCode","barCode"], 可以指定扫二维码还是一维码，默认二者都有

    private void getExtraIntent() {
        scanType = getIntent().getStringArrayExtra(KEY_TYPE);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.common_im_leftImage) {
            finish();
        } else if (i == R.id.tv_change) {

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                    PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "请开启文件读写权限", Toast.LENGTH_SHORT).show();
                ActivityCompat.requestPermissions(CaptureActivity.this, new String[]{Manifest.permission
                        .WRITE_EXTERNAL_STORAGE}, 1);
                return;
            }

            //改变
            if (type == 2) {
                type = 1;
                typeTV.setText("将二维码放入框内即可自动扫描");
                changeTV.setText("二维码图片识别");
            } else {
                type = 2;
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("image/*");
                startActivityForResult(intent, GET_QRCODE_PIC);
                typeTV.setText("将二维码图片放入框内即可自动扫描");
                changeTV.setText("二维码识别");
            }
        } else if (i == R.id.common_im_rightImage) {
            if (isOpen) {
                //关闭
                cameraManager.setTorch(false);
                isOpen = false;
            } else {
                //开启
                cameraManager.setTorch(true);
                isOpen = true;
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[]
            grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(CaptureActivity.this, "权限获取成功", Toast.LENGTH_SHORT).show();
                    if (type == 2) {
                        type = 1;

                        typeTV.setText("将二维码放入框内即可自动扫描");
                        changeTV.setText("二维码图片识别");

                    } else {
                        type = 2;
                        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                        intent.addCategory(Intent.CATEGORY_OPENABLE);
                        intent.setType("image/*");
                        startActivityForResult(intent, GET_QRCODE_PIC);
                        typeTV.setText("将二维码图片放入框内即可自动扫描");
                        changeTV.setText("二维码识别");
                    }
                } else {
                    Toast.makeText(CaptureActivity.this, "权限获取失败", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (GET_QRCODE_PIC == requestCode) {
            if (data != null) {
                Uri uri = data.getData();
                try {
                    CodeUtils.analyzeBitmap(QrcodeImageUtil.getImageAbsolutePath(this, uri), new CodeUtils
                            .AnalyzeCallback() {
                        @Override
                        public void onAnalyzeSuccess(Bitmap mBitmap, String result) {
//                            Toast.makeText(mContext, "解析结果:" + result, Toast.LENGTH_LONG).show();
                            Log.e("qrcode", "解析结果：" + result);
                            //直接返回结果
                            Intent intent = new Intent();
                            Bundle bundle = new Bundle();
                            bundle.putString(SCAN_RESULT, result);
                            intent.putExtras(bundle);
                            setResult(RESULT_OK, intent);
                            finish();
                        }

                        @Override
                        public void onAnalyzeFailed() {
                            Toast.makeText(mContext, "解析二维码失败", Toast.LENGTH_LONG).show();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    protected void shapeSolid(View v, String fillColor) {
        GradientDrawable gd = (GradientDrawable) v.getBackground();
        gd.setColor(Color.parseColor(fillColor));
    }
}
