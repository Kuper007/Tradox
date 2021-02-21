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
    TradoxDataAccessService tradoxDataAccessService = new TradoxDataAccessService();

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
            int count = 0;
            for (MainArr mainArr : root.mainArr) {
                count++;
                System.out.println(count);
                Country country = tradoxDataAccessService.getCountryById(mainArr.country);

                String[] vaccines = new String[3];
                for (String s : vaccines) {
                    if (mainArr.vaccines.polio) {
                        vaccines[0] = "polio, ";
                    } else vaccines[0] = "";
                    if (mainArr.vaccines.hepatitisA) {
                        vaccines[1] = "hepatitisA, ";
                    } else vaccines[1] = "";
                    if (mainArr.vaccines.malaria) {
                        vaccines[2] = "malaria";
                    } else vaccines[2] = "";
                }
                String allVaccines = vaccines[0] + vaccines[1] + vaccines[2];
                if (allVaccines.length() < 4) allVaccines = "no vaccines required";

                Medicine medicine = new MedicineImpl(
                        null,
                        allVaccines,
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

    public static class Vaccines {
        @JsonProperty("Chickenpox")
        public boolean chickenpox;
        @JsonProperty("Cholera")
        public boolean cholera;
        @JsonProperty("Diphtheria-Tetanus-Pertussis")
        public boolean diphtheriaTetanusPertussis;
        @JsonProperty("Flu")
        public boolean flu;
        @JsonProperty("Hepatitis A")
        public boolean hepatitisA;
        @JsonProperty("Hepatitis B")
        public boolean hepatitisB;
        @JsonProperty("Japanese Encephalitis")
        public boolean japaneseEncephalitis;
        @JsonProperty("Malaria")
        public boolean malaria;
        @JsonProperty("Measles")
        public boolean measles;
        @JsonProperty("Measles-Mumps-Rubella")
        public boolean measlesMumpsRubella;
        @JsonProperty("Meningitis (Meningococcal disease)")
        public boolean meningitisMeningococcaldisease;
        @JsonProperty("Polio")
        public boolean polio;
        @JsonProperty("Typhoid")
        public boolean typhoid;
        @JsonProperty("Rabies")
        public boolean rabies;
        @JsonProperty("Yellow Fever")
        public boolean yellowFever;
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
        @JsonProperty("MainArr")
        public List<MainArr> mainArr;
    }

}
