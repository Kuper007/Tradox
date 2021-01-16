package com.nc.tradox.model.impl;

import com.nc.tradox.model.Country;

public class DepartureImpl implements com.nc.tradox.model.Departure {

    protected Country departureCountry;

    public DepartureImpl(Country departureCountry) {
        this.departureCountry = departureCountry;
    }

    @Override
    public Country getDepartureCountry() {
        return this.departureCountry;
    }

}
