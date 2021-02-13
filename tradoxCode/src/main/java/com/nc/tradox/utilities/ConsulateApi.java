package com.nc.tradox.utilities;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nc.tradox.dao.impl.TradoxDataAccessService;
import com.nc.tradox.model.*;
import com.nc.tradox.model.impl.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConsulateApi {

    private static final Logger log = Logger.getLogger(ConsulateApi.class.getName());
    TradoxDataAccessService tradoxDataAccessService = new TradoxDataAccessService();

    public List<Consulate> consulateList() {
        ObjectMapper objectMapper = new ObjectMapper();

        File jsonFile = new File("tradoxCode/src/main/resources/jsonsAndFriends/consulates.json");

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

        List<Consulate> consulateList = new ArrayList<>();
        if (root != null) {
            int count = 0;
            for (MyArray myArray : root.myArrays) {
                count++;
                System.out.println(count);
                Country ownerCountry = tradoxDataAccessService.getCountryById(myArray.owner_id);
                Country country = tradoxDataAccessService.getCountryById(myArray.country_id);

                FullRoute fullRoute = new FullRouteImpl(ownerCountry, country);

                Consulate consulate = new ConsulateImpl(
                        null,
                        myArray.city,
                        myArray.address.street + " " + myArray.address.number,
                        myArray.phone,
                        fullRoute
                );
                consulateList.add(consulate);
            }
            return consulateList;
        } else {
            log.log(Level.SEVERE, "Couldn't parse json to root class");
        }
        return null;
    }

    public static class Address {
        public String street;
        public String number;
        public String zip_code;
    }

    public static class MyArray {
        public int consulate_id;
        public String city;
        public Address address;
        public String phone;
        public String owner_id;
        public String country_id;
    }

    public static class Root {
        @JsonProperty("MyArray")
        public List<MyArray> myArrays;
    }

}
