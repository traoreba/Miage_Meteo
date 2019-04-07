package com.example.miagemto.MetroDonnées.HorairesLigne;

import java.util.List;

public class Direction {
    private List<Arret> arrets = null;
    private List<Trips> trips = null;
    private Integer prevTime;
    private Integer nextTime;

    public List<Arret> getArrets() {
        return arrets;
    }

    public void setArrets(List<Arret> arrets) {
        this.arrets = arrets;
    }

    public List<Trips> getTrips() {
        return trips;
    }

    public void setTrips(List<Trips> trips) {
        this.trips = trips;
    }

    public Integer getPrevTime() {
        return prevTime;
    }

    public void setPrevTime(Integer prevTime) {
        this.prevTime = prevTime;
    }

    public Integer getNextTime() {
        return nextTime;
    }

    public void setNextTime(Integer nextTime) {
        this.nextTime = nextTime;
    }



}
