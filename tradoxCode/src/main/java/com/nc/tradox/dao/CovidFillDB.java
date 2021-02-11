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
//public class CovidFillDB {
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
//                            "INSERT INTO COVID_INFO(SUMMARY_TOTAL_CASES," +
//                                    "TODAY_TOTAL_CASES, SUMMARY_ACTIVE_CASES," +
//                                    "TODAY_ACTIVE_CASES, SUMMARY_DEATHS," +
//                                    "TODAY_DEATHS, SUMMARY_RECOVERED," +
//                                    "TODAY_RECOVERED, COUNTRY_ID)" +
//                                    "VALUES (" +
//                                    country.getCovidInfo().getSummaryTotalCases() + ", " +
//                                    country.getCovidInfo().getTodayTotalCases() + ", " +
//                                    country.getCovidInfo().getSummaryActiveCases() + ", " +
//                                    country.getCovidInfo().getTodayActiveCases() + ", " +
//                                    country.getCovidInfo().getTodayDeaths() + ", " +
//                                    country.getCovidInfo().getTodayDeaths() + ", " +
//                                    country.getCovidInfo().getSummaryRecovered() + ", " +
//                                    country.getCovidInfo().getTodayRecovered() + ", " +
//                                    country.getShortName() + ")");
//                    statement.close();
//                } catch (
//                        SQLException exception) {
//                    log.log(Level.SEVERE, "SQL exception", exception);
//                }
//            }
//    }
//}