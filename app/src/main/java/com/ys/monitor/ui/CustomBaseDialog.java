package com.ys.monitor.ui;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.ys.monitor.R;


public class CustomBaseDialog extends BaseDialog<CustomBaseDialog> {
    private String nowMenu = "assets/cc.zip";
    TextView mTvCancel;
    TextView mTvExit;

    public CustomBaseDialog(Context context) {
        super(context);
    }

    @Override
    public View onCreateView() {
        widthScale(0.85f);
        showAnim(new Swing());

        dismissAnim(new ZoomOutExit());
        View inflate = View.inflate(mContext, R.layout.dialog_custom_base, null);
        mTvCancel = inflate.findViewById(R.id.tv_cancel);
        mTvExit = inflate.findViewById(R.id.tv_exit);
        inflate.setBackgroundDrawable(
                CornerUtils.cornerDrawable(Color.parseColor("#ffffff"), dp2px(5)));

        return inflate;
    }

    @Override
    public void setUiBeforShow() {
        mTvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if (clickListener != null) {
                    clickListener.click(mTvCancel.getText().toString());
                }
            }
        });
        mTvExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if (clickListener != null) {
                    clickListener.click(mTvExit.getText().toString());
                }
            }
        });
    }

    public interface ClickListener {
        void click(String name);
    }

    private ClickListener clickListener;

    public ClickListener getClickListener() {

        return clickListener;
    }

    public void setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

}
