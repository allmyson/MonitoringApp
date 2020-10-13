package com.ys.monitor.fragment;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.esri.arcgisruntime.concurrent.ListenableFuture;
import com.esri.arcgisruntime.data.Feature;
import com.esri.arcgisruntime.data.FeatureCollection;
import com.esri.arcgisruntime.data.ServiceFeatureTable;
import com.esri.arcgisruntime.geometry.Point;
import com.esri.arcgisruntime.geometry.PointCollection;
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
import com.esri.arcgisruntime.symbology.SimpleLineSymbol;
import com.esri.arcgisruntime.symbology.SimpleMarkerSymbol;
import com.esri.arcgisruntime.symbology.SimpleRenderer;
import com.huamai.poc.IPocEngineEventHandler;
import com.huamai.poc.PocEngine;
import com.huamai.poc.PocEngineFactory;
import com.ys.monitor.R;
import com.ys.monitor.adapter.DataAdapter;
import com.ys.monitor.adapter.LayerAdapter;
import com.ys.monitor.base.BaseFragment;
import com.ys.monitor.bean.LayerBean;
import com.ys.monitor.sp.LocationSP;
import com.ys.monitor.util.GPSUtil;
import com.ys.monitor.util.L;
import com.ys.monitor.util.StringUtil;
import com.ys.monitor.util.ToastUtil;
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
public class MapFragment extends BaseFragment implements View.OnClickListener {
    private MapView mMapView;
    private ArcGisZoomView zoomBtn;
    private ArcgisToolManager arcgisToolManager;
    private MeasureToolView measureToolView;
    private GridView layerGV;
    private LayerAdapter layerAdapter;
    private List<LayerBean> layerBeanList;
    private ImageView layerIV, gjIV;

    private FeatureLayer featureLayer_gsmm;

    public static MapFragment newInstance() {
        return new MapFragment();
    }

    private boolean isShowLayer;
    private boolean isShowGj;

    @Override
    protected void init() {
        pocEngine = PocEngineFactory.get();
        initPointDetailLayout();
        layerIV = getView(R.id.iv_layer);
        layerIV.setOnClickListener(this);
        gjIV = getView(R.id.iv_gj);
        gjIV.setOnClickListener(this);
        layerGV = getView(R.id.gv_layer);
        layerBeanList = new ArrayList<>();
        layerBeanList.addAll(LayerBean.getDefaultLayers());
        layerAdapter = new LayerAdapter(mContext, layerBeanList, R.layout.item_layer);
        layerGV.setAdapter(layerAdapter);
        layerGV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                layerAdapter.selectPosition(i);
                if (layerAdapter.isSelect(i)) {
                    show("选中");
                    if (!mMapView.getMap().getOperationalLayers().contains(featureLayer_gsmm))
                        mMapView.getMap().getOperationalLayers().add(featureLayer_gsmm);
                } else {
                    show("取消选中");
                    if (mMapView.getMap().getOperationalLayers().contains(featureLayer_gsmm))
                        mMapView.getMap().getOperationalLayers().remove(featureLayer_gsmm);
                }
            }
        });
        layerGV.setVisibility(View.INVISIBLE);
        // get a reference to the map view
        mMapView = getView(R.id.mapView);
        // create new Tiled Layer from service url
        ArcGISTiledLayer tiledLayerBaseMap = new ArcGISTiledLayer(YS.MAP_SEARVER);
        // set tiled layer as basemap
        Basemap basemap = new Basemap(tiledLayerBaseMap);
        // create a map with the basemap
        ArcGISMap map = new ArcGISMap(basemap);
        // set the map to be displayed in this view
        mMapView.setMap(map);
        initTool();
        //定位后就不在北碚了
