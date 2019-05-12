package com.ys.zy.racing.fragment;

import com.ys.zy.base.BaseFragment;

import java.util.List;
import java.util.Map;

public abstract class RacingFragment extends BaseFragment {
    public abstract void clearData();
    public abstract String getTZResult();
    public abstract String getJsonResult();
}
