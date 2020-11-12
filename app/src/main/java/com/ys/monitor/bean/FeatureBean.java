package com.ys.monitor.bean;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.view.ViewGroup;

import com.esri.arcgisruntime.data.ServiceFeatureTable;
import com.esri.arcgisruntime.layers.FeatureLayer;
import com.esri.arcgisruntime.symbology.PictureMarkerSymbol;
import com.esri.arcgisruntime.symbology.SimpleMarkerSymbol;
import com.esri.arcgisruntime.symbology.UniqueValueRenderer;
import com.ys.monitor.R;
import com.ys.monitor.base.App;
import com.ys.monitor.util.DensityUtil;
import com.ys.monitor.util.StringUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * @author lh
 * @version 1.0.0
 * @filename FeatureBean
 * @description -------------------------------------------------------
 * @date 2020/10/23 10:07
 */
public class FeatureBean {
    public static final String FEATURE_SERVER = "http://222.178.189.231:9080/arcgis/rest/services" +
            "/JysBaseData/FeatureServer";
    public int drawableId;
    public String layerName;
    public String url;
    public FeatureLayer featureLayer;
    private static Map<String, Integer> drawableMap = new HashMap<>();

    static {
        drawableMap.put("主要保护对象", R.mipmap.point_zybhdx);
        drawableMap.put("保护站", R.mipmap.point_bhz);
        drawableMap.put("古树名木", R.mipmap.maker_gsmm);
        drawableMap.put("备用水池", R.mipmap.point_bysc);
        drawableMap.put("居民安置点", R.mipmap.point_jmazd);
        drawableMap.put("应急队伍", R.mipmap.point_yjdw);
        drawableMap.put("景点", R.mipmap.point_jd);
        drawableMap.put("消防水池", R.mipmap.point_xfsc);
        drawableMap.put("珍稀植物", R.mipmap.point_zxzw);
        drawableMap.put("瞭望塔", R.mipmap.point_lwt);
        drawableMap.put("重点保护野生动物", R.mipmap.point_zdbhysdw);
        drawableMap.put("防火检查站点", R.mipmap.point_fwjczd);
    }

    public FeatureBean() {
    }

    public FeatureBean(int drawableId, String layerName, String url) {
        this.drawableId = drawableId;
        this.layerName = layerName;
        this.url = url;
    }

    public FeatureBean(int drawableId, String layerName, String url, FeatureLayer featureLayer) {
        this.drawableId = drawableId;
        this.layerName = layerName;
        this.url = url;
        this.featureLayer = featureLayer;
    }

    public static List<FeatureBean> getLayerList() {
        List<FeatureBean> list = new ArrayList<>();
        list.add(new FeatureBean(R.mipmap.layer_zyd, "资源点数据", FEATURE_SERVER + "/0",
                getPointFeatureLayer(FEATURE_SERVER + "/0")));
        list.add(new FeatureBean(R.mipmap.layer_cxd, "车行道", FEATURE_SERVER + "/1",
                getFeatureLayer(FEATURE_SERVER + "/1")));
        list.add(new FeatureBean(R.mipmap.layer_bxd, "步行道", FEATURE_SERVER + "/2",
                getFeatureLayer(FEATURE_SERVER + "/2")));
        list.add(new FeatureBean(R.mipmap.layer_sthd, "生态环道", FEATURE_SERVER + "/3",
                getFeatureLayer(FEATURE_SERVER + "/3")));
        list.add(new FeatureBean(R.mipmap.layer_bhq, "保护区边界", FEATURE_SERVER + "/4",
                getFeatureLayer(FEATURE_SERVER + "/4")));
        list.add(new FeatureBean(R.mipmap.layer_gnqh, "功能区划", FEATURE_SERVER + "/5",
                getFeatureLayer(FEATURE_SERVER + "/5")));
        list.add(new FeatureBean(R.mipmap.layer_water, "水系", FEATURE_SERVER + "/6",
                getFeatureLayer(FEATURE_SERVER + "/6")));
        list.add(new FeatureBean(R.mipmap.layer_xb, "小班", FEATURE_SERVER + "/7",
                getFeatureLayer(FEATURE_SERVER + "/7")));
        list.add(new FeatureBean(R.mipmap.layer_xzqh, "行政区划", FEATURE_SERVER + "/8",
                getFeatureLayer(FEATURE_SERVER + "/8")));
        return list;
    }

