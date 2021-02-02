package com.nc.tradox.model.impl;

import com.nc.tradox.model.Country;
import com.nc.tradox.model.FullRoute;

public class FullRouteImpl implements FullRoute {

    protected Country destination;
    protected Country departure;

    public FullRouteImpl() {

    }

    public FullRouteImpl(Country destination, Country departure) {
        this.destination = destination;
        this.departure = departure;
    }

    @Override
    public Country getDestination() {
        return destination;
    }

    @Override
    public void setDestination(Country destination) {
        this.destination = destination;
    }

    @Override
    public Country getDeparture() {
        return departure;
    }

    @Override
    public void setDeparture(Country departure) {
        this.departure = departure;
    }

}