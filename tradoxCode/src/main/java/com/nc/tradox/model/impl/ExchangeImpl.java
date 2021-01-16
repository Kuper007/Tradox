package com.nc.tradox.model.impl;

import com.nc.tradox.model.Departure;
import com.nc.tradox.model.Destination;

public class ExchangeImpl implements com.nc.tradox.model.Exchange {

    protected final Integer exchangeId;

    /*
        From needs number of value of departure country currency.
        localCurrency - to 1 value of destination country currency.
        dollarCurrency - to 1 USD.
    */
    protected String localCurrency;
    protected String dollarCurrency;

    protected FullRouteImpl fullRouteImpl;

    public ExchangeImpl(Integer exchangeId, String localCurrency, String dollarCurrency, FullRouteImpl fullRouteImpl) {
        this.exchangeId = exchangeId;
        this.localCurrency = localCurrency;
        this.dollarCurrency = dollarCurrency;
        this.fullRouteImpl = fullRouteImpl;
    }

    @Override
    public Integer getExchangeId() {
        return this.exchangeId;
    }

    @Override
    public String getLocalCurrency() {
        return this.localCurrency;
    }

    @Override
    public void setLocalCurrency(String localCurrency) {
        this.localCurrency = localCurrency;
    }

    @Override
    public String getDollarCurrency() {
        return this.dollarCurrency;
    }

    @Override
    public void setDollarCurrency(String dollarCurrency) {
        this.dollarCurrency = dollarCurrency;
    }

    @Override
    public com.nc.tradox.model.FullRoute getFullRoute() {
        return this.fullRouteImpl;
    }

    @Override
    public Departure getDepartureCountry() {
        return this.fullRouteImpl.getDepartureCountry();
    }

    @Override
    public Destination getDestinationCountry() {
        return this.fullRouteImpl.getDestinationCountry();
    }
}
