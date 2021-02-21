package com.nc.tradox.utilities;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nc.tradox.dao.impl.TradoxDataAccessService;
import com.nc.tradox.model.Country;
import com.nc.tradox.model.SpeedLimit;
import com.nc.tradox.model.impl.SpeedLimitImpl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SpeedLimitApi {

    private static final Logger log = Logger.getLogger(SpeedLimitApi.class.getName());
    TradoxDataAccessService tradoxDataAccessService = new TradoxDataAccessService();

    public List<SpeedLimit> speedLimitList() {
        ObjectMapper objectMapper = new ObjectMapper();

        File jsonFile = new File("tradoxCode/src/main/resources/jsonsAndFriends/speedLimits.json");

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

        List<SpeedLimit> speedLimitList = new ArrayList<>();
        if (root != null) {
            com.nc.tradox.model.SpeedLimit.TypeOfRoad typeOfRoad;
            int speed;
            int count = 0;
            for (MainArr mainArr : root.mainArr) {
                count++;
                System.out.println(count);
                Country destination_country = tradoxDataAccessService.getCountryById(mainArr.country);

                for (int i = 0; i < 4; i++) {
                    if (i == 0) {
                        typeOfRoad = com.nc.tradox.model.SpeedLimit.TypeOfRoad.urban;
                        speed = mainArr.urban;
                    } else if (i == 1) {
                        typeOfRoad = com.nc.tradox.model.SpeedLimit.TypeOfRoad.rural;
                        speed = mainArr.rural;
                    } else if (i == 2) {
                        typeOfRoad = com.nc.tradox.model.SpeedLimit.TypeOfRoad.highway;
                        speed = mainArr.highway;
                    } else {
                        typeOfRoad = com.nc.tradox.model.SpeedLimit.TypeOfRoad.motorway;
                        speed = mainArr.motorway;
                    }

                    SpeedLimit speedLimit = new SpeedLimitImpl(
                            null,
                            typeOfRoad,
                            speed,
                            destination_country
                    );
                    speedLimitList.add(speedLimit);
                }
            }
            return speedLimitList;
        } else {
            log.log(Level.SEVERE, "Couldn't parse json to root class");
        }
        return null;
    }

    public static class MainArr {
        public String country;
        public int urban;
        public int rural;
        public int highway;
        public int motorway;
    }

    public static class Root {
        public List<MainArr> mainArr;
    }

}
