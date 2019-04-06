package com.example.miagemto.MetroDonnées.HorairesLigne;

import org.json.JSONObject;

public class Pattern {

    private String id;
    private String desc;
    private String dir;
    private String shortDesc;

    public Pattern (JSONObject js) {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    public String getShortDesc() {
        return shortDesc;
    }

    public void setShortDesc(String shortDesc) {
        this.shortDesc = shortDesc;
    }

}