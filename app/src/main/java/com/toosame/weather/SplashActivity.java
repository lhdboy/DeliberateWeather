package com.toosame.weather;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;

public class SplashActivity extends Activity {

    public static final String ADCODE = "adcode";
    public static final String CITYNAME = "cityname";

    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;

    private SharedPreferences userSettings;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Log.d("TEST", "onCreate: " + System.currentTimeMillis());

        userSettings = getSharedPreferences("setting", MODE_PRIVATE);
        final String adcode = userSettings.getString(ADCODE,"");
        final String cityName = userSettings.getString(CITYNAME,"");

        if(!TextUtils.isEmpty(adcode)){
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(SplashActivity.this,MainActivity.class);
                    intent.putExtra(ADCODE,adcode);
                    intent.putExtra(CITYNAME,cityName);
                    startActivity(intent);
                    finish();
                }
            },1000);
            return;
        }

        //初始化定位
        mLocationClient = new AMapLocationClient(getApplicationContext());
        mLocationOption = new AMapLocationClientOption();
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);

        //获取一次定位结果：
        // 该方法默认为false。
        mLocationOption.setOnceLocation(true);

        //获取最近3s内精度最高的一次定位结果：
        // 设置setOnceLocationLatest(boolean b)接口为true，启动定位时SDK会返回最近3s内精度最高的一次定位结果。
        // 如果设置其为true，setOnceLocation(boolean b)接口也会被设置为true，反之不会，默认为false。
        mLocationOption.setOnceLocationLatest(true);

        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);


        //设置是否强制刷新WIFI提高精度，默认为true，强制刷新。
        mLocationOption.setWifiActiveScan(false);

        //设置是否允许模拟位置,默认为false，不允许模拟位置
        mLocationOption.setMockEnable(false);

        //单位是毫秒，默认30000毫秒，建议超时时间不要低于8000毫秒。
        mLocationOption.setHttpTimeOut(20000);

        //关闭缓存机制
        mLocationOption.setLocationCacheEnable(true);

        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        //启动定位
        mLocationClient.startLocation();
        //设置定位回调监听
        mLocationClient.setLocationListener(new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
                if (aMapLocation != null) {
                    if (aMapLocation.getErrorCode() == 0) {
                        //定位成功
                        String cityname = aMapLocation.getCity() + " "
                                + aMapLocation.getDistrict();

                        SharedPreferences.Editor editor = userSettings.edit();
                        editor.putString(ADCODE, aMapLocation.getAdCode());
                        editor.putString(CITYNAME, cityname);
                        editor.apply();

                        Intent intent = new Intent(SplashActivity.this,MainActivity.class);
                        intent.putExtra(ADCODE,aMapLocation.getAdCode());
                        intent.putExtra(CITYNAME,cityname);

                        // 停止定位后，本地定位服务并不会被销毁
                        mLocationClient.stopLocation();

                        //销毁定位客户端，同时销毁本地定位服务。
                        mLocationClient.onDestroy();
                        startActivity(intent);
                        finish();
                    }else {
                        //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                        Intent intent = new Intent(SplashActivity.this,SelectCityActivity.class);
                        startActivity(intent);

//                        Log.e("AmapError","location Error, ErrCode:"
//                                + aMapLocation.getErrorCode() + ", errInfo:"
//                                + aMapLocation.getErrorInfo());
                        Toast.makeText(SplashActivity.this, "我们找不到你，无法提供天气信息；错误代码:" + aMapLocation.getErrorCode(), Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }else {
                    Intent intent = new Intent(SplashActivity.this,SelectCityActivity.class);
                    startActivity(intent);
                    Toast.makeText(SplashActivity.this, "请给我位置权限，或者手动输入你所在城市:", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }
}
