package com.toosame.weather.model;

import java.util.List;

/**
 * Created by 旋风小伙 on 2017/4/27.
 */

public class Forecasts {
    private String city;

    private String adcode;

    private String province;

    private String reporttime;

    private List<Casts> casts ;

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

    public void setProvince(String province){
        this.province = province;
    }

    public String getProvince(){
        return this.province;
    }

    public void setReporttime(String reporttime){
        this.reporttime = reporttime;
    }

    public String getReporttime(){
        return this.reporttime;
    }

    public void setCasts(List<Casts> casts){
        this.casts = casts;
    }

    public List<Casts> getCasts(){
        return this.casts;
    }
}
