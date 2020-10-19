package com.ys.monitor.dialog;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.ys.monitor.R;
import com.ys.monitor.util.StringUtil;


/**
 * @author lh
 * @version 1.0.0
 * @filename SureFragment
 * @description -------------------------------------------------------
 * @date 2017/12/13 16:44
 */
public class PointTipFragment extends DialogFragment {
    private int mTheme;
    private int mStyle;
    private ListView lv;
    private View mContentView;
    private ClickListener clickListener;
    private TextView areaTV, locationTV, sureTV;
    private String areaText;
    private String locationText;


    public static PointTipFragment newInstance(int style, int theme) {
        PointTipFragment pFragment = new PointTipFragment();
        Bundle args = new Bundle();
        args.putInt("style", style);
        args.putInt("theme", theme);
        pFragment.setArguments(args);
        return pFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setCancelable(false);// 设置点击屏幕Dialog不消失
        mStyle = getArguments().getInt("style");
        mTheme = getArguments().getInt("theme");
        setStyle(mStyle, mTheme);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mContentView = inflater.inflate(R.layout.fragment_point_tip, null, false);
        areaTV = (TextView) mContentView.findViewById(R.id.tv_area);
        locationTV = (TextView) mContentView.findViewById(R.id.tv_location);
        areaTV.setText(StringUtil.valueOf(areaText));
        locationTV.setText(StringUtil.valueOf(locationText));
        sureTV = (TextView) mContentView.findViewById(R.id.tv_sure);
        sureTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogUtil.removeDialog(getActivity());
            }
        });
//        sureTV.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (clickListener != null) {
//                    clickListener.sure();
//                }
//            }
//        });
        //去掉背景
        getDialog().getWindow().setBackgroundDrawable(new
                ColorDrawable(Color.TRANSPARENT));
        return mContentView;
    }

    public interface ClickListener {
        void sure();
    }

    public void setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public String getAreaText() {
        return areaText;
    }

    public void setAreaText(String areaText) {
        this.areaText = areaText;
    }

    public String getLocationText() {
        return locationText;
    }

    public void setLocationText(String locationText) {
        this.locationText = locationText;
    }
}
