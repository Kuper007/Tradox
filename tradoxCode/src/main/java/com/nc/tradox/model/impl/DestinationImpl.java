package com.nc.tradox.model.impl;

import com.nc.tradox.model.Country;

public class DestinationImpl implements com.nc.tradox.model.Destination {

    protected Country destinationCountry;

    public DestinationImpl(com.nc.tradox.model.Country destinationCountry) {
        this.destinationCountry = destinationCountry;
    }

    @Override
    public Country getDestinationCountry() {
        return this.destinationCountry;
    }

}
