package com.nc.tradox.api;

public class CountryIds {
    protected String departureId;
    protected String destinationId;

    public CountryIds(String departureId, String destinationId){
        this.departureId = departureId;
        this.destinationId = destinationId;
    }

    public String getDepartureId() {
        return departureId;
    }

    public String getDestinationId() {
        return destinationId;
    }

    public void setDepartureId(String departureId) {
        this.departureId = departureId;
    }

    public void setDestinationId(String destinationId) {
        this.destinationId = destinationId;
    }
}

