package com.ys.zy.fragment;

import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;
import com.yanzhenjie.nohttp.rest.Response;
import com.ys.zy.R;
import com.ys.zy.activity.SubOpenAccountActivity;
import com.ys.zy.base.BaseFragment;
import com.ys.zy.bean.BaseBean;
import com.ys.zy.http.HttpListener;
import com.ys.zy.sp.User;
import com.ys.zy.sp.UserSP;
import com.ys.zy.util.DecimalInputTextWatcher;
import com.ys.zy.util.HttpUtil;
import com.ys.zy.util.KeyBoardUtils;
import com.ys.zy.util.StringUtil;
import com.ys.zy.util.YS;

//下级返点率
public class SubReturnRateFragment extends BaseFragment implements View.OnClickListener, View.OnFocusChangeListener {
    private EditText et;
    private Button btn;
    private User user;
    private String userId;
    /**
     * 输入框小数的位数
     */
    private static final int DECIMAL_DIGITS = 1;

    public static SubReturnRateFragment newInstance() {
        return new SubReturnRateFragment();
    }

    @Override
    protected void init() {
        et = getView(R.id.et_);
        btn = getView(R.id.btn_);
        setBtnClickable(false, btn);
        et.addTextChangedListener(new DecimalInputTextWatcher(et, 1, 1) {
            @Override
            public void afterTextChanged(Editable editable) {
                super.afterTextChanged(editable);
                if (editable.length() == 0) {
                    setBtnClickable(false, btn);
                } else {
                    setBtnClickable(true, btn);
                }
            }
        });//限制输入位数：整数1位，小数点后1位
        et.setOnFocusChangeListener(this);
        btn.setOnClickListener(this);
    }

    @Override
    protected void getData() {
        userId = UserSP.getUserId(mContext);
        user = UserSP.getUserInfo(mContext);
        if (user != null && user.data != null) {
            et.setHint("自身返点" + StringUtil.valueOf(user.data.backNum) + ",可设置返点0.0-" + StringUtil.valueOf(user.data.backNum));
        }
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_sub_return_rate;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_:
//                show("生成邀请码");
                KeyBoardUtils.closeKeybord(et, mContext);
                double fd = StringUtil.StringToDoubleOne(et.getText().toString().trim());
                if (fd >= 0 && fd <= StringUtil.StringToDoubleOne(user.data.backNum)) {
                    addYqm();
                } else {
                    show("自身返点率" + StringUtil.valueOf(user.data.backNum) + ",可设置返点率0.0-" + StringUtil.valueOf(user.data.backNum));
                }
                break;
        }
    }

    private void addYqm() {
        HttpUtil.createYqm(mContext, userId, StringUtil.StringToDoubleStr(et.getText().toString().trim()), new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                BaseBean baseBean = new Gson().fromJson(response.get(), BaseBean.class);
                if (baseBean != null) {
                    if (YS.SUCCESE.equals(baseBean.code)) {
                        //通知邀请码列表刷新
                        ((SubOpenAccountActivity) getActivity()).refreshInvitationCodeFragment();
                    }
                    show(baseBean.msg);
                } else {
                    show(YS.HTTP_TIP);
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {

            }
        });
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        switch (v.getId()){
            case R.id.et_:
                setFocusChange(hasFocus,getView(R.id.view_fd));
                break;
        }
    }
}
