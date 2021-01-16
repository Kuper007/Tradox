package com.nc.tradox.model.impl;

import com.nc.tradox.model.Covid;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CountryImpl implements com.nc.tradox.model.Country {
    protected String fullName;
    protected String shortName; //id
    protected String currency;
    protected int mediumBill;
    protected int tourismCount;
    protected Covid covidInfo;

    public CountryImpl(String fullName,
                       String shortName,
                       String currency,
                       int mediumBill,
                       int tourismCount,
                       Covid covidImplInfo) {
        this.fullName = fullName;
        this.shortName = shortName;
        this.currency = currency;
        this.mediumBill = mediumBill;
        this.tourismCount = tourismCount;
        this.covidInfo = covidImplInfo;
    }

    //TODO: добавить второй параметр для ковида
    public CountryImpl(ResultSet res) {
        try {
            this.fullName = res.getString("full_name");
            this.shortName = res.getString("short_name");
            this.currency = res.getString("currency");
            this.mediumBill = res.getInt("medium_bill");
            this.tourismCount = res.getInt("tourism_count");
           // TODO: проиницализировать covidInfo

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    @Override
    public String getFullName() {
        return fullName;
    }

    @Override
    public String getShortName() {
        return shortName;
    }

    @Override
    public String getCurrency() {
        return currency;
    }

    @Override
    public int getMediumBill() {
        return mediumBill;
    }

    @Override
    public void setMediumBill(int mediumBill) {
        this.mediumBill = mediumBill;
    }

    @Override
    public int getTourismCount() {
        return tourismCount;
    }

    @Override
    public void setTourismCount(int tourismCount) {
        this.tourismCount = tourismCount;
    }

    @Override
    public Covid getCovidInfo() {
        return covidInfo;
    }

    @Override
    public void setCovidInfo(CovidImpl covidImplInfo) {
        this.covidInfo = covidImplInfo;
    }
}
