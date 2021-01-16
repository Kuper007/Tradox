package com.nc.tradox.model.impl;

import com.nc.tradox.model.Country;
import com.nc.tradox.model.Destination;

public class MedicineImpl implements com.nc.tradox.model.Medicine {

    protected final Integer medicineId;
    protected String name;
    protected Destination destination;

    public MedicineImpl(Integer medicineId, String name, Destination destination) {
        this.medicineId = medicineId;
        this.name = name;
        this.destination = destination;
    }

    @Override
    public Integer getElementId() {
        return this.medicineId;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Country getDestinationCountry() {
        return this.destination.getDestinationCountry();
    }
}
