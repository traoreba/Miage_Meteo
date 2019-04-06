package com.example.miagemto.MetroDonnées.HorairesLigne;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HoraireLigneParArret {
    private String description;
    private Pattern pattern;
    private List<Time> times = new ArrayList<>();

    public HoraireLigneParArret(JSONObject js) {
        try {
            description = js.getString("id");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Pattern getPattern() {
        return pattern;
    }

    public void setPattern(Pattern pattern) {
        this.pattern = pattern;
    }

    public List<Time> getTimes() {
        return times;
    }

    public void setTimes(List<Time> times) {
        this.times = times;
    }
}
