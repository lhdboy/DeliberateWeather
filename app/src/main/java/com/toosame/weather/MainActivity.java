package com.toosame.weather;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.toosame.weather.model.Casts;
import com.toosame.weather.model.Forecasts;
import com.toosame.weather.model.Lives;
import com.toosame.weather.model.TimeWeather;
import com.toosame.weather.model.Weather;
import com.toosame.weather.utils.HttpClient;
import com.toosame.weather.utils.IHttpCallback;
import com.toosame.weather.utils.WeatherUtils;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private String cityName;
    private String adcode;
    private LinearLayout relativeLayout;
    private LinearLayout linearLayout;
    private TextView headerLabel;
    private TextView temperatureLabel;
    private TextView posttimeLabel;
    private TextView windLabel;
    private TextView windDirectionLabel;
    private TextView weatherLabel;
    private ImageView weatherImage;
    private Button switchBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(Build.VERSION.SDK_INT>=21)
        {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        setContentView(R.layout.activity_main);

        init();
    }

    private void init(){
        initView();
        initData();
    }

    private void initView(){
        relativeLayout = (LinearLayout) findViewById(R.id.activity_main);
        linearLayout = (LinearLayout)findViewById(R.id.main_weather_info_layout);
        headerLabel = (TextView)findViewById(R.id.main_header_label);
        weatherImage = (ImageView)findViewById(R.id.main_weather_image);
        weatherLabel = (TextView)findViewById(R.id.main_weather_info);
        windDirectionLabel = (TextView)findViewById(R.id.main_weather_direction);
        windLabel = (TextView)findViewById(R.id.main_weather_wind);
        posttimeLabel = (TextView)findViewById(R.id.main_weather_posttime);
        temperatureLabel = (TextView)findViewById(R.id.main_wearher_temperature);
        switchBtn = (Button)findViewById(R.id.change_btn);

        switchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,SelectCityActivity.class);
                startActivity(intent);
            }
        });

        Calendar now = Calendar.getInstance();
        if(now.get(Calendar.HOUR_OF_DAY) > 18 || now.get(Calendar.HOUR_OF_DAY) < 7){
            relativeLayout.setBackgroundResource(R.drawable.dark);
        }else {
            relativeLayout.setBackgroundResource(R.drawable.light);
        }
    }

    private void initData(){
        Intent intent = getIntent();
        cityName = intent.getStringExtra(SplashActivity.CITYNAME);
        adcode = intent.getStringExtra(SplashActivity.ADCODE);
        headerLabel.setText(cityName);

        HttpClient.query(adcode, HttpClient.WEATHER_TYPE_BASE, Weather.class, new IHttpCallback() {
            @Override
            public <T> void onSuccess(T result, boolean isSuccess) {
                if(isSuccess){
                     Weather weather = (Weather)result;
                    if (weather.getInfo().equals("OK") && weather.getCount().equals("1")){
                        final Lives info = weather.getLives().get(0);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                temperatureLabel.setText(info.getTemperature());
                                posttimeLabel.setText(format(info.getReporttime()) + "更新");
                                windDirectionLabel.setText(info.getWinddirection());
                                windLabel.setText("湿度" + info.getHumidity() + "%");

                                if (WeatherUtils.WeatherKV.containsKey(info.getWeather())){
                                    weatherLabel.setText(info.getWeather());
                                    weatherImage.setImageResource(WeatherUtils.WeatherKV.get(info.getWeather()));
                                }else {
                                    temperatureLabel.setText("N/A");
                                }
                            }
                        });
                    }else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                temperatureLabel.setText("服务不可用");
                                Toast.makeText(MainActivity.this, "服务不可用", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            temperatureLabel.setText("无法提供天气信息");
                            Toast.makeText(MainActivity.this, "无法提供天气信息", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        HttpClient.query(adcode, HttpClient.WEATHER_TYPE_ALL, TimeWeather.class, new IHttpCallback() {
            @Override
            public <T> void onSuccess(T result, boolean isSuccess) {
                if (isSuccess){
                    final TimeWeather timeWeather = (TimeWeather)result;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (timeWeather.getInfo().equals("OK") && timeWeather.getCount().equals("1")){
                                for (Forecasts forecasts : timeWeather.getForecasts()){
                                    for (Casts casts : forecasts.getCasts()){
                                        View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.weather_itme,linearLayout,false);
                                        TextView date = (TextView)view.findViewById(R.id.item_date);
                                        TextView max = (TextView)view.findViewById(R.id.item_max);
                                        TextView min = (TextView)view.findViewById(R.id.itme_min);
                                        TextView currentWeather = (TextView)view.findViewById(R.id.item_weather);
                                        TextView week = (TextView)view.findViewById(R.id.item_week);

                                        date.setText(getDay(casts.getDate()));
                                        max.setText(casts.getDaytemp() + "°");
                                        min.setText(casts.getNighttemp() + "°");
                                        currentWeather.setText(casts.getDayweather());
                                        week.setText(getWeek(casts.getWeek()));

                                        linearLayout.addView(view);
                                    }
                                }
                            }
                        }
                    });
                }
            }
        });
    }

    private String format(String posttime){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date = dateFormat.parse(posttime);

            return new SimpleDateFormat("HH:MM").format(date);
        } catch (ParseException e) {
            return "刚刚更新";
        }
    }

    private String getDay(String date){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date toDate = dateFormat.parse(date);

            return new SimpleDateFormat("dd").format(toDate);
        } catch (ParseException e) {
            return "N/A";
        }
    }

    private String getWeek(String week){
        switch (week)
        {
            case "1":
                return "星期一";
            case "2":
                return "星期二";
            case "3":
                return "星期三";
            case "4":
                return "星期四";
            case "5":
                return "星期五";
            case "6":
                return "星期六";
            default:
                return "星期日";
        }
    }

    //获取手机状态栏高
//    public static int getStatusBarHeight(Context context)
//    {
//        int result = 0;
//        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
//        if (resourceId > 0)
//        {
//            result = context.getResources().getDimensionPixelSize(resourceId);
//        }
//        return result;
//    }
}
