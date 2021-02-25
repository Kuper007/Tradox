package com.nc.tradox.service;

import com.nc.tradox.dao.Dao;
import com.nc.tradox.model.*;
import com.nc.tradox.model.service.CountryView;
import com.nc.tradox.model.service.HaveDocumentView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

    private final Dao dao;

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

    public boolean saveCountry(CountryView countryView) {
        boolean result = dao.updateCountry(countryView);
        if (!result) {
            result = dao.addCountry(countryView);
        }
        return result;
    }

    public boolean saveUser(User user) {
        return dao.updateUser(user);
    }

    public boolean saveDocument(Document document) {
        boolean result = dao.updateDocument(document);
        if (!result) {
            result = dao.addDocument(document);
        }
        return result;
    }

    public boolean saveConsulate(Consulate consulate) {
        boolean result = dao.updateConsulate(consulate);
        if (!result) {
            result = dao.addConsulate(consulate);
        }
        return result;
    }

    public boolean saveCountryDocument(HaveDocumentView haveDocumentView) {
        boolean result = dao.updateCountryDocument(haveDocumentView);
        if (!result) {
            result = dao.addCountryDocument(haveDocumentView);
        }
        return result;
    }

    public boolean saveMedicine(Medicine medicine) {
        boolean result = dao.updateMedicine(medicine);
        if (!result) {
            result = dao.addMedicine(medicine);
        }
        return result;
    }

    public boolean saveStatus(Status status) {
        boolean result = dao.updateStatus(status);
        if (!result) {
            result = dao.addStatus(status);
        }
        if (result) {
            boolean reasonResult = dao.updateReason(status);
            if (!reasonResult) {
                dao.addReason(status);
            }
        }
        return result;
    }

    public boolean deleteCountry(CountryView countryView) {
        return dao.deleteCountry(countryView);
    }

    public boolean deleteUser(User user) {
        return dao.deleteUser(user);
    }

    public boolean deleteDocument(Document document) {
        return dao.deleteDocument(document);
    }

    public boolean deleteConsulate(Consulate consulate) {
        return dao.deleteConsulate(consulate);
    }

    public boolean deleteCountryDocument(HaveDocumentView haveDocumentView) {
        return dao.deleteCountryDocument(haveDocumentView);
    }

    public boolean deleteMedicine(Medicine medicine) {
        return dao.deleteMedicine(medicine);
    }

    public boolean deleteStatus(Status status) {
        dao.deleteReason(status);
        return dao.deleteStatus(status);
    }

}