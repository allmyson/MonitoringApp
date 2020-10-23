package com.ys.monitor.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMap.OnMapStatusChangeListener;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.baidu.mapapi.search.sug.OnGetSuggestionResultListener;
import com.baidu.mapapi.search.sug.SuggestionResult;
import com.baidu.mapapi.search.sug.SuggestionSearch;
import com.baidu.mapapi.search.sug.SuggestionSearchOption;
import com.google.gson.Gson;
import com.yanzhenjie.nohttp.rest.Response;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RationaleListener;
import com.ys.monitor.R;
import com.ys.monitor.base.BaseActivity;
import com.ys.monitor.http.BaseHttp;
import com.ys.monitor.http.HttpListener;
import com.ys.monitor.util.AppUtil;
import com.ys.monitor.util.GPSUtil;
import com.ys.monitor.util.L;
import com.ys.monitor.util.StringUtil;
import com.ys.monitor.util.ToastUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class BaiduMapSelectAddressActivity extends BaseActivity implements OnClickListener,
        OnGetSuggestionResultListener,
        OnMapStatusChangeListener, BDLocationListener, OnGetGeoCoderResultListener {
    private ImageView iv_left;
    protected static final String TAG = "MoreAddressActivity";
    protected static final int REQUEST_OK = 0;
    private ListView lv_near_address;
    private SuggestionSearch mSuggestionSearch = null;
    private BaiduMap mBaiduMap = null;
    private double latitude;
    private double longitude;
    /**
     * 搜索关键字输入窗口
     */
    private EditText keyWorldsView = null;
    private SearchResultAdapter sugAdapter = null;
    private ListView searchLV = null;
    private int load_Index = 0;
    private PoiSearchAdapter adapter;
    private MapView map;
    private MyLocationConfiguration.LocationMode mCurrentMode;
    private LocationClient mLocClient;
    private GeoCoder geoCoder;
    private String city;
    private boolean isFirstLoc = true;
    private LatLng locationLatLng;

    private ImageView mImg_load_animation;
    private long startTime;
    private long stopTome;
    private TextView mTv_net;
    private RelativeLayout mRl_gps;


    private List<SuggestionResult.SuggestionInfo> searchList = new ArrayList();


    @Override
    public int getLayoutId() {
        return R.layout.activity_baidumap_select_address;
    }

    @Override
    public void initView() {
        setBarColor("#0D87F8",false);
        getPermission();
        initData();
//
//        startTime = System.currentTimeMillis();
    }

    @Override
    public void getData() {

    }

    private void initData() {
        ///XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
//        searchAddress = (EditText) findViewById(R.id.main_search_address);
//        searchPois = (ListView) findViewById(R.id.main_search_pois);
        mImg_load_animation = (ImageView) findViewById(R.id.img_load_animation);
        mTv_net = (TextView) findViewById(R.id.tv_net);
        mRl_gps = (RelativeLayout) findViewById(R.id.rl_gps);
        mRl_gps.setClickable(true);

        ///XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
        map = (MapView) findViewById(R.id.map);
        mBaiduMap = map.getMap();
        MapStatus mapStatus = new MapStatus.Builder().zoom(15).build();
        MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mapStatus);
        mBaiduMap.setMapStatus(mMapStatusUpdate);

        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
        // 地图状态改变相关监听
        mBaiduMap.setOnMapStatusChangeListener(this);

        // 定位图层显示方式
        mCurrentMode = MyLocationConfiguration.LocationMode.NORMAL;
        mBaiduMap.setMyLocationConfigeration(new MyLocationConfiguration(mCurrentMode, true, null));
        mLocClient = new LocationClient(this);
        // 注册定位监听
        mLocClient.registerLocationListener(this);
        // 定位选项
        LocationClientOption option = new LocationClientOption();
        /**
         * coorType - 取值有3个： 返回国测局经纬度坐标系：gcj02 返回百度墨卡托坐标系 ：bd09 返回百度经纬
         度坐标系
         * ：bd09ll
         */
        option.setCoorType("bd09ll");
        // 设置是否需要地址信息，默认为无地址
        option.setIsNeedAddress(true);
        // 设置是否需要返回位置语义化信息，可以在BDLocation.getLocationDescribe()中得到数据，ex:"在天安门附近"，
        // 可以用作地址信息的补充
        option.setIsNeedLocationDescribe(true);
        // 设置是否需要返回位置POI信息，可以在BDLocation.getPoiList()中得到数据
        option.setIsNeedLocationPoiList(true);
        /**
         * 设置定位模式 Battery_Saving 低功耗模式 Device_Sensors 仅设备(Gps)模式 Hight_Accuracy
         * 高精度模式
         /   */
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        // 设置是否打开gps进行定位
        option.setOpenGps(true);
        // 设置扫描间隔，单位是毫秒 当<1000(1s)时，定时定位无效
        option.setScanSpan(1000);
        // 设置 LocationClientOption
        mLocClient.setLocOption(option);
        // 开始定位
        mLocClient.start();
        lv_near_address = (ListView) findViewById(R.id.lv_near_address);
        // 初始化搜索模块，注册搜索事件监听
        mSuggestionSearch = SuggestionSearch.newInstance();
        mSuggestionSearch.setOnGetSuggestionResultListener(this);
        keyWorldsView = (EditText) findViewById(R.id.et_search);
        searchLV = getView(R.id.search_listview);
        sugAdapter = new SearchResultAdapter();

        /**
         * 当输入关键字变化时，动态更新建议列表
         */
        keyWorldsView.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable arg0) {
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

            }

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                if (cs.length() <= 0) {
                    return;
                }

                /**
                 * 使用建议搜索服务获取建议列表，结果在onSuggestionResult()中更新
                 */
                mSuggestionSearch.requestSuggestion((new SuggestionSearchOption()).keyword(cs.toString
                        ()).city("重庆"));
