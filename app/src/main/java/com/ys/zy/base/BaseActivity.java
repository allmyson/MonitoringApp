package com.ys.zy.base;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.gyf.barlibrary.ImmersionBar;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RationaleListener;
import com.ys.zy.R;
import com.ys.zy.ui.TitleView;
import com.ys.zy.util.ActivityUtil;
import com.ys.zy.util.Constant;
import com.ys.zy.util.L;
import com.ys.zy.util.PermissionUtil;

import java.util.List;


public abstract class BaseActivity extends FragmentActivity implements PermissionListener, View.OnClickListener {
    protected Context mContext;
    protected Activity mActivity;
    protected ImmersionBar mImmersionBar;
    public static final int REQUEST_CODE_PERMISSION = 517;
    private static final int REQUEST_CODE_SETTING = 300;
    private String[] permissions;
    protected RelativeLayout backRL;
    protected TitleView titleView;
    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        mContext = this;
        mActivity = this;
        initImmersionBar();
        ActivityUtil.addActivity(this);
        setContentView(getLayoutId());
        if (isShowPermission()) {
            checkPermission();
        }
//        registLogin();
        titleView = getView(R.id.title);
        initView();
        getData();
    }

    protected void checkPermission() {
        permissions = PermissionUtil.getMyDanger(mContext);
        if (!AndPermission.hasPermission(mContext, permissions)) {
            L.e("权限不足");
            AndPermission.with(this)
                    .requestCode(REQUEST_CODE_PERMISSION)
                    .permission(permissions)
                    // rationale作用是：用户拒绝一次权限，再次申请时先征求用户同意，再打开授权对话框，避免用户勾选不再提示。
                    .rationale(new RationaleListener() {
                        @Override
                        public void showRequestPermissionRationale(int requestCode, Rationale rationale) {
                            // 这里的对话框可以自定义，只要调用rationale.resume()就可以继续申请。
                            AndPermission.rationaleDialog(mContext, rationale).show();
                        }
                    }).send();
        } else {
            //有权限了
            L.e("权限足");
            doSomeThing();
        }
    }


    public abstract int getLayoutId();

    public abstract void initView();

    public abstract void getData();

    protected void doSomeThing() {

    }


    protected final <E extends View> E getView(int id) {
        try {
            return (E) findViewById(id);
        } catch (ClassCastException ex) {
            L.e(ex.toString());
            throw ex;
        }
    }

    protected void show(String str) {
        Toast.makeText(mContext, str, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[]
            grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        /**
         * 转给AndPermission分析结果。
         *
         * @param requestCode  请求码。
         * @param permissions  权限数组，一个或者多个。
         * @param grantResults 请求结果。
         * @param listener PermissionListener 对象。
         */
        AndPermission.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onSucceed(int requestCode, List<String> grantPermissions) {
        switch (requestCode) {
            case REQUEST_CODE_PERMISSION: {
//                Toast.makeText(this, "获取定位相关权限成功", Toast.LENGTH_SHORT).show();
                onSucc();
                break;
            }
        }
    }

    protected void onSucc() {
        L.e("获取相关权限成功", "222");
    }

    @Override
    public void onFailed(int requestCode, List<String> deniedPermissions) {
        switch (requestCode) {
            case REQUEST_CODE_PERMISSION: {
                StringBuilder sb = new StringBuilder();
                for (String s : deniedPermissions) {
                    sb.append(s + "、");
                }
                sb.deleteCharAt(sb.length() - 1);
                L.e("获取相关权限失败,失败的权限包括" + sb.toString());
                break;
            }
        }

        // 用户否勾选了不再提示并且拒绝了权限，那么提示用户到设置中授权。
        if (AndPermission.hasAlwaysDeniedPermission(this, deniedPermissions)) {
            // 第一种：用默认的提示语。
            AndPermission.defaultSettingDialog(this, REQUEST_CODE_SETTING).show();
        }
    }


    private LoginReceiver loginReceiver;

    private void registLogin() {
        IntentFilter intentFilter = new IntentFilter(Constant.Receiver.ACTION_LOGIN_SUCCESS);
        loginReceiver = new LoginReceiver();
        registerReceiver(loginReceiver, intentFilter);
    }

    private void unRegistLogin() {
        if (loginReceiver != null) {
            unregisterReceiver(loginReceiver);
        }
    }

    private class LoginReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            L.e("收到登录成功的广播" + intent.getAction());
            loginCallback(context, intent);
        }
    }


    /**
     * 用于子类接收登录成功广播通知
     *
     * @param context
     * @param intent
     */
    protected void loginCallback(Context context, Intent intent) {

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mImmersionBar.destroy();
//        unRegistLogin();
    }


    protected void initImmersionBar() {
        mImmersionBar = ImmersionBar.with(this);
        mImmersionBar
                .statusBarColor(R.color.main_color)   //状态栏颜色，不写默认透明色
                .fitsSystemWindows(true)//解决状态栏和布局重叠问题，任选其一，默认为false，当为true时一定要指定statusBarColor()，不然状态栏为透明色
                .init();   //所有子类都将继承这些相同的属性
    }


    protected void setTransparent() {
        mImmersionBar
                .statusBarColor(android.R.color.transparent)     //状态栏颜色，不写默认透明色
                .fitsSystemWindows(false)//解决状态栏和布局重叠问题，任选其一，默认为false，当为true时一定要指定statusBarColor()，不然状态栏为透明色
                .init();   //所有子类都将继承这些相同的属性
    }


    //添加fragment
    protected void addFragment(BaseFragment fragment, int parentId) {
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(parentId, fragment, fragment.getClass().getSimpleName())
                    .addToBackStack(fragment.getClass().getSimpleName())
                    .commitAllowingStateLoss();
        }
    }

    //移除fragment
    protected void removeFragment() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            getSupportFragmentManager().popBackStack();
        } else {
            finish();
        }
    }

    protected boolean isShowPermission() {
        return true;
    }

//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.rl_back:
//                finish();
//                break;
//        }
//    }
}
