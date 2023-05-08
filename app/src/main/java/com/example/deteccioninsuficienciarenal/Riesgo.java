package com.example.deteccioninsuficienciarenal;

public class Riesgo {
    private int idresults;
    private int porcentrisk;

    public int getPorcentrisk() {
        return porcentrisk;
    }

    public void setPorcentrisk(int porcentrisk) {
        this.porcentrisk = porcentrisk;
    }

    private int diabetes;
    private int bloodpreasure;
    private int heartfailure;
    private int liverdiseasease;

    public int getIdresults() {
        return idresults;
    }

    public void setIdresults(int idresults) {
        this.idresults = idresults;
    }

    public int getDiabetes() {
        return diabetes;
    }

    public void setDiabetes(int diabetes) {
        this.diabetes = diabetes;
    }

    public int getBloodpreasure() {
        return bloodpreasure;
    }

    public void setBloodpreasure(int bloodpreasure) {
        this.bloodpreasure = bloodpreasure;
    }

    public int getHeartfailure() {
        return heartfailure;
    }

    public void setHeartfailure(int heartfailure) {
        this.heartfailure = heartfailure;
    }

    public int getLiverdiseasease() {
        return liverdiseasease;
    }

    public void setLiverdiseasease(int liverdiseasease) {
        this.liverdiseasease = liverdiseasease;
    }

    public int getKidneydisease() {
        return kidneydisease;
    }

    public void setKidneydisease(int kidneydisease) {
        this.kidneydisease = kidneydisease;
    }

    public int getCancer() {
        return cancer;
    }

    public void setCancer(int cancer) {
        this.cancer = cancer;
    }

    public String getCreatedat() {
        return createdat;
    }

    public void setCreatedat(String createdat) {
        this.createdat = createdat;
    }

    public int getOverweight() {
        return overweight;
    }

    public void setOverweight(int overweight) {
        this.overweight = overweight;
    }

    public int getCreatinine() {
        return creatinine;
    }

    public void setCreatinine(int creatinine) {
        this.creatinine = creatinine;
    }

    public int getObstruccionbloodv() {
        return obstruccionbloodv;
    }

    public void setObstruccionbloodv(int obstruccionbloodv) {
        this.obstruccionbloodv = obstruccionbloodv;
    }

    public int getUrinarysediment() {
        return urinarysediment;
    }

    public void setUrinarysediment(int urinarysediment) {
        this.urinarysediment = urinarysediment;
    }

    public int getIduser() {
        return iduser;
    }

    public void setIduser(int iduser) {
        this.iduser = iduser;
    }

    private int kidneydisease;
    private int cancer;
    private String createdat;
    private int overweight;
    private int creatinine;
    private int obstruccionbloodv;
    private int urinarysediment;
    private int iduser;
}
