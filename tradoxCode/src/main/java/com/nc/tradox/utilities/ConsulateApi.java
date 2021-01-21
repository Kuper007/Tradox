package com.nc.tradox.utilities;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nc.tradox.dao.TradoxDataAccessService;
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
    TradoxDataAccessService tradoxDataAccessService;

    public List<Consulate> consulateList(){
        ObjectMapper objectMapper = new ObjectMapper();

        File jsonFile = new File("tradoxCode/src/main/resources/jsonsAndFriends/consulate.json");

        if (!jsonFile.exists()){
            log.log(Level.SEVERE,"File does not exist");
        }else if (jsonFile.isDirectory()){
            log.log(Level.SEVERE,"This is directory, not file");
        }

        Root root = null;
        try {
            root = objectMapper.readValue(jsonFile, Root.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<Consulate> consulateList = new ArrayList<>();
        if (root != null) {
            for (MyArray myArray : root.myArrays) {

                Country ownerCountry = tradoxDataAccessService.getCountryById(myArray.owner_id);
                Country country = tradoxDataAccessService.getCountryById(myArray.country_id);

                Departure departure = new DepartureImpl(ownerCountry);
                Destination destination = new DestinationImpl(country);

                FullRoute fullRoute = new FullRouteImpl(departure, destination);

                Consulate consulate = new ConsulateImpl(
                        null,
                        ownerCountry,
                        myArray.city,
                        myArray.address.street + " " + myArray.address.number + " " + myArray.address.zip_code,
                        myArray.phone,
                        fullRoute
                        );
                consulateList.add(consulate);
            }
            return consulateList;
        }else {
            log.log(Level.SEVERE,"Couldn't parse json to root class");
        }
        return null;
    }

    public static class Address{
        public String street;
        public String number;
        public String zip_code;
    }

    public static class MyArray{
        public int consulate_id;
        public String city;
        public Address address;
        public String phone;
        public String owner_id;
        public String country_id;
    }

    public static class Root{
        public List<MyArray> myArrays;
    }
}
