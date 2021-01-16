package com.nc.tradox.model.impl;

import com.nc.tradox.model.Country;
import com.nc.tradox.model.Departure;
import com.nc.tradox.model.Destination;
import com.nc.tradox.model.FullRoute;

public class ConsulateImpl implements com.nc.tradox.model.Consulate {

    protected final Integer consulateId;
    protected Country owner;
    protected String city;
    protected String address;
    protected String phoneNumber;
    protected FullRoute fullRoute;

    public ConsulateImpl(Integer consulateId, Country owner, String city, String address, String phoneNumber, FullRoute fullRoute) {
        this.consulateId = consulateId;
        this.owner = owner;
        this.city = city;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.fullRoute = fullRoute;
    }

    @Override
    public Integer getElementId() {
        return this.consulateId;
    }

    @Override
    public Country getOwner() {
        return this.owner;
    }

    @Override
    public String getCityOfConsulate() {
        return this.city;
    }

    @Override
    public void setCityOfConsulate(String newCity) {
        this.city = newCity;
    }

    @Override
    public String getAddressOfConsulate() {
        return this.address;
    }

    @Override
    public void setAddressOfConsulate(String newAddress) {
        this.address = newAddress;
    }

    @Override
    public String getPhoneNumberOfConsulate() {
        return this.phoneNumber;
    }

    @Override
    public void setPhoneNumberOfConsulate(String newPhoneNumber) {
        this.phoneNumber = newPhoneNumber;
    }

    @Override
    public Departure getDepartureCountry() {
        return this.fullRoute.getDepartureCountry();
    }

    @Override
    public Destination getDestinationCountry() {
        return this.fullRoute.getDestinationCountry();
    }
}
