//package com.nc.tradox.dao;
//
//import com.nc.tradox.dao.impl.TradoxDataAccessService;
//import com.nc.tradox.model.Country;
//import com.nc.tradox.utilities.CountryApi;
//
//import java.io.IOException;
//import java.sql.Connection;
//import java.sql.SQLException;
//import java.sql.Statement;
//import java.util.List;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//
//public class CountryFillDB {
//
//    private static final Logger log = Logger.getLogger(ConsulateFillDB.class.getName());
//    TradoxDataAccessService tradoxDataAccessService;
//    CountryApi countryApi;
//
//    public void consulateFillDB() {
//        Connection connection = tradoxDataAccessService.connection;
//        List<Country> countryList = null;
//        try {
//            countryList = countryApi.fillCountry();
//        } catch (IOException | InterruptedException e) {
//            e.printStackTrace();
//        }
//        if (countryList != null)
//            for (Country country : countryList) {
//                try {
//                    Statement statement = connection.createStatement();
//                    int rows = statement.executeUpdate(
//                            "INSERT INTO COUNTRY(COUNTRY_ID, FULL_NAME, SHORT_NAME, CURRENCY, MEDIUM_BIL, TOURISM_COUNT)" +
//                                    "VALUES (" +
//                                    country.getShortName() + ", " +
//                                    country.getFullName() + ", " +
//                                    country.getShortName() + ", " +
//                                    country.getCurrency() + ", " +
//                                    country.getMediumBill() + ", " +
//                                    country.getTourismCount() + ")");
//                    statement.close();
//                } catch (SQLException exception) {
//                    log.log(Level.SEVERE, "SQL exception", exception);
//                }
//            }
//    }
//
//}
