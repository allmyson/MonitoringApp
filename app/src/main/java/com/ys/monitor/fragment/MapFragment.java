package com.ys.monitor.fragment;

import android.Manifest;
import android.animation.Animator;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.esri.arcgisruntime.concurrent.ListenableFuture;
import com.esri.arcgisruntime.data.Feature;
import com.esri.arcgisruntime.data.FeatureCollection;
import com.esri.arcgisruntime.data.FeatureQueryResult;
import com.esri.arcgisruntime.data.Field;
import com.esri.arcgisruntime.data.QueryParameters;
import com.esri.arcgisruntime.data.ServiceFeatureTable;
import com.esri.arcgisruntime.geometry.Envelope;
import com.esri.arcgisruntime.geometry.Geometry;
import com.esri.arcgisruntime.geometry.Point;
import com.esri.arcgisruntime.geometry.PointCollection;
import com.esri.arcgisruntime.geometry.Polygon;
import com.esri.arcgisruntime.geometry.Polyline;
import com.esri.arcgisruntime.geometry.SpatialReference;
import com.esri.arcgisruntime.geometry.SpatialReferences;
import com.esri.arcgisruntime.layers.ArcGISTiledLayer;
import com.esri.arcgisruntime.layers.FeatureCollectionLayer;
import com.esri.arcgisruntime.layers.FeatureLayer;
import com.esri.arcgisruntime.mapping.ArcGISMap;
import com.esri.arcgisruntime.mapping.Basemap;
import com.esri.arcgisruntime.mapping.GeoElement;
import com.esri.arcgisruntime.mapping.view.Graphic;
import com.esri.arcgisruntime.mapping.view.GraphicsOverlay;
import com.esri.arcgisruntime.mapping.view.IdentifyLayerResult;
import com.esri.arcgisruntime.mapping.view.LocationDisplay;
import com.esri.arcgisruntime.mapping.view.MapView;
import com.esri.arcgisruntime.symbology.PictureMarkerSymbol;
import com.esri.arcgisruntime.symbology.SimpleFillSymbol;
import com.esri.arcgisruntime.symbology.SimpleLineSymbol;
import com.esri.arcgisruntime.symbology.SimpleMarkerSymbol;
import com.esri.arcgisruntime.symbology.SimpleRenderer;
import com.google.gson.Gson;
import com.huamai.poc.IPocEngineEventHandler;
import com.huamai.poc.PocEngine;
import com.huamai.poc.PocEngineFactory;
import com.yanzhenjie.nohttp.rest.Response;
import com.ys.monitor.R;
import com.ys.monitor.activity.ResoureActivity;
import com.ys.monitor.adapter.CommonAdapter;
import com.ys.monitor.adapter.DataAdapter;
import com.ys.monitor.adapter.LayerAdapter;
import com.ys.monitor.adapter.ViewHolder;
import com.ys.monitor.base.BaseFragment;
import com.ys.monitor.bean.FeatureBean;
import com.ys.monitor.bean.FireBean;
import com.ys.monitor.bean.GjBean;
import com.ys.monitor.dialog.DialogUtil;
import com.ys.monitor.http.HttpListener;
import com.ys.monitor.sp.LocationSP;
import com.ys.monitor.sp.UserSP;
import com.ys.monitor.ui.CustomBaseDialog;
import com.ys.monitor.ui.ParticleView;
import com.ys.monitor.util.AnimationUtil;
import com.ys.monitor.util.GPSUtil;
import com.ys.monitor.util.HttpUtil;
import com.ys.monitor.util.L;
import com.ys.monitor.util.NavigationUtil;
import com.ys.monitor.util.StringUtil;
import com.ys.monitor.util.TimeUtil;
import com.ys.monitor.util.YS;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import cn.sddman.arcgistool.common.Variable;
import cn.sddman.arcgistool.entity.DrawEntity;
import cn.sddman.arcgistool.listener.MapViewOnTouchListener;
import cn.sddman.arcgistool.listener.MeasureClickListener;
import cn.sddman.arcgistool.listener.ZoomClickListener;
import cn.sddman.arcgistool.manager.ArcgisToolManager;
import cn.sddman.arcgistool.view.ArcGisZoomView;
import cn.sddman.arcgistool.view.MeasureToolView;

/**
 * @author lh
 * @version 1.0.0
 * @filename MapFragment
 * @description -------------------------------------------------------
 * @date 2020/9/24 10:44
 */
public class MapFragment extends BaseFragment implements View.OnClickListener,
        OnGetGeoCoderResultListener {
    private MapView mMapView;
    private ArcGisZoomView zoomBtn;
    private ArcgisToolManager arcgisToolManager;
    private MeasureToolView measureToolView;
    private GridView layerGV;
    private LayerAdapter layerAdapter;
    private List<FeatureBean> layerBeanList;
    private ImageView layerIV, gjIV, playGj, fireIV;

    private FeatureLayer featureLayer_gsmm;
    private String userId;

    public static MapFragment newInstance() {
        return new MapFragment();
    }

    private boolean isShowLayer;
    private boolean isShowGj;
    private boolean isShowFire;

    private ImageView locationIV;

    @Override
    protected void init() {
        initFirePointLayout();
        initTool();
        initBase();
        initPointDetailLayout();
        //定位后就不在北碚了
        markLocation();
        initLayer();
        createGraphicsOverlay();
    }

    @Override
    protected void getData() {
        userId = UserSP.getUserId(mContext);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getFire();
            }
        }, 2000);
//        addLayer();
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_map;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_layer:
                isShowLayer = !isShowLayer;
                if (isShowLayer) {
                    layerGV.setVisibility(View.VISIBLE);
                } else {
                    layerGV.setVisibility(View.INVISIBLE);
                }
                break;
            case R.id.iv_gj:
                isShowGj = !isShowGj;
                if (isShowGj) {
                    gjIV.setImageResource(R.mipmap.ic_gj_check);
                    getGj();
//                    createPolylineGraphics();
                } else {
                    gjIV.setImageResource(R.mipmap.ic_gj_uncheck);
                    clearGjGraphics();
                }
                break;
            case R.id.iv_playGj:
                if (isCanPlay) {
                    playGj.setImageResource(R.mipmap.ic_play_gj_check);
                    playGj();
                } else {
                    show("正在播放轨迹");
                }
                break;
            case R.id.iv_showFire:
                isShowFire = !isShowFire;
                if (isShowFire) {
                    getFire();
                } else {
                    removeFire();
                }
                break;
            case R.id.rl_close:
