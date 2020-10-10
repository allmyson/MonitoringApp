package com.ys.monitor.fragment;

import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.esri.arcgisruntime.concurrent.ListenableFuture;
import com.esri.arcgisruntime.data.Feature;
import com.esri.arcgisruntime.data.FeatureCollection;
import com.esri.arcgisruntime.data.ServiceFeatureTable;
import com.esri.arcgisruntime.geometry.Point;
import com.esri.arcgisruntime.geometry.SpatialReference;
import com.esri.arcgisruntime.layers.ArcGISTiledLayer;
import com.esri.arcgisruntime.layers.FeatureCollectionLayer;
import com.esri.arcgisruntime.layers.FeatureLayer;
import com.esri.arcgisruntime.mapping.ArcGISMap;
import com.esri.arcgisruntime.mapping.Basemap;
import com.esri.arcgisruntime.mapping.GeoElement;
import com.esri.arcgisruntime.mapping.view.IdentifyLayerResult;
import com.esri.arcgisruntime.mapping.view.MapView;
import com.esri.arcgisruntime.symbology.PictureMarkerSymbol;
import com.esri.arcgisruntime.symbology.SimpleMarkerSymbol;
import com.esri.arcgisruntime.symbology.SimpleRenderer;
import com.ys.monitor.R;
import com.ys.monitor.adapter.LayerAdapter;
import com.ys.monitor.base.BaseFragment;
import com.ys.monitor.bean.LayerBean;

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
    private ImageView layerIV;

    private FeatureLayer featureLayer_gsmm;

    public static MapFragment newInstance() {
        return new MapFragment();
    }

    private boolean isShowLayer;

    @Override
    protected void init() {
        layerIV = getView(R.id.iv_layer);
        layerIV.setOnClickListener(this);
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
        ArcGISTiledLayer tiledLayerBaseMap = new ArcGISTiledLayer("http://gis.cqzhly" +
                ".cn:9080/arcgis/rest/services/2020/%E7%BC%99%E4%BA%91%E5%B1%B1%E5%8D%AB%E6%98%9F" +
                "%E5%9C%B0%E5%9B%BE/MapServer");
        // set tiled layer as basemap
        Basemap basemap = new Basemap(tiledLayerBaseMap);
        // create a map with the basemap
        ArcGISMap map = new ArcGISMap(basemap);
        // set the map to be displayed in this view
        mMapView.setMap(map);
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
//
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
                        Toast.makeText(getActivity(), "MeasureToolView prevClick2",
                                Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void nextClick(boolean hasNext) {
                        Toast.makeText(getActivity(), "MeasureToolView nextClick2",
                                Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void lengthClick() {
                        Toast.makeText(getActivity(), "MeasureToolView lengthClick2",
                                Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void areaClick() {
                        Toast.makeText(getActivity(), "MeasureToolView areaClick2",
                                Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void clearClick(DrawEntity draw) {
                        Toast.makeText(getActivity(), "MeasureToolView clearClick2",
                                Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void endClick(DrawEntity draw) {
                        Toast.makeText(getActivity(), "MeasureToolView endClick2",
                                Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(getActivity(), "zoom in", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void zoomOutClick(View view) {
                        Toast.makeText(getActivity(), "zoom out", Toast.LENGTH_SHORT).show();
                    }
                });
        initLayer();
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


    private void onSingleTapFeatureLayer(
            android.graphics.Point clickPoint, FeatureLayer layer, boolean isSentiveLayer) {
//        QueryParameters queryParameters = new QueryParameters();
//        queryParameters.setGeometry(clickPoint);
//        queryParameters.setReturnGeometry(true);
//        FeatureTable mTable = layer.getFeatureTable();//得到查询属性表
//                        final ListenableFuture<IdentifyLayerResult> featureQueryResult
//                                = mMapView.identifyLayerAsync(featureLayer_gsmm,screenPoint,0,
//                                false);

//        final ListenableFuture<FeatureQueryResult> featureQueryResult
//                = mTable.queryFeaturesAsync(queryParameters);
//        featureQueryResult.addDoneListener(new Runnable() {
//            @Override
//            public void run() {
//                try {
////                    FeatureQueryResult featureResul = featureQueryResult.get();
////                    for (Object element : featureResul) {
////                        if (element instanceof Feature) {
////                            Feature mFeatureGrafic = (Feature) element;
////                            Map<String, Object> mQuerryString =
////                                    mFeatureGrafic.getAttributes();
////                            for (String key : mQuerryString.keySet()) {
////                                L.e(key + "===" +
////                                        String.valueOf(mQuerryString.get(key)));
////                            }
////                        }
////                    }
//                    FeatureQueryResult featureResul = featureQueryResult.get();
//                    for (Object element : featureResul) {
//                        if (element instanceof Feature) {
//                            Feature mFeatureGrafic = (Feature) element;
//                            Map<String, Object> mQuerryString = mFeatureGrafic.getAttributes();
//                            for(String key : mQuerryString.keySet()){
//                                L.e(key + "===" +
//                                        String.valueOf(mQuerryString.get(key)));
//                            }
//                        }
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        });
        final ListenableFuture<List<IdentifyLayerResult>> listListenableFuture =
                mMapView.identifyLayersAsync(clickPoint, 0, false, 1);
        listListenableFuture.addDoneListener(new Runnable() {
            @Override
            public void run() {
                try {
                    boolean mBoolean = false;
                    List<IdentifyLayerResult> identifyLayerResults = listListenableFuture.get();
                    int i = 0;
                    int d = identifyLayerResults.size();
//                    if(d==1){
                    for (IdentifyLayerResult identifyLayerResult : identifyLayerResults) {


                        for (final GeoElement geoElement : identifyLayerResult.getElements()) {
                            if (geoElement == null) {
                                continue;
                            }
                            Map<String, Object> attributes = geoElement.getAttributes();
                            Map<String, Object> map = attributes;
                            for (Map.Entry<String, Object> m : map.entrySet()) {
                                Log.e("aaa", m.getKey() + "--------" + m.getValue());
                            }
                            Log.e("aaa", attributes.size() + "");

                        }
                    }
//                    }else if (d>1){
//                        mBoolean=true;
//
//                        int o=0;
//                        for (IdentifyLayerResult identifyLayerResult : identifyLayerResults) {
//
//                        }
//                    }

                } catch (InterruptedException e) {
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        });

    }
}
