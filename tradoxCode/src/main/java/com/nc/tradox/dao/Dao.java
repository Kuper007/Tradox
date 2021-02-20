package com.nc.tradox.dao;

import com.nc.tradox.model.*;
import com.nc.tradox.model.impl.*;

import java.sql.ResultSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface Dao {
    Route getRoute(String userId, String destinationId);

    Response auth(String email, String password);

    Country getCountryById(String id);

    Passport getPassportById(String id);

    Route getRouteById(String id);

    Boolean saveRoute(Route route, Integer userId);

    Boolean registrate(User user, String password);

    boolean updateUser(User user);

    Boolean verifyUserById(int userId);

    Boolean changePassword(int id, String newPassword);

    boolean deleteUser(User user);

    @Deprecated
    boolean deleteUser(int userId);

    Map<String, Status.StatusEnum> getCountriesWhereNameLike(String countryId, String search);

    boolean deleteRoute(Integer id);

    Documents getDocumentsByCountryIds(FullRoute fullRoute);

    SpeedLimits getSpeedLimitsByCountryId(Country destination);

    Medicines getMedicinesByCountryId(Country destination);

    Consulates getConsulatesByCountryIds(FullRoute fullRoute);

    News getNewsByCountryId(Country destination);

    Status getStatusByCountryIds(FullRoute fullRoute);

    Covid getCovidInfo(Country country);

    double getMediumBill(Country country);

    int getTourismCount(Country country);

    String getCurrency(Country country);

    Boolean saveTransit(Set<InfoData> infoData, Integer route_id);

    Country getCountryByFullName(String fullName);

    User getUserById(int userId);

    Country getUserLocationById(int userId);

    Integer getUserByEmail(String email);

    boolean isUser(String email);

    ResultSet transitFor(int routeId, InfoData infoData);

    ResultSet changeTransitOrder(int transitId, int newOrder);

    ResultSet createNewTransit(int order, String countryId, int routeId);

    Route getRoute();

    boolean isPassport(String passportId);

    boolean addPassport(Passport passport);

    boolean deletePassport(Passport passport);

    List<Country> getAllCountries();

    boolean isCountry(String fullName);

    List<CountryOld> getCountryList();


}
