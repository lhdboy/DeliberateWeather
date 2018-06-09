package com.toosame.weather.model;

/**
 * Created by 旋风小伙 on 2017年4月30日 0030.
 */

public class City {

    private String name;

    private int id;

    public City(String name,int id){
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
