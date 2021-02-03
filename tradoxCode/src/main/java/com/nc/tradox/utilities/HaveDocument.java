package com.nc.tradox.utilities;

import com.nc.tradox.model.Country;

public class HaveDocument {
    private int documentId;
    private Country departure;
    private Country destination;

    public int getDocumentId() {
        return documentId;
    }

    public void setDocumentId(int documentId) {
        this.documentId = documentId;
    }

    public Country getDeparture() {
        return departure;
    }

    public void setDeparture(Country departure) {
        this.departure = departure;
    }

    public Country getDestination() {
        return destination;
    }

    public void setDestination(Country destination) {
        this.destination = destination;
    }

    @Override
    public String toString() {
        return "HaveDocument{" +
                "documentId=" + documentId +
                ", departure=" + departure +
                ", destination=" + destination +
                '}';
    }

}
