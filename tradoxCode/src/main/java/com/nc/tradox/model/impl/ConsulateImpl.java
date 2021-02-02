package com.nc.tradox.model.impl;

import com.nc.tradox.model.Consulate;
import com.nc.tradox.model.Country;

public class ConsulateImpl implements Consulate {

    protected Integer consulateId;
    protected Country owner;
    protected String city;
    protected String address;
    protected String phoneNumber;
    protected Country country;

    public ConsulateImpl() {

    }

    public ConsulateImpl(Integer consulateId, Country owner, String city, String address, String phoneNumber, Country country) {
        this.consulateId = consulateId;
        this.owner = owner;
        this.city = city;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.country = country;
    }

    @Override
    public Integer getConsulateId() {
        return consulateId;
    }

    @Override
    public void setConsulateId(Integer consulateId) {
        this.consulateId = consulateId;
    }

    @Override
    public Country getCountry() {
        return country;
    }

    @Override
    public void setCountry(Country country) {
        this.country = country;
    }

    @Override
    public String getCity() {
        return city;
    }

    @Override
    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String getAddress() {
        return address;
    }

    @Override
    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String getPhoneNumber() {
        return phoneNumber;
    }

    @Override
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public Country getOwner() {
        return owner;
    }

    @Override
    public void setOwner(Country owner) {
        this.owner = owner;
    }

    @Override
    public Integer getElementId() {
        return 0;
    }

}