package com.nc.tradox.model.impl;

import com.nc.tradox.model.Country;
import com.nc.tradox.model.Passport;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PassportImpl implements Passport {

    protected String passportSeries;
    protected String passportNumber;
    protected Country citizenshipCountry;

    public PassportImpl() {

    }

    public PassportImpl(String passportSeries,
                        Country citizenshipCountry) {
        this.passportSeries = passportSeries.substring(0, 2);
        this.passportNumber = passportSeries.substring(2);
        this.citizenshipCountry = citizenshipCountry;
    }

    public PassportImpl(ResultSet resultSet) throws SQLException {
        this.passportSeries = resultSet.getString("series");
        this.passportSeries = resultSet.getString("num");
        this.citizenshipCountry = new CountryImpl(resultSet);
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
        return passportNumber;
    }

    @Override
    public void setPassportNumber(String passportNumber) {
        this.passportNumber = passportNumber;
    }

    @Override
    public Country getCitizenshipCountry() {
        return citizenshipCountry == null ? new CountryImpl() : citizenshipCountry;
    }

    @Override
    public void setCitizenshipCountry(Country country) {
        this.citizenshipCountry = country;
    }

}