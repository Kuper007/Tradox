package com.nc.tradox.model;

import com.nc.tradox.model.impl.*;

public interface InfoData {
    Boolean reloadData();

    FullRoute getFullRoute();

    void setFullRoute(FullRoute fullRoute);

    Country getDeparture();

    void setDeparture(Country departure);

    Country getDestination();

    void setDestination(Country destination);

    double getMediumBill();

    void setMediumBill(double mediumBill);

    Documents getDocuments();

    void setDocuments(Documents documents);

    int getTourismCount();

    void setTourismCount(int tourismCount);

    Medicines getMedicines();

    void setMedicines(Medicines medicines);

    Covid getCovidInfo();

    void setCovidInfo(Covid covidInfo);

    Consulates getConsulates();

    void setConsulates(Consulates consulates);

    SpeedLimits getSpeedLimits();

    void setSpeedLimits(SpeedLimits speedLimits);

    String getCurrency();

    void setCurrency(String currency);

    Exchange getExchange();

    void setExchange(Exchange exchange);

    News getNews();

    void setNews(News news);

    Status getStatus();

    void setStatus(Status status);
}