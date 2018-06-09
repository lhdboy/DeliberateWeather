package com.toosame.weather.model;

import java.util.List;

/**
 * Created by 旋风小伙 on 2017/4/27.
 */

public class Weather {
    private String status;

    private String count;

    private String info;

    private String infocode;

    private List<Lives> lives ;

    public void setStatus(String status){
        this.status = status;
    }

    public String getStatus(){
        return this.status;
    }

    public void setCount(String count){
        this.count = count;
    }

    public String getCount(){
        return this.count;
    }

    public void setInfo(String info){
        this.info = info;
    }

    public String getInfo(){
        return this.info;
    }

    public void setInfocode(String infocode){
        this.infocode = infocode;
    }

    public String getInfocode(){
        return this.infocode;
    }

    public void setLives(List<Lives> lives){
        this.lives = lives;
    }

    public List<Lives> getLives(){
        return this.lives;
    }
}
