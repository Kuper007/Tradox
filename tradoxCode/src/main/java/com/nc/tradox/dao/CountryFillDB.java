package com.nc.tradox.dao;

import com.nc.tradox.dao.impl.TradoxDataAccessService;
import com.nc.tradox.model.Country;
import com.nc.tradox.utilities.CountryApi;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CountryFillDB {

    private static final Logger log = Logger.getLogger(ConsulateFillDB.class.getName());
    TradoxDataAccessService tradoxDataAccessService = new TradoxDataAccessService();
    CountryApi countryApi = new CountryApi();

    public void consulateFillDB() {
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
                log.log(Level.SEVERE, "SQL error", exception);
            }
        }
    }

}
