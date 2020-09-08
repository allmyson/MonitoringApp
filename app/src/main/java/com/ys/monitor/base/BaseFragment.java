package com.ys.monitor.base;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ys.monitor.util.L;


public abstract class BaseFragment extends Fragment {
    protected View mView;
    protected Context mContext;
    protected Activity mActivity;
    protected boolean isTB = false;//是否同步过服务器时间

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
        mActivity = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mView == null) {
            mView = inflater.inflate(getLayoutResource(), container, false);
        }
        ViewGroup parent = (ViewGroup) mView.getParent();
        if (parent != null) {
            parent.removeView(mView);
        }
        init();
        getData();
        return mView;
    }

    protected abstract void init();

    protected abstract void getData();

    protected abstract int getLayoutResource();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isTB = false;
    }

    @Override
    public void onResume() {
        super.onResume();
        isTB = false;
    }

    @Override
    public void onPause() {
        super.onPause();
    }


    protected final <E extends View> E getView(int id) {
        try {
            return (E) mView.findViewById(id);
        } catch (ClassCastException ex) {
            L.e(ex.toString());
            throw ex;
        }
    }

    protected void show(String str) {
        Toast.makeText(mContext, str, Toast.LENGTH_SHORT).show();
    }


    protected void setBtnClickable(boolean canClick, View view) {
        if (canClick) {
            view.setClickable(true);
            view.setAlpha(1f);
        } else {
            view.setClickable(false);
            view.setAlpha(0.3f);
        }
    }

    protected void setFocusChange(boolean hasFocus, View view) {
        if (hasFocus) {
            view.setBackgroundColor(Color.parseColor("#dd2230"));
        } else {
            view.setBackgroundColor(Color.parseColor("#d1d1d1"));
        }
    }
}
