package com.nc.tradox.dao;

import com.nc.tradox.dao.impl.TradoxDataAccessService;
import com.nc.tradox.model.SpeedLimit;
import com.nc.tradox.utilities.SpeedLimitApi;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SpeedLimitFillDB {

    private static final Logger log = Logger.getLogger(SpeedLimitFillDB.class.getName());
    TradoxDataAccessService tradoxDataAccessService = new TradoxDataAccessService();
    SpeedLimitApi speedLimitApi = new SpeedLimitApi();

    public void speedLimitFillDB() {
        List<SpeedLimit> speedLimitList = speedLimitApi.speedLimitList();
        if (speedLimitList != null){
            try {
                int count = 0;
                Statement statement = tradoxDataAccessService.connection.createStatement();
                for (SpeedLimit speedLimit: speedLimitList){
                    if (speedLimit == null ||
                            speedLimit.getCountry().getShortName() == null) continue;
                    String speedLimitQuery = "INSERT INTO SPEED_LIMITS(SPEED, ROAD_TYPE, COUNTRY_ID) " +
                            "VALUES ('" + speedLimit.getSpeed() + "', '" +
                            speedLimit.getTypeOfRoad() + "', '" +
                            speedLimit.getCountry().getShortName() + "')";
                    System.out.println(speedLimitQuery);
                    statement.executeUpdate(speedLimitQuery);
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
