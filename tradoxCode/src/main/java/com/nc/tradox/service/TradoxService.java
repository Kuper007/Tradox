package com.nc.tradox.service;

import com.nc.tradox.dao.Dao;
import com.nc.tradox.model.*;
import com.nc.tradox.model.impl.*;
import com.nc.tradox.utilities.ExchangeApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

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

    public InfoData getInfoData(String departureId, String destinationId) {
        Documents documents = dao.getDocumentsByCountryIds(departureId, destinationId);
        SpeedLimits speedLimits = dao.getSpeedLimitsByCountryId(destinationId);
        Medicines medicines = dao.getMedicinesByCountryId(destinationId);
        Consulates consulates = dao.getConsulatesByCountryIds(departureId, destinationId);
        News news = dao.getNewsByCountryId(destinationId);
        Exchange exchange = getExchangeByCountryId(departureId, destinationId);
        Status status = dao.getStatusByCountryIds(departureId, destinationId);
        return new InfoDataImpl(exchange.getFullRoute(), documents, speedLimits, medicines, consulates, news, exchange, status);
    }

    public Exchange getExchangeByCountryId(String departureId, String destinationId) {
        try {
            Country departure = dao.getCountryById(departureId);
            Country destination = dao.getCountryById(destinationId);
            List<String> apiExchanges = new ExchangeApi().currentExchange(departure.getCurrency(), destination.getCurrency());
            FullRouteImpl fullRoute = new FullRouteImpl(departure, destination);
            return new ExchangeImpl(apiExchanges.get(1), apiExchanges.get(0), fullRoute);
        } catch (InterruptedException | IOException exception) {
            LOGGER.log(Level.SEVERE, "TradoxDataAccessService.getExchangeByCountryId " + exception.getMessage());
        }
        return null;
    }

    public Documents getDocumentsByCountryIds(String departureId, String destinationId) {
        return dao.getDocumentsByCountryIds(departureId, destinationId);
    }

    public Boolean deleteRoute(Route route) {
        return dao.deleteRoute(route.getElementId());
    }

    public Map<User, String> auth(String email, String password) {
        return dao.auth(email, password);
    }

    public Boolean updateUser(User user) {
        return dao.updateUser(user);
    }

    public Boolean deleteUser(Integer userId) {
        return dao.deleteUser(userId);
    }

    public String registerUser(Map<String, String> map) {
        boolean emailNotUnique = false;
        boolean passportNotUnique = false;
        if (dao.isUser(map.get("email")))
            emailNotUnique = true;
        if (dao.isPassport(map.get("passport")))
            passportNotUnique = true;
        if (emailNotUnique || passportNotUnique)
            return "{\"result\": false, \"emailNotUnique\": " + emailNotUnique + ", \"passportNotUnique\": " + passportNotUnique + "}";

        Country citizenship = getCountryByFullName(map.get("citizenship"));
        Country location = getCountryByFullName(map.get("location"));
        Passport passport = new PassportImpl(map.get("passport"), citizenship);
        if (!dao.addPassport(passport)) {
            return "{\"result\": false, \"emailNotUnique\": false, \"passportNotUnique\": false}";
        } else {
            try {
                User user = new UserImpl();
                user.setFirstName(map.get("firstName"));
                user.setLastName(map.get("lastName"));
                user.setBirthDate(new SimpleDateFormat("dd.MM.yyyy").parse(map.get("birthDate")));
                user.setEmail(map.get("email"));
                user.setPhone(map.get("phone"));
                user.setPassport(passport);
                user.setLocation(location);
                if (!dao.registrate(user, map.get("password"))) {
                    dao.deletePassport(passport);
                } else {
                    return "{\"result\": true, \"emailNotUnique\": false, \"passportNotUnique\": false}";
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

    public Country getCountryById(String id) {
        return dao.getCountryById(id);
    }

}
