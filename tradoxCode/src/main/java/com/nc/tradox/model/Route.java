package com.nc.tradox.model;

import java.util.Set;

public interface Route extends Element {
    enum TransportType {
        car,plane,other
    }
    TransportType getTransportType();
    void setTransportType(TransportType tType);
    Set<InfoData> getTransit();
    Boolean addTransitCountry(InfoData infoData);
    Boolean removeFromTransit(InfoData infoData);
}
