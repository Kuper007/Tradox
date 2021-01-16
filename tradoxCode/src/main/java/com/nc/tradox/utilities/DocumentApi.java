package com.nc.tradox.utilities;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nc.tradox.dao.impl.TradoxDataAccessService;
import com.nc.tradox.model.Country;
import com.nc.tradox.model.Departure;
import com.nc.tradox.model.Destination;
import com.nc.tradox.model.impl.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DocumentApi {

    TradoxDataAccessService tradoxDataAccessService;

    public List<HaveDocument> documentList(){
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

        List<HaveDocument> haveDocumentList = new ArrayList<>();
        if (root != null) {
            for (MainArr mainArr : root.mainArr) {
                HaveDocument haveDocument = new HaveDocument();

                Country departure_country = tradoxDataAccessService.getCountryById(mainArr.departure_country);
                Departure departure = new DepartureImpl(departure_country);

                haveDocument.setDeparture(departure);

                for (Arr arr : mainArr.arr) {
                    Country destination_country = tradoxDataAccessService.getCountryById(arr.destination_country);
                    Destination destination = new DestinationImpl(destination_country);

                    haveDocument.setDocumentId(arr.document_id);
                    haveDocument.setDestination(destination);
                }
                haveDocumentList.add(haveDocument);
            }
            return haveDocumentList;
        }
        return null;
    }

    public static class Arr{
        public String destination_country;
        public int document_id;
    }

    public static class MainArr{
        public String departure_country;
        public List<Arr> arr;
    }

    public static class Root{
        public List<MainArr> mainArr;
    }
}
