package com.ys.zy.ui;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.ys.zy.R;

/**
 * @author lh
 * @version 1.0.0
 * @filename NoNetView
 * @description -------------------------------------------------------
 * @date 2019/3/18 14:02
 */
public class NoNetView extends RelativeLayout {
    private Context context;
    private View view;
    private Button reloadBtn;
    private ClickListener clickListener;

    public NoNetView(Context context) {
        this(context, null);
    }

    public NoNetView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NoNetView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.context = context;
        view = LayoutInflater.from(context).inflate(R.layout.layout_nonet, this);
        reloadBtn = (Button) view.findViewById(R.id.btn_reload);
        reloadBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickListener != null) {
                    clickListener.reload();
                }
            }
        });
    }

    public interface ClickListener {
        void reload();
    }

    public ClickListener getClickListener() {
        return clickListener;
    }

    public void setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }
}
