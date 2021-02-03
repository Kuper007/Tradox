package com.nc.tradox.model;

public interface Reason {
    Integer getReasonId();

    void setReasonId(Integer reasonId);

    Boolean getCovidReason();

    void setCovidReason(Boolean covidReason);

    Boolean getCitizenshipReason();

    void setCitizenshipReason(Boolean citizenshipReason);
}