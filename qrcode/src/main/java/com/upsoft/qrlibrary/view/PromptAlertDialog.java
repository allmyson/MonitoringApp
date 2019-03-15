package com.upsoft.qrlibrary.view;

import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.upsoft.qrlibrary.R;


/**
 * @author lh
 * @version 1.0.0
 * @filename PromptAlertDialog
 * @description -------------------------------------------------------
 * @date 2016/12/28 16:13
 */
public class PromptAlertDialog {

    private android.app.AlertDialog ad;
    private TextView titleView;
    private TextView messageView;
    private Window window;

    public PromptAlertDialog(Context context) {
        ad = new android.app.AlertDialog.Builder(context).create();
        ad.setCancelable(false);
        ad.show();
        // 关键在下面的两行,使用window.setContentView,替换整个对话框窗口的布局
        window = ad.getWindow();
        window.setContentView(R.layout.dialog_layout);
        titleView = (TextView) window.findViewById(R.id.dialog_title);
        messageView = (TextView) window.findViewById(R.id.dialog_content);
    }

    public void setTitle(int resId) {
        titleView.setText(resId);
    }

    public void setTitle(String title) {
        titleView.setText(title);
    }

    public TextView getTitleView() {
        return titleView;
    }

    public void setMessage(int resId) {
        messageView.setText(resId);
    }

    public void setMessage(String message) {
        messageView.setText(message);
    }

    /**
     * 设置按钮
     *
     * @param text
     * @param listener
     */
    public void setNegativeButton(int id, String text,
                                  final View.OnClickListener listener, int visibility) {
        TextView textView = (TextView) window.findViewById(id);
        textView.setText(text);
        textView.setOnClickListener(listener);
        textView.setVisibility(visibility);
    }

    /**
     * 关闭对话框
     */
    public void dismiss() {
        ad.dismiss();
    }

}
