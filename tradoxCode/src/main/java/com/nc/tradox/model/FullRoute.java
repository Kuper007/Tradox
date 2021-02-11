package com.nc.tradox.model;

public interface FullRoute {
    Country getDeparture();

    void setDeparture(Country departure);

    Country getDestination();

    void setDestination(Country destination);
}