package com.nc.tradox.api;

public class UserData {

    private final String firstName;
    private final String lastName;
    private final String birthDate;
    private final String email;
    private final String phone;
    private final String passport;
    private final String citizenship;
    private final String location;

    public UserData(String firstName, String lastName, String birthDate, String email, String phone, String passport, String citizenship, String location) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.email = email;
        this.phone = phone;
        this.passport = passport;
        this.citizenship = citizenship;
        this.location = location;
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

}
