package com.ys.zy.ttz.bean;

import com.ys.zy.R;

public class PaiBean {
    public String name;//庄、闲1、闲2、闲3
    public int firstValue;//1-9
    public int secondValue;//1-9

    public PaiBean() {
    }

    public PaiBean(String name, int firstValue, int secondValue) {
        this.name = name;
        this.firstValue = firstValue;
        this.secondValue = secondValue;
    }

    public static int getDrawableId(int value) {
        int drawableId = R.mipmap.second_img_otherside;
        switch (value) {
            case 0:
                drawableId = R.mipmap.second_img_otherside;
                break;
            case 1:
                drawableId = R.mipmap.second_img_t1;
                break;
            case 2:
                drawableId = R.mipmap.second_img_t2;
                break;
            case 3:
                drawableId = R.mipmap.second_img_t3;
                break;
            case 4:
                drawableId = R.mipmap.second_img_t4;
                break;
            case 5:
                drawableId = R.mipmap.second_img_t5;
                break;
            case 6:
                drawableId = R.mipmap.second_img_t6;
                break;
            case 7:
                drawableId = R.mipmap.second_img_t7;
                break;
            case 8:
                drawableId = R.mipmap.second_img_t8;
                break;
            case 9:
                drawableId = R.mipmap.second_img_t9;
                break;
        }
        return drawableId;
    }

}
