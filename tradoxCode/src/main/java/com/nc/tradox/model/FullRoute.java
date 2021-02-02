package com.nc.tradox.model;

public interface FullRoute {
    Country getDestination();

    void setDestination(Country destination);

    Country getDeparture();

    void setDeparture(Country departure);
}