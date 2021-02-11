package com.nc.tradox.model;

public interface Exchange {
    String getLocalCurrency();

    void setLocalCurrency(String localCurrency);

    String getDollarCurrency();

    void setDollarCurrency(String dollarCurrency);

    Country getDeparture();

    void setDeparture(Country departure);

    Country getDestination();

    void setDestination(Country destination);

    FullRoute getFullRoute();

    void setFullRoute(FullRoute fullRoute);
}