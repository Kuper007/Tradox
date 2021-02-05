package com.nc.tradox.model.impl;

import com.nc.tradox.model.Country;
import com.nc.tradox.model.FullRoute;
import com.nc.tradox.model.Reason;
import com.nc.tradox.model.Status;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StatusImpl implements Status {

    protected Integer statusId;
    protected StatusEnum status;
    protected ReasonImpl reasons;
    protected FullRoute fullRoute;
    protected Reason reason;

    public StatusImpl() {

    }

    public StatusImpl(Integer statusId1, StatusEnum status, ReasonImpl reasons, FullRoute fullRoute) {
        this.statusId = statusId1;
        this.status = status;
        this.reasons = reasons;
        this.fullRoute = fullRoute;
    }

    public StatusImpl(ResultSet resultSet) throws SQLException {
        this.statusId = resultSet.getInt("status_id");
        this.status = resultSet.getInt("value") == 0 ? Status.StatusEnum.close : Status.StatusEnum.open;
        this.reasons = new ReasonImpl(resultSet);
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
    public ReasonImpl getReasons() {
        return reasons;
    }

    @Override
    public void setReasons(ReasonImpl reasons) {
        this.reasons = reasons;
    }

    @Override
    public Country getCountry() {
        return fullRoute.getDeparture();
    }

    @Override
    public void setCountry(Country country) {
        fullRoute.setDeparture(country);
    }

    @Override
    public Country getDestination() {
        return fullRoute.getDestination();
    }

    @Override
    public void setDestination(Country destination) {
        fullRoute.setDestination(destination);
    }

}