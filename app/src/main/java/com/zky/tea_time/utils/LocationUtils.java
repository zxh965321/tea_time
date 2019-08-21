package com.zky.tea_time.utils;

import android.content.Context;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;

/**
 * 1.在高德地图官网上申请appkey
 * 2.在manifest中添加APSService和appkey的标签，以及添加对应的权限
 * 3.在build.gradle中添加implementation 'com.amap.api:location:4.1.0'
 * 4.在build.gradle中添加测试和正式环境的
 */
public class LocationUtils {

    /**
     * 高德地图获取经纬度(高精度模式)
     * 高精度定位模式：会同时使用网络定位和GPS定位，优先返回最高精度的定位结果，以及对应的地址描述信息。
     * 注意： 外部控制定位权限，如果没有打开GPS可能获取定位不成功
     *
     * @see "https://lbs.amap.com/api/android-location-sdk/guide/android-location/getlocation"
     */
    public static void getGdAddressInfo(Context context, CallBack callBack) {
        //声明AMapLocationClient类对象
        AMapLocationClient mLocationClient = new AMapLocationClient( context );
        //声明定位回调监听器
        AMapLocationListener mapLocationListener = new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
                if (aMapLocation != null) {
                    if (aMapLocation.getErrorCode() == 0) {
                        int locationType = aMapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
                        double latitude = aMapLocation.getLatitude();//获取纬度
                        double longitude = aMapLocation.getLongitude();//获取经度
                        String address = aMapLocation.getAddress();//地址，如果option中设置isNeedAddress为false，则没有此结果，网络定位结果中会有地址信息，GPS定位不返回地址信息。
                        String county = aMapLocation.getCountry();//国家信息
                        String province = aMapLocation.getProvince();//省信息
                        String city = aMapLocation.getCity();//城市信息
                        String district = aMapLocation.getDistrict();//城区信息
                        String street = aMapLocation.getStreet();//街道信息
                        String streetNum = aMapLocation.getStreetNum();//街道门牌号信息
                        String cityCode = aMapLocation.getCityCode();//城市编码
                        String adCode = aMapLocation.getAdCode();//地区编码
                        String aoiName = aMapLocation.getAoiName();//获取当前定位点的AOI信息
                        String buildingId = aMapLocation.getBuildingId();//获取当前室内定位的建筑物Id
                        String floor = aMapLocation.getFloor();//获取当前室内定位的楼层
                        int gpsState = aMapLocation.getGpsAccuracyStatus();//获取GPS的当前状态

                        GDLocationBean gdLocationBean = new GDLocationBean();
                        gdLocationBean.setLatitude( latitude );
                        gdLocationBean.setLongitude( longitude );
                        gdLocationBean.setAddress( address );
                        gdLocationBean.setSuceess( true );
                        callBack.location( gdLocationBean );

                        if (mLocationClient!=null){
                            mLocationClient.stopLocation();
                            mLocationClient.onDestroy();
                        }

                    }else {

                        GDLocationBean gdLocationBean = new GDLocationBean();
                        gdLocationBean.setLatitude( 0 );
                        gdLocationBean.setLongitude( 0 );
                        gdLocationBean.setAddress( "" );
                        gdLocationBean.setSuceess( false );
                        callBack.location( gdLocationBean );

                        if (mLocationClient!=null){
                            mLocationClient.stopLocation();
                            mLocationClient.onDestroy();
                        }

                    }
                }
            }
        };

        mLocationClient.setLocationListener( mapLocationListener );
        //初始化AMapLocationClientOption对象
        AMapLocationClientOption mLocationOption = new AMapLocationClientOption();
        //设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
        mLocationOption.setLocationMode( AMapLocationClientOption.AMapLocationMode.Hight_Accuracy );
        //获取最近3s内精度最高的一次定位结果：
        mLocationOption.setOnceLocationLatest( true );
        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress( true );
        mLocationClient.setLocationOption( mLocationOption );
        //启动定位
        mLocationClient.startLocation();
    }

    public interface CallBack{
        void location(GDLocationBean gdLocationBean);
    }

    public static class GDLocationBean {

        private boolean suceess = false;
        private int locationType;//获取当前定位结果来源，如网络定位结果，详见定位类型表
        private double latitude;//获取纬度
        private double longitude;//获取经度
        private String address;//地址，如果option中设置isNeedAddress为false，则没有此结果，网络定位结果中会有地址信息，GPS定位不返回地址信息。
        private String county;//国家信息
        private String province;//省信息
        private String city;//城市信息
        private String district;//城区信息
        private String street;//街道信息
        private String streetNum;//街道门牌号信息
        private String cityCode;//城市编码
        private String adCode;//地区编码
        private String aoiName;//获取当前定位点的AOI信息
        private String buildingId;//获取当前室内定位的建筑物Id
        private String floor;//获取当前室内定位的楼层
        private int gpsState;//获取GPS的当前状态

        public boolean isSuceess() {
            return suceess;
        }

        public void setSuceess(boolean suceess) {
            this.suceess = suceess;
        }

        public int getLocationType() {
            return locationType;
        }

        public void setLocationType(int locationType) {
            this.locationType = locationType;
        }

        public double getLatitude() {
            return latitude;
        }

        public void setLatitude(double latitude) {
            this.latitude = latitude;
        }

        public double getLongitude() {
            return longitude;
        }

        public void setLongitude(double longitude) {
            this.longitude = longitude;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getCounty() {
            return county;
        }

        public void setCounty(String county) {
            this.county = county;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getDistrict() {
            return district;
        }

        public void setDistrict(String district) {
            this.district = district;
        }

        public String getStreet() {
            return street;
        }

        public void setStreet(String street) {
            this.street = street;
        }

        public String getStreetNum() {
            return streetNum;
        }

        public void setStreetNum(String streetNum) {
            this.streetNum = streetNum;
        }

        public String getCityCode() {
            return cityCode;
        }

        public void setCityCode(String cityCode) {
            this.cityCode = cityCode;
        }

        public String getAdCode() {
            return adCode;
        }

        public void setAdCode(String adCode) {
            this.adCode = adCode;
        }

        public String getAoiName() {
            return aoiName;
        }

        public void setAoiName(String aoiName) {
            this.aoiName = aoiName;
        }

        public String getBuildingId() {
            return buildingId;
        }

        public void setBuildingId(String buildingId) {
            this.buildingId = buildingId;
        }

        public String getFloor() {
            return floor;
        }

        public void setFloor(String floor) {
            this.floor = floor;
        }

        public int getGpsState() {
            return gpsState;
        }

        public void setGpsState(int gpsState) {
            this.gpsState = gpsState;
        }
    }

}
