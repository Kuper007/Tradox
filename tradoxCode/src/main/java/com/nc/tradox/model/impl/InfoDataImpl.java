package com.nc.tradox.model.impl;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nc.tradox.model.*;

public class InfoDataImpl implements InfoData {

    protected FullRoute fullRoute;
    protected double mediumBill;
    protected Documents documents;
    protected int tourismCount;
    protected Medicines medicines;
    protected Covid covidInfo;
    protected Consulates consulates;
    protected SpeedLimits speedLimits;
    protected String currency;
    protected Exchange exchange;
    protected News news;
    protected Status status;

    public InfoDataImpl() {

    }

    public InfoDataImpl(FullRoute fullRoute,
                        double mediumBill,
                        Documents documents,
                        int tourismCount,
                        Medicines medicines,
                        Covid covidInfo,
                        Consulates consulates,
                        SpeedLimits speedLimits,
                        String currency,
                        Exchange exchange,
                        News news,
                        Status status) {
        this.fullRoute = fullRoute;
        this.mediumBill = mediumBill;
        this.documents = documents;
        this.tourismCount = tourismCount;
        this.medicines = medicines;
        this.covidInfo = covidInfo;
        this.consulates = consulates;
        this.speedLimits = speedLimits;
        this.currency = currency;
        this.exchange = exchange;
        this.news = news;
        this.status = status;
    }

    @Override
    public Boolean reloadData() {
        // обновление данных всего обьекта из базы
        return false;
    }

    @Override
    public FullRoute getFullRoute() {
        return fullRoute == null ? new FullRouteImpl() : fullRoute;
    }

    @Override
    public void setFullRoute(FullRoute fullRoute) {
        this.fullRoute = fullRoute;
    }

    @Override
    @JsonIgnore
    public Country getDeparture() {
        return getFullRoute().getDeparture();
    }

    @Override
    public void setDeparture(Country departure) {
        getFullRoute().setDeparture(departure);
    }

    @Override
    @JsonIgnore
    public Country getDestination() {
        return getFullRoute().getDestination();
    }

    @Override
    public void setDestination(Country destination) {
        getFullRoute().setDestination(destination);
    }

    @Override
    public double getMediumBill() {
        return mediumBill;
    }

    @Override
    public void setMediumBill(double mediumBill) {
        this.mediumBill = mediumBill;
    }

    @Override
    public Documents getDocuments() {
        return documents;
    }

    @Override
    public void setDocuments(Documents documents) {
        this.documents = documents;
    }

    @Override
    public int getTourismCount() {
        return tourismCount;
    }

    @Override
    public void setTourismCount(int tourismCount) {
        this.tourismCount = tourismCount;
    }

    @Override
    public Medicines getMedicines() {
        return medicines;
    }

    @Override
    public void setMedicines(Medicines medicines) {
        this.medicines = medicines;
    }

    @Override
    public Covid getCovidInfo() {
        return covidInfo;
    }

    @Override
    public void setCovidInfo(Covid covidInfo) {
        this.covidInfo = covidInfo;
    }

    @Override
    public Consulates getConsulates() {
        return consulates;
    }

    @Override
    public void setConsulates(Consulates consulates) {
        this.consulates = consulates;
    }

    @Override
    public SpeedLimits getSpeedLimits() {
        return speedLimits;
    }

    @Override
    public void setSpeedLimits(SpeedLimits speedLimits) {
        this.speedLimits = speedLimits;
    }

    @Override
    public String getCurrency() {
        return currency;
    }

    @Override
    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @Override
    public Exchange getExchange() {
        return exchange;
    }

    @Override
    public void setExchange(Exchange exchange) {
        this.exchange = exchange;
    }

    @Override
    public News getNews() {
        return news;
    }

    @Override
    public void setNews(News news) {
        this.news = news;
    }

    @Override
    public Status getStatus() {
        return status;
    }

    @Override
    public void setStatus(Status status) {
        this.status = status;
    }

}