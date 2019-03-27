package com.example.miagemto.MetroDonnées.LignesProximite;

import org.json.JSONException;
import org.json.JSONObject;

public class LignesProximite {
    private String id;
    private String name;
    private String lon;
    private String lat;
    private String lines;

    public LignesProximite(JSONObject js) {
        try {
            id = js.getString("id");
            name = js.getString("name");
            lon = js.getString("longitude");
            lat = js.getString("latitude");
            lines = js.getString("lines");
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLines() {
        return lines;
    }

    public void setLines(String lines) {
        this.lines = lines;
    }
}
