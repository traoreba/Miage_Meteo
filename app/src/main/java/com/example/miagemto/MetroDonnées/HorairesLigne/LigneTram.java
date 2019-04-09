package com.example.miagemto.MetroDonnées.HorairesLigne;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class LigneTram {

    Direction direction0 ;
    Direction direction1;


    public LigneTram(JSONObject jsonObject){
        try {
            direction0 = new Direction(jsonObject.getJSONObject("0"));
            direction1 = new Direction(jsonObject.getJSONObject("1"));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public Direction getDirection0() {
        return direction0;
    }

    public void setDirection0(Direction direction0) {
        this.direction0 = direction0;
    }

    public Direction getDirection1() {
        return direction1;
    }

    public void setDirection1(Direction direction1) {
        this.direction1 = direction1;
    }
}
