package com.nc.tradox.dao;

import com.nc.tradox.model.*;
import com.nc.tradox.model.impl.*;

import java.sql.ResultSet;
import java.util.Map;
import java.util.Set;

public interface Dao {
    Route getRoute(String userId, String destinationId);

    User auth(String email, String password);

    Country getCountryById(String id);

    Passport getPassportById(String id);

    Route getRouteById(String id);

    Boolean saveRoute(Route route, Integer userId);

    Boolean registrate(Map<String, String> info);

    boolean updateUser(User user);

    Boolean deleteUser(User user);

    Boolean deleteUser(Integer id);

    CovidImpl getCovidByCountryId(String id);

    InfoData getInfoData(String departureId, String destinationId);

    Map<String, Status.StatusEnum> getCountriesWhereNameLike(String countryId,String search);

    boolean deleteRoute(Integer id);

    Documents getDocumentsByCountriesIds(String departureId, String destinationId);

    SpeedLimits getSpeedLimitsByCountryId(String id);

    Medicines getMedicinesByCountryId(String id);

    Consulates getConsulatesByCountriesIds(String departureId, String destinationId);

    News getNewsByCountryId(String id);

    Status getStatusByCountriesIds(String departureId, String destinationId);

    com.nc.tradox.model.impl.Reasons getReasonsByStatusId(Integer id);

    Boolean saveTransit(Set<InfoData> infoData, Integer route_id);

    Country getCountryByFullName(String fullName);

    User getUserById(int id);

    ResultSet transitFor(int routeId, InfoData infoData);

    ResultSet changeTransitOrder(int transitId, int newOrder);

    ResultSet createNewTransit(int order,String countryId, int routeId);

    Route getRoute();

    boolean savePassport(String id, String citizenship);
}
