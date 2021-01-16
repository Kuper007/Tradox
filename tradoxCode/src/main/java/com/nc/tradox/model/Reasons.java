package com.nc.tradox.model;

public interface Reasons {
    Integer getReasonId();
    Boolean getCovidReason();
    void setCovidReason(Boolean covidReason);
    Boolean getCitizenshipReason();
    void setCitizenshipReason(Boolean citizenshipReason);
}
