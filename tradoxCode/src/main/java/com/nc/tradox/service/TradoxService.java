package com.nc.tradox.service;

import com.nc.tradox.api.RandomString;
import com.nc.tradox.dao.Dao;
import com.nc.tradox.model.*;
import com.nc.tradox.model.impl.*;
import com.nc.tradox.model.service.Response;
import com.nc.tradox.utilities.ExchangeApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service //EQUAL TO COMPONENT BUT FOR SPECIFICATE USE THIS
public class TradoxService {

    private final Dao dao;
    private final Logger LOGGER = Logger.getLogger(TradoxService.class.getName());

    @Autowired
    public TradoxService(@Qualifier("oracle") Dao dao) {
        this.dao = dao;
    }

    public Route getRoute() {
        return dao.getRoute();
    }

    public Route getRoute(String userId, String destinationId) {
        return dao.getRoute(userId, destinationId);
    }

    public InfoData getInfoData(Country departure, Country destination) {
        FullRoute fullRoute = new FullRouteImpl(departure, destination);
        double mediumBill = dao.getMediumBill(destination);
        Documents documents = getDocuments(fullRoute);
        int tourismCount = dao.getTourismCount(destination);
        Medicines medicines = dao.getMedicinesByCountryId(destination);
        Covid covidInfo = dao.getCovidInfo(destination);
        Consulates consulates = dao.getConsulatesByCountryIds(fullRoute);
        SpeedLimits speedLimits = dao.getSpeedLimitsByCountryId(destination);
        String departureCurrency = dao.getCurrency(fullRoute.getDeparture());
        String destinationCurrency = dao.getCurrency(fullRoute.getDestination());
        Exchange exchange = getExchangeByCountryIds(fullRoute, departureCurrency, destinationCurrency);
        News news = dao.getNewsByCountryId(destination);
        Status status = dao.getStatusByCountryIds(fullRoute);
        return new InfoDataImpl(fullRoute,
                mediumBill,
                documents,
                tourismCount,
                medicines,
                covidInfo,
                consulates,
                speedLimits,
                destinationCurrency,
                exchange,
                news,
                status);
    }

    public Documents getDocuments(FullRoute fullRoute) {
        return dao.getDocumentsByCountryIds(fullRoute);
    }

    public Exchange getExchangeByCountryIds(FullRoute fullRoute, String departureCurrency, String destinationCurrency) {
        try {
            List<String> apiExchanges = new ExchangeApi().currentExchange(departureCurrency, destinationCurrency);
            return new ExchangeImpl(apiExchanges.get(1), apiExchanges.get(0), fullRoute);
        } catch (InterruptedException | IOException exception) {
            LOGGER.log(Level.SEVERE, "TradoxDataAccessService.getExchangeByCountryId " + exception.getMessage());
        }
        return null;
    }

    public Boolean deleteRoute(Route route) {
        return dao.deleteRoute(route.getRouteId());
    }

    public Response auth(String email, String password) {
        return dao.auth(email, password);
    }

    public Boolean updateUserData(User user) {
        return dao.updateUserData(user);
    }

    public Boolean deleteUser(Integer userId) {
        return dao.deleteUser(userId);
    }

    public String registerUser(Map<String, String> map, HttpSession session) {
        boolean emailNotUnique = false;
        boolean passportNotUnique = false;
        if (dao.isUser(map.get("email")))
            emailNotUnique = true;
        if (dao.isPassport(map.get("passport_id")))
            passportNotUnique = true;
        if (emailNotUnique || passportNotUnique)
            return "{\"result\": false, \"emailNotUnique\": " + emailNotUnique + ", \"passportNotUnique\": " + passportNotUnique + "}";
        Country citizenship = getCountryById(map.get("citizenship"));
        Country location = getCountryById(map.get("country_id"));
        Passport passport = new PassportImpl(map.get("passport_id"), citizenship);
        if (!dao.addPassport(passport)) {
            return "{\"result\": false, \"emailNotUnique\": false, \"passportNotUnique\": false}";
        } else {
            try {
                User user = new UserImpl();
                user.setFirstName(map.get("first_name"));
                user.setLastName(map.get("last_name"));
                user.setBirthDate(new SimpleDateFormat("yyyy-MM-dd").parse(map.get("birth_date")));
                user.setEmail(map.get("email"));
                user.setPhone(map.get("phone"));
                user.setPassport(passport);
                user.setLocation(location);
                if (!dao.registrate(user, map.get("password"))) {
                    dao.deletePassport(passport);
                } else {
                    Integer userId = dao.getUserByEmail(map.get("email"));
                    session.setAttribute("userId", userId);
                    if (userId != 0) {
                        return "{\"result\": true, \"emailNotUnique\": false, \"passportNotUnique\": false, \"userId\": " + userId + "}";
                    }
                }
            } catch (ParseException exception) {
                exception.printStackTrace();
            }
        }
        return "{\"result\": false, \"emailNotUnique\": false, \"passportNotUnique\": false}";
    }

    public Country getCountryByFullName(String countryFullName) {
        return dao.getCountryByFullName(countryFullName);
    }

    public Map<String, Status.StatusEnum> getCountriesWhereNameLike(String countryId, String search) {
        return dao.getCountriesWhereNameLike(countryId, search);
    }

    public Boolean saveRoute(Route route, int userId) {
        return dao.saveRoute(route, userId);
    }

    public void editTransits(int userId, Integer route_id, Set<InfoData> transits) {
        int newOrder = 1;
        while (transits.iterator().hasNext()) {
            InfoData infoData = transits.iterator().next();
            // check if this transit for this route and user already exists with this number
            ResultSet resultSet = dao.transitFor(route_id, infoData);
            int transit_id = -1;
            int order = -1;
            try {
                transit_id = resultSet.getInt("transit_id");
                order = resultSet.getInt("order");
            } catch (SQLException e) {
                e.printStackTrace();
            }
            if ((transit_id != -1)) {
                // if exists but wrong order change the order
                if (newOrder != order) {
                    ResultSet resultSet1 = dao.changeTransitOrder(transit_id, newOrder);
                }
            } else {
                // if dont exists create new
                dao.createNewTransit(newOrder, infoData.getDestination().getShortName(), route_id);
            }
            newOrder++;
        }
    }

    public User getUserById(int id) {
        return dao.getUserById(id);
    }

    public Country getUserLocation(int userId) {
        return dao.getUserLocationById(userId);
    }

    public Country getCountryById(String id) {
        return dao.getCountryById(id);
    }

    public Boolean verifyUser(int id) {
        return dao.verifyUserById(id);
    }

    public String resetPassword(String email) {

        String newPwd = "";
        int userId = dao.getUserByEmail(email);
        if (userId != 0) {
            newPwd = new RandomString(8).nextString();
            boolean res = dao.changePassword(userId, newPwd);
            if (res) {
                return newPwd;
            } else {
                return "";
            }
        }
        return newPwd;
    }

    public List<Country> getAllCountries() {
        return dao.getAllCountries();
    }

    public boolean isCountry(String fullName) {
        return dao.isCountry(fullName);
    }

}
