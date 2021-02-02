package com.nc.tradox.model;

import com.nc.tradox.model.impl.Reasons;

public interface Status {
    enum StatusEnum {
        open, close
    }

    Integer getStatusId();

    void setStatusId(Integer statusId);

    StatusEnum getStatus();

    void setStatus(StatusEnum status);

    Reasons getReasons();

    void setReasons(Reasons reasons);

    Country getCountry();

    void setCountry(Country country);

    Country getDestination();

    void setDestination(Country destination);
}
