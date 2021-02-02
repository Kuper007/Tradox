package com.nc.tradox.model.impl;

import com.nc.tradox.model.Country;
import com.nc.tradox.model.Status;

public class StatusImpl implements Status {

    protected Integer statusId;
    protected StatusEnum status;
    protected Reasons reasons;
    protected Country country;
    protected Country destination;

    public StatusImpl() {

    }

    public StatusImpl(Integer statusId1, StatusEnum status, Reasons reasons, Country country, Country destination) {
        this.statusId = statusId1;
        this.status = status;
        this.reasons = reasons;
        this.country = country;
        this.destination = destination;
    }

    @Override
    public Integer getStatusId() {
        return statusId;
    }

    @Override
    public void setStatusId(Integer statusId) {
        this.statusId = statusId;
    }

    @Override
    public StatusEnum getStatus() {
        return status;
    }

    @Override
    public void setStatus(StatusEnum status) {
        this.status = status;
    }

    @Override
    public Reasons getReasons() {
        return reasons;
    }

    @Override
    public void setReasons(Reasons reasons) {
        this.reasons = reasons;
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
    public Country getDestination() {
        return destination;
    }

    @Override
    public void setDestination(Country destination) {
        this.destination = destination;
    }

}