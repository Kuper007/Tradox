package com.nc.tradox.model;

import com.nc.tradox.model.impl.ReasonImpl;

public interface Status {
    enum StatusEnum {
        open, close
    }

    Integer getStatusId();

    void setStatusId(Integer statusId);

    StatusEnum getStatus();

    void setStatus(StatusEnum status);

    ReasonImpl getReasons();

    void setReasons(ReasonImpl reasons);

    Country getCountry();

    void setCountry(Country country);

    Country getDestination();

    void setDestination(Country destination);
}
