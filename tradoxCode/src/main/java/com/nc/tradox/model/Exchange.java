package com.nc.tradox.model;

public interface Exchange extends FullRoute {
    Integer getExchangeId();
    String getLocalCurrency();
    void setLocalCurrency(String localCurrency);
    String getDollarCurrency();
    void setDollarCurrency(String dollarCurrency);
    FullRoute getFullRoute();
}
