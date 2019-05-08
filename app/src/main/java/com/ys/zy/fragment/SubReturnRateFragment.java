package com.ys.zy.fragment;

import android.text.Editable;
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
import com.ys.zy.util.HttpUtil;
import com.ys.zy.util.KeyBoardUtils;
import com.ys.zy.util.StringUtil;
import com.ys.zy.util.YS;

//下级返点率
public class SubReturnRateFragment extends BaseFragment implements View.OnClickListener {
    private EditText et;
    private Button btn;
    private User user;
    private String userId;

    public static SubReturnRateFragment newInstance() {
        return new SubReturnRateFragment();
    }

    @Override
    protected void init() {
        et = getView(R.id.et_);
        btn = getView(R.id.btn_);
        setBtnClickable(false, btn);
        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 0) {
                    setBtnClickable(false, btn);
                } else {
                    setBtnClickable(true, btn);
                }
            }
        });
        btn.setOnClickListener(this);
    }

    @Override
    protected void getData() {
        userId = UserSP.getUserId(mContext);
        user = UserSP.getUserInfo(mContext);
        if (user != null && user.data != null) {
            et.setHint("自身返点率" + StringUtil.valueOf(user.data.backNum) + ",可设置返点率0.0-" + StringUtil.valueOf(user.data.backNum));
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
                addYqm();
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

}
