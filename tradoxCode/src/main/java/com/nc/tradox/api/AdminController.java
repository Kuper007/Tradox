package com.nc.tradox.api;

import com.nc.tradox.model.*;
import com.nc.tradox.model.service.CountryView;
import com.nc.tradox.model.service.HaveDocumentView;
import com.nc.tradox.model.service.Response;
import com.nc.tradox.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RequestMapping("api/v1/admin")
@RestController
public class AdminController {

    private final AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/getCountries")
    public Response getCounties(HttpSession httpSession) {
        Response response = new Response();
        if (adminAuthorized(httpSession)) {
            List<CountryView> countryList = adminService.getCountryList();
            response.setObject(countryList);
        } else {
            response.setError("permissions error");
        }
        return response;
    }

    @PutMapping("/saveCounties")
    public Response saveCountries(@RequestBody List<CountryView> countryList, HttpSession httpSession) {
        Response response = new Response();
        return response;
    }

    @DeleteMapping("/deleteCountries")
    public Response deleteCountries(@RequestBody List<CountryView> countryList, HttpSession httpSession) {
        Response response = new Response();
        return response;
    }

    @GetMapping("/getUsers")
    public Response getUsers(HttpSession httpSession) {
        Response response = new Response();
        if (adminAuthorized(httpSession)) {
            List<User> userList = adminService.getUserList();
            response.setObject(userList);
        } else {
            response.setError("permissions error");
        }
        return response;
    }

    @GetMapping("/getDocuments")
    public Response getDocuments(HttpSession httpSession) {
        Response response = new Response();
        if (adminAuthorized(httpSession)) {
            List<Document> documentList = adminService.getDocumentList();
            response.setObject(documentList);
        } else {
            response.setError("permissions error");
        }
        return response;
    }

    @GetMapping("/getConsulates")
    public Response getConsulates(HttpSession httpSession) {
        Response response = new Response();
        if (adminAuthorized(httpSession)) {
            List<Consulate> consulateList = adminService.getConsulateList();
            response.setObject(consulateList);
        } else {
            response.setError("permissions error");
        }
        return response;
    }

    @GetMapping("/getCountryDocuments")
    public Response getCountryDocuments(HttpSession httpSession) {
        Response response = new Response();
        if (adminAuthorized(httpSession)) {
            List<HaveDocumentView> countryDocumentList = adminService.getCountryDocumentList();
            response.setObject(countryDocumentList);
        } else {
            response.setError("permissions error");
        }
        return response;
    }

    @GetMapping("/getMedicines")
    public Response getMedicines(HttpSession httpSession) {
        Response response = new Response();
        if (adminAuthorized(httpSession)) {
            List<Medicine> medicineList = adminService.getMedicineList();
            response.setObject(medicineList);
        } else {
            response.setError("permissions error");
        }
        return response;
    }

    @GetMapping("/getStatuses")
    public Response getStatuses(HttpSession httpSession) {
        Response response = new Response();
        if (adminAuthorized(httpSession)) {
            List<Status> statusList = adminService.getStatusList();
            response.setObject(statusList);
        } else {
            response.setError("permissions error");
        }
        return response;
    }

    public boolean adminAuthorized(HttpSession httpSession) {
        Integer userId = (Integer) httpSession.getAttribute("userId");
        if (isValidUser(userId)) {
            String userType = (String) httpSession.getAttribute("userType");
            return "admin".equals(userType);
        }
        return false;
    }

    private boolean isValidUser(Integer userId) {
        if (userId != null) {
            return userId >= 0;
        }
        return false;
    }

}