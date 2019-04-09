package com.example.miagemto.MetroDonnées.DescriptionLigne;

import org.json.JSONException;
import org.json.JSONObject;

public class Ligne {

    private String id,gtfsId,shortName,longName,color,textColor,mode,type;

    public String getId() {
        return id;
    }

    public String getGtfsId() {
        return gtfsId;
    }

    public String getShortName() {
        return shortName;
    }

    public String getLongName() {
        return longName;
    }

    public String getColor() {
        return color;
    }

    public String getTextColor() {
        return textColor;
    }

    public String getMode() {
        return mode;
    }

    public String getType() {
        return type;
    }

    public Ligne(JSONObject jsonObject) throws JSONException {
        id = jsonObject.getString("id");
        gtfsId = jsonObject.getString("gtfsId");
        shortName = jsonObject.getString("shortName");
        longName = jsonObject.getString("longName");
        color = jsonObject.getString("color");
        textColor = jsonObject.getString("textColor");
        mode = jsonObject.getString("mode");
        type = jsonObject.getString("type");
    }

}
