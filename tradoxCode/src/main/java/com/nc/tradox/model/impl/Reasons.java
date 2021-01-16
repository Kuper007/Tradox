package com.nc.tradox.model.impl;

public class Reasons implements com.nc.tradox.model.Reasons {

    protected final Integer reasonId;
    protected Boolean covidReason;
    protected Boolean citizenshipReason;

    public Reasons(Integer reasonId, Boolean covidReason, Boolean citizenshipReason) {
        this.reasonId = reasonId;
        this.covidReason = covidReason;
        this.citizenshipReason = citizenshipReason;
    }

    @Override
    public Integer getReasonId() {
        return reasonId;
    }

    @Override
    public Boolean getCovidReason() {
        return covidReason;
    }

    @Override
    public void setCovidReason(Boolean covidReason) {
        if (this.covidReason != covidReason) {
            this.covidReason = covidReason;
        }
    }

    @Override
    public Boolean getCitizenshipReason() {
        return citizenshipReason;
    }

    @Override
    public void setCitizenshipReason(Boolean citizenshipReason) {
        if (this.citizenshipReason != citizenshipReason) {
            this.citizenshipReason = citizenshipReason;
        }
    }
}
