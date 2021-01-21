package com.nc.tradox.utilities;

import com.nc.tradox.model.Departure;
import com.nc.tradox.model.Destination;

public class HaveDocument {
    private int documentId;
    private Departure departure;
    private Destination destination;

    public int getDocumentId() {
        return documentId;
    }

    public void setDocumentId(int documentId) {
        this.documentId = documentId;
    }

    public Departure getDeparture() {
        return departure;
    }

    public void setDeparture(Departure departure) {
        this.departure = departure;
    }

    public Destination getDestination() {
        return destination;
    }

    public void setDestination(Destination destination) {
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
