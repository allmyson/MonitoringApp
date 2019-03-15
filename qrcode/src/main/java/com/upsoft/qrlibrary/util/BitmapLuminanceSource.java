package com.upsoft.qrlibrary.util;

import android.graphics.Bitmap;

import com.google.zxing.LuminanceSource;

/**
 * Copyright (c) 2016,重庆扬讯软件技术有限公司<br>
 * All rights reserved.<br>
 * <p/>
 * 文件名称：FragmentTask<br>
 * 摘要 自定义解析Bitmap LuminanceSource<br>
 * -------------------------------------------------------<br>
 * 当前版本：1.1.1<br>
 * 作者：董杰科<br>
 * 完成日期：2017/2/16<br>
 * -------------------------------------------------------<br>
 * 取代版本：1.1.0<br>
 * 原作者：董杰科<br>
 * 完成日期：2017/2/16<br>
 */
public class BitmapLuminanceSource extends LuminanceSource {

    private byte bitmapPixels[];

    public BitmapLuminanceSource(Bitmap bitmap) {
        super(bitmap.getWidth(), bitmap.getHeight());

        // 首先，要取得该图片的像素数组内容
        int[] data = new int[bitmap.getWidth() * bitmap.getHeight()];
        this.bitmapPixels = new byte[bitmap.getWidth() * bitmap.getHeight()];
        bitmap.getPixels(data, 0, getWidth(), 0, 0, getWidth(), getHeight());

        // 将int数组转换为byte数组，也就是取像素值中蓝色值部分作为辨析内容
        for (int i = 0; i < data.length; i++) {
            this.bitmapPixels[i] = (byte) data[i];
        }
    }

    @Override
    public byte[] getMatrix() {
        // 返回我们生成好的像素数据
        return bitmapPixels;
    }

    @Override
    public byte[] getRow(int y, byte[] row) {
        // 这里要得到指定行的像素数据
        System.arraycopy(bitmapPixels, y * getWidth(), row, 0, getWidth());
        return row;
    }
}
