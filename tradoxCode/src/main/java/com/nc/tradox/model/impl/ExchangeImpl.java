package com.nc.tradox.model.impl;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nc.tradox.model.Country;
import com.nc.tradox.model.Exchange;
import com.nc.tradox.model.FullRoute;

public class ExchangeImpl implements Exchange {

    /*
        From needs number of value of departure country currency.
        localCurrency - to 1 value of destination country currency.
        dollarCurrency - to 1 USD.
    */

    protected String localCurrency;
    protected String dollarCurrency;
    protected FullRoute fullRoute;

    public ExchangeImpl() {

    }

    public ExchangeImpl(String localCurrency,
                        String dollarCurrency,
                        FullRoute fullRoute) {
        this.localCurrency = localCurrency;
        this.dollarCurrency = dollarCurrency;
        this.fullRoute = fullRoute;
    }

    @Override
    public String getLocalCurrency() {
        return localCurrency;
    }

    @Override
    public void setLocalCurrency(String localCurrency) {
        this.localCurrency = localCurrency;
    }

    @Override
    public String getDollarCurrency() {
        return dollarCurrency;
    }

    @Override
    public void setDollarCurrency(String dollarCurrency) {
        this.dollarCurrency = dollarCurrency;
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

    public FullRoute getFullRoute() {
        return fullRoute == null ? new FullRouteImpl() : fullRoute;
    }

    public void setFullRoute(FullRoute fullRoute) {
        this.fullRoute = fullRoute;
    }

}