package com.naf.groupbuying.activity.map;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.cloud.CloudListener;
import com.baidu.mapapi.cloud.CloudManager;
import com.baidu.mapapi.cloud.CloudPoiInfo;
import com.baidu.mapapi.cloud.CloudRgcResult;
import com.baidu.mapapi.cloud.CloudSearchResult;
import com.baidu.mapapi.cloud.DetailSearchResult;
import com.baidu.mapapi.cloud.NearbySearchInfo;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.LatLngBounds;
import com.naf.groupbuying.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class LocationActivity extends AppCompatActivity implements CloudListener {


    // 定位相关
    LocationClient mLocClient;

    public MyLocationListenner myListener = new MyLocationListenner();
    @BindView(R.id.btn_test_map_jing)
    Button btnJing;
    @BindView(R.id.bmapView)
    MapView bmapView;
    private MapView mMapView;
    private BaiduMap mBaiduMap;
    private boolean isFirstLoc = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        ButterKnife.bind(this);
        CloudManager.getInstance().init(this);
        initMap();


    }

    private void initMap() {
        // 地图初始化
        mMapView = (MapView) findViewById(R.id.bmapView);
        mBaiduMap = mMapView.getMap();
        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
        // 定位初始化
        mLocClient = new LocationClient(this);
        mLocClient.registerLocationListener(myListener);
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(1000);
        mLocClient.setLocOption(option);
        mLocClient.start();
    }

    @Override
    public void onGetSearchResult(CloudSearchResult cloudSearchResult, int i) {
        if(cloudSearchResult!=null&&cloudSearchResult.poiList!=null&&cloudSearchResult.poiList.size()>0){
            mBaiduMap.clear();
            BitmapDescriptor bd= BitmapDescriptorFactory.fromResource(R.mipmap.icon_openmap_mark);
            LatLngBounds.Builder builder=new LatLngBounds.Builder();
            LatLng latLng;
            for(CloudPoiInfo info:cloudSearchResult.poiList){
                latLng=new LatLng(info.latitude,info.longitude);
                MarkerOptions options=new MarkerOptions().icon(bd).position(latLng);
                mBaiduMap.addOverlay(options);
                builder.include(latLng);


            }
        }
    }

    @Override
    public void onGetDetailSearchResult(DetailSearchResult detailSearchResult, int i) {

    }

    @Override
    public void onGetCloudRgcResult(CloudRgcResult cloudRgcResult, int i) {

    }

    @OnClick(R.id.btn_test_map_jing)
    public void onClick() {

        NearbySearchInfo  info=new NearbySearchInfo();
        info.ak="t3gQuDF2MPRjMDRbBOCdVo9vjnGhWfGp";
        info.geoTableId=158556;
        info.radius=100000;
        info.location="114.027688,22.660899";
        CloudManager.getInstance().nearbySearch(info);

    }


    /***
     * 实现定位的sdk监听函数
     */
    public class MyLocationListenner implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            if (mMapView == null || location == null) {
                return;
            }
            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(100).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
            mBaiduMap.setMyLocationData(locData);
            if (isFirstLoc) {
                isFirstLoc = false;
                LatLng ll = new LatLng(location.getLatitude(),
                        location.getLongitude());
                MapStatus.Builder builder = new MapStatus.Builder();
                builder.target(ll).zoom(18.0f);
                mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
            }
        }
    }


    @Override
    protected void onPause() {
        mMapView.onPause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        mMapView.onResume();
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        // 退出时销毁定位
        mLocClient.stop();
        // 关闭定位图层
        mBaiduMap.setMyLocationEnabled(false);
        mMapView.onDestroy();
        mMapView = null;
        super.onDestroy();
    }

}
