package com.nc.tradox.model.impl;

import com.nc.tradox.model.Country;
import com.nc.tradox.model.Destination;

import java.util.Date;

public class NewsItemImpl implements com.nc.tradox.model.NewsItem {

    protected final Integer newsItemId;
    protected String text;
    protected Date date;
    protected Destination destination;

    public NewsItemImpl(Integer newsItemId, String text, Date date, Destination destination) {
        this.newsItemId = newsItemId;
        this.text = text;
        this.date = date;
        this.destination = destination;
    }

    @Override
    public Integer getElementId() {
        return this.newsItemId;
    }

    @Override
    public String getText() {
        return this.text;
    }

    @Override
    public void setText(String newText) {
        this.text = text;
    }

    @Override
    public Date getDate() {
        return this.date;
    }

    @Override
    public void setDate(Date newDate) {
        this.date = date;
    }

    @Override
    public Country getDestinationCountry() {
        return this.destination.getDestinationCountry();
    }
}
