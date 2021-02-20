package com.nc.tradox.model.impl;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nc.tradox.model.Consulate;
import com.nc.tradox.model.Country;
import com.nc.tradox.model.FullRoute;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ConsulateImpl implements Consulate {

    protected Integer consulateId;
    protected String city;
    protected String address;
    protected String phoneNumber;
    protected FullRoute fullRoute;

    public ConsulateImpl() {

    }

    public ConsulateImpl(Integer consulateId,
                         String city,
                         String address,
                         String phoneNumber,
                         FullRoute fullRoute) {
        this.consulateId = consulateId;
        this.city = city;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.fullRoute = fullRoute;
    }

    public ConsulateImpl(ResultSet resultSet) throws SQLException {
        this.consulateId = resultSet.getInt("consulate_id");
        this.city = resultSet.getString("city");
        this.address = resultSet.getString("address");
        this.phoneNumber = resultSet.getString("phone");
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
        return getFullRoute().getDestination();
    }

    @Override
    public void setCountry(Country country) {
        getFullRoute().setDestination(country);
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
        return getFullRoute().getDeparture();
    }

    @Override
    public void setOwner(Country owner) {
        getFullRoute().setDeparture(owner);
    }

    @JsonIgnore
    public FullRoute getFullRoute() {
        return fullRoute == null ? fullRoute = new FullRouteImpl() : fullRoute;
    }

    public void setFullRoute(FullRoute fullRoute) {
        this.fullRoute = fullRoute;
    }

}