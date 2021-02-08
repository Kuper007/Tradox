package com.nc.tradox.model;

import java.util.Date;
import java.util.Set;

public interface User  {
    void setVerify();

    enum UserTypeEnum {
        user,admin,vip
    }
    Integer getUserId();
    UserTypeEnum getUserType();
    Boolean setUserType(UserTypeEnum userTypeEnum);
    String getFirstName();
    Boolean setFirstName(String firstName);
    String getLastName();
    Boolean setLastName(String lastName);
    Date getBirthDate();
    Boolean setBirthDate(Date birthDate);
    String getEmail();
    Boolean setEmail(String email);
    String getPhone();
    Boolean setPhone(String phone);
    Country getLocation();
    Boolean setLocation(Country location);
    Passport getPassport();
    Boolean setPassport(Passport passport);
    Set<Route> getHistory();
    Boolean addToHistory(Route route);
    Boolean removeFromHistory(Route route);
    Boolean isVerified();
}
