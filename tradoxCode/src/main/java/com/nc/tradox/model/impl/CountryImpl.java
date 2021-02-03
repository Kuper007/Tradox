package com.nc.tradox.model.impl;

import com.nc.tradox.model.Country;
import com.nc.tradox.model.Covid;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CountryImpl implements Country {
    protected String fullName;
    protected String shortName;
    protected String currency;
    protected int mediumBill;
    protected int tourismCount;
    protected Covid covidInfo;

    public CountryImpl() {

    }

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

    public CountryImpl(ResultSet resultSet) throws SQLException {
        this.fullName = resultSet.getString("full_name");
        this.shortName = resultSet.getString("short_name");
        this.currency = resultSet.getString("currency");
        this.mediumBill = resultSet.getInt("medium_bill");
        this.tourismCount = resultSet.getInt("tourism_count");
        this.covidInfo = new CovidImpl(resultSet);
    }

    @Override
    public String getFullName() {
        return fullName;
    }

    @Override
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @Override
    public String getShortName() {
        return shortName;
    }

    @Override
    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    @Override
    public String getCurrency() {
        return currency;
    }

    @Override
    public void setCurrency(String currency) {
        this.currency = currency;
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
    public void setCovidInfo(Covid covidInfo) {
        this.covidInfo = covidInfo;
    }

}