package com.nc.tradox.utilities;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nc.tradox.dao.impl.TradoxDataAccessService;
import com.nc.tradox.model.Country;
import com.nc.tradox.model.Medicine;
import com.nc.tradox.model.impl.MedicineImpl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MedicineApi {

    private static final Logger log = Logger.getLogger(MedicineApi.class.getName());
    TradoxDataAccessService tradoxDataAccessService;

    public List<Medicine> medicineList() {
        ObjectMapper objectMapper = new ObjectMapper();

        File jsonFile = new File("tradoxCode/src/main/resources/jsonsAndFriends/medicine.json");

        if (!jsonFile.exists()) {
            log.log(Level.SEVERE, "File does not exist");
        } else if (jsonFile.isDirectory()) {
            log.log(Level.SEVERE, "This is directory, not file");
        }

        Root root = null;
        try {
            root = objectMapper.readValue(jsonFile, Root.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<Medicine> medicineArrayList = new ArrayList<>();
        if (root != null) {
            for (MainArr mainArr : root.mainArr) {

                Country country = tradoxDataAccessService.getCountryById(mainArr.country);

                Medicine medicine = new MedicineImpl(
                        null,
                        mainArr.covidInfo,
                        country
                );
                medicineArrayList.add(medicine);
            }
            return medicineArrayList;
        } else {
            log.log(Level.SEVERE, "Couldn't parse json to root class");
        }
        return null;
    }

    public static class Alltravelers {
        @JsonProperty("Chickenpox")
        public boolean chickenpox;
        @JsonProperty("Diphtheria-Tetanus-Pertussis")
        public boolean diphtheriaTetanusPertussis;
        @JsonProperty("Flu")
        public boolean flu;
        @JsonProperty("Measles")
        public boolean measles;
        @JsonProperty("Measles-Mumps-Rubella")
        public boolean measlesMumpsRubella;
        @JsonProperty("Polio")
        public boolean polio;
    }

    public static class Mosttravelers {
        @JsonProperty("Hepatitis A")
        public boolean hepatitisA;
        @JsonProperty("Typhoid")
        public boolean typhoid;
    }

    public static class Sometravelers {
        @JsonProperty("Cholera")
        public boolean cholera;
        @JsonProperty("Hepatitis B")
        public boolean hepatitisB;
        @JsonProperty("Malaria")
        public boolean malaria;
        @JsonProperty("Rabies")
        public boolean rabies;
        @JsonProperty("Yellow Fever")
        public boolean yellowFever;
    }

    public static class Vaccines {
        @JsonProperty("All travelers")
        public Alltravelers alltravelers;
        @JsonProperty("Most travelers")
        public Mosttravelers mosttravelers;
        @JsonProperty("Some travelers")
        public Sometravelers sometravelers;
    }

    public static class MainArr {
        @JsonProperty("Country")
        public String country;
        @JsonProperty("CovidInfo")
        public String covidInfo;
        @JsonProperty("Vaccines")
        public Vaccines vaccines;
    }

    public static class Root {
        public List<MainArr> mainArr;
    }

}
