package com.ys.zy.fragment;

import android.widget.TextView;

import com.google.gson.Gson;
import com.yanzhenjie.nohttp.rest.Response;
import com.ys.zy.R;
import com.ys.zy.base.BaseFragment;
import com.ys.zy.bean.BaseBean;
import com.ys.zy.http.HttpListener;
import com.ys.zy.sp.User;
import com.ys.zy.sp.UserSP;
import com.ys.zy.util.HttpUtil;
import com.ys.zy.util.StringUtil;
import com.ys.zy.util.YS;

public class TodayYKFragment extends BaseFragment {
    private String userId;
    private TextView ykTV;

    public static TodayYKFragment newInstance() {
        return new TodayYKFragment();
    }


    @Override
    protected void init() {
        ykTV = getView(R.id.tv_yk);
        userId = UserSP.getUserId(mContext);
    }

    @Override
    protected void getData() {
        HttpUtil.getTodayYK(mContext, userId, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                BaseBean baseBean = new Gson().fromJson(response.get(), BaseBean.class);
                if (baseBean != null && YS.SUCCESE.equals(baseBean.code)) {
                    ykTV.setText(StringUtil.StringToDoubleStr(StringUtil.valueOf(baseBean.data)));
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {

            }
        });
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_today_yk;
    }
}
