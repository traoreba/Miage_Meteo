package com.example.miagemto.MetroDonnées;

import com.example.miagemto.MetroDonnées.DescriptionLigne.DescriptionLignes;
import com.example.miagemto.MetroDonnées.HorairesLigne.HoraireLigneParArret;
import com.example.miagemto.MetroDonnées.LignesProximite.LignesProximite;

import org.json.JSONException;
import org.json.JSONObject;

public class DonnesMetro {

    DescriptionLignes descLignes;
    HoraireLigneParArret horaireParArret;
    LignesProximite ligneProximo;

    public DonnesMetro(JSONObject js) {
        try {
            descLignes = new DescriptionLignes(new JSONObject(js.getString("tram A")));
            horaireParArret = new HoraireLigneParArret(new JSONObject(js.getString("tram A")));
            ligneProximo = new LignesProximite(new JSONObject((js.getString("tram A"))));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
