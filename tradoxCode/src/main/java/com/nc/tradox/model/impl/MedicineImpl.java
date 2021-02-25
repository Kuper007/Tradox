package com.nc.tradox.model.impl;

import com.nc.tradox.model.Country;
import com.nc.tradox.model.Medicine;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MedicineImpl implements Medicine {

    protected Integer medicineId;
    protected String name;
    protected Country country;

    public MedicineImpl() {

    }

    public MedicineImpl(Integer medicineId,
                        String name,
                        Country country) {
        this.medicineId = medicineId;
        this.name = name;
        this.country = country;
    }

    public MedicineImpl(ResultSet resultSet) throws SQLException {
        this.medicineId = resultSet.getInt("medicine_id");
        this.name = resultSet.getString("name");
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
        return country == null ? country = new CountryImpl() : country;
    }

    @Override
    public void setCountry(Country country) {
        this.country = country;
    }

}