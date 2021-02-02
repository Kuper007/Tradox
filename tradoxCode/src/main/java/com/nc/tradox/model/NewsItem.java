package com.nc.tradox.model;

import java.util.Date;

public interface NewsItem {
    Integer getNewsItemId();

    void setNewsItemId(Integer newsItemId);

    String getText();

    void setText(String newText);

    Date getDate();

    void setDate(Date newDate);

    Country getCountry();

    void setCountry(Country country);
}