package com.nc.tradox.api;

import com.nc.tradox.model.User;
import com.nc.tradox.model.impl.CountryOld;
import com.nc.tradox.model.impl.Response;
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

    @GetMapping("/getCountriesList")
    public Response getCountiesList(HttpSession httpSession) {
        Response response = new Response();
        if (adminAuthorized(httpSession)) {
            List<CountryOld> infoDataList = adminService.getCountryList();
            response.setObject(infoDataList);
        } else {
            response.setError("permissions error");
        }
        return response;
    }

    @PutMapping("/updateCounties")
    public Response updateCountries(@RequestBody List<CountryOld> countryList, HttpSession httpSession) {
        Response response = new Response();
        return response;
    }

    @DeleteMapping("/deleteCountries")
    public Response deleteCountries(@RequestBody List<CountryOld> countryList, HttpSession httpSession) {
        Response response = new Response();
        return response;
    }

    public boolean adminAuthorized(HttpSession httpSession) {
        Integer userId = (Integer) httpSession.getAttribute("userId");
        if (isValidUser(userId)) {
            User.UserTypeEnum userType = (User.UserTypeEnum) httpSession.getAttribute("userType");
            return User.UserTypeEnum.admin.equals(userType);
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