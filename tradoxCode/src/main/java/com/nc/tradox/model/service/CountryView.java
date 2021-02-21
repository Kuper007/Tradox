package com.nc.tradox.model.service;

public class CountryView {

    protected String shortName;
    protected String fullName;
    protected String currency;
    protected double mediumBill;
    protected int tourismCount;

    public CountryView() {

    }

    public CountryView(String shortName, String fullName, String currency, double mediumBill, int tourismCount) {
        this.shortName = shortName;
        this.fullName = fullName;
        this.currency = currency;
        this.mediumBill = mediumBill;
        this.tourismCount = tourismCount;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public double getMediumBill() {
        return mediumBill;
    }

    public void setMediumBill(double mediumBill) {
        this.mediumBill = mediumBill;
    }

    public int getTourismCount() {
        return tourismCount;
    }

    public void setTourismCount(int tourismCount) {
        this.tourismCount = tourismCount;
    }

}