    public static FeatureLayer getFeatureLayer(String url) {
        ServiceFeatureTable serviceFeatureTable = new ServiceFeatureTable(url);
        FeatureLayer featureLayer = new FeatureLayer(serviceFeatureTable);
        return featureLayer;
    }

    public static FeatureLayer getPointFeatureLayer(String url) {
        ServiceFeatureTable serviceFeatureTable = new ServiceFeatureTable(url);
        FeatureLayer featureLayer = new FeatureLayer(serviceFeatureTable);
        featureLayer.setRenderer(getUniqueValueRenderer());
        return featureLayer;
    }

    public static UniqueValueRenderer getUniqueValueRenderer() {
        Context context = App.getInstance().getApplicationContext();
        PictureMarkerSymbol pictureMarkerSymbol1 = null;
        PictureMarkerSymbol pictureMarkerSymbol2 = null;
        PictureMarkerSymbol pictureMarkerSymbol3 = null;
        PictureMarkerSymbol pictureMarkerSymbol4 = null;
        PictureMarkerSymbol pictureMarkerSymbol5 = null;
        PictureMarkerSymbol pictureMarkerSymbol6 = null;
        PictureMarkerSymbol pictureMarkerSymbol7 = null;
        PictureMarkerSymbol pictureMarkerSymbol8 = null;
        PictureMarkerSymbol pictureMarkerSymbol9 = null;
        PictureMarkerSymbol pictureMarkerSymbol10 = null;
        PictureMarkerSymbol pictureMarkerSymbol11 = null;
        PictureMarkerSymbol pictureMarkerSymbol12 = null;
        PictureMarkerSymbol pictureMarkerSymbol13 = null;
        try {
            pictureMarkerSymbol1 =
                    PictureMarkerSymbol.createAsync((BitmapDrawable) context.getResources().getDrawable(R.mipmap.point_zybhdx)).get();
            pictureMarkerSymbol2 =
                    PictureMarkerSymbol.createAsync((BitmapDrawable) context.getResources().getDrawable(R.mipmap.point_bhz)).get();
            pictureMarkerSymbol3 =
                    PictureMarkerSymbol.createAsync((BitmapDrawable) context.getResources().getDrawable(R.mipmap.maker_gsmm)).get();
            pictureMarkerSymbol4 =
                    PictureMarkerSymbol.createAsync((BitmapDrawable) context.getResources().getDrawable(R.mipmap.point_bysc)).get();
            pictureMarkerSymbol5 =
                    PictureMarkerSymbol.createAsync((BitmapDrawable) context.getResources().getDrawable(R.mipmap.point_jmazd)).get();
            pictureMarkerSymbol6 =
                    PictureMarkerSymbol.createAsync((BitmapDrawable) context.getResources().getDrawable(R.mipmap.point_yjdw)).get();
            pictureMarkerSymbol7 =
                    PictureMarkerSymbol.createAsync((BitmapDrawable) context.getResources().getDrawable(R.mipmap.point_jd)).get();
            pictureMarkerSymbol8 =
                    PictureMarkerSymbol.createAsync((BitmapDrawable) context.getResources().getDrawable(R.mipmap.point_xfsc)).get();
            pictureMarkerSymbol9 =
                    PictureMarkerSymbol.createAsync((BitmapDrawable) context.getResources().getDrawable(R.mipmap.point_mhwzk)).get();
            pictureMarkerSymbol10 =
                    PictureMarkerSymbol.createAsync((BitmapDrawable) context.getResources().getDrawable(R.mipmap.point_zxzw)).get();
            pictureMarkerSymbol11 =
                    PictureMarkerSymbol.createAsync((BitmapDrawable) context.getResources().getDrawable(R.mipmap.point_lwt)).get();
            pictureMarkerSymbol12 =
                    PictureMarkerSymbol.createAsync((BitmapDrawable) context.getResources().getDrawable(R.mipmap.point_zdbhysdw)).get();
            pictureMarkerSymbol13 =
                    PictureMarkerSymbol.createAsync((BitmapDrawable) context.getResources().getDrawable(R.mipmap.point_fwjczd)).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        List<Object> value1 = new ArrayList<>();
        value1.add("主要保护对象");
        List<Object> value2 = new ArrayList<>();
        value2.add("保护站");
        List<Object> value3 = new ArrayList<>();
        value3.add("古树名木");
        List<Object> value4 = new ArrayList<>();
        value4.add("备用水池");
        List<Object> value5 = new ArrayList<>();
        value5.add("居民安置点");
        List<Object> value6 = new ArrayList<>();
        value6.add("应急队伍");
        List<Object> value7 = new ArrayList<>();
        value7.add("景点");
        List<Object> value8 = new ArrayList<>();
        value8.add("消防水池");
        List<Object> value9 = new ArrayList<>();
        value9.add("灭火物资库");
        List<Object> value10 = new ArrayList<>();
        value10.add("珍稀植物");
        List<Object> value11 = new ArrayList<>();
        value11.add("瞭望塔");
        List<Object> value12 = new ArrayList<>();
        value12.add("重点保护野生动物");
        List<Object> value13 = new ArrayList<>();
        value13.add("防火检查站点");


        UniqueValueRenderer uniqueValueRenderer = new UniqueValueRenderer();
        // 设置用于唯一值渲染的字段
        uniqueValueRenderer.getFieldNames().add("资源类型"); //你可以以lis
        SimpleMarkerSymbol defaultFillSymbol =
                new SimpleMarkerSymbol(SimpleMarkerSymbol.Style.CIRCLE, Color.BLUE, 10);
        // 设置默认Symbol（符号）
        uniqueValueRenderer.setDefaultSymbol(defaultFillSymbol);
        uniqueValueRenderer.setDefaultLabel("default");


        uniqueValueRenderer.getUniqueValues().add(new UniqueValueRenderer.UniqueValue(StringUtil.valueOf(value1.get(0)),
                StringUtil.valueOf(value1.get(0)), pictureMarkerSymbol1, value1));
        uniqueValueRenderer.getUniqueValues().add(new UniqueValueRenderer.UniqueValue(StringUtil.valueOf(value2.get(0)),
                StringUtil.valueOf(value2.get(0)), pictureMarkerSymbol2, value2));
        uniqueValueRenderer.getUniqueValues().add(new UniqueValueRenderer.UniqueValue(StringUtil.valueOf(value3.get(0)),
                StringUtil.valueOf(value3.get(0)), pictureMarkerSymbol3, value3));
        uniqueValueRenderer.getUniqueValues().add(new UniqueValueRenderer.UniqueValue(StringUtil.valueOf(value4.get(0)),
                StringUtil.valueOf(value4.get(0)), pictureMarkerSymbol4, value4));
        uniqueValueRenderer.getUniqueValues().add(new UniqueValueRenderer.UniqueValue(StringUtil.valueOf(value5.get(0)),
                StringUtil.valueOf(value5.get(0)), pictureMarkerSymbol5, value5));
        uniqueValueRenderer.getUniqueValues().add(new UniqueValueRenderer.UniqueValue(StringUtil.valueOf(value6.get(0)),
                StringUtil.valueOf(value6.get(0)), pictureMarkerSymbol6, value6));
        uniqueValueRenderer.getUniqueValues().add(new UniqueValueRenderer.UniqueValue(StringUtil.valueOf(value7.get(0)),
                StringUtil.valueOf(value7.get(0)), pictureMarkerSymbol7, value7));
        uniqueValueRenderer.getUniqueValues().add(new UniqueValueRenderer.UniqueValue(StringUtil.valueOf(value8.get(0)),
                StringUtil.valueOf(value8.get(0)), pictureMarkerSymbol8, value8));
        uniqueValueRenderer.getUniqueValues().add(new UniqueValueRenderer.UniqueValue(StringUtil.valueOf(value9.get(0)),
                StringUtil.valueOf(value9.get(0)), pictureMarkerSymbol9, value9));
        uniqueValueRenderer.getUniqueValues().add(new UniqueValueRenderer.UniqueValue(StringUtil.valueOf(value10.get(0)),
                StringUtil.valueOf(value10.get(0)), pictureMarkerSymbol10, value10));
        uniqueValueRenderer.getUniqueValues().add(new UniqueValueRenderer.UniqueValue(StringUtil.valueOf(value11.get(0)),
                StringUtil.valueOf(value11.get(0)), pictureMarkerSymbol11, value11));
        uniqueValueRenderer.getUniqueValues().add(new UniqueValueRenderer.UniqueValue(StringUtil.valueOf(value12.get(0)),
                StringUtil.valueOf(value12.get(0)), pictureMarkerSymbol12, value12));
        uniqueValueRenderer.getUniqueValues().add(new UniqueValueRenderer.UniqueValue(StringUtil.valueOf(value13.get(0)),
                StringUtil.valueOf(value13.get(0)), pictureMarkerSymbol13, value13));
        return uniqueValueRenderer;
    }


    public static PictureMarkerSymbol getPictureMarkerSymbol(Context context, String type) {
        int drawId = getDrawableId(type);
        PictureMarkerSymbol pictureMarkerSymbol = null;
        try {
            pictureMarkerSymbol =
                    PictureMarkerSymbol.createAsync((BitmapDrawable) context.getResources().getDrawable(drawId)).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return pictureMarkerSymbol;
    }
    public static PictureMarkerSymbol getPictureMarkerSymbol2(Context context, String type) {
        int drawId = getDrawableId(type);
        BitmapDrawable bitmapDrawable = getBitmapDrawable(context,drawId);
        PictureMarkerSymbol pictureMarkerSymbol = null;
        try {
            pictureMarkerSymbol =
                    PictureMarkerSymbol.createAsync(bitmapDrawable).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return pictureMarkerSymbol;
    }

    public static PictureMarkerSymbol getPictureMarkerSymbol(Context context, int drawId) {
        PictureMarkerSymbol pictureMarkerSymbol = null;
        try {
            pictureMarkerSymbol =
                    PictureMarkerSymbol.createAsync((BitmapDrawable) context.getResources().getDrawable(drawId)).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return pictureMarkerSymbol;
    }

    public static int getDrawableId(String type) {
        if (drawableMap.containsKey(type)) {
            return drawableMap.get(type);
        }
        return R.mipmap.point_default;
    }

    public static BitmapDrawable getBitmapDrawable(Context context, int drawableId) {
        View view1 = ViewGroup.inflate(context, R.layout.map_search, null);
        Bitmap bitmap = createViewBitmap(view1);
        BitmapDrawable drawable = new BitmapDrawable(context.getResources(), bitmap);
        return drawable;
    }

    public static Bitmap createViewBitmap(View v) {
        Bitmap bitmap = Bitmap.createBitmap(DensityUtil.dp2px(v.getContext(),80),DensityUtil.dp2px(v.getContext(),80),
                Bitmap.Config.ARGB_8888); //创建一个和View大小一样的Bitmap
        Canvas canvas = new Canvas(bitmap);  //使用上面的Bitmap创建canvas
        v.draw(canvas);  //把View画到Bitmap上
        return bitmap;
    }
}
