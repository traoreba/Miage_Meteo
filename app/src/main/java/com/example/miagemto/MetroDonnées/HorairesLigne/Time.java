package com.example.miagemto.MetroDonnées.HorairesLigne;

public class Time {

    private String stopId;
    private String stopName;
    private Integer scheduledArrival;
    private Integer scheduledDeparture;
    private Integer realtimeArrival;
    private Integer realtimeDeparture;
    private Integer arrivalDelay;
    private Integer departureDelay;
    private Boolean timepoint;
    private Boolean realtime;
    private Integer serviceDay;
    private String tripId;

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

    public Integer getScheduledArrival() {
        return scheduledArrival;
    }

    public void setScheduledArrival(Integer scheduledArrival) {
        this.scheduledArrival = scheduledArrival;
    }

    public Integer getScheduledDeparture() {
        return scheduledDeparture;
    }

    public void setScheduledDeparture(Integer scheduledDeparture) {
        this.scheduledDeparture = scheduledDeparture;
    }

    public Integer getRealtimeArrival() {
        return realtimeArrival;
    }

    public void setRealtimeArrival(Integer realtimeArrival) {
        this.realtimeArrival = realtimeArrival;
    }

    public Integer getRealtimeDeparture() {
        return realtimeDeparture;
    }

    public void setRealtimeDeparture(Integer realtimeDeparture) {
        this.realtimeDeparture = realtimeDeparture;
    }

    public Integer getArrivalDelay() {
        return arrivalDelay;
    }

    public void setArrivalDelay(Integer arrivalDelay) {
        this.arrivalDelay = arrivalDelay;
    }

    public Integer getDepartureDelay() {
        return departureDelay;
    }

    public void setDepartureDelay(Integer departureDelay) {
        this.departureDelay = departureDelay;
    }

    public Boolean getTimepoint() {
        return timepoint;
    }

    public void setTimepoint(Boolean timepoint) {
        this.timepoint = timepoint;
    }

    public Boolean getRealtime() {
        return realtime;
    }

    public void setRealtime(Boolean realtime) {
        this.realtime = realtime;
    }

    public Integer getServiceDay() {
        return serviceDay;
    }

    public void setServiceDay(Integer serviceDay) {
        this.serviceDay = serviceDay;
    }

    public String getTripId() {
        return tripId;
    }

    public void setTripId(String tripId) {
        this.tripId = tripId;
    }

}
