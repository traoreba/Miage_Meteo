package com.example.miagemto.MetroDonnées;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class Horaires {

    private String id;
     private String name;
    private Double lon;
    private Double lat;
    private String zone;
    private List<String> lines = null;

    public Horaires(JSONObject js) {
        try {
            name = js.getString("name");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName(){
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public List<String> getLines() {
        return lines;
    }

    public void setLines(List<String> lines) {
        this.lines = lines;
    }

}
