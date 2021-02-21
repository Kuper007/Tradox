package com.nc.tradox.model.impl;

import com.nc.tradox.model.Country;
import com.nc.tradox.model.FullRoute;

public class FullRouteImpl implements FullRoute {

    protected Country departure;
    protected Country destination;

    public FullRouteImpl() {

    }

    public FullRouteImpl(Country departure, Country destination) {
        this.departure = departure;
        this.destination = destination;
    }

    @Override
    public Country getDeparture() {
        return departure == null ? departure = new CountryImpl() : departure;
    }

    @Override
    public void setDeparture(Country departure) {
        this.departure = departure;
    }

    @Override
    public Country getDestination() {
        return destination == null ? destination = new CountryImpl() : destination;
    }

    @Override
    public void setDestination(Country destination) {
        this.destination = destination;
    }

}