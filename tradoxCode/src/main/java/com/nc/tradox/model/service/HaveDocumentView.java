package com.nc.tradox.model.service;

import com.nc.tradox.model.Country;
import com.nc.tradox.model.FullRoute;
import com.nc.tradox.model.impl.FullRouteImpl;

public class HaveDocumentView {

    private Integer id;
    private Integer documentId;
    private FullRoute fullRoute;

    public HaveDocumentView() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDocumentId() {
        return documentId;
    }

    public void setDocumentId(Integer documentId) {
        this.documentId = documentId;
    }

    private FullRoute getFullRoute() {
        return fullRoute == null ? fullRoute = new FullRouteImpl() : fullRoute;
    }

    public Country getDeparture() {
        return getFullRoute().getDeparture();
    }

    public void setDeparture(Country departure) {
        getFullRoute().setDeparture(departure);
    }

    public Country getDestination() {
        return getFullRoute().getDestination();
    }

    public void setDestination(Country destination) {
        getFullRoute().setDestination(destination);
    }

}