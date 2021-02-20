package com.nc.tradox.service;

import com.nc.tradox.dao.Dao;
import com.nc.tradox.model.*;
import com.nc.tradox.model.service.CountryView;
import com.nc.tradox.model.service.HaveDocumentView;
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

    public List<CountryView> getCountryList() {
        return dao.getCountryList();
    }

    public List<User> getUserList() {
        return dao.getUserList();
    }

    public List<Document> getDocumentList() {
        return dao.getDocumentList();
    }

    public List<Consulate> getConsulateList() {
        return dao.getConsulateList();
    }

    public List<HaveDocumentView> getCountryDocumentList() {
        return dao.getCountryDocumentList();
    }

    public List<Medicine> getMedicineList() {
        return dao.getMedicineList();
    }

    public List<Status> getStatusList() {
        return dao.getStatusList();
    }

}