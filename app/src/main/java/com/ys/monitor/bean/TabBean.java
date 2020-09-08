package com.ys.monitor.bean;

import android.support.v4.app.Fragment;

public class TabBean {
    public String title;
    public Fragment fragment;

    public TabBean() {
    }

    public TabBean(String title, Fragment fragment) {
        this.title = title;
        this.fragment = fragment;
    }
}
