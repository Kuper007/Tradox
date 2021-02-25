package com.nc.tradox.api;

import com.nc.tradox.model.Route;
import com.nc.tradox.model.User;

import java.util.Set;

public class UserData {

    private final String firstName;
    private final String lastName;
    private final String birthDate;
    private final String email;
    private final String phone;
    private final String passport;
    private final String citizenship;
    private final String location;
    private final Set<Route> transit;

    public UserData(String firstName, String lastName, String birthDate, String email, String phone, String passport, String citizenship, String location, Set<Route> transit) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.email = email;
        this.phone = phone;
        this.passport = passport;
        this.citizenship = citizenship;
        this.location = location;
        this.transit = transit;
    }

    public UserData(User user) {
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.birthDate = user.getBirthDate().toString();
        this.email = user.getEmail();
        this.phone = user.getPhone();
        this.location = user.getLocation().getShortName();
        this.citizenship = user.getPassport().getCitizenshipCountry().getShortName();
        this.passport = user.getPassportId();
        this.transit = user.getHistory();
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getPassport() {
        return passport;
    }

    public String getCitizenship() {
        return citizenship;
    }

    public String getLocation() {
        return location;
    }

    public Set<Route> getTransit() {
        return transit;
    }
}
