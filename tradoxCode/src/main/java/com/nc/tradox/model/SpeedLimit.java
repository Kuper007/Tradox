package com.nc.tradox.model;

public interface SpeedLimit {
    enum TypeOfRoad {
        urban, rural, highway, motorway
    }

    Integer getSpeedLimitId();

    void setSpeedLimitId(Integer speedLimitId);

    TypeOfRoad getTypeOfRoad();

    void setTypeOfRoad(TypeOfRoad typeOfRoad);

    Integer getSpeed();

    void setSpeed(Integer speed);

    Country getCountry();

    void setCountry(Country country);
}