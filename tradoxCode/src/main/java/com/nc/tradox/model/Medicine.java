package com.nc.tradox.model;

public interface Medicine {
    Integer getMedicineId();

    void setMedicineId(Integer medicineId);

    String getName();

    void setName(String name);

    Country getCountry();

    void setCountry(Country country);
}