package com.ys.monitor.bean;

import android.support.annotation.NonNull;

import com.ys.monitor.util.StringUtil;

public class KeyValue implements Comparable<KeyValue> {
    public String fd;//返点
    public String odd;//赔率

    public KeyValue() {
    }

    public KeyValue(String fd, String odd) {
        this.fd = fd;
        this.odd = odd;
    }

    @Override
    public int compareTo(@NonNull KeyValue o) {
        //自定义比较方法，如果认为此实体本身大则返回1，否则返回-1
        if (StringUtil.StringToDouble(o.fd) > StringUtil.StringToDouble(this.fd)) {
            return 1;
        }
        return -1;
    }
}
