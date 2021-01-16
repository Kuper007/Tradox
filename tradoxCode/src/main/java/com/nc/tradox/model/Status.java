package com.nc.tradox.model;

import com.nc.tradox.model.impl.Reasons;

public interface Status extends Destination {
    Integer getStatusId();
    enum StatusEnum {
        open,close;
    }
    StatusEnum getStatus();
    void setStatus(StatusEnum statusEnum);
    Reasons getReasons();
    void changeCovidReason();
    void changeCitizenshipReason();
}
