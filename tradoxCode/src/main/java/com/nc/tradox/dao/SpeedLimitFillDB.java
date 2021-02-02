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
    TradoxDataAccessService tradoxDataAccessService;
    SpeedLimitApi speedLimitApi;

    public void speedLimitFillDB() {
        Connection connection = tradoxDataAccessService.connection;
        List<SpeedLimit> speedLimitList = speedLimitApi.speedLimitList();

        for (SpeedLimit speedLimit : speedLimitList) {
            try {
                Statement statement = connection.createStatement();
                int rows = statement.executeUpdate(
                        "INSERT INTO SPEED_LIMITS(SPEED, ROAD_TYPE, COUNTRY_ID)" +
                                "VALUES (" + speedLimit.getSpeed() + ", " +
                                speedLimit.getTypeOfRoad() + ", " +
                                speedLimit.getCountry() + ")");
                statement.close();
            } catch (SQLException exception) {
                log.log(Level.SEVERE, "SQL exception", exception);
            }
        }
    }
}
