package com.nc.tradox.model;

import com.nc.tradox.model.impl.CovidImpl;

public interface Country {
    String getFullName();
    String getShortName();
    String getCurrency();
    int getMediumBill();
    void setMediumBill(int mediumBill);
    int getTourismCount();
    void setTourismCount(int tourismCount);
    Covid getCovidInfo();
    void setCovidInfo(CovidImpl covidImplInfo);
}
