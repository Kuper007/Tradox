package com.nc.tradox.utilities;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nc.tradox.model.Country;
import com.nc.tradox.model.impl.CountryImpl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CountryApi {

    private static final Logger log = Logger.getLogger(CountryApi.class.getName());
    ObjectMapper objectMapper = new ObjectMapper();

    private void isFileExists(File jsonFile){
        if (!jsonFile.exists()){
            log.log(Level.SEVERE,"File does not exist");
        }else if (jsonFile.isDirectory()){
            log.log(Level.SEVERE,"This is directory, not file");
        }
    }

    private List<Double> bigMacFromJson(){

        File bigMacJsonFile = new File("tradoxCode/src/main/resources/jsonsAndFriends/bigMac.json");

        isFileExists(bigMacJsonFile);

        BigMacRoot bigMacRoot = null;
        try {
            bigMacRoot = objectMapper.readValue(bigMacJsonFile, BigMacRoot.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<Double> bigMacIndexList = new ArrayList<>();
        if (bigMacRoot!=null) {
            for (BigMacData bigMacData : bigMacRoot.bigMacData) {
                bigMacIndexList.add(bigMacData.bigMacIndex);
            }
        }
        return bigMacIndexList;
    }

    private List<Integer> touristCountFromJson(){
        File touristCountJsonFile = new File("tradoxCode/src/main/resources/jsonsAndFriends/touristCount.json");

        isFileExists(touristCountJsonFile);

        TouristCountRoot touristCountRoot = null;
        try {
            touristCountRoot = objectMapper.readValue(touristCountJsonFile, TouristCountRoot.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<Integer> touristCountList = new ArrayList<>();
        if (touristCountRoot!=null) {
            for (TouristCountData touristCountData : touristCountRoot.touristCountData) {
                touristCountList.add(touristCountData.touristCount);
            }
        }
        return touristCountList;
    }

    private List<String> currencyCodeFromJson(){
        File currencyCodeJsonFile = new File("tradoxCode/src/main/resources/jsonsAndFriends/currencyCode.json");

        isFileExists(currencyCodeJsonFile);

        CurrencyCodeRoot currencyCodeRoot = null;
        try {
            currencyCodeRoot = objectMapper.readValue(currencyCodeJsonFile, CurrencyCodeRoot.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<String> currencyCodeList = new ArrayList<>();
        if (currencyCodeRoot!=null) {
            for (CurrencyCodeData currencyCodeData : currencyCodeRoot.currencyCodeData) {
                currencyCodeList.add(currencyCodeData.currencyCode);
            }
        }
        return currencyCodeList;
    }

    private List<String> countryFullNameFromJson(){
        File countryFullNameJsonFile = new File("tradoxCode/src/main/resources/jsonsAndFriends/countryFullName.json");

        isFileExists(countryFullNameJsonFile);

        CountryNameRoot countryNameRoot = null;
        try {
            countryNameRoot = objectMapper.readValue(countryFullNameJsonFile, CountryNameRoot.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<String> countryNameList = new ArrayList<>();
        if (countryNameRoot!=null) {
            for (CountryNameData countryNameData : countryNameRoot.countryNameData) {
                countryNameList.add(countryNameData.countryFullName);
            }
        }
        return countryNameList;
    }

    private List<String> countryNameFromJson(){
        File countryNameJsonFile = new File("tradoxCode/src/main/resources/jsonsAndFriends/countryFullName.json");

        isFileExists(countryNameJsonFile);

        CountryNameRoot countryNameRoot = null;
        try {
            countryNameRoot = objectMapper.readValue(countryNameJsonFile, CountryNameRoot.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<String> countryNameList = new ArrayList<>();
        if (countryNameRoot!=null) {
            for (CountryNameData countryNameData : countryNameRoot.countryNameData) {
                countryNameList.add(countryNameData.countryName);
            }
        }
        return countryNameList;
    }

    public List<Country> fillCountryName() {

        List<String> countryFullNameList = countryFullNameFromJson();
        List<String> countryNameList = countryNameFromJson();

        List<Country> countryNamesList = new ArrayList<>();

        for (int i = 0; i < 253; i++) {
            countryNamesList.add(new CountryImpl(
                            countryNameList.get(i),
                            countryFullNameList.get(i)));
        }
        return countryNamesList;
    }

    public List<CountryInfo> fillCountryInfo() {

        List<Double> bigMacList = bigMacFromJson();
        List<Integer> touristCountList = touristCountFromJson();
        List<String> currencyCodeList = currencyCodeFromJson();

        List<CountryInfo> countryInfoList = new ArrayList<>();

        for (int i = 0; i < 253; i++) {
            countryInfoList.add(new CountryInfo(
                    currencyCodeList.get(i),
                    bigMacList.get(i),
                    touristCountList.get(i)));
        }
        return countryInfoList;
    }

    public static class CountryInfo{

        private final String currency;
        private final double mediumBill;
        private final int tourismCount;

        public CountryInfo(String currency, double mediumBill, int tourismCount) {
            this.currency = currency;
            this.mediumBill = mediumBill;
            this.tourismCount = tourismCount;
        }

        public String getCurrency() {
            return currency;
        }

        public double getMediumBill() {
            return mediumBill;
        }

        public int getTourismCount() {
            return tourismCount;
        }
    }

    public static class BigMacData{
        public String countryName;
        public double bigMacIndex;
    }

    public static class BigMacRoot{
        @JsonProperty("BigMacData")
        public List<BigMacData> bigMacData;
    }

    public static class TouristCountData{
        public String countryName;
        public int touristCount;
    }

    public static class TouristCountRoot{
        @JsonProperty("TouristCountData")
        public List<TouristCountData> touristCountData;
    }

    public static class CurrencyCodeData{
        public String countryName;
        public String currencyCode;
    }

    public static class CurrencyCodeRoot{
        @JsonProperty("CurrencyCodeData")
        public List<CurrencyCodeData> currencyCodeData;
    }

    public static class CountryNameData{
        public String countryName;
        public String countryFullName;
    }

    public static class CountryNameRoot{
        @JsonProperty("CountryNameData")
        public List<CountryNameData> countryNameData;
    }

}
