package com.nc.tradox.dao;

import com.nc.tradox.dao.impl.TradoxDataAccessService;
import com.nc.tradox.model.Medicine;
import com.nc.tradox.utilities.MedicineApi;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MedicineFillDB {

    private static final Logger log = Logger.getLogger(MedicineFillDB.class.getName());
    TradoxDataAccessService tradoxDataAccessService;
    MedicineApi medicineApi;

    public void medicineFillDB() {
        Connection connection = tradoxDataAccessService.connection;
        List<Medicine> medicineList = medicineApi.medicineList();

        for (Medicine medicine : medicineList) {
            try {
                Statement statement = connection.createStatement();
                int rows = statement.executeUpdate(
                        "INSERT INTO MEDICINE(NAME, COUNTRY_ID)" +
                                "VALUES (" + medicine.getName() + ", " + medicine.getCountry() + ")");
                statement.close();
            } catch (SQLException exception) {
                log.log(Level.SEVERE, "SQL exception", exception);
            }

        }
    }
}
