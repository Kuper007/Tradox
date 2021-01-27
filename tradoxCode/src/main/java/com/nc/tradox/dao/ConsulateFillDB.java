package com.nc.tradox.dao;

import com.nc.tradox.dao.impl.TradoxDataAccessService;
import com.nc.tradox.model.Consulate;
import com.nc.tradox.utilities.ConsulateApi;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConsulateFillDB {

    private static final Logger log = Logger.getLogger(ConsulateFillDB.class.getName());
    TradoxDataAccessService tradoxDataAccessService;
    ConsulateApi consulateApi;

    public void consulateFillDB() {
        Connection connection = tradoxDataAccessService.connection;
        List<Consulate> consulateList = consulateApi.consulateList();

        for (Consulate consulate : consulateList) {
            try {
                Statement statement = connection.createStatement();
                int rows = statement.executeUpdate(
                        "INSERT INTO CONSULATE(CITY, ADDRESS, PHONE, OWNER_ID, COUNTRY_ID)" +
                                "VALUES (" + consulate.getCityOfConsulate() + ", " +
                                consulate.getAddressOfConsulate() + ", " +
                                consulate.getPhoneNumberOfConsulate() + ", " +
                                consulate.getDepartureCountry() + ", " +
                                consulate.getDestinationCountry() + ")");
                statement.close();
            } catch (SQLException exception) {
                log.log(Level.SEVERE, "SQL exception", exception);
            }
        }
    }
}
