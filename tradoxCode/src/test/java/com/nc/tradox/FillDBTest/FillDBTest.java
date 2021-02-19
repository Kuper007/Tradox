package com.nc.tradox.FillDBTest;

import com.nc.tradox.dao.impl.TradoxDataAccessService;
import com.nc.tradox.model.*;
import com.nc.tradox.model.impl.NewsItemImpl;
import com.nc.tradox.utilities.*;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class FillDBTest {

    CountryApi countryApi = new CountryApi();
    CovidApi covidApi = new CovidApi();
    ConsulateApi consulateApi = new ConsulateApi();
    SpeedLimitApi speedLimitApi = new SpeedLimitApi();
    MedicineApi medicineApi = new MedicineApi();
    DocumentApi documentApi = new DocumentApi();
    NewsApi newsApi = new NewsApi();
    TradoxDataAccessService tradoxDataAccessService = new TradoxDataAccessService();

    public void testFillCountry(){
        List<Country> countryList = countryApi.fillCountryName();
        List<CountryApi.CountryInfo> countryInfoList = countryApi.fillCountryInfo();

        if (countryList != null && countryInfoList != null){
            try {
                Statement statement = tradoxDataAccessService.connection.createStatement();

                for (int i = 0; i < 253; i++){
                    String countryQuery = "INSERT INTO COUNTRY(COUNTRY_ID, FULL_NAME, SHORT_NAME, CURRENCY, MEDIUM_BILL, TOURISM_COUNT)" +
                        "SELECT '" + countryList.get(i).getShortName() + "', '" +
                        countryList.get(i).getFullName() + "', '" +
                        countryList.get(i).getShortName() + "', '" +
                        countryInfoList.get(i).getCurrency() + "', " +
                        countryInfoList.get(i).getMediumBill() + ", " +
                        countryInfoList.get(i).getTourismCount() + " " +
                        "from dual where not exists (select * from COUNTRY WHERE COUNTRY_ID = '"+ countryList.get(i).getShortName() +"')";

                    statement.executeUpdate(countryQuery);
                }

            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        }
    }

    public void testFillCovid(){
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
                exception.printStackTrace();
            }
        }
    }

    public void testFillConsulate(){
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
                exception.printStackTrace();
            }
        }
    }

    public void testFillSpeedLimit(){
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
                exception.printStackTrace();
            }
        }
    }

    public void testFillMedicine(){
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
                exception.printStackTrace();
            }
        }
    }

    public void testFillHaveDocs(){
        List<HaveDocument> haveDocumentList = documentApi.documentList();
        if (haveDocumentList != null){
            try {
                int count = 0;
                Statement statement = tradoxDataAccessService.connection.createStatement();
                for (HaveDocument haveDocument : haveDocumentList) {
                    if (haveDocument == null ||
                        haveDocument.getDestination() == null ||
                        haveDocument.getDeparture() == null ||
                        haveDocument.getDestination().getShortName() == null ||
                        haveDocument.getDeparture().getShortName() == null) continue;
                    String haveDocumentQuery = "INSERT INTO HAVE_DOCUMENT(DOCUMENT_ID, DESTINATION_ID, DEPARTURE_ID) " +
                            "VALUES (" + haveDocument.getDocumentId() + ", '" +
                            haveDocument.getDestination().getShortName() + "', '" +
                            haveDocument.getDeparture().getShortName() + "')";
                    System.out.println(haveDocumentQuery);
                    statement.executeUpdate(haveDocumentQuery);
                    count++;
                    if (count == 200){
                        count = 0;
                        statement.close();
                        statement = tradoxDataAccessService.connection.createStatement();
                    }
                }
                statement.close();
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        }
    }

    public void testFillNews(){
        List<NewsItem> newsItemList = null;
        try {
            newsItemList = newsApi.news();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (newsItemList != null){
            try {
                int count = 0;
                Statement statement = tradoxDataAccessService.connection.createStatement();
                for (NewsItem newsItem: newsItemList) {
                    String haveDocumentQuery = "INSERT INTO NEWS(TEXT, \"DATE\", COUNTRY_ID) " +
                            "VALUES ('" + newsItem.getText().replace("'", " ") + "', " +
                            "TO_DATE(sysdate, 'YYYY-MM-DD HH24:MI:SS')" + ", '" +
                            newsItem.getCountry().getShortName() + "')";
                    System.out.println(haveDocumentQuery);
                    statement.executeUpdate(haveDocumentQuery);
                    count++;
                    if (count == 200){
                        count = 0;
                        statement.close();
                        statement = tradoxDataAccessService.connection.createStatement();
                    }
                }
                statement.close();
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        }
    }

    public void testTest(){
        try {
            Statement statement = tradoxDataAccessService.connection.createStatement();

            NewsItem newsItem = new NewsItemImpl();
            newsItem.setText("1");
            newsItem.setDate(java.util.Calendar.getInstance().getTime());

            String haveDocumentQuery = "INSERT INTO NEWS(TEXT, DATE, COUNTRY_ID) " +
                    "VALUES ('" + newsItem.getText() + "', " +
                    newsItem.getDate() + ", '" +
                    "UA" + "')";
            statement.executeUpdate(haveDocumentQuery);
            statement.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public static void main(String[] args) {
        FillDBTest fillDBTest = new FillDBTest();
        //fillDBTest.testFillCountry();
        //fillDBTest.testFillCovid();
        //fillDBTest.testFillConsulate();
        //fillDBTest.testFillSpeedLimit();
        //fillDBTest.testFillMedicine();
        //fillDBTest.testFillHaveDocs();
        //fillDBTest.testFillNews();
        //fillDBTest.testTest();
    }

}
