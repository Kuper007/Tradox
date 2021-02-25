package com.nc.tradox.model.impl;

import com.nc.tradox.model.InfoData;
import com.nc.tradox.model.Route;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashSet;
import java.util.Set;

public class RouteImpl implements Route {

    protected Integer routeId;
    protected TransportType transportType;
    protected Set<InfoData> transitSet;

    public RouteImpl() {
        this.transportType = TransportType.plane;
        this.transitSet = new LinkedHashSet<>();
    }

    public RouteImpl(Integer routeId,
                     TransportType transportType,
                     Set<InfoData> transitSet) {
        this.routeId = routeId;
        this.transportType = transportType;
        this.transitSet = transitSet;
    }

    public RouteImpl(Integer routeId,
                     Set<InfoData> transitSet) {
        this.routeId = routeId;
        this.transportType = TransportType.plane;
        this.transitSet = transitSet;
    }

    public RouteImpl(ResultSet resultSet) throws SQLException {
        this.routeId = resultSet.getInt("route_id");
        this.transportType = TransportType.valueOf(resultSet.getString("transport_type"));
        this.transitSet = new LinkedHashSet<>();
    }

    @Override
    public Integer getRouteId() {
        return routeId;
    }

    @Override
    public void setRouteId(Integer routeId) {
        this.routeId = routeId;
    }

    @Override
    public TransportType getTransportType() {
        return transportType;
    }

    @Override
    public void setTransportType(TransportType transportType) {
        this.transportType = transportType;
    }

    @Override
    public Set<InfoData> getTransit() {
        return this.transitSet;
    }

    @Override
    public Boolean addTransitCountry(InfoData infoData) {
        return this.transitSet.add(infoData);
    }

    @Override
    public Boolean removeFromTransit(InfoData infoData) {
        return this.transitSet.remove(infoData);
    }

}