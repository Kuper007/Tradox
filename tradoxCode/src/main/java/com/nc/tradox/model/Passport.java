package com.nc.tradox.model;

public interface Passport {
    String getPassportId();
    String getPassportSeries();
    void setPassportSeries(String passportSeries);
    String getPassportNumber();
    void setPassportNumber(String passportNumber);
    Country getCountry();
    void setCountry(Country country);
}
