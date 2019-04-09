package com.example.miagemto.MetroDonnées.HorairesLigne;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Arret {
    private String stopId;
    private List<String> trip = new ArrayList<>();
    private String stopName;
    private double lat;
    private double lon;
    private ParentStation parentStation;

    private List<String> lines = new ArrayList<>();

    public List<String> getLines() {
        return lines;
    }

    public String getStopId() {
        return stopId;
    }

    public void setStopId(String stopId) {
        this.stopId = stopId;
    }

    public String getStopName() {
        return stopName;
    }

    public void setStopName(String stopName) {
        this.stopName = stopName;
    }

    public Double getLat() {
        return lat;
    }

    public List<String> getTrip() {
        return trip;
    }

    public void setTrip(List<String> trip) {
        this.trip = trip;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public ParentStation getParentStation() {
        return parentStation;
    }

    public void setParentStation(ParentStation parentStation) {
        this.parentStation = parentStation;
    }

    public void setLines(List<String> lines) {
        this.lines = lines;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public Arret(JSONObject js) throws JSONException {
        stopId = js.getString("stopId");

        JSONArray trips = js.getJSONArray("trips");

        for (int i = 0; i < trips.length(); i++) {
            trip.add(trips.getString(i));
        }
        stopName = js.getString("stopName");
        lat = js.getDouble("lat");
        lon = js.getDouble("lon");
        parentStation = new ParentStation(js.getJSONObject("parentStation"));
    }
}
