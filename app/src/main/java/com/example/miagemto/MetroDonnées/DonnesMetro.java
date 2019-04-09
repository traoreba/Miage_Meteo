package com.example.miagemto.MetroDonnées;

import com.example.miagemto.MetroDonnées.DescriptionLigne.DescriptionLignes;
import com.example.miagemto.MetroDonnées.HorairesLigne.HoraireLigneParArret;
import com.example.miagemto.MetroDonnées.LignesProximite.LignesProximite;

import org.json.JSONException;
import org.json.JSONObject;

public class DonnesMetro {

    private String metro_JsonString;

    /**
     * Traitement des données LignesProximite
     */
    private LignesProximite ligneProximite;

    public LignesProximite getLignesProximite(){return this.ligneProximite;}
    public void setLignesProximite (LignesProximite ligneProximite ) {this.ligneProximite = ligneProximite;}


    /**
     * Traitement des données HorairesLigneParArret
     */
    private HoraireLigneParArret horligneArret;

    public HoraireLigneParArret getHoraireLigneArret(){return this.horligneArret;}
    public void setHoraireLigneArret(HoraireLigneParArret horligneArret){this.horligneArret = horligneArret;}


    public DonnesMetro (String sJsonString) {

        try {
            metro_JsonString = sJsonString;
            JSONObject object = new JSONObject(sJsonString);
            ligneProximite = new LignesProximite(new JSONObject(object.getString("ligneProximite")));
        } catch (JSONException e) {
            String sMsg = e.getMessage();
        }

    }

    public String toJsonString(){
        return metro_JsonString;
    }
}
