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
    TradoxDataAccessService tradoxDataAccessService = new TradoxDataAccessService();
    ConsulateApi consulateApi = new ConsulateApi();

    public void consulateFillDB() {
        List<Consulate> consulateList = consulateApi.consulateList();
        if (consulateList != null){
            try {
                int count = 0;
                Statement statement = tradoxDataAccessService.connection.createStatement();
                for (Consulate consulate : consulateList) {
                    if (consulate == null ||
                            consulate.getFullRoute().getDeparture().getShortName() == null ||
                            consulate.getFullRoute().getDestination().getShortName() == null) continue;
                    String consulateQuery = "INSERT INTO CONSULATE(CITY, ADDRESS, PHONE, OWNER_ID, COUNTRY_ID) " +
                            "VALUES ('" + consulate.getCity().replace("'", " ") + "', '" +
                            consulate.getAddress() + "', '" +
                            consulate.getPhoneNumber() + "', '" +
                            consulate.getFullRoute().getDeparture().getShortName() + "', '" +
                            consulate.getFullRoute().getDestination().getShortName() + "')";
                    System.out.println(consulateQuery);
                    statement.executeUpdate(consulateQuery);
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
