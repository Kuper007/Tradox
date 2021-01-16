package com.nc.tradox.model.impl;

import com.nc.tradox.model.Country;
import com.nc.tradox.model.Destination;

public class SpeedLimitImpl implements com.nc.tradox.model.SpeedLimit {

    protected final Integer speedLimitId;
    protected TypeOfRoad typeOfRoad;
    protected Destination destination;
    protected Integer speed;

    public SpeedLimitImpl(Integer speedLimitId, TypeOfRoad typeOfRoad, Destination destination, Integer speed) {
        this.speedLimitId = speedLimitId;
        this.typeOfRoad = typeOfRoad;
        this.destination = destination;
        this.speed = speed;
    }

    @Override
    public TypeOfRoad getTypeOfRoad() {
        return this.typeOfRoad;
    }

    @Override
    public Integer getElementId() {
        return this.speedLimitId;
    }

    @Override
    public Country getDestinationCountry() {
        return this.destination.getDestinationCountry();
    }

    @Override
    public Integer getSpeed() {
        return this.speed;
    }

    //    protected Integer urban;
//    protected Integer rural;
//    protected Integer highway;
//    protected Integer motorway;
//
//    public SpeedLimit(Integer urban, Integer rural, Integer highway, Integer motorway) {
//        this.urban = urban;
//        this.rural = rural;
//        this.highway = highway;
//        this.motorway = motorway;
//    }

//    @Override
//    public Integer getUrbanSpeedLimit() {
//        return this.urban;
//    }
//
//    @Override
//    public void setUrbanSpeedLimit(Integer urbanSpeedLimit) {
//        this.urban = urbanSpeedLimit;
//    }
//
//    @Override
//    public Integer getRuralSpeedLimit() {
//        return this.rural;
//    }
//
//    @Override
//    public void setRuralSpeedLimit(Integer ruralSpeedLimit) {
//        this.rural = ruralSpeedLimit;
//    }
//
//    @Override
//    public Integer getHighwaySpeedLimit() {
//        return this.highway;
//    }
//
//    @Override
//    public void setHighwaySpeedLimit(Integer highwaySpeedLimit) {
//        this.highway = highwaySpeedLimit;
//    }
//
//    @Override
//    public Integer getMotorwaySpeedLimit() {
//        return this.motorway;
//    }
//
//    @Override
//    public void setMotorwaySpeedLimit(Integer motorwaySpeedLimit) {
//        this.motorway = motorwaySpeedLimit;
//    }
}
