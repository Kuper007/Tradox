package com.nc.tradox.dao;

import com.nc.tradox.model.*;
import com.nc.tradox.model.Reason;
import com.nc.tradox.model.impl.*;

import java.sql.ResultSet;
import java.util.Map;
import java.util.Set;

public interface Dao {
    Route getRoute(String userId, String destinationId);

    Map<User, String> auth(String email, String password);

    Country getCountryById(String id);

    Passport getPassportById(String id);

    Route getRouteById(String id);

    Boolean saveRoute(Route route, Integer userId);

    Boolean registrate(User user, String password);

    boolean updateUser(User user);

    Boolean verifyUserById(int id);

    Boolean changePassword(int id, String newPassword);

    Boolean deleteUser(User user);

    Boolean deleteUser(Integer id);

    Covid getCovidByCountryId(String id);

    InfoData getInfoData(String departureId, String destinationId);

    Map<String, Status.StatusEnum> getCountriesWhereNameLike(String countryId, String search);

    boolean deleteRoute(Integer id);

    Documents getDocumentsByCountryIds(FullRoute fullRoute);

    SpeedLimits getSpeedLimitsByCountryId(Country destination);

    Medicines getMedicinesByCountryId(Country destination);

    Consulates getConsulatesByCountryIds(FullRoute fullRoute);

    News getNewsByCountryId(Country destination);

    Status getStatusByCountryIds(FullRoute fullRoute);

    Reason getReasonsByStatusId(Integer statusId);

    Boolean saveTransit(Set<InfoData> infoData, Integer route_id);

    Country getCountryByFullName(String fullName);

    User getUserById(int id);

    Integer getUserByEmail(String email);

    boolean isUser(String email);

    ResultSet transitFor(int routeId, InfoData infoData);

    ResultSet changeTransitOrder(int transitId, int newOrder);

    ResultSet createNewTransit(int order, String countryId, int routeId);

    Route getRoute();

    boolean isPassport(String passportId);

    boolean addPassport(Passport passport);

    boolean deletePassport(Passport passport);
}
