package com.nc.tradox.model;

import java.util.Date;
import java.util.Set;

public interface User {
    void setVerify();

    Integer getUserId();

    void setUserId(Integer userId);

    UserTypeEnum getUserType();

    void setUserType(UserTypeEnum userType);

    String getFirstName();

    void setFirstName(String firstName);

    String getLastName();

    void setLastName(String lastName);

    Date getBirthDate();

    void setBirthDate(Date birthDate);

    String getEmail();

    void setEmail(String email);

    String getPhone();

    void setPhone(String phone);

    Country getLocation();

    void setLocation(Country location);

    Passport getPassport();

    void setPassport(Passport passport);

    String getPassportId();

    Country getCitizenshipCountry();

    void setCitizenshipCountry(Country country);

    Set<Route> getHistory();

    Boolean addToHistory(Route route);

    Boolean removeFromHistory(Route route);

    void setTransit(Set<Route> transit);

    Boolean isVerified();

    enum UserTypeEnum {
        user, admin, vip
    }
}
