package com.nc.tradox.model.impl;

import com.nc.tradox.model.Departure;
import com.nc.tradox.model.Destination;

public class DocumentImpl implements com.nc.tradox.model.Document {

    protected final Integer documentId;
    protected String name;
    protected String description;
    protected String fileLink;
    protected FullRouteImpl fullRouteImpl;

    private DocumentImpl(Integer documentId, String name, String description, String fileLink, FullRouteImpl fullRouteImpl) {
        this.documentId = documentId;
        this.name = name;
        this.description = description;
        this.fileLink = fileLink;
        this.fullRouteImpl = fullRouteImpl;
    }

    @Override
    public Integer getElementId() {
        return this.documentId;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public String getFileLink() {
        return this.getFileLink();
    }

    @Override
    public Departure getDepartureCountry() {
        return this.fullRouteImpl.getDepartureCountry();
    }

    @Override
    public Destination getDestinationCountry() {
        return this.fullRouteImpl.getDestinationCountry();
    }
}
