package com.nc.tradox.dao;

import com.nc.tradox.model.*;
import com.nc.tradox.model.impl.CovidImpl;

import java.util.Map;

public interface Dao {
    Route getRoute();
    User auth(String email, String password);
    Country getCountryById(String id);
    Passport getPassportById(String id);
    Route getRouteById(String id);
    Boolean registrate(Map<String, String> info);
    Boolean deleteUser(User user);
    CovidImpl getCovidByCountryId(String id);
    InfoData getInfoData(String departureId, String destinationId);
    Map<String, Status.StatusEnum> getCountriesWhereNameLike(String countryId,String search);
}
