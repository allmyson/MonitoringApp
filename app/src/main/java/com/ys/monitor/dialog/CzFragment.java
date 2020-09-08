package com.ys.monitor.dialog;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.ys.monitor.R;
import com.ys.monitor.util.DensityUtil;
import com.ys.monitor.util.KeyBoardUtils;

public class CzFragment extends LhDialogFragment implements TextWatcher, View.OnFocusChangeListener {
    private int mTheme;
    private int mStyle;
    private View mContentView;
    private ClickListener clickListener;
    private EditText et;
    private Button sureBtn;


    public static CzFragment newInstance(int style, int theme) {
        CzFragment pFragment = new CzFragment();
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
        mContentView = inflater.inflate(R.layout.fragment_cz, null, false);
        et = (EditText) mContentView.findViewById(R.id.et_);
        sureBtn = (Button) mContentView.findViewById(R.id.btn_sure);
        et.addTextChangedListener(this);
        et.setOnFocusChangeListener(this);
        sureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KeyBoardUtils.closeKeybord(et, getContext());
                if (clickListener != null) {
                    clickListener.sure(et.getText().toString().trim());
                }
            }
        });
        setBtnClickable(false, sureBtn);
        //去掉背景
        getDialog().getWindow().setBackgroundDrawable(new
                ColorDrawable(Color.TRANSPARENT));
        return mContentView;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (s.length() == 0) {
            setBtnClickable(false, sureBtn);
        } else {
            setBtnClickable(true, sureBtn);
        }
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (v.getId() == R.id.et_) {
            setFocusChange(hasFocus, mContentView.findViewById(R.id.view_));
        }
    }

    public interface ClickListener {
        void sure(String money);
    }

    public void setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @Override
    public void onResume() {
        super.onResume();
        getDialog().getWindow().setLayout(DensityUtil.dp2px(getContext(), 240), DensityUtil.dp2px(getContext(), 200));
    }
}