//	                mSuggestionSearch.requestSuggestion((new SuggestionSearchOption()).keyword(cs
//	                .toString()));
            }
        });

        searchLV.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String address = parent.getItemAtPosition(position).toString();
                SuggestionResult.SuggestionInfo info = searchList.get(position);
                getAddressByPoint(info.pt);
//                L.e("数据=="+new Gson().toJson(info));
//
//                setResult(info.getKey(),info.pt);
            }
        });


        iv_left = (ImageView) findViewById(R.id.iv_left);
        iv_left.setOnClickListener(this);
    }

    /*
     * 接受周边地理位置结果
     *
     * @param poiResult
     */

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBaiduMap.setMyLocationEnabled(false);
        map.onDestroy();
        if (geoCoder != null) {
            geoCoder.destroy();
        }
        map = null;
        if (mSuggestionSearch != null) {
            mSuggestionSearch.destroy();
            mSuggestionSearch = null;
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_left:
                finish();
                break;

            default:
                break;
        }
    }

    public void onResume() {
        super.onResume();
        map.onResume();
    }

    public void onPause() {
        super.onPause();
        map.onPause();
    }

    @Override
    public void onGetSuggestionResult(SuggestionResult res) {
        if (res == null || res.getAllSuggestions() == null) {
            return;
        }

        searchList.clear();
        searchList.addAll(res.getAllSuggestions());

//        for (SuggestionResult.SuggestionInfo info : res.getAllSuggestions()) {
//            if (info.key != null)
//                sugAdapter.add(info.key + " " + info.city + info.district);
//        }
        searchLV.setAdapter(sugAdapter);
        searchLV.setVisibility(View.VISIBLE);
        sugAdapter.notifyDataSetChanged();
        L.e(sugAdapter.getCount() + "===搜索到数据==" + new Gson().toJson(res.getAllSuggestions()));
    }


    ///XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
    class PoiSearchAdapter extends BaseAdapter {

        private Context context;
        private List<PoiInfo> list;
        private ViewHolder holder;

        public PoiSearchAdapter(Context context, List<PoiInfo> appGroup, LatLng locationLatLng) {
            this.context = context;
            this.list = appGroup;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int location) {
            return list.get(location);
        }

        @Override
        public long getItemId(int arg0) {
            return arg0;
        }

        public void addObject(List<PoiInfo> mAppGroup) {
            this.list = mAppGroup;
            notifyDataSetChanged();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                holder = new ViewHolder();
                convertView = LayoutInflater.from(context).inflate(R.layout.poi_search_item, null);
                holder.mpoi_name = (TextView) convertView.findViewById(R.id.mpoiNameT);
                holder.mpoi_address = (TextView) convertView.findViewById(R.id.mpoiAddressT);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.mpoi_name.setText(list.get(position).name);
            holder.mpoi_address.setText(list.get(position).address);
            // Log.i("yxx", "==1=poi===城市：" + poiInfo.city + "名字：" +
            // poiInfo.name + "地址：" + poiInfo.address);
            return convertView;
        }

        public class ViewHolder {
            public TextView mpoi_name;// 名称
            public TextView mpoi_address;// 地址

        }

    }

    @Override
    public void onMapStatusChange(MapStatus mapStatus) {

    }

    @Override
    public void onMapStatusChangeFinish(MapStatus mapStatus) {
        // 地图操作的中心点
        try {
            if (mapStatus != null && geoCoder != null) {
                LatLng cenpt = mapStatus.target;
                geoCoder.reverseGeoCode(new ReverseGeoCodeOption().location(cenpt));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onMapStatusChangeStart(MapStatus mapStatus) {

    }

    @Override
    public void onReceiveLocation(BDLocation bdLocation) {
        // 如果bdLocation为空或mapView销毁后不再处理新数据接收的位置
        if (bdLocation == null || mBaiduMap == null) {
            return;
        }

        // 定位数据
        MyLocationData data = new MyLocationData.Builder()
                // 定位精度bdLocation.getRadius()
                .accuracy(bdLocation.getRadius())
                // 此处设置开发者获取到的方向信息，顺时针0-360
                .direction(bdLocation.getDirection())
                // 经度
                .latitude(bdLocation.getLatitude())
                // 纬度
                .longitude(bdLocation.getLongitude())
                // 构建
                .build();

        // 设置定位数据
        mBaiduMap.setMyLocationData(data);

        // 是否是第一次定位
        if (isFirstLoc) {
            isFirstLoc = false;
            LatLng ll = new LatLng(bdLocation.getLatitude(), bdLocation.getLongitude());
            MapStatusUpdate msu = MapStatusUpdateFactory.newLatLngZoom(ll, 18);
            mBaiduMap.animateMapStatus(msu);
        }

        locationLatLng = new LatLng(bdLocation.getLatitude(), bdLocation.getLongitude());
        // 获取城市，待会用于POISearch
        city = bdLocation.getCity();

        // 创建GeoCoder实例对象
        geoCoder = GeoCoder.newInstance();
        // 发起反地理编码请求(经纬度->地址信息)
        ReverseGeoCodeOption reverseGeoCodeOption = new ReverseGeoCodeOption();
        // 设置反地理编码位置坐标
        reverseGeoCodeOption.location(new LatLng(bdLocation.getLatitude(),
                bdLocation.getLongitude()));
        geoCoder.reverseGeoCode(reverseGeoCodeOption);

        // 设置查询结果监听者
        geoCoder.setOnGetGeoCodeResultListener(this);
    }

    @Override
    public void onGetGeoCodeResult(GeoCodeResult arg0) {


    }

    // 拿到变化地点后的附近地址
    @Override
    public void onGetReverseGeoCodeResult(ReverseGeoCodeResult reverseGeoCodeResult) {
            /*onGetReverseGeoCodeResult获取到结果之后,
	        mLocClient.stop(); 这里获取到附近定位的地址之后关闭定位*/
        stopTome = System.currentTimeMillis();
        mRl_gps.setVisibility(View.GONE);
        mLocClient.stop();
        final List<PoiInfo> poiInfos = reverseGeoCodeResult.getPoiList();
        if (poiInfos != null && !"".equals(poiInfos)) {
            PoiAdapter poiAdapter = new PoiAdapter(BaiduMapSelectAddressActivity.this, poiInfos);
            lv_near_address.setAdapter(poiAdapter);
            lv_near_address.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String name = poiInfos.get(position).name.toString();
                    setResult(poiInfos.get(position).address, poiInfos.get(position).location);
                }
            });
        }
    }

    class PoiAdapter extends BaseAdapter {
        private Context context;
        private List<PoiInfo> pois;
        private LinearLayout linearLayout;

        PoiAdapter(Context context, List<PoiInfo> pois) {
            this.context = context;
            this.pois = pois;
        }

        @Override
        public int getCount() {
            return pois.size();
        }

        @Override
        public Object getItem(int position) {
            return pois.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.locationpois_item,
                        null);
                linearLayout =
                        (LinearLayout) convertView.findViewById(R.id.locationpois_linearlayout);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }


            PoiInfo poiInfo = pois.get(position);
            holder.locationpoi_name.setText(poiInfo.name);
            holder.locationpoi_address.setText(poiInfo.address);
            return convertView;
        }

        class ViewHolder {
            //            ImageView iv_gps;
            TextView locationpoi_name;
            TextView locationpoi_address;

            ViewHolder(View view) {
                locationpoi_name = (TextView) view.findViewById(R.id.locationpois_name);
                locationpoi_address = (TextView) view.findViewById(R.id.locationpois_address);
//                iv_gps = (ImageView) view.findViewById(R.id.iv_gps);
            }
        }
    }

    @Override
    public void onMapStatusChangeStart(MapStatus arg0, int arg1) {
        // TODO Auto-generated method stub

    }

    public class SearchResultAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return searchList.size();
        }

        @Override
        public Object getItem(int position) {
            return searchList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view =
                    LayoutInflater.from(BaiduMapSelectAddressActivity.this).inflate(R.layout.address_item, null);
            TextView textView = (TextView) view.findViewById(R.id.tv_address);
            textView.setText(searchList.get(position).key);
            return view;
        }
    }

    /**
     * @return void
     * @version 1.0
     * @Description: 设置结果
     * @time： 2019/6/11
     */
    public void setResult(String address, LatLng point) {
        double[] pointD = GPSUtil.bd09_To_gps84(point.latitude, point.longitude);
        Intent intent = new Intent();
        intent.putExtra("address", address);
        intent.putExtra("lon", StringUtil.valueOf(pointD[1]));
        intent.putExtra("lat", StringUtil.valueOf(pointD[0]));
        setResult(RESULT_OK, intent);
        finish();
    }

    /**
     * @return void
     * @version 1.0
     * @Description: 设置中心点
     * @time： 2019/6/11
     */

    public void setCenterPoint() {
        double[] pointD = GPSUtil.gps84_To_bd09(28.928918, 105.465244);
        double lat = pointD[0];
        double lon = pointD[1];
        if (lat <= 0 || lon <= 0) {
            ToastUtil.show(mContext, "经纬度数据错误!");
        } else {
            LatLng ll = new LatLng(lat, lon);
            MapStatusUpdate msu = MapStatusUpdateFactory.newLatLngZoom(ll, 16);
            mBaiduMap.animateMapStatus(msu);
        }
    }

    /**
     * @return void
     * @version 1.0
     * @Description: 根据经纬度获取具体地理位置
     * @time： 2019/6/15
     */

    public void getAddressByPoint(LatLng point) {
        String url = "http://api.map.baidu.com/geocoder/v2/?callback=renderReverse&output=json" +
                "&pois=1&latest_admin=1&ak=KZyOL5eB8V8p4fjgex3bzCABeFGlUGkp&mcode=12:59:91:D1:63" +
                ":AB:83:E0:32:55:1E:D9:80:C4:A3:F7:DA:0A:FE:B2;com.upsoft.ckq";
        url = url + "&&location=" + point.latitude + "," + point.longitude;
        BaseHttp.getInstance().postSimpleJson(mContext, url, new JSONObject().toString(),
                new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {

                try {
                    String result = response.get();
                    String address = result.replace("renderReverse&&renderReverse(", "").replace(
                            ")", "");
                    org.json.JSONObject jsonObject = new org.json.JSONObject(address);
                    JSONArray arr = jsonObject.getJSONObject("result").getJSONArray("pois");
                    String addressStr = arr.getJSONObject(0).getString("addr");
                    double lon = arr.getJSONObject(0).getJSONObject("point").getDouble("x");
                    double lat = arr.getJSONObject(0).getJSONObject("point").getDouble("y");
//                    double[] pointD = GPSPointUtil.bd09_To_gps84(lat, lon);
//                    Intent intent = new Intent();
//                    intent.putExtra("address", addressStr);
//                    intent.putExtra("lon", StringUtil.valueOf(pointD[0]));
//                    intent.putExtra("lat", StringUtil.valueOf(pointD[1]));
//                    setResult(RESULT_OK, intent);
//                    finish();
                    searchList.clear();
                    sugAdapter.notifyDataSetChanged();

                    LatLng ll = new LatLng(lat, lon);
                    MapStatusUpdate msu = MapStatusUpdateFactory.newLatLngZoom(ll,
                            mBaiduMap.getMapStatus().zoom);
                    mBaiduMap.animateMapStatus(msu);

                } catch (JSONException e) {
                    e.printStackTrace();
                    ToastUtil.show(mContext, "地址获取失败!");
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                ToastUtil.show(mContext, "地址获取失败!");
            }
        });
    }

    private void getPermission() {
        if (AppUtil.getSDKVersion() > 22) {
            //先判断有没有权限 ，没有就在这里进行权限的申请
            AndPermission.with(this)
                    .requestCode(604)
                    .permission(
                            Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION
                    )
                    // rationale作用是：用户拒绝一次权限，再次申请时先征求用户同意，再打开授权对话框，避免用户勾选不再提示。
                    .rationale(new RationaleListener() {
                        @Override
                        public void showRequestPermissionRationale(int requestCode,
                                                                   Rationale rationale) {
                            // 这里的对话框可以自定义，只要调用rationale.resume()就可以继续申请
                            // 。AndPermission.rationaleDialog(mContext, rationale).show();
                        }
                    }).send();
        }
    }


}
