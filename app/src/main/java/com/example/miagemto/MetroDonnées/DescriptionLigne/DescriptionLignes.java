package com.example.miagemto.MetroDonnées.DescriptionLigne;

import org.json.JSONObject;

import java.util.List;

public class DescriptionLignes {
    private String description;
    private String type;
    private List<Feature> features = null;

    public DescriptionLignes(JSONObject tram_a) {
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Feature> getFeatures() {
        return features;
    }

    public void setFeatures(List<Feature> features) {
        this.features = features;
    }
}
