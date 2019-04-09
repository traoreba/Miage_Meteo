package com.example.miagemto.MetroDonnées.HorairesLigne;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Direction {
    private List<Arret> arrets = new ArrayList<>();
    private List<Trip> trips = new ArrayList<>();
    private Integer prevTime;
    private Integer nextTime;

    public void setArrets(List<Arret> arrets) {
        this.arrets = arrets;
    }

    public void setTrips(List<Trip> trips) {
        this.trips = trips;
    }

    public void setPrevTime(Integer prevTime) {
        this.prevTime = prevTime;
    }

    public void setNextTime(Integer nextTime) {
        this.nextTime = nextTime;
    }

    public List<Arret> getArrets() {
        return arrets;
    }

    public List<Trip> getTrips() {
        return trips;
    }

    public Integer getPrevTime() {
        return prevTime;
    }

    public Integer getNextTime() {
        return nextTime;
    }

    public Direction(JSONObject jsonObject){

        try {

            JSONArray arrets_array = jsonObject.getJSONArray("arrets");
            for (int i=0; i<arrets_array.length(); i++) {
                arrets.add(new Arret(arrets_array.getJSONObject(i)));
            }
            JSONArray trips_array = jsonObject.getJSONArray("trips");
            for (int i=0; i<trips_array.length(); i++){
                trips.add( new Trip(trips_array.getJSONObject(i)));
            }
            prevTime = jsonObject.getInt("prevTime");
            nextTime = jsonObject.getInt("nextTime");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
