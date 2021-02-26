package com.nc.tradox.dao;

import com.nc.tradox.model.*;
import com.nc.tradox.model.impl.*;
import com.nc.tradox.model.service.CountryView;
import com.nc.tradox.model.service.HaveDocumentView;
import com.nc.tradox.model.service.Response;

import java.sql.ResultSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface Dao {

    InfoData getInfoData(Country departure, Country destination);

    Route getRoute(Integer userId, String destinationId);

    Response auth(String email, String password);

    Country getCountryById(String id);

    Passport getPassportById(String id);

    Route getRouteById(Integer id);

    Boolean saveRoute(Route route, Integer userId);

    @Deprecated
    Boolean saveRoute(Integer userId, FullRoute fullRoute);

    Boolean registrate(User user, String password);

    boolean updateUserData(User user);

    Boolean verifyUserById(int userId);

    Boolean changePassword(int id, String newPassword);

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

    boolean isRoute(Integer userId, String departureId, String destinationId);

    boolean isPassport(String passportId);

    boolean addPassport(Passport passport);

    boolean deletePassport(Passport passport);

    List<Country> getAllCountries();

    boolean isCountry(String fullName);

    boolean isShortCountry(String countryId);

    List<CountryView> getCountryList();

    List<User> getUserList();

    List<Document> getDocumentList();

    List<Consulate> getConsulateList();

    List<HaveDocumentView> getCountryDocumentList();

    List<Medicine> getMedicineList();

    List<Status> getStatusList();

    boolean updateCountry(CountryView countryView);

    boolean updateUser(User user);

    boolean updateDocument(Document document);

    boolean updateConsulate(Consulate consulate);

    boolean updateCountryDocument(HaveDocumentView haveDocumentView);

    boolean updateMedicine(Medicine medicine);

    boolean updateStatus(Status status);

    boolean updateReason(Status status);

    boolean addCountry(CountryView countryView);

    boolean addDocument(Document document);

    boolean addConsulate(Consulate consulate);

    boolean addCountryDocument(HaveDocumentView haveDocumentView);

    boolean addMedicine(Medicine medicine);

    boolean addStatus(Status status);

    boolean addReason(Status status);

    boolean deleteCountry(String countryId);

    boolean deleteUser(User user);

    boolean deleteDocument(Document document);

    boolean deleteConsulate(Consulate consulate);

    boolean deleteCountryDocument(HaveDocumentView haveDocumentView);

    boolean deleteMedicine(Medicine medicine);

    boolean deleteStatus(Status status);

    boolean deleteReason(Status status);

}