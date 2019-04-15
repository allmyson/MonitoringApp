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
import android.widget.GridView;
import android.widget.TextView;

import com.ys.zy.R;
import com.ys.zy.adapter.CommonAdapter;
import com.ys.zy.adapter.ViewHolder;
import com.ys.zy.util.DensityUtil;
import com.ys.zy.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

public class PlayFragment extends LhDialogFragment {
    private int mTheme;
    private int mStyle;
    private View mContentView;
    private GridView gv;
    private List<String> list;
    private PlayAdapter playAdapter;
    private ClickListener clickListener;

    public static PlayFragment newInstance(int style, int theme) {
        PlayFragment pFragment = new PlayFragment();
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
        mContentView = inflater.inflate(R.layout.fragment_play, null, false);
        gv = (GridView) mContentView.findViewById(R.id.gv_);
        if (list == null) {
            list = new ArrayList<>();
        }
        playAdapter = new PlayAdapter(getContext(), list, R.layout.item_play);
        gv.setAdapter(playAdapter);
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (clickListener != null) {
                    clickListener.click(playAdapter.getItem(position));
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
            attributes.gravity = Gravity.TOP | Gravity.LEFT;//对齐方式
//            attributes.width = DensityUtil.dp2px(getContext(),100);
//            attributes.x = DensityUtil.dp2px(getContext(), 0);
            attributes.width = WindowManager.LayoutParams.MATCH_PARENT;
            attributes.horizontalMargin = 0;
            attributes.y = DensityUtil.dp2px(getContext(), 45);//具体头部距离
            dialog.getWindow().setAttributes(attributes);
            dialog.getWindow().getDecorView().setPadding(0, 0, 0, 0);
//            dialog.getWindow().setBackgroundDrawable( new ColorDrawable( 0xff000000 ) );
            dialog.getWindow().getDecorView().setMinimumWidth(getResources().getDisplayMetrics().widthPixels);
            dialog.getWindow().setLayout(dm.widthPixels, getDialog().getWindow().getAttributes().height);
        }
        return mContentView;
    }


    public class PlayAdapter extends CommonAdapter<String> {

        public PlayAdapter(Context context, List<String> mDatas, int itemLayoutId) {
            super(context, mDatas, itemLayoutId);
        }

        @Override
        public void convert(ViewHolder helper, String item, int position) {
            helper.setText(R.id.tv_, StringUtil.valueOf(item));
        }
    }

    public void setList(List<String> list) {
        this.list = list;
    }

    public void setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public interface ClickListener {
        void click(String name);
    }
}
