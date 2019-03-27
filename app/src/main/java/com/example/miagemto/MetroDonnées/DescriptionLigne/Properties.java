package com.example.miagemto.MetroDonnées.DescriptionLigne;

import java.util.List;

public class Properties {

    private String nUMERO;
    private String pMR;
    private String cOULEUR;
    private String cODE;
    private String lIBELLE;
    private List<String> zONESARRET = null;
    private String type;
    private String id;
    private String shape;

    public String getNUMERO() {
        return nUMERO;
    }

    public void setNUMERO(String nUMERO) {
        this.nUMERO = nUMERO;
    }

    public String getPMR() {
        return pMR;
    }

    public void setPMR(String pMR) {
        this.pMR = pMR;
    }

    public String getCOULEUR() {
        return cOULEUR;
    }

    public void setCOULEUR(String cOULEUR) {
        this.cOULEUR = cOULEUR;
    }

    public String getCODE() {
        return cODE;
    }

    public void setCODE(String cODE) {
        this.cODE = cODE;
    }

    public String getLIBELLE() {
        return lIBELLE;
    }

    public void setLIBELLE(String lIBELLE) {
        this.lIBELLE = lIBELLE;
    }

    public List<String> getZONESARRET() {
        return zONESARRET;
    }

    public void setZONESARRET(List<String> zONESARRET) {
        this.zONESARRET = zONESARRET;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getShape() {
        return shape;
    }

    public void setShape(String shape) {
        this.shape = shape;
    }

}