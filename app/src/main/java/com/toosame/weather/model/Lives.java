package com.toosame.weather.model;

import com.toosame.weather.utils.WeatherUtils;

/**
 * Created by 旋风小伙 on 2017/4/27.
 */

public class Lives {
    private String province;

    private String city;

    private String adcode;

    private String weather;

    private String temperature;

    private String winddirection;

    private String windpower;

    private String humidity;

    private String reporttime;

    public void setProvince(String province){
        this.province = province;
    }

    public String getProvince(){
        return this.province;
    }

    public void setCity(String city){
        this.city = city;
    }

    public String getCity(){
        return this.city;
    }

    public void setAdcode(String adcode){
        this.adcode = adcode;
    }

    public String getAdcode(){
        return this.adcode;
    }

    public void setWeather(String weather){
        this.weather = weather;
    }

    public String getWeather(){
        return this.weather;
    }

    public void setTemperature(String temperature){
        this.temperature = temperature;
    }

    public String getTemperature(){
        return this.temperature + "°";
    }

    public void setWinddirection(String winddirection){
        this.winddirection = winddirection;
    }

    public String getWinddirection(){
        return this.winddirection + "风";
    }

    public void setWindpower(String windpower){
        this.windpower = windpower;
    }

    public String getWindpower(){
        if (WeatherUtils.weatherWind.containsKey(windpower)){
            return WeatherUtils.weatherWind.get(windpower);
        }else {
            return "N/A";
        }
    }

    public void setHumidity(String humidity){
        this.humidity = humidity;
    }

    public String getHumidity(){
        return this.humidity;
    }

    public void setReporttime(String reporttime){
        this.reporttime = reporttime;
    }

    public String getReporttime(){
        return this.reporttime;
    }
}
