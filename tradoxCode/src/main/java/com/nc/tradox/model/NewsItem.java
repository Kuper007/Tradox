package com.nc.tradox.model;

import java.util.Date;

public interface NewsItem extends Element, Destination {
    String getText();
    void setText(String newText);
    Date getDate();
    void setDate(Date newDate);
}
