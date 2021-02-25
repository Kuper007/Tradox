package com.nc.tradox.model;

import java.util.Set;

public interface Route {
    Integer getRouteId();

    void setRouteId(Integer routeId);

    TransportType getTransportType();

    void setTransportType(TransportType tType);

    Set<InfoData> getTransit();

    Boolean addTransitCountry(InfoData infoData);

    Boolean removeFromTransit(InfoData infoData);

    enum TransportType {
        car, plane, other
    }
}