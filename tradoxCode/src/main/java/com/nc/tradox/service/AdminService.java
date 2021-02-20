package com.nc.tradox.service;

import com.nc.tradox.dao.Dao;
import com.nc.tradox.model.impl.CountryOld;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service
public class AdminService {

    private final Dao dao;
    private final Logger LOGGER = Logger.getLogger(AdminService.class.getName());

    @Autowired
    public AdminService(@Qualifier("oracle") Dao dao) {
        this.dao = dao;
    }

    public List<CountryOld> getCountryList() {
        return dao.getCountryList();
    }

//    public

}