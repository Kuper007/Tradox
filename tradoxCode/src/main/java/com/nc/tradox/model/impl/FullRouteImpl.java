package com.nc.tradox.model.impl;

import com.nc.tradox.model.Departure;
import com.nc.tradox.model.Destination;

public class FullRouteImpl implements com.nc.tradox.model.FullRoute {

    protected Destination destination;
    protected Departure departure;

    public FullRouteImpl(Departure departure, Destination destination) {
        this.departure = departure;
        this.destination = destination;
    }

    @Override
    public Departure getDepartureCountry() {
        return null;
    }

    @Override
    public Destination getDestinationCountry() {
        return null;
    }
}
