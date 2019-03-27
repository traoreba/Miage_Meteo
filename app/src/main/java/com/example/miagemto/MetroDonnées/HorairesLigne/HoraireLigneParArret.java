package com.example.miagemto.MetroDonnées.HorairesLigne;

import org.json.JSONObject;

import java.util.List;

public class HoraireLigneParArret {
    private String description;
    private Pattern pattern;
    private List<Time> times = null;

    public HoraireLigneParArret(JSONObject tram_a) {
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
