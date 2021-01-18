package com.nc.tradox.model.impl;

import com.nc.tradox.dao.impl.TradoxDataAccessService;
import com.nc.tradox.model.Country;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PassportImpl implements com.nc.tradox.model.Passport {

    protected String passportSeries;
    protected String passportNumber;
    protected Country citizenshipCountry;
    TradoxDataAccessService service = new TradoxDataAccessService();

    public PassportImpl(ResultSet res) {
        try {
            this.passportSeries = res.getString("series");
            this.passportSeries = res.getString("number");
            this.citizenshipCountry = service.getCountryById(res.getString("country_id"));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public String getPassportId() {
        return passportSeries + passportNumber;
    }

    @Override
    public String getPassportSeries() {
        return passportSeries;
    }

    @Override
    public void setPassportSeries(String passportSeries) {
        this.passportSeries = passportSeries;
    }

    @Override
    public String getPassportNumber() {
        return this.passportNumber;
    }

    @Override
    public void setPassportNumber(String passportNumber) {
        this.passportNumber = passportNumber;
    }

    @Override
    public Country getCountry() {
        return this.citizenshipCountry;
    }

    @Override
    public void setCountry(Country country) {
        this.citizenshipCountry = country;
    }
}
