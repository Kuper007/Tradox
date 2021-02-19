package com.nc.tradox.dao;

import com.nc.tradox.dao.impl.TradoxDataAccessService;
import com.nc.tradox.model.Country;
import com.nc.tradox.model.Covid;
import com.nc.tradox.utilities.CountryApi;
import com.nc.tradox.utilities.CovidApi;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CovidFillDB {

    private static final Logger log = Logger.getLogger(ConsulateFillDB.class.getName());
    TradoxDataAccessService tradoxDataAccessService = new TradoxDataAccessService();
    CovidApi covidApi = new CovidApi();
    CountryApi countryApi = new CountryApi();

    public void consulateFillDB() {
        List<Country> countryList = countryApi.fillCountryName();

        if (countryList != null){
            try {
                Statement statement = tradoxDataAccessService.connection.createStatement();
                for (int i = 0; i < 50; i++){
                    Covid covid = covidApi.covidInfo(countryList.get(i).getFullName().toLowerCase());
                    String covidQuery =
                            "INSERT INTO COVID_INFO(SUMMARY_TOTAL_CASES," +
                                    "TODAY_TOTAL_CASES, SUMMARY_ACTIVE_CASES," +
                                    "TODAY_ACTIVE_CASES, SUMMARY_DEATHS," +
                                    "TODAY_DEATHS, SUMMARY_RECOVERED," +
                                    "TODAY_RECOVERED, COUNTRY_ID)" +
                                    "VALUES (" +
                                    covid.getSummaryTotalCases() + ", " +
                                    covid.getTodayTotalCases() + ", " +
                                    covid.getSummaryActiveCases() + ", " +
                                    covid.getTodayActiveCases() + ", " +
                                    covid.getTodayDeaths() + ", " +
                                    covid.getTodayDeaths() + ", " +
                                    covid.getSummaryRecovered() + ", " +
                                    covid.getTodayRecovered() + ", '" +
                                    countryList.get(i).getShortName() + "')";
                    statement.executeUpdate(covidQuery);
                }

                for (int i = 50; i < 100; i++){
                    Covid covid = covidApi.covidInfo(countryList.get(i).getFullName().toLowerCase());
                    String covidQuery =
                            "INSERT INTO COVID_INFO(SUMMARY_TOTAL_CASES," +
                                    "TODAY_TOTAL_CASES, SUMMARY_ACTIVE_CASES," +
                                    "TODAY_ACTIVE_CASES, SUMMARY_DEATHS," +
                                    "TODAY_DEATHS, SUMMARY_RECOVERED," +
                                    "TODAY_RECOVERED, COUNTRY_ID)" +
                                    "VALUES (" +
                                    covid.getSummaryTotalCases() + ", " +
                                    covid.getTodayTotalCases() + ", " +
                                    covid.getSummaryActiveCases() + ", " +
                                    covid.getTodayActiveCases() + ", " +
                                    covid.getTodayDeaths() + ", " +
                                    covid.getTodayDeaths() + ", " +
                                    covid.getSummaryRecovered() + ", " +
                                    covid.getTodayRecovered() + ", '" +
                                    countryList.get(i).getShortName() + "')";
                    statement.executeUpdate(covidQuery);
                }

                for (int i = 100; i < 150; i++){
                    Covid covid = covidApi.covidInfo(countryList.get(i).getFullName().toLowerCase());
                    String covidQuery =
                            "INSERT INTO COVID_INFO(SUMMARY_TOTAL_CASES," +
                                    "TODAY_TOTAL_CASES, SUMMARY_ACTIVE_CASES," +
                                    "TODAY_ACTIVE_CASES, SUMMARY_DEATHS," +
                                    "TODAY_DEATHS, SUMMARY_RECOVERED," +
                                    "TODAY_RECOVERED, COUNTRY_ID)" +
                                    "VALUES (" +
                                    covid.getSummaryTotalCases() + ", " +
                                    covid.getTodayTotalCases() + ", " +
                                    covid.getSummaryActiveCases() + ", " +
                                    covid.getTodayActiveCases() + ", " +
                                    covid.getTodayDeaths() + ", " +
                                    covid.getTodayDeaths() + ", " +
                                    covid.getSummaryRecovered() + ", " +
                                    covid.getTodayRecovered() + ", '" +
                                    countryList.get(i).getShortName() + "')";
                    statement.executeUpdate(covidQuery);
                }

                for (int i = 150; i < 200; i++){
                    Covid covid = covidApi.covidInfo(countryList.get(i).getFullName().toLowerCase());
                    String covidQuery =
                            "INSERT INTO COVID_INFO(SUMMARY_TOTAL_CASES," +
                                    "TODAY_TOTAL_CASES, SUMMARY_ACTIVE_CASES," +
                                    "TODAY_ACTIVE_CASES, SUMMARY_DEATHS," +
                                    "TODAY_DEATHS, SUMMARY_RECOVERED," +
                                    "TODAY_RECOVERED, COUNTRY_ID)" +
                                    "VALUES (" +
                                    covid.getSummaryTotalCases() + ", " +
                                    covid.getTodayTotalCases() + ", " +
                                    covid.getSummaryActiveCases() + ", " +
                                    covid.getTodayActiveCases() + ", " +
                                    covid.getTodayDeaths() + ", " +
                                    covid.getTodayDeaths() + ", " +
                                    covid.getSummaryRecovered() + ", " +
                                    covid.getTodayRecovered() + ", '" +
                                    countryList.get(i).getShortName() + "')";
                    statement.executeUpdate(covidQuery);
                }

                for (int i = 200; i < 253; i++){
                    Covid covid = covidApi.covidInfo(countryList.get(i).getFullName().toLowerCase());
                    String covidQuery =
                            "INSERT INTO COVID_INFO(SUMMARY_TOTAL_CASES," +
                                    "TODAY_TOTAL_CASES, SUMMARY_ACTIVE_CASES," +
                                    "TODAY_ACTIVE_CASES, SUMMARY_DEATHS," +
                                    "TODAY_DEATHS, SUMMARY_RECOVERED," +
                                    "TODAY_RECOVERED, COUNTRY_ID)" +
                                    "VALUES (" +
                                    covid.getSummaryTotalCases() + ", " +
                                    covid.getTodayTotalCases() + ", " +
                                    covid.getSummaryActiveCases() + ", " +
                                    covid.getTodayActiveCases() + ", " +
                                    covid.getTodayDeaths() + ", " +
                                    covid.getTodayDeaths() + ", " +
                                    covid.getSummaryRecovered() + ", " +
                                    covid.getTodayRecovered() + ", '" +
                                    countryList.get(i).getShortName() + "')";
                    statement.executeUpdate(covidQuery);
                }

            } catch (SQLException | InterruptedException | IOException exception) {
                log.log(Level.SEVERE, "SQL error", exception);
            }
        }
    }
}