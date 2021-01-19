package com.nc.tradox.dao;

import com.nc.tradox.model.*;

import java.util.Map;

public interface Dao {
    Route getRoute();

    User auth(String email, String password);

    Country getCountryById(String id);

    Country getCountryByFullName(String fullName);

    Passport getPassportById(String id);

    Route getRouteById(String id);

    User getUserById(int id);

    Boolean register(Map<String, String> info);

    Boolean deleteUser(User user);

    Map<String, Status.StatusEnum> getCountriesWhereNameLike(String curCountryId, String search);
}
