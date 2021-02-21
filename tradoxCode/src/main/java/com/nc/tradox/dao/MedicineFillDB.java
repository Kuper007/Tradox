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
    TradoxDataAccessService tradoxDataAccessService = new TradoxDataAccessService();
    MedicineApi medicineApi = new MedicineApi();

    public void medicineFillDB() {
        List<Medicine> medicineList = medicineApi.medicineList();
        if (medicineList != null){
            try {
                int count = 0;
                Statement statement = tradoxDataAccessService.connection.createStatement();
                for (Medicine medicine: medicineList){
                    if (medicine == null ||
                            medicine.getCountry().getShortName() == null) continue;
                    String medicineQuery = "INSERT INTO MEDICINE(NAME, COUNTRY_ID)" +
                            "VALUES ('" + medicine.getName() + "', '" +
                            medicine.getCountry().getShortName() + "')";
                    System.out.println(medicineQuery);
                    statement.executeUpdate(medicineQuery);
                    count++;
                    if (count == 200){
                        count = 0;
                        statement.close();
                        statement = tradoxDataAccessService.connection.createStatement();
                    }
                }
                statement.close();
            } catch (SQLException exception) {
                log.log(Level.SEVERE, "SQL error", exception);
            }
        }
    }
}
