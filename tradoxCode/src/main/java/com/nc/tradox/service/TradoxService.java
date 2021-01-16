package com.nc.tradox.service;

import com.nc.tradox.dao.Dao;
import com.nc.tradox.model.Route;
import com.nc.tradox.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

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

    public User auth(String email, String password) {
        return dao.auth(email,password);
    }
}
