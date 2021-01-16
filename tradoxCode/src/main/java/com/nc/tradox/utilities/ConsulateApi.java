package com.nc.tradox.utilities;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nc.tradox.dao.impl.TradoxDataAccessService;
import com.nc.tradox.model.*;
import com.nc.tradox.model.impl.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ConsulateApi {

    TradoxDataAccessService tradoxDataAccessService;

    public List<Consulate> consulateList(){
        ObjectMapper objectMapper = new ObjectMapper();

        File jsonFile = new File(" ");

        if (!jsonFile.exists()){
            System.out.println("File does not exist");
            System.exit(-1);
        }else if (jsonFile.isDirectory()){
            System.out.println("This is directory, not file");
            System.exit(-1);
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
