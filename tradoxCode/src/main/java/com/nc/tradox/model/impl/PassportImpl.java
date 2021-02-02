package com.nc.tradox.model.impl;

import com.nc.tradox.dao.impl.TradoxDataAccessService;
import com.nc.tradox.model.Country;
import com.nc.tradox.model.Passport;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PassportImpl implements Passport {

    protected String passportSeries;
    protected String passportNumber;
    protected Country citizenshipCountry;

    public PassportImpl(String passportSeries, Country citizenshipCountry) {
        this.passportSeries = passportSeries.substring(0, 2);
        this.passportNumber = passportSeries.substring(2);
        this.citizenshipCountry = citizenshipCountry;
    }

    @Deprecated
    public PassportImpl(String passportSeries, String passportNumber, Country citizenshipCountry) {
        this.passportSeries = passportSeries;
        this.passportNumber = passportNumber;
        this.citizenshipCountry = citizenshipCountry;
    }

    public PassportImpl(ResultSet resultSet) {
        try {
            this.passportSeries = resultSet.getString("series");
            this.passportSeries = resultSet.getString("number");
            this.citizenshipCountry = new TradoxDataAccessService().getCountryById(resultSet.getString("country_id"));
        } catch (SQLException throwable) {
            throwable.printStackTrace();
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
    public Country getCitizenshipCountry() {
        return this.citizenshipCountry;
    }

    @Override
    public void setCitizenshipCountry(Country country) {
        this.citizenshipCountry = country;
    }

}
