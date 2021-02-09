package com.nc.tradox.model;

public interface Status {
    enum StatusEnum {
        open, close
    }

    Integer getStatusId();

    void setStatusId(Integer statusId);

    StatusEnum getStatus();

    void setStatus(StatusEnum status);

    Reason getReason();

    void setReason(Reason reason);

    Country getCountry();

    void setCountry(Country country);

    Country getDestination();

    void setDestination(Country destination);

    FullRoute getFullRoute();

    void setFullRoute(FullRoute fullRoute);
}