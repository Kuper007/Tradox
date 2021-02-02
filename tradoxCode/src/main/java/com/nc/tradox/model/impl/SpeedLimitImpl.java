package com.nc.tradox.model.impl;

import com.nc.tradox.model.Country;
import com.nc.tradox.model.SpeedLimit;

public class SpeedLimitImpl implements SpeedLimit {

    protected Integer speedLimitId;
    protected TypeOfRoad typeOfRoad;
    protected Integer speed;
    protected Country country;

    public SpeedLimitImpl() {

    }

    public SpeedLimitImpl(Integer speedLimitId, TypeOfRoad typeOfRoad, Integer speed, Country country) {
        this.speedLimitId = speedLimitId;
        this.typeOfRoad = typeOfRoad;
        this.speed = speed;
        this.country = country;
    }

    @Override
    public Integer getSpeedLimitId() {
        return speedLimitId;
    }

    @Override
    public void setSpeedLimitId(Integer speedLimitId) {
        this.speedLimitId = speedLimitId;
    }

    @Override
    public TypeOfRoad getTypeOfRoad() {
        return typeOfRoad;
    }

    @Override
    public void setTypeOfRoad(TypeOfRoad typeOfRoad) {
        this.typeOfRoad = typeOfRoad;
    }

    @Override
    public Integer getSpeed() {
        return speed;
    }

    @Override
    public void setSpeed(Integer speed) {
        this.speed = speed;
    }

    @Override
    public Country getCountry() {
        return country;
    }

    @Override
    public void setCountry(Country country) {
        this.country = country;
    }

}