//                show("关闭");
//                dataLL.setVisibility(View.GONE);
                goneAnimal();
                break;
            case R.id.ll_navigation:
                //导航
                goThere();
                break;
            case R.id.ll_update_data:
                startActivity(new Intent(mContext, ResoureActivity.class));
                break;
            case R.id.iv_location:
                markLocation();
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.pause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.dispose();
        if (mCoder != null) {
            mCoder.destroy();
        }
    }

    private void initBase() {
        mCoder = GeoCoder.newInstance();
        mCoder.setOnGetGeoCodeResultListener(this);
        fireList = new ArrayList<>();
        pocEngine = PocEngineFactory.get();
        layerIV = getView(R.id.iv_layer);
        layerIV.setOnClickListener(this);
        gjIV = getView(R.id.iv_gj);
        gjIV.setOnClickListener(this);
        playGj = getView(R.id.iv_playGj);
        playGj.setOnClickListener(this);
        fireIV = getView(R.id.iv_showFire);
        fireIV.setOnClickListener(this);
        locationIV = getView(R.id.iv_location);
        locationIV.setOnClickListener(this);
        layerGV = getView(R.id.gv_layer);
        layerBeanList = new ArrayList<>();
        layerBeanList.addAll(FeatureBean.getLayerList());
        layerAdapter = new LayerAdapter(mContext, layerBeanList, R.layout.item_layer);
        layerGV.setAdapter(layerAdapter);
        layerGV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                layerAdapter.selectPosition(i);
                FeatureBean featureBean = layerAdapter.getItem(i);
                if (layerAdapter.isSelect(i)) {
//                    show("选中");
                    if (!mMapView.getMap().getOperationalLayers().contains(featureBean.featureLayer))
                        mMapView.getMap().getOperationalLayers().add(featureBean.featureLayer);
                } else {
//                    show("取消选中");
                    if (mMapView.getMap().getOperationalLayers().contains(featureBean.featureLayer))
                        mMapView.getMap().getOperationalLayers().remove(featureBean.featureLayer);
                }
            }
        });
        layerGV.setVisibility(View.INVISIBLE);
        // get a reference to the map view
    }

    private void addLayer() {
        ServiceFeatureTable serviceFeatureTable = new ServiceFeatureTable("http://gis.cqzhly" +
                ".cn:9080/arcgis/rest/services/%E5%8F%A4%E6%A0%91%E5%90%8D%E6%9C%A8/FeatureServer" +
                "/0");
        FeatureLayer featureLayer = new FeatureLayer(serviceFeatureTable);
        mMapView.getMap().getOperationalLayers().add(featureLayer);
    }

    private void removeLayer() {
        ServiceFeatureTable serviceFeatureTable = new ServiceFeatureTable("http://gis.cqzhly" +
                ".cn:9080/arcgis/rest/services/%E5%8F%A4%E6%A0%91%E5%90%8D%E6%9C%A8/FeatureServer" +
                "/0");
        FeatureLayer featureLayer = new FeatureLayer(serviceFeatureTable);
        mMapView.getMap().getOperationalLayers().remove(featureLayer);
    }

    private void createFeatureCollection() {
        if (mMapView != null) {
            FeatureCollection featureCollection = new FeatureCollection();
            FeatureCollectionLayer featureCollectionLayer =
                    new FeatureCollectionLayer(featureCollection);
            mMapView.getMap().getOperationalLayers().add(featureCollectionLayer);
            createPointTable(featureCollection);
        }
    }

    private void createPointTable(FeatureCollection featureCollection) {
        List<Feature> features = new ArrayList<>();
    }

    private void initLayer() {
//        ServiceFeatureTable serviceFeatureTable = new ServiceFeatureTable("http://gis.cqzhly" +
//                ".cn:9080/arcgis/rest/services/%E5%8F%A4%E6%A0%91%E5%90%8D%E6%9C%A8
//                /FeatureServer" +
//                "/0");
        ServiceFeatureTable serviceFeatureTable = new ServiceFeatureTable("https://222.178.189" +
                ".231:9443/arcgis/rest/services/JysBaseData/FeatureServer/0");
        featureLayer_gsmm = new FeatureLayer(serviceFeatureTable);
        SimpleMarkerSymbol simpleMarkerSymbol =
                new SimpleMarkerSymbol(SimpleMarkerSymbol.Style.CIRCLE, Color.BLUE, 10);
        PictureMarkerSymbol pictureMarkerSymbol =
                new PictureMarkerSymbol((BitmapDrawable) getResources().getDrawable(R.mipmap.maker_gsmm));
        featureLayer_gsmm.setRenderer(new SimpleRenderer(pictureMarkerSymbol));
    }

    private void initTool() {
        mMapView = getView(R.id.mapView);
        // create new Tiled Layer from service url
        ArcGISTiledLayer tiledLayerBaseMap = new ArcGISTiledLayer(YS.MAP_SEARVER);
        // set tiled layer as basemap
        Basemap basemap = new Basemap(tiledLayerBaseMap);
        // create a map with the basemap
        ArcGISMap map = new ArcGISMap(basemap);
        // set the map to be displayed in this view
        mMapView.setMap(map);
        //隐藏网格线
//        BackgroundGrid mainBackgroundGrid = new BackgroundGrid();
//        mainBackgroundGrid.setColor(0xffffffff);
//        mainBackgroundGrid.setGridLineColor(0xffffffff);
//        mainBackgroundGrid.setGridLineWidth(0);
//        mMapView.setBackgroundGrid(mainBackgroundGrid);

        arcgisToolManager = new ArcgisToolManager(getActivity(), mMapView);
        measureToolView = getView(R.id.measure_tool);
        arcgisToolManager
                .setMapClickCallBack(new MapViewOnTouchListener() {
                    @Override
                    public boolean onSingleTapUp(MotionEvent e) {
//                        if (!mMapView.isLoaded()) {
//                            return;
//                        }
                        //Toast.makeText(MainActivity2.this,"onSingleTapUp2",Toast.LENGTH_SHORT)
                        // .show();
                        android.graphics.Point screenPoint =
                                new android.graphics.Point(Math.round(e.getX()),
                                        Math.round(e.getY()));
                        Point clickPoint = mMapView.screenToLocation(screenPoint);
                        if (clickPoint == null) {
                            return super.onSingleTapUp(e);
                        }
                        onSingleTapFeatureLayer(screenPoint);
                        return super.onSingleTapUp(e);
                    }

                    @Override
                    public boolean onDoubleTap(MotionEvent e) {
                        //Toast.makeText(MainActivity2.this,"onDoubleTap2",Toast.LENGTH_SHORT)
                        // .show();
                        return super.onDoubleTap(e);
                    }

                    @Override
                    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
                                            float distanceY) {
                        //Toast.makeText(MainActivity2.this,"onScroll2",Toast.LENGTH_SHORT).show();
                        return super.onScroll(e1, e2, distanceX, distanceY);
                    }

                    @Override
                    public boolean onRotate(MotionEvent event, double rotationAngle) {
                        //Toast.makeText(MainActivity2.this,"onRotate2",Toast.LENGTH_SHORT).show();
                        return super.onRotate(event, rotationAngle);
                    }

                    @Override
                    public boolean onScale(ScaleGestureDetector detector) {
                        //Toast.makeText(MainActivity2.this,"onScale2",Toast.LENGTH_SHORT).show();
                        return super.onScale(detector);
                    }

                    @Override
                    public void onLongPress(MotionEvent e) {
                        L.e("onLongPress" + e.getX() + "--" + e.getY());
                        showPointDetail(e);
                        super.onLongPress(e);
                    }
                })
                .builderMeasure(measureToolView)
                .setButtonWidth(32)
                .setButtonHeight(32)
                .setMeasureBackground(R.color.measure_tool)
                .setSohwText(false)
                .setFontSize(12)
                .setFontColor(R.color.color444)
                .setMeasurePrevStr("撤销")
                .setMeasureNextStr("恢复")
                .setMeasureLengthStr("测距")
                .setMeasureAreaStr("测面积")
                .setMeasureClearStr("清除")
                .setMeasureEndStr("完成")
                .setMeasurePrevImage(R.drawable.sddman_measure_prev)
                .setMeasureNextImage(R.drawable.sddman_measure_next)
                .setMeasureLengthImage(R.drawable.sddman_measure_length)
                .setMeasureAreaImage(R.drawable.sddman_measure_area)
                .setMeasureClearImage(R.drawable.sddman_measure_clear)
                .setMeasureEndImage(R.drawable.sddman_measure_end)
                .setSpatialReference(SpatialReference.create(3857))
                .setLengthType(Variable.Measure.KM)
                .setAreaType(Variable.Measure.KM2)
                .setMeasureClickListener(new MeasureClickListener() {
                    @Override
                    public void prevClick(boolean hasPrev) {
//                        Toast.makeText(getActivity(), "MeasureToolView prevClick2",
//                                Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void nextClick(boolean hasNext) {
//                        Toast.makeText(getActivity(), "MeasureToolView nextClick2",
//                                Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void lengthClick() {
//                        Toast.makeText(getActivity(), "MeasureToolView lengthClick2",
//                                Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void areaClick() {
//                        Toast.makeText(getActivity(), "MeasureToolView areaClick2",
//                                Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void clearClick(DrawEntity draw) {
//                        Toast.makeText(getActivity(), "MeasureToolView clearClick2",
//                                Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void endClick(DrawEntity draw) {
//                        Toast.makeText(getActivity(), "MeasureToolView endClick2",
//                                Toast.LENGTH_SHORT).show();
                    }
                });
        zoomBtn = getView(R.id.arcgis_zoom_btn);
        arcgisToolManager.builderZoomView(zoomBtn)
                .setZoomHeight(32)
                .setZoomWidth(32)
                .setZoomBackground(R.drawable.round_corner)
                .isHorizontal(false)
                .setZoomOutImage(R.drawable.sddman_zoomout)
                .setZoomInImage(R.drawable.sddman_zoomin)
                .setShowText(false)
                .setZoomOutText("缩小")
                .setZoomInText("放大")
                .setFontSize(12)
                .setFontColor(R.color.colorMain)
                .setZoomClickListener(new ZoomClickListener() {
                    @Override
                    public void zoomInClick(View view) {
//                        Toast.makeText(getActivity(), "zoom in", Toast.LENGTH_SHORT).show();
                        L.e("当前比例尺（放大）：" + mMapView.getMapScale());
                    }

                    @Override
                    public void zoomOutClick(View view) {
//                        Toast.makeText(getActivity(), "zoom out", Toast.LENGTH_SHORT).show();
                        L.e("当前比例尺(缩小)：" + mMapView.getMapScale());
                    }
                });
    }

    private void onSingleTapFeatureLayer(
            android.graphics.Point clickPoint) {
        final ListenableFuture<List<IdentifyLayerResult>> listListenableFuture =
                mMapView.identifyLayersAsync(clickPoint, 0, false, 1);
        listListenableFuture.addDoneListener(new Runnable() {
            @Override
            public void run() {
                try {
                    List<IdentifyLayerResult> identifyLayerResults = listListenableFuture.get();
                    handleIdentifyResults(identifyLayerResults);
                } catch (InterruptedException | ExecutionException e) {
                    L.e("aaa", "Error identifying results: " + e.getMessage());
                }

            }
        });

    }


    // 将识别结果处理为一个字符串，将它通过ShowAlerDialog的方式弹出
    private void handleIdentifyResults(List<IdentifyLayerResult> identifyLayerResults) {
        if (identifyLayerResults == null || identifyLayerResults.size() == 0) {
            return;
        }
        int totalCount = 0;
        boolean isBreak = false;
        for (IdentifyLayerResult identifyLayerResult : identifyLayerResults) {
            int count = geoElementsCountFromResult(identifyLayerResult);
            L.e("count=" + count);
            String layerName = identifyLayerResult.getLayerContent().getName();
            try {
                layerName = URLDecoder.decode(layerName, "utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
//            if ("缙云山卫星地图".equals(layerName)) {
//                L.e("aaa", "layerName=" + layerName + "--continue");
//                continue;
//            }
            if (!"资源点数据".equals(layerName)) {
                L.e("aaa", "layerName=" + layerName + "--continue");
                continue;
            }
            L.e("aaa", "layerName=" + layerName);
            if (count == 0) {
                continue;
            }
            for (final GeoElement geoElement : identifyLayerResult.getElements()) {
                if (geoElement == null) {
                    continue;
                }
                Map<String, Object> attributes = geoElement.getAttributes();
                Log.e("aaa", attributes.size() + "");
                if (attributes == null || attributes.size() == 0) {
                    continue;
                }
                isBreak = true;
                Map<String, Object> map = attributes;
                for (Map.Entry<String, Object> m : map.entrySet()) {
                    Log.e("aaa", m.getKey() + "--------" + m.getValue());
                }
                //显示布局
                showView(layerName, map);
                break;
            }
            totalCount += count;
            if (isBreak) {
                isBreak = false;
                break;
            }
        }

        // 如果有元素被发现则展示识别结果，否则通知用户没有元素被找到
        if (totalCount > 0) {
//            showAlertDialog(message);
//            ToastUtil.show(mContext, totalCount + "");
            Log.e("aaa", "totalCount=" + totalCount);
        } else {
//            Toast.makeText(getActivity(), "No element found", Toast.LENGTH_SHORT).show();
            Log.e("aaa", "No element found.");
        }
    }

    // 在处理后的结果图层中获取地理元素的个数
    private int geoElementsCountFromResult(IdentifyLayerResult result) {
        // 创建一个临时的数组
        List<IdentifyLayerResult> tempResults = new ArrayList<>();
        tempResults.add(result);

        // 使用深度优先搜索方法来处理递归
        int count = 0;
        int index = 0;

        while (index < tempResults.size()) {
            // 从array数组中获取结果对象
            IdentifyLayerResult identifyResult = tempResults.get(index);
            // 更新结果中的地理元素数量
            // update count with geoElements from the result
            count += identifyResult.getElements().size();

            // 如果子图层中有结果，在当前的结果后面添加结果对象
            if (identifyResult.getSublayerResults().size() > 0) {
                tempResults.add(identifyResult.getSublayerResults().get(index));
            }

            // update the count and repeat
            // 更新计数重复循环
            index += 1;
        }
        return count;
    }

    //在AlertDialog中展示消息
    private void showAlertDialog(StringBuilder message) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
        // 设置标题
        alertDialogBuilder.setTitle("Number of elements found");

        // 设置dialog message消息
        alertDialogBuilder
                .setMessage(message.toString())
                .setCancelable(false)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });

        // 创建AlertDialog对象
        AlertDialog alertDialog = alertDialogBuilder.create();
        // 显示AlertDialog
        alertDialog.show();
    }

    private String currentPointLat;
    private String currentPointLon;
    private String currentLayerName;
    private Map<String, Object> currentMap;
    private String currentAddress;

    private void showView(String layerName, Map<String, Object> map) {
        dataList.clear();
        dataLL.setVisibility(View.VISIBLE);
        dataLL.setAnimation(AnimationUtil.moveToViewLocation());
//        nameTV.setText(StringUtil.valueOf(layerName));
        currentLayerName = layerName;
        currentMap = map;
        currentPointLat = StringUtil.valueOf(map.get("纬度"));
        currentPointLon = StringUtil.valueOf(map.get("经度"));
        Map<String, Object> locationMap = LocationSP.getLocationData(mContext);
        if (locationMap != null) {
            double lat = (double) locationMap.get("lat");
            double lon = (double) locationMap.get("lon");
            double[] gps = GPSUtil.bd09_To_gps84(lat, lon);
            double distance = GPSUtil.gps2km(StringUtil.StringToDouble(currentPointLat),
                    StringUtil.StringToDouble(currentPointLon), gps[0], gps[1]);
            distanceTV.setText(distance + "KM");
        }
//        if ("古树名木1984".equals(layerName)) {
        nameTV.setText(StringUtil.valueOf(map.get("名称")));
        String address =
                StringUtil.valueOf(map.get("区县名")) + StringUtil.valueOf(map.get("乡镇名")) + StringUtil.valueOf(map.get("村名"));
        currentAddress = address;
        addressTV.setText("\t\t|\t\t" + address);
//            dataList.add("树种名:" + StringUtil.valueOf(map.get("树种名")));
//            dataList.add("年龄:" + StringUtil.StringToInt(StringUtil.valueOf(map.get("年龄"))) + "岁");
//            dataAdapter.refresh(dataList);
//        }
    }

    private LinearLayout dataLL;
    private TextView nameTV;
    private RelativeLayout closeRL;
    private TextView distanceTV;
    private TextView addressTV;
    private LinearLayout dhLL;
    private GridView dataGV;
    private DataAdapter dataAdapter;
    private List<String> dataList;
    private LinearLayout updateDataLL;

    private void initPointDetailLayout() {
        dataList = new ArrayList<>();
        dataLL = getView(R.id.ll_data);
        nameTV = getView(R.id.tv_name);
        closeRL = getView(R.id.rl_close);
        distanceTV = getView(R.id.tv_distance);
        addressTV = getView(R.id.tv_address);
        dhLL = getView(R.id.ll_navigation);
        dataGV = getView(R.id.gv_data);
        dataAdapter = new DataAdapter(mContext, dataList, R.layout.item_data);
        dataGV.setAdapter(dataAdapter);
        closeRL.setOnClickListener(this);
        dhLL.setOnClickListener(this);
        updateDataLL = getView(R.id.ll_update_data);
        updateDataLL.setOnClickListener(this);
    }

    private LocationDisplay locationDisplay;
    //定位所需权限
    String[] reqPermissions = new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION};
    private int requestCode = 2;

    /**
     * 定位当前位置
     */
    private void markLocation() {
        //根据mapview对象获取locationDisplay对象
        locationDisplay = mMapView.getLocationDisplay();
        //设置定位模式
        locationDisplay.setAutoPanMode(LocationDisplay.AutoPanMode.RECENTER);
        locationDisplay.startAsync();
        //监听位置的变化
        locationDisplay.addDataSourceStatusChangedListener(new LocationDisplay.DataSourceStatusChangedListener() {
            @Override
            public void onStatusChanged(LocationDisplay.DataSourceStatusChangedEvent dataSourceStatusChangedEvent) {
                if (dataSourceStatusChangedEvent.isStarted())
                    return;
                if (dataSourceStatusChangedEvent.getError() == null)
                    return;
                //检查ACCESS_FINE_LOCATION权限是否允许
                boolean permissionCheck1 = ContextCompat.checkSelfPermission(getActivity(),
                        reqPermissions[0]) ==
                        PackageManager.PERMISSION_GRANTED;
                //检查ACCESS_COARSE_LOCATION权限是否允许
                boolean permissionCheck2 = ContextCompat.checkSelfPermission(getActivity(),
                        reqPermissions[1]) ==
                        PackageManager.PERMISSION_GRANTED;
                if (!(permissionCheck1 && permissionCheck2)) {
                    ActivityCompat.requestPermissions(getActivity(), reqPermissions, requestCode);
                } else {
                    String message = String.format("Error in DataSourceStatusChangedListener: %s"
                            , dataSourceStatusChangedEvent
                                    .getSource().getLocationDataSource().getError().getMessage());
                    Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
                }
            }
        });
//        Map<String, Object> locationMap = LocationSP.getLocationData(mContext);
//        if (locationMap != null) {
//            double lat = (double) locationMap.get("lat");
//            double lon = (double) locationMap.get("lon");
//            double[] gps = GPSUtil.bd09_To_gps84(lat, lon);
//        }
    }


    private GraphicsOverlay gjOverlay;//轨迹
    private GraphicsOverlay fireOverlay;//火情
    private PictureMarkerSymbol fireSymbol;

    private void createGraphicsOverlay() {
        gjOverlay = new GraphicsOverlay();
        mMapView.getGraphicsOverlays().add(gjOverlay);
        fireOverlay = new GraphicsOverlay();
        mMapView.getGraphicsOverlays().add(fireOverlay);
        pointOverlay = new GraphicsOverlay();
        mMapView.getGraphicsOverlays().add(pointOverlay);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_fire);
        BitmapDrawable bitmapDrawable = new BitmapDrawable(bitmap);
        fireSymbol = new PictureMarkerSymbol(bitmapDrawable);
    }

    private void createPolylineGraphics(List<Point> list) {
        gjOverlay.getGraphics().clear();
        int size = list.size();
        if (size == 1) {
            //画点
            SimpleMarkerSymbol pointSymbol =
                    new SimpleMarkerSymbol(SimpleMarkerSymbol.Style.CIRCLE, Color.RED, 10);
            Graphic pointGraphic = new Graphic(list.get(0), pointSymbol);
            gjOverlay.getGraphics().add(pointGraphic);
            mMapView.setViewpointCenterAsync(list.get(0)).addDoneListener(new Runnable() {
                @Override
                public void run() {
                    L.e("平移完成");
                }
            });
        } else {
            //画线
            PointCollection polylinePoints = new PointCollection(SpatialReferences.getWgs84());
            polylinePoints.addAll(list);
            Polyline polyline = new Polyline(polylinePoints);
            SimpleLineSymbol polylineSymbol = new SimpleLineSymbol(SimpleLineSymbol.Style.SOLID,
                    Color.BLUE, 3.0f);
            Graphic polylineGraphic = new Graphic(polyline, polylineSymbol);
            gjOverlay.getGraphics().add(polylineGraphic);
            mMapView.setViewpointCenterAsync(list.get(size - 1)).addDoneListener(new Runnable() {
                @Override
                public void run() {
                    L.e("平移完成");
                }
            });

//            mMapView.setViewpointGeometryAsync(gjOverlay.getExtent(),50).addDoneListener(new
//            Runnable() {
//                @Override
//                public void run() {
//                    L.e("轨迹平移完成");
//                }
//            });
        }
//        PointCollection polylinePoints = new PointCollection(SpatialReferences.getWgs84());
//        polylinePoints.add(new Point(106.41431823010231, 29.854030543574765));
//        polylinePoints.add(new Point(106.37181760929531, 29.833475430246949));
//        polylinePoints.add(new Point(106.40042894753793, 29.835975076988291));
//        Polyline polyline = new Polyline(polylinePoints);
//        SimpleLineSymbol polylineSymbol = new SimpleLineSymbol(SimpleLineSymbol.Style.SOLID,
//                Color.BLUE, 3.0f);
//        Graphic polylineGraphic = new Graphic(polyline, polylineSymbol);
//        gjOverlay.getGraphics().add(polylineGraphic);
    }

    private void clearGjGraphics() {
        gjOverlay.getGraphics().clear();
    }

    private PocEngine pocEngine;

    private void getGj() {
        String beginTime = TimeUtil.getBeginTime();
        String endTime = TimeUtil.getEndTime();
        if (pocEngine.hasServiceConnected()) {
            pocEngine.getGPSTrackList(pocEngine.getCurrentUser().getNumber() + "", beginTime
                    , endTime, new IPocEngineEventHandler.Callback<String>() {
                        @Override
                        public void onResponse(final String json) {
                            //回调在子线程
                            new Handler().post(new Runnable() {
                                @Override
                                public void run() {
                                    L.e("json=" + json);
                                    if (StringUtil.isGoodJson(json)) {
                                        GjBean gjBean = new Gson().fromJson(json, GjBean.class);
                                        if (gjBean != null && gjBean.track != null && gjBean.track.size() > 0) {
                                            List<Point> list = new ArrayList<>();
                                            for (GjBean.TrackBean trackBean : gjBean.track) {
                                                double[] gps =
                                                        GPSUtil.bd09_To_gps84(StringUtil.StringToDouble(trackBean.gis_wd),
                                                                StringUtil.StringToDouble(trackBean.gis_jd));
                                                Point point = new Point(gps[1], gps[0],
                                                        SpatialReferences.getWgs84());
                                                list.add(point);
                                            }
                                            createPolylineGraphics(list);
                                        }
                                    } else {
                                        show("暂无轨迹！");
                                    }
                                }
                            });
                        }
                    });
        } else {
            L.e("服务未连接");
            show("服务未连接");
        }
    }

    //播放轨迹
    private void playGj() {
        String beginTime = TimeUtil.getBeginTime();
        String endTime = TimeUtil.getEndTime();
        if (pocEngine.hasServiceConnected()) {
            pocEngine.getGPSTrackList(pocEngine.getCurrentUser().getNumber() + "", beginTime
                    , endTime, new IPocEngineEventHandler.Callback<String>() {
                        @Override
                        public void onResponse(final String json) {
                            //回调在子线程
                            new Handler().post(new Runnable() {
                                @Override
                                public void run() {
                                    L.e("json=" + json);
                                    if (StringUtil.isGoodJson(json)) {
                                        GjBean gjBean = new Gson().fromJson(json, GjBean.class);
                                        if (gjBean != null && gjBean.track != null && gjBean.track.size() > 0) {
                                            List<Point> list = new ArrayList<>();
                                            for (GjBean.TrackBean trackBean : gjBean.track) {
                                                double[] gps =
                                                        GPSUtil.bd09_To_gps84(StringUtil
                                                                        .StringToDouble(trackBean.gis_wd),
                                                                StringUtil.StringToDouble
                                                                        (trackBean.gis_jd));
                                                Point point = new Point(gps[1], gps[0],
                                                        SpatialReferences.getWgs84());
                                                list.add(point);
                                            }
//                                            //测试数据
//                                            list.add(new Point(106.41431823010231,
//                                                    29.854030543574765,
//                                                    SpatialReferences.getWgs84()));
//                                            list.add(new Point(106.37181760929531,
//                                                    29.833475430246949,
//                                                    SpatialReferences.getWgs84()));
//                                            list.add(new Point(106.40042894753793,
//                                                    29.835975076988291,
//                                                    SpatialReferences.getWgs84()));
//                                            list.add(new Point(106.36116413241882,
//                                                    29.818852793721533,
//                                                    SpatialReferences.getWgs84()));
//                                            list.add(new Point(106.36110841042483,
//                                                    29.818837171598261,
//                                                    SpatialReferences.getWgs84()));
                                            playTodayGj(list);
                                        }
                                    } else {
                                        show("暂无轨迹！");
                                    }
                                }
                            });
                        }
                    });
        } else {
            L.e("服务未连接");
            show("服务未连接");
        }
    }

    private void playTodayGj(List<Point> list) {
        clearGjGraphics();
        isCanPlay = false;
        show("开始播放轨迹");
        if (list.size() == 1) {
            //画点
            SimpleMarkerSymbol pointSymbol =
                    new SimpleMarkerSymbol(SimpleMarkerSymbol.Style.CIRCLE, Color.RED, 10);
            Graphic pointGraphic = new Graphic(list.get(0), pointSymbol);
            gjOverlay.getGraphics().add(pointGraphic);
            mMapView.setViewpointCenterAsync(list.get(0));
            show("轨迹播放完成");
            isCanPlay = true;
        } else {
//            mMapView.setViewpointCenterAsync(list.get(0));
            new Thread() {
                @Override
                public void run() {
                    super.run();
                    while (i < list.size() - 1) {
                        try {
                            Thread.sleep(1000);
                            List<Point> dataList = new ArrayList<>();
                            dataList.add(list.get(i));
                            dataList.add(list.get(i + 1));
                            Message msg = new Message();
                            msg.obj = dataList;
                            msg.what = 0;
                            handler.sendMessage(msg);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            i = 0;
                        }
                        i++;
                    }
                    if (i >= list.size() - 1) {
                        i = 0;
                        handler.sendEmptyMessage(1);
                    }
                }
            }.start();
        }
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                List<Point> points = (List<Point>) msg.obj;
                if (points != null && points.size() == 2) {
//                    show("绘制中");
                    drawLine(points.get(0), points.get(1));
//                    mMapView.setViewpointCenterAsync(points.get(1));
                }
            } else if (msg.what == 1) {
                show("轨迹播放完成");
                isCanPlay = true;
                playGj.setImageResource(R.mipmap.ic_play_gj_uncheck);

            }
        }
    };
    private int i = 0;
    private boolean isCanPlay = true;


    private void drawLine(Point point1, Point point2) {
        //绘制面板为空，说明重新绘制一个line，在地图和线集合里添加一个新line
//        PolylineBuilder lineGeometry = new PolylineBuilder(SpatialReferences.getWgs84());
//        lineGeometry.addPoint(point1);
//        lineGeometry.addPoint(point2);
//        SimpleLineSymbol polylineSymbol = new SimpleLineSymbol(SimpleLineSymbol.Style.SOLID,
//                Color.BLUE, 3.0f);
//        Graphic lineGraphic = new Graphic(lineGeometry.toGeometry(), polylineSymbol);
//        gjOverlay.getGraphics().add(lineGraphic);
        PointCollection polylinePoints = new PointCollection(SpatialReferences.getWgs84());
        polylinePoints.add(point1);
        polylinePoints.add(point2);
        Polyline polyline = new Polyline(polylinePoints);
        SimpleLineSymbol polylineSymbol = new SimpleLineSymbol(SimpleLineSymbol.Style.SOLID,
                Color.BLUE, 3.0f);
        Graphic polylineGraphic = new Graphic(polyline, polylineSymbol);
        gjOverlay.getGraphics().add(polylineGraphic);
    }

    private List<FireBean.DataBean.RowsBean> fireList;

    private void getFire() {
        HttpUtil.getFireListWithNoDialog(mContext, userId, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                fireList.clear();
                try {
                    FireBean fireBean = new Gson().fromJson(response.get(), FireBean.class);
                    if (fireBean != null && fireBean.data != null && fireBean.data.rows != null && fireBean.data.rows.size() > 0) {
                        List<FireBean.DataBean.RowsBean> rowsBeanList = new ArrayList<>();
                        for (FireBean.DataBean.RowsBean rowsBean1 : fireBean.data.rows) {
                            if (YS.FireStatus.Status_FSHZ.equals(rowsBean1.status)) {
                                fireList.add(rowsBean1);
                            }
                        }
                        if (fireList.size() > 0) {
                            showFire(fireList);
                        } else {
                            show("暂无火情");
                        }
                    } else {
                        show("暂无火情");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    show("暂无火情");
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {
            }
        });
    }

    private void showFire(List<FireBean.DataBean.RowsBean> list) {
        for (FireBean.DataBean.RowsBean rowsBean : list) {
            if (rowsBean != null) {
                Point point = new Point(StringUtil.StringToDouble(rowsBean.longitude),
                        StringUtil.StringToDouble(rowsBean.latitude));
                drawCircle(point);
            }
        }
    }

    private void removeFire() {
        fireOverlay.getGraphics().clear();
    }

    /**
     * 绘制圆
     */
    private void drawCircle(Point point) {
        double scale = mMapView.getMapScale() / 100;
        L.e("比例尺=" + scale + "米");
        double radius = 0.01;
        Point point1 = new Point(106.37487306404074, 29.826253132276292);
        Point point2 = new Point(106.35709505988916, 29.819586659396862);
//        double x = (point1.getX() - point2.getX());
//        double y = (point1.getY() - point2.getY());
//        radius = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
        L.e("radius=" + radius);
        mMapView.setViewpointCenterAsync(point1);
        PointCollection polylinePoints = new PointCollection(SpatialReferences.getWgs84());
        Point[] points = getPoints(point1, radius);
        for (Point p : points) {
            polylinePoints.add(p);
        }
        Polygon polygon = new Polygon(polylinePoints);
//        SimpleMarkerSymbol simpleMarkerSymbol =
//                new SimpleMarkerSymbol(SimpleMarkerSymbol.Style.CIRCLE, Color.RED, 10);
//        Graphic pointGraphic = new Graphic(point1, simpleMarkerSymbol);
        Graphic pointGraphic = new Graphic(point1, fireSymbol);
        fireOverlay.getGraphics().add(pointGraphic);

        SimpleLineSymbol lineSymbol = new SimpleLineSymbol(SimpleLineSymbol.Style.SOLID,
                Color.parseColor("#FC8145"), 3.0f);
        SimpleFillSymbol simpleFillSymbol = new SimpleFillSymbol(SimpleFillSymbol.Style.SOLID,
                Color.parseColor("#33e97676"), lineSymbol);
        Graphic graphic = new Graphic(polygon, simpleFillSymbol);
        fireOverlay.getGraphics().add(graphic);
        mMapView.setViewpointGeometryAsync(fireOverlay.getExtent(), 50);
        queryFeaturesFromTable(polygon);
        //查询要素
//        queryFeature(polygon);
    }

    /**
     * 通过中心点和半径计算得出圆形的边线点集合
     *
     * @param center
     * @param radius
     * @return
     */
    private Point[] getPoints(Point center, double radius) {
        Point[] points = new Point[50];
        double sin;
        double cos;
        double x;
        double y;
        for (double i = 0; i < 50; i++) {
            sin = Math.sin(Math.PI * 2 * i / 50);
            cos = Math.cos(Math.PI * 2 * i / 50);
            x = center.getX() + radius * sin;
            y = center.getY() + radius * cos;
            points[(int) i] = new Point(x, y);
        }
        return points;
    }

    private void goThere() {
        final CustomBaseDialog dialog = new CustomBaseDialog(mContext);
        dialog.setClickListener(new CustomBaseDialog.ClickListener() {
            @Override
            public void click(String name) {
                if ("高德地图".equals(name)) {
                    navigation(NavigationUtil.TYPE.GAODE);
                } else if ("百度地图".equals(name)) {
                    navigation(NavigationUtil.TYPE.BAIDU);
                }
            }
        });
        dialog.show();
        dialog.setCanceledOnTouchOutside(true);
    }

    private void navigation(NavigationUtil.TYPE type) {
        Map<String, Object> locationMap = LocationSP.getLocationData(mContext);
        if (locationMap != null) {
            double lat = (double) locationMap.get("lat");//百度坐标系
            double lon = (double) locationMap.get("lon");//百度坐标系
            String address = (String) locationMap.get("address");
//            double[] gps = GPSUtil.bd09_To_gps84(lat, lon);
            NavigationUtil.goAddress(mContext, type, lon,
                    lat, address, StringUtil.StringToDouble(currentPointLon),
                    StringUtil.StringToDouble(currentPointLat), currentAddress);
        } else {
            show("未获取到当前位置");
        }
    }

    private void goneAnimal() {
        ParticleView particleAnimator = new ParticleView(mContext, 2000);//3000为动画持续时间
        particleAnimator.setOnAnimationListener(new ParticleView.OnAnimationListener() {
            @Override
            public void onAnimationStart(View v, Animator animation) {
                v.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationEnd(View v, Animator animation) {

            }
        });
        particleAnimator.boom(dataLL);
    }

    private Point longPressPoint;

    private void showPointDetail(MotionEvent e) {
        android.graphics.Point screenPoint =
                new android.graphics.Point(Math.round(e.getX()),
                        Math.round(e.getY()));
        longPressPoint = mMapView.screenToLocation(screenPoint);
        if (longPressPoint != null) {
            L.e("x=" + longPressPoint.getX() + "--y=" + longPressPoint.getY());
            double[] points = GPSUtil.gps84_To_bd09(longPressPoint.getY(), longPressPoint.getX());
            geoAddress(points[0], points[1]);
//            DialogUtil.showPointTip(getActivity(), "行政区划：",
//                    clickPoint.getX() + "," + clickPoint.getY());
        }

    }

    private GeoCoder mCoder;

    private void geoAddress(double lat, double lon) {
        mCoder.reverseGeoCode(new ReverseGeoCodeOption()
                .location(new LatLng(lat, lon))
                // 设置是否返回新数据 默认值0不返回，1返回
                .newVersion(1)
                // POI召回半径，允许设置区间为0-1000米，超过1000米按1000米召回。默认值为1000
                .radius(500));
    }

    @Override
    public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {

    }

    @Override
    public void onGetReverseGeoCodeResult(ReverseGeoCodeResult reverseGeoCodeResult) {
        String city = "";
        if (reverseGeoCodeResult == null || reverseGeoCodeResult.error != SearchResult.ERRORNO.NO_ERROR) {
            //没有找到检索结果
            city = "未检索到行政区划";
            L.e("onGetReverseGeoCodeResult---没有检索到结果");
        } else {
            //详细地址
            String address = reverseGeoCodeResult.getAddress();
            //行政区号
            int adCode = reverseGeoCodeResult.getCityCode();
            city = address;
            L.e("address=" + address + "--adCode=" + adCode);
        }
        if (longPressPoint != null) {
            DialogUtil.showPointTip(getActivity(), city,
                    longPressPoint.getX() + "," + longPressPoint.getY());
        }
    }

    private GraphicsOverlay pointOverlay;//点位
    private SimpleMarkerSymbol pointFireMarker =
            new SimpleMarkerSymbol(SimpleMarkerSymbol.Style.CIRCLE, Color.BLUE, 4);

    private void queryFeature(Geometry geometry) {
        try {
            ServiceFeatureTable serviceFeatureTable =
                    new ServiceFeatureTable(FeatureBean.FEATURE_SERVER + "/0");
            FeatureLayer featureLayer = new FeatureLayer(serviceFeatureTable);
            QueryParameters args = new QueryParameters();
            args.setReturnGeometry(true);// 是否返回Geometry
            args.setGeometry(geometry); // 查询范围面
            args.setOutSpatialReference(SpatialReferences.getWgs84());
            args.setSpatialRelationship(QueryParameters.SpatialRelationship.WITHIN);
            //获取查询结果result
            ListenableFuture<FeatureQueryResult> future =
                    serviceFeatureTable.queryFeaturesAsync(args);
            future.addDoneListener(new Runnable() {
                @Override
                public void run() {
                    try {
                        // 调用future的get方法获取结果
                        FeatureQueryResult result = future.get();
                        // 检查是否查询出了结果
                        if (result.iterator().hasNext()) {
                            // 获取查询结果的第一个要素的地图范围，并设置地图缩放至该范围
                            Feature feature = result.iterator().next();
                            //获取结果feature的几何形状变量
                            Geometry geoResult = feature.getGeometry();
                            //获取结果feature的几何外接矩形
                            Envelope envelope = geoResult.getExtent();
                            //创建用于显示查询结果的Symbol符号
                            SimpleFillSymbol FillSymbol =
                                    new SimpleFillSymbol(SimpleFillSymbol.Style.SOLID, Color.RED,
                                            new SimpleLineSymbol(SimpleLineSymbol.Style.SOLID,
                                                    Color.RED, 2));
                            //创建用于显示查询结果的Graphic图形
                            Graphic graphicResult = new Graphic(geoResult, FillSymbol);
                            //创建用于显示查询结果的GraphicOverlay，并将Graphic加入其中
                            GraphicsOverlay graphicsOverlay = new GraphicsOverlay();
                            graphicsOverlay.getGraphics().add(graphicResult);
                            //显示查询结果
                            mMapView.getGraphicsOverlays().clear();
                            mMapView.getGraphicsOverlays().add(graphicsOverlay);
                            mMapView.setViewpointGeometryAsync(envelope, 10);
                            //查询要素
                            featureLayer.selectFeature(feature);
                        } else {
                            show("No states found with name: ");
                        }
                    } catch (Exception e) {
                    }
                }
            });
        } catch (Exception e) {

        }
    }

    private void queryFeaturesFromTable(Geometry geometry) {
        L.e("queryFeaturesFromTable");
        pointList.clear();
        pointOverlay.getGraphics().clear();
        ServiceFeatureTable table = new ServiceFeatureTable(
                "http://222.178.189.231:9080/arcgis/rest/services/JysBaseData/FeatureServer/0");
        table.loadAsync();
        table.addDoneLoadingListener(() -> {
            QueryParameters query = new QueryParameters();
            query.setWhereClause("1=1");
            query.setGeometry(geometry);
            query.setReturnGeometry(true);
            ListenableFuture<FeatureQueryResult> tableQueryResult =
                    table.queryFeaturesAsync(query,
                            ServiceFeatureTable.QueryFeatureFields.LOAD_ALL);
            /* ** ADD ** */
            tableQueryResult.addDoneListener(() -> {
                try {
                    List<Graphic> graphics = new ArrayList<>();
                    FeatureQueryResult result = tableQueryResult.get();
                    List<Field> fields = result.getFields();
                    for (Field field : fields) {
                        L.e("Field==" + field.getName() + "--" + field.getAlias());
                    }
                    int i = 0;
                    for (Feature feature : result) {
                        i++;
                        Map<String, Object> attributes = feature.getAttributes();
                        L.e("attributes.size()=" + attributes.size());
                        for (Map.Entry<String, Object> m : attributes.entrySet()) {
                            L.e(m.getKey() + "--------" + m.getValue());
                        }
                        pointList.add(attributes);
                        SimpleMarkerSymbol simpleMarkerSymbol =
                                new SimpleMarkerSymbol(SimpleMarkerSymbol.Style.CIRCLE,
                                        Color.BLUE, 4);
                        graphics.add(new Graphic(feature.getGeometry(), simpleMarkerSymbol));
                    }
                    L.e("result.size()=" + i);
                    pointOverlay.getGraphics().addAll(graphics);
                    if (i > 0) {
                        fireLL.setVisibility(View.VISIBLE);
                        setPointNum(i);
                        pointAdapter.refresh(pointList);
                    } else {
                        fireLL.setVisibility(View.GONE);
                    }
                } catch (Exception e) {
                    L.e(e.getMessage());
                }
            });
        });
    }

    private LinearLayout fireLL;
    private LinearLayout showLL;
    private TextView numTV;
    private ListView pointLV;
    private ImageView showIV;
    private PointAdapter pointAdapter;
    private List<Map<String, Object>> pointList;
    private boolean isShowFirePoint = false;

    private void initFirePointLayout() {
        pointList = new ArrayList<>();
        fireLL = getView(R.id.ll_fire);
        showLL = getView(R.id.ll_show);
        numTV = getView(R.id.tv_num);
        showIV = getView(R.id.iv_show);
        pointLV = getView(R.id.lv_point);
        pointAdapter = new PointAdapter(mContext, pointList, R.layout.item_fire_point);
        pointLV.setAdapter(pointAdapter);
        showLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isShowFirePoint = !isShowFirePoint;
                if (isShowFirePoint) {
                    pointLV.setVisibility(View.VISIBLE);
                    showIV.setImageResource(R.mipmap.fire_down);
                } else {
                    pointLV.setVisibility(View.GONE);
                    showIV.setImageResource(R.mipmap.fire_up);
                }
            }
        });
    }

    public class PointAdapter extends CommonAdapter<Map<String, Object>> {
        private Map<String, Object> locationMap;
        private double[] gps;

        public PointAdapter(Context context, List<Map<String, Object>> mDatas, int itemLayoutId) {
            super(context, mDatas, itemLayoutId);
            locationMap = LocationSP.getLocationData(mContext);
            if (locationMap != null) {
                double lat = (double) locationMap.get("lat");
                double lon = (double) locationMap.get("lon");
                gps = GPSUtil.bd09_To_gps84(lat, lon);
            }
        }

        @Override
        public void convert(ViewHolder helper, Map<String, Object> item, int position) {
            if (gps != null) {
                double distance =
                        GPSUtil.gps2km(StringUtil.StringToDouble(StringUtil.valueOf(item.get("纬度"))),
                        StringUtil.StringToDouble(StringUtil.valueOf(item.get("经度"))), gps[0],
                                gps[1]);
                helper.setText(R.id.tv_distance, distance + "KM");
            }
            helper.setText(R.id.tv_name, StringUtil.valueOf(item.get("名称")));
            String address =
                    StringUtil.valueOf(item.get("区县名")) + StringUtil.valueOf(item.get("乡镇名")) + StringUtil.valueOf(item.get("村名"));
            helper.setText(R.id.tv_address, "\t\t|\t\t" + address);
            helper.getView(R.id.ll_navigation).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }
    }

    private void setPointNum(int num) {
        String numText = String.format("附近共找到资源<font color=\"#000000\">%d</font>处", num);
        numTV.setText(Html.fromHtml(numText));
    }
}
