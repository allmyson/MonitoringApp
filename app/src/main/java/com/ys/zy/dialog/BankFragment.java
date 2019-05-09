package com.ys.zy.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yanzhenjie.nohttp.rest.Response;
import com.ys.zy.R;
import com.ys.zy.adapter.CommonAdapter;
import com.ys.zy.adapter.ViewHolder;
import com.ys.zy.bean.BankBean;
import com.ys.zy.bean.BankData;
import com.ys.zy.http.HttpListener;
import com.ys.zy.sp.UserSP;
import com.ys.zy.util.DensityUtil;
import com.ys.zy.util.HttpUtil;
import com.ys.zy.util.ToastUtil;
import com.ys.zy.util.YS;

import java.util.ArrayList;
import java.util.List;

public class BankFragment extends LhDialogFragment {
    private int mTheme;
    private int mStyle;
    private View mContentView;
    private ListView lv;
    private List<BankData.DataBean> list;
    private BankAdapter bankAdapter;
    private String userId;
    public static BankFragment newInstance(int style, int theme) {
        BankFragment pFragment = new BankFragment();
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
        mContentView = inflater.inflate(R.layout.fragment_bank, null, false);
        lv = (ListView) mContentView.findViewById(R.id.lv_);
        list = new ArrayList<>();
        bankAdapter = new BankAdapter(mContentView.getContext(), list, R.layout.item_bank_dialog);
        lv.setAdapter(bankAdapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                bankAdapter.setSelectItem(position);
                if (clickListener != null) {
                    clickListener.click(bankAdapter.getItem(position));
                }
            }
        });
        //去掉背景
        //去掉背景
        getDialog().getWindow().setBackgroundDrawable(new
                ColorDrawable(Color.TRANSPARENT));
        Dialog dialog = getDialog();
        if (dialog != null) {
            DisplayMetrics dm = new DisplayMetrics();
            getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
            WindowManager.LayoutParams attributes = dialog.getWindow().getAttributes();
            attributes.gravity = Gravity.BOTTOM | Gravity.LEFT;//对齐方式
//            attributes.width = DensityUtil.dp2px(getContext(),100);
//            attributes.x = DensityUtil.dp2px(getContext(), 0);
            attributes.width = WindowManager.LayoutParams.MATCH_PARENT;
            attributes.horizontalMargin = 0;
//            attributes.y = DensityUtil.dp2px(getContext(), 40);//具体头部距离
            dialog.getWindow().setAttributes(attributes);
            dialog.getWindow().getDecorView().setPadding(0, 0, 0, 0);
//            dialog.getWindow().setBackgroundDrawable( new ColorDrawable( 0xff000000 ) );
            dialog.getWindow().getDecorView().setMinimumWidth(getResources().getDisplayMetrics().widthPixels);
            dialog.getWindow().setLayout(dm.widthPixels, getDialog().getWindow().getAttributes().height);
        }
        userId = UserSP.getUserId(getContext());
        return mContentView;
    }


    @Override
    public void onResume() {
        super.onResume();
        HttpUtil.getBankList(getContext(), userId, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                list.clear();
                BankData bankData = new Gson().fromJson(response.get(), BankData.class);
                if (bankData != null) {
                    if (YS.SUCCESE.equals(bankData.code) && bankData.data != null && bankData.data.size() > 0) {
                        list.addAll(bankData.data);
                    }
                } else {
                    ToastUtil.show(getContext(),YS.HTTP_TIP);
                }
                bankAdapter.refresh(list);
            }

            @Override
            public void onFailed(int what, Response<String> response) {

            }
        });
    }


    public class BankAdapter extends CommonAdapter<BankData.DataBean> {
        private int selectItem = -1;

        public BankAdapter(Context context, List<BankData.DataBean> mDatas, int itemLayoutId) {
            super(context, mDatas, itemLayoutId);
        }

        @Override
        public void convert(ViewHolder helper, BankData.DataBean item, int position) {
            helper.setText(R.id.tv_, item.bankName + "（" + item.cardNumber.substring(item.cardNumber.length() - 4, item.cardNumber.length()) + "）");
            if (selectItem == position) {
                helper.setImageResource(R.id.iv_, R.mipmap.checkbox_icon_select);
            } else {
                helper.setImageResource(R.id.iv_, R.mipmap.checkbox_icon_normal);
            }
        }

        public void setSelectItem(int position) {
            this.selectItem = position;
            notifyDataSetInvalidated();
        }
    }

    private ClickListener clickListener;

    public interface ClickListener {
        void click(BankData.DataBean bankBean);
    }

    public void setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }
}
