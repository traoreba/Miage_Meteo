package com.example.miagemto.MetroDonnées.HorairesLigne;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Arret {
    private String stopId;
    private JSONArray trips;
    private List<Trips> trip;
    private String stopName;
    private double lat;
    private double lon;
    private String zone;
    private JSONArray jsonLines;

    private List<String> lines = new ArrayList<>();

    public List<String> getLines() {
        return lines;
    }

    public String getZone(){
        return zone;
    }

    public void setZone (String zone){
        this.zone = zone;
    }

    public String getStopId() {
        return stopId;
    }

    public void setStopId(String stopId) {
        this.stopId = stopId;
    }

    public JSONArray getTrips() {
        return trips;
    }

    public void setTrips(JSONArray trips) {
        this.trips = trips;
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
        for (int i = 0; i < trips.length(); i++) {
            trip.add((Trips) trips.get(i));
        }
        trips = js.getJSONArray("trips");
        stopName = js.getString("stopName");
        lat = js.getDouble("lat");
        lon = js.getDouble("lon");
        zone = js.getString("zone");

        for (int i = 0; i < jsonLines.length(); i++) {
            lines.add(jsonLines.getString(i));
        }
    }
}
