package com.nc.tradox.model.impl;

import com.nc.tradox.model.Reason;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ReasonImpl implements Reason {

    protected Integer reasonId;
    protected Boolean covidReason;
    protected Boolean citizenshipReason;

    public ReasonImpl() {

    }

    public ReasonImpl(Integer reasonId,
                      Boolean covidReason,
                      Boolean citizenshipReason) {
        this.reasonId = reasonId;
        this.covidReason = covidReason;
        this.citizenshipReason = citizenshipReason;
    }

    public ReasonImpl(ResultSet resultSet) throws SQLException {
        this.reasonId = resultSet.getInt("reason_id");
        this.covidReason = resultSet.getBoolean("covid_reason");
        this.citizenshipReason = resultSet.getBoolean("citizenship_reason");
    }

    @Override
    public Integer getReasonId() {
        return reasonId;
    }

    @Override
    public void setReasonId(Integer reasonId) {
        this.reasonId = reasonId;
    }

    @Override
    public Boolean getCovidReason() {
        return covidReason;
    }

    @Override
    public void setCovidReason(Boolean covidReason) {
        this.covidReason = covidReason;
    }

    @Override
    public Boolean getCitizenshipReason() {
        return citizenshipReason;
    }

    @Override
    public void setCitizenshipReason(Boolean citizenshipReason) {
        this.citizenshipReason = citizenshipReason;
    }

}