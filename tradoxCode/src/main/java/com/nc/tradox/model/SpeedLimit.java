package com.nc.tradox.model;

public interface SpeedLimit extends Element, Destination {
//    Integer getUrbanSpeedLimit();
//    void setUrbanSpeedLimit(Integer urbanSpeedLimit);
//    Integer getRuralSpeedLimit();
//    void setRuralSpeedLimit(Integer ruralSpeedLimit);
//    Integer getHighwaySpeedLimit();
//    void setHighwaySpeedLimit(Integer highwaySpeedLimit);
//    Integer getMotorwaySpeedLimit();
//    void setMotorwaySpeedLimit(Integer motorwaySpeedLimit);
    enum TypeOfRoad {
        urban,rural,highway,motorway;
    }
    TypeOfRoad getTypeOfRoad();
    Integer getSpeed();
}
