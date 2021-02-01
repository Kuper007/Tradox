package com.nc.tradox.api;

public class RouteCredentials {
    protected String departureId;
    protected String destinationId;

    public RouteCredentials(String departureId, String destinationId){
        this.departureId = departureId;
        this.destinationId = destinationId;
    }

    public String getDepartureId() {
        return departureId;
    }

    public String getDestinationId() {
        return destinationId;
    }
}
