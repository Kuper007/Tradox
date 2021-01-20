package com.nc.tradox.model.impl;

import com.nc.tradox.model.Departure;
import com.nc.tradox.model.Destination;
import com.nc.tradox.model.Exchange;
import com.nc.tradox.model.Status;

import java.util.List;

public class InfoDataImpl implements com.nc.tradox.model.InfoData {

    protected Documents documents;
    protected SpeedLimits speedLimits;
    protected Medicines medicines;
    protected Consulates consulates;
    protected News news;
    protected Exchange exchange;
    protected Status status;

    public InfoDataImpl(Documents documents, SpeedLimits speedLimits, Medicines medicines,
                        Consulates consulates, News news, Exchange exchange, Status status) {
        this.documents = documents;
        this.speedLimits = speedLimits;
        this.medicines = medicines;
        this.consulates = consulates;
        this.news = news;
        this.exchange = exchange;
        this.status = status;
    }

    @Override
    public Boolean reloadData() {
        // обновление данных всего обьекта из базы
        return null;
    }

    @Override
    public Documents getDocuments() {
        return this.documents;
    }

    @Override
    public SpeedLimits getSpeedLimits() {
        return this.speedLimits;
    }

    @Override
    public Medicines getMedicines() {
        return this.medicines;
    }

    @Override
    public Consulates getConsulates() {
        return this.consulates;
    }

    @Override
    public News getNews() {
        return this.news;
    }

    @Override
    public Exchange getExchanges() {
        return this.exchange;
    }

    @Override
    public Status getStatus() {
        return this.status;
    }

    @Override
    public Departure getDepartureCountry() {
        return this.consulates.getList().get(0).getDepartureCountry();
    }

    @Override
    public Destination getDestinationCountry() {
        return this.consulates.getList().get(0).getDestinationCountry();
    }
}
