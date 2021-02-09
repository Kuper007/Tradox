package com.nc.tradox.model.impl;

import com.nc.tradox.model.Country;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CountryImpl implements Country {
    protected String shortName;
    protected String fullName;

    public CountryImpl() {

    }

    public CountryImpl(String shortName, String fullName) {
        this.shortName = shortName;
        this.fullName = fullName;
    }

    public CountryImpl(ResultSet resultSet) throws SQLException {
        this.shortName = resultSet.getString("short_name");
        this.fullName = resultSet.getString("full_name");
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
    public String getFullName() {
        return fullName;
    }

    @Override
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

}