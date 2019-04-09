package com.example.miagemto.MetroDonnées.HorairesLigne;

import org.json.JSONObject;

public class ParentStation {

    private String id;
    private String code;
    private String city;
    private String name;
    private Double lat;
    private Double lon;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public ParentStation(JSONObject jsonObject){
        try {
            id= jsonObject.getString("id");
            code= jsonObject.getString("code");
            city = jsonObject.getString("city");
            name =jsonObject.getString("name");
            lat = jsonObject.getDouble("lat");
            lon = jsonObject.getDouble("lon");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
