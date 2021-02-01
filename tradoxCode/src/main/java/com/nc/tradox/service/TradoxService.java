package com.nc.tradox.service;

import com.nc.tradox.dao.Dao;
import com.nc.tradox.model.*;
import com.nc.tradox.model.impl.Documents;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.Set;

@Service //EQUAL TO COMPONENT BUT FOR SPECIFICATE USE THIS
public class TradoxService {

    private final Dao dao;

    @Autowired // автоматически устанавливает значение поля
    public TradoxService(@Qualifier("oracle") Dao dao) {
        this.dao = dao;
    }

    public Route getRoute() {
        return dao.getRoute();
    }

    public Route getRoute(String userId, String destinationId) {
        return dao.getRoute(userId, destinationId);
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

    public Boolean registerUser(Map<String, String> map){return dao.registrate(map);}

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
                dao.createNewTransit(newOrder, infoData.getDestinationCountry().getDestinationCountry().getShortName(), route_id);
            }
            newOrder++;
        }
    }

    public User getUserById(int id) {
        return dao.getUserById(id);
    }

    public Documents getDocumentsByCountriesIds(String departureId, String destinationId){
        return dao.getDocumentsByCountriesIds(departureId,destinationId);
    }

    public Country getCountryById(String id) {
        return dao.getCountryById(id);
    }

}
