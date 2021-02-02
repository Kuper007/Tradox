package com.nc.tradox.model.impl;

import com.nc.tradox.model.*;

public class InfoDataImpl implements InfoData {

    protected FullRoute fullRoute;
    protected Documents documents;
    protected SpeedLimits speedLimits;
    protected Medicines medicines;
    protected Consulates consulates;
    protected News news;
    protected Exchange exchange;
    protected Status status;

    public InfoDataImpl() {

    }

    public InfoDataImpl(FullRoute fullRoute, Documents documents, SpeedLimits speedLimits, Medicines medicines,
                        Consulates consulates, News news, Exchange exchange, Status status) {
        this.fullRoute = fullRoute;
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
    public Country getDestination() {
        return this.fullRoute.getDestination();
    }

    @Override
    public Country getDeparture() {
        return this.fullRoute.getDeparture();
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

}