package com.toosame.weather.model;

import java.util.List;

/**
 * Created by 旋风小伙 on 2017年4月30日 0030.
 */

public class Districts {
    private List<String> citycode;

    private String adcode;

    private String name;

    private String center;

    private String level;

    private List<DisCity> districts ;

    public void setCitycode(List<String> citycode){
        this.citycode = citycode;
    }

    public List<String> getCitycode(){
        return this.citycode;
    }

    public void setAdcode(String adcode){
        this.adcode = adcode;
    }

    public String getAdcode(){
        return this.adcode;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

    public void setCenter(String center){
        this.center = center;
    }

    public String getCenter(){
        return this.center;
    }

    public void setLevel(String level){
        this.level = level;
    }

    public String getLevel(){
        return this.level;
    }

    public void setDistricts(List<DisCity> districts){
        this.districts = districts;
    }

    public List<DisCity> getDistricts(){
        return this.districts;
    }
}
