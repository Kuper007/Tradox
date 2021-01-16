package com.nc.tradox.model;

public interface Consulate extends Element, FullRoute {
    Country getOwner();
    String getCityOfConsulate();
    void setCityOfConsulate(String newCity);
    String getAddressOfConsulate();
    void setAddressOfConsulate(String newAddress);
    String getPhoneNumberOfConsulate();
    void setPhoneNumberOfConsulate(String newPhoneNumber);
}
