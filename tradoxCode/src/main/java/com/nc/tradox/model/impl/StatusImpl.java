package com.nc.tradox.model.impl;

import com.nc.tradox.model.Country;
import com.nc.tradox.model.Destination;

public class StatusImpl implements com.nc.tradox.model.Status {

    protected final Integer statusId;
    protected StatusEnum status;
    protected Reasons reasons;
    protected Destination destination;

    public StatusImpl(Integer statusId1, StatusEnum status, Reasons reasons, Destination destination) {
        this.statusId = statusId1;
        this.status = status;
        this.reasons = reasons;
        this.destination = destination;
    }

    @Override
    public Integer getStatusId() {
        return statusId;
    }

    @Override
    public StatusEnum getStatus() {
        return this.status;
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
    public void changeCovidReason() {
        this.reasons.setCovidReason(!this.reasons.getCovidReason());
    }

    @Override
    public void changeCitizenshipReason() {
        this.reasons.setCitizenshipReason(!this.reasons.getCitizenshipReason());
    }

    @Override
    public Country getDestinationCountry() {
        return this.destination.getDestinationCountry();
    }
}
