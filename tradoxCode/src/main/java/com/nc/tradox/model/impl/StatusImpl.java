package com.nc.tradox.model.impl;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nc.tradox.model.Country;
import com.nc.tradox.model.FullRoute;
import com.nc.tradox.model.Reason;
import com.nc.tradox.model.Status;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StatusImpl implements Status {

    protected Integer statusId;
    protected StatusEnum status;
    protected Reason reason;
    protected FullRoute fullRoute;

    public StatusImpl() {

    }

    public StatusImpl(Integer statusId1,
                      StatusEnum status,
                      Reason reason,
                      FullRoute fullRoute) {
        this.statusId = statusId1;
        this.status = status;
        this.reason = reason;
        this.fullRoute = fullRoute;
    }

    public StatusImpl(ResultSet resultSet) throws SQLException {
        this.statusId = resultSet.getInt("status_id");
        this.status = resultSet.getInt("value") == 0 ? Status.StatusEnum.close : Status.StatusEnum.open;
        this.reason = new ReasonImpl(resultSet);
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
    public Reason getReason() {
        return reason == null ? reason = new ReasonImpl() : reason;
    }

    @Override
    public void setReason(Reason reason) {
        this.reason = reason;
    }

    @Override
    @JsonIgnore
    public Country getCountry() {
        return getFullRoute().getDeparture();
    }

    @Override
    public void setCountry(Country country) {
        getFullRoute().setDeparture(country);
    }

    @Override
    @JsonIgnore
    public Country getDestination() {
        return getFullRoute().getDestination();
    }

    @Override
    public void setDestination(Country destination) {
        getFullRoute().setDestination(destination);
    }

    @Override
    public FullRoute getFullRoute() {
        return fullRoute == null ? fullRoute = new FullRouteImpl() : fullRoute;
    }

    @Override
    public void setFullRoute(FullRoute fullRoute) {
        this.fullRoute = fullRoute;
    }

}