package com.toosame.weather.model;

import java.util.List;

/**
 * Created by 旋风小伙 on 2017年4月30日 0030.
 */

public class DistrictsRoot {
    private List<Districts> districts ;

    public void setDistricts(List<Districts> districts){
        this.districts = districts;
    }

    public List<Districts> getDistricts(){
        return this.districts;
    }
}
