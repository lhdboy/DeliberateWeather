package com.toosame.weather;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.toosame.weather.model.City;
import com.toosame.weather.model.DisCity;
import com.toosame.weather.model.Districts;
import com.toosame.weather.model.DistrictsRoot;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SelectCityActivity extends AppCompatActivity {

    AutoCompleteTextView autoCompleteTextView;
    private List<Integer> cities;
    private List<String> cityKeyVal;
    private List<String> cityVal;
    private Gson jsonConverter = new Gson();
    private Button doneBtn;

    private String selectName;
    private String selectCode;

    private SharedPreferences userSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(Build.VERSION.SDK_INT>=21)
        {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        setContentView(R.layout.activity_select_city);

        userSettings = getSharedPreferences("setting", MODE_PRIVATE);

        cities = new ArrayList<>();
        cities.add(R.raw.anhui);
        cities.add(R.raw.aomeng);
        cities.add(R.raw.beijin);
        cities.add(R.raw.chongqing);
        cities.add(R.raw.fujiang);
        cities.add(R.raw.gangsu);
        cities.add(R.raw.guangdong);
        cities.add(R.raw.guangxi);
        cities.add(R.raw.guizhou);
        cities.add(R.raw.hainang);
        cities.add(R.raw.hebei);
        cities.add(R.raw.heilongjiang);
        cities.add(R.raw.henang);
        cities.add(R.raw.hongkong);
        cities.add(R.raw.hubei);
        cities.add(R.raw.hunang);
        cities.add(R.raw.jiangsu);
        cities.add(R.raw.jiangxi);
        cities.add(R.raw.jiling);
        cities.add(R.raw.liaoning);
        cities.add(R.raw.neimenggu);
        cities.add(R.raw.ningxia);
        cities.add(R.raw.qinghai);
        cities.add(R.raw.shangdong);
        cities.add(R.raw.shanghai);
        cities.add(R.raw.shangxi);
        cities.add(R.raw.shanxi);
        cities.add(R.raw.sichuang);
        cities.add(R.raw.tianjin);
        cities.add(R.raw.xinjiang);
        cities.add(R.raw.xizan);
        cities.add(R.raw.yunnang);
        cities.add(R.raw.zhejiang);

        doneBtn = (Button)findViewById(R.id.done_btn);
        autoCompleteTextView = (AutoCompleteTextView)findViewById(R.id.city_textview);

        cityKeyVal = new ArrayList<>();
        cityVal = new ArrayList<>();

        findCity();
        ArrayAdapter<String> autoTextString = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,cityKeyVal);
        autoCompleteTextView.setAdapter(autoTextString);

        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Object obj = adapterView.getItemAtPosition(i);
                int index = cityKeyVal.indexOf(obj);
                selectCode = cityVal.get(index);
                selectName = obj.toString();
            }
        });

        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(selectCode) && !TextUtils.isEmpty(selectName)){
                    SharedPreferences.Editor editor = userSettings.edit();
                    editor.putString(SplashActivity.ADCODE, selectCode);
                    editor.putString(SplashActivity.CITYNAME, selectName);
                    editor.apply();

                    Intent intent = new Intent(SelectCityActivity.this,MainActivity.class);
                    intent.putExtra(SplashActivity.ADCODE,selectCode);
                    intent.putExtra(SplashActivity.CITYNAME,selectName);

                    startActivity(intent);
                    finish();
                }else {
                    Toast.makeText(SelectCityActivity.this, "请输入你城市的名字，然后在下拉框选择支持的城市", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void findCity(){
        for (int i : cities){
            StringBuilder stringBuilder = new StringBuilder();
            InputStream inputStream = getResources().openRawResource(i);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            try {
                String line = "";
                while ((line = reader.readLine()) != null){
                    stringBuilder.append(line);
                }

                DistrictsRoot dis = jsonConverter.fromJson(stringBuilder.toString(),DistrictsRoot.class);

                if (dis.getDistricts().size() > 0){
                    List<Districts> _dis = dis.getDistricts();
                    if (_dis.size() > 0){
                        Districts currentDis = _dis.get(0);

                        DisCity disCity = new DisCity();
                        disCity.setAdcode(currentDis.getAdcode());
                        disCity.setName(currentDis.getName());
                        disCity.setDistricts(currentDis.getDistricts());

                        whileCity(currentDis.getDistricts(),disCity);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void whileCity(List<DisCity> districtses, DisCity parenCity){
        for (DisCity c : districtses){
            if (c.getDistricts().size() > 0){
                whileCity(c.getDistricts(),c);
            }else {
                cityKeyVal.add(parenCity.getName() + " " + c.getName());
                cityVal.add(c.getAdcode());
            }
        }
    }
}
