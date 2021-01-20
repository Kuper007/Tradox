package com.nc.tradox.model.impl;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nc.tradox.dao.impl.TradoxDataAccessService;
import com.nc.tradox.model.InfoData;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;

public class RouteImpl implements com.nc.tradox.model.Route {

    protected Integer routeId;
    protected TransportType transportType;
    protected Set<InfoData> transitSet;
    TradoxDataAccessService service = new TradoxDataAccessService();

    public RouteImpl(Integer routeId, TransportType transportType, Set<InfoData> transitSet) {
        this.routeId = routeId;
        this.transportType = transportType;
        this.transitSet = transitSet;
    }


    public RouteImpl(ResultSet res){
        try {
            this.routeId = res.getInt("route_id");
            this.transportType = TransportType.valueOf(res.getString("transport_type"));
            InfoData infoData = service.getInfoData(res.getString("departure_id"),res.getString("destination_id"));
            this.transitSet.add(infoData);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public Integer getElementId() {
        return this.routeId;
    }

    @Override
    public TransportType getTransportType() {
        return this.transportType;
    }

    @Override
    public void setTransportType(TransportType tType) {
        this.transportType = tType;
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
