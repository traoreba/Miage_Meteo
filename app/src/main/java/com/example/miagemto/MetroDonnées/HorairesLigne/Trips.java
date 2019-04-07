package com.example.miagemto.MetroDonnées.HorairesLigne;

import org.json.JSONException;
import org.json.JSONObject;

public class Trips {
    private String tripId;
    private String pickupType;


    public Trips (JSONObject js) throws JSONException {
        tripId = js.getString("tripId");
        pickupType = js.getString("pickupType");
    }

    public String getTripId() {
        return tripId;
    }

    public void setTripId(String tripId) {
        this.tripId = tripId;
    }

    public String getPickupType() {
        return pickupType;
    }

    public void setPickupType(String pickupType) {
        this.pickupType = pickupType;
    }
}