//        markLocation();
        initLayer();
        createGraphicsOverlay();
        getGj();
    }

    @Override
    protected void getData() {
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
                    getGj();
//                    createPolylineGraphics();
                } else {
                    clearGjGraphics();
                }
                break;
            case R.id.rl_close:
                show("关闭");
                dataLL.setVisibility(View.GONE);
                break;
            case R.id.ll_navigation:
                //导航
                show("导航");
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
        ServiceFeatureTable serviceFeatureTable = new ServiceFeatureTable("http://gis.cqzhly" +
                ".cn:9080/arcgis/rest/services/%E5%8F%A4%E6%A0%91%E5%90%8D%E6%9C%A8/FeatureServer" +
                "/0");
        featureLayer_gsmm = new FeatureLayer(serviceFeatureTable);
        SimpleMarkerSymbol simpleMarkerSymbol =
                new SimpleMarkerSymbol(SimpleMarkerSymbol.Style.CIRCLE, Color.BLUE, 10);
        PictureMarkerSymbol pictureMarkerSymbol =
                new PictureMarkerSymbol((BitmapDrawable) getResources().getDrawable(R.mipmap.maker_gsmm));
        featureLayer_gsmm.setRenderer(new SimpleRenderer(pictureMarkerSymbol));
    }

    private void initTool() {
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
                        onSingleTapFeatureLayer(screenPoint, featureLayer_gsmm, false);
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
                })
                .builderMeasure(measureToolView)
                .setButtonWidth(32)
                .setButtonHeight(32)
                .setMeasureBackground(R.color.colorAccent)
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
                    }

                    @Override
                    public void zoomOutClick(View view) {
//                        Toast.makeText(getActivity(), "zoom out", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void onSingleTapFeatureLayer(
            android.graphics.Point clickPoint, FeatureLayer layer, boolean isSentiveLayer) {
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
            if ("缙云山卫星地图".equals(layerName)) {
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
            ToastUtil.show(mContext, totalCount + "");
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

    private void showView(String layerName, Map<String, Object> map) {
        dataList.clear();
        dataLL.setVisibility(View.VISIBLE);
        nameTV.setText(StringUtil.valueOf(layerName));
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
        if ("古树名木1984".equals(layerName)) {
            String address =
                    StringUtil.valueOf(map.get("区县")) + StringUtil.valueOf(map.get("乡镇")) + StringUtil.valueOf(map.get("村名")) + StringUtil.valueOf(map.get("小地名"));
            addressTV.setText("\t\t|\t\t" + address);
            dataList.add("树种名:" + StringUtil.valueOf(map.get("树种名")));
            dataList.add("年龄:" + StringUtil.StringToInt(StringUtil.valueOf(map.get("年龄"))) + "岁");
            dataAdapter.refresh(dataList);
        }
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

    private void createGraphicsOverlay() {
        gjOverlay = new GraphicsOverlay();
        mMapView.getGraphicsOverlays().add(gjOverlay);
    }

    private void createPolylineGraphics() {
        PointCollection polylinePoints = new PointCollection(SpatialReferences.getWgs84());
        polylinePoints.add(new Point(106.41431823010231, 29.854030543574765));
        polylinePoints.add(new Point(106.37181760929531, 29.833475430246949));
        Polyline polyline = new Polyline(polylinePoints);
        SimpleLineSymbol polylineSymbol = new SimpleLineSymbol(SimpleLineSymbol.Style.SOLID,
                Color.BLUE, 3.0f);
        Graphic polylineGraphic = new Graphic(polyline, polylineSymbol);
        gjOverlay.getGraphics().add(polylineGraphic);
    }

    private void clearGjGraphics() {
        gjOverlay.getGraphics().clear();
    }

    private PocEngine pocEngine;

    private void getGj() {
        if(pocEngine.hasServiceConnected()) {
            pocEngine.getGPSTrackList(pocEngine.getCurrentUser().getNumber() + "", "1970-01-01 00:00"
                    , "2020-10-14 00:00", new IPocEngineEventHandler.Callback<String>() {
                        @Override
                        public void onResponse(final String json) {
                            //回调在子线程
                            new Handler().post(new Runnable() {
                                @Override
                                public void run() {
                                    L.e("json=" + json);
                                    createPolylineGraphics();
                                }
                            });
                        }
                    });
        }else {
            L.e("服务未连接");
            show("服务未连接");
        }
    }
}
