package com.nc.tradox.model.impl;

import com.nc.tradox.model.Country;
import com.nc.tradox.model.Medicine;

public class MedicineImpl implements Medicine {

    protected Integer medicineId;
    protected String name;
    protected Country country;

    public MedicineImpl() {

    }

    public MedicineImpl(Integer medicineId, String name, Country country) {
        this.medicineId = medicineId;
        this.name = name;
        this.country = country;
    }

    @Override
    public Integer getMedicineId() {
        return medicineId;
    }

    @Override
    public void setMedicineId(Integer medicineId) {
        this.medicineId = medicineId;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
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