package com.ys.monitor.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lh
 * @version 1.0.0
 * @filename LayerBean
 * @description -------------------------------------------------------
 * @date 2020/9/30 14:57
 */
public class LayerBean {
    public boolean isSelect;
    public static List<LayerBean> getDefaultLayers() {
        List<LayerBean> list = new ArrayList<>();
        list.add(null);
        list.add(null);
        list.add(null);
        list.add(null);
        list.add(null);
        list.add(null);
        list.add(null);
        list.add(null);
        list.add(null);
        list.add(null);
        list.add(null);
        list.add(null);
        list.add(null);
        return list;
    }
}
