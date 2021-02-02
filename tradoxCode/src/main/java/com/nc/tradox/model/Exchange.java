package com.nc.tradox.model;

public interface Exchange {
    String getLocalCurrency();

    void setLocalCurrency(String localCurrency);

    String getDollarCurrency();

    void setDollarCurrency(String dollarCurrency);

    FullRoute getFullRoute();

    void setFullRoute(FullRoute fullRoute);
}