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
        return dao.updateCountry(countryView);
    }

    public boolean saveUser(User user) {
        return dao.updateUser(user);
    }

    public boolean saveDocument(Document document) {
        return dao.updateDocument(document);
    }

    public boolean saveConsulate(Consulate consulate) {
        return dao.updateConsulate(consulate);
    }

    public boolean saveCountryDocument(HaveDocumentView haveDocumentView) {
        return dao.updateCountryDocument(haveDocumentView);
    }

    public boolean saveMedicine(Medicine medicine) {
        return dao.updateMedicine(medicine);
    }

    public boolean saveStatus(Status status) {
        boolean result = dao.updateStatus(status);
        if (result) {
            dao.updateReason(status);
        }
        return result;
    }

    public boolean addCountry(CountryView countryView) {
        return dao.addCountry(countryView);
    }

    public boolean addDocument(Document document) {
        return dao.addDocument(document);
    }

    public boolean addConsulate(Consulate consulate) {
        return dao.addConsulate(consulate);
    }

    public boolean addCountryDocument(HaveDocumentView haveDocumentView) {
        return dao.addCountryDocument(haveDocumentView);
    }

    public boolean addMedicine(Medicine medicine) {
        return dao.addMedicine(medicine);
    }

    public boolean addStatus(Status status) {
        if (dao.addStatus(status)) {
            return dao.addReason(status);
        }
        return false;
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