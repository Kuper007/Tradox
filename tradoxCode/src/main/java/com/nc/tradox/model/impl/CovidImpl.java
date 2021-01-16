package com.nc.tradox.model.impl;

public class CovidImpl implements com.nc.tradox.model.Covid {

    protected int summaryTotalCases;
    protected int todayTotalCases;
    protected int summaryActiveCases;
    protected int todayActiveCases;
    protected int todayDeaths;
    protected int summaryRecovered;
    protected int todayRecovered;

    public CovidImpl(int summaryTotalCases,
                     int todayTotalCases,
                     int summaryActiveCases,
                     int todayActiveCases,
                     int todayDeaths,
                     int summaryRecovered,
                     int todayRecovered) {
        this.summaryTotalCases = summaryTotalCases;
        this.todayTotalCases = todayTotalCases;
        this.summaryActiveCases = summaryActiveCases;
        this.todayActiveCases = todayActiveCases;
        this.todayDeaths = todayDeaths;
        this.summaryRecovered = summaryRecovered;
        this.todayRecovered = todayRecovered;
    }

    @Override
    public int getSummaryTotalCases() {
        return this.summaryTotalCases;
    }

    @Override
    public void setSummaryTotalCases(int summaryTotalCases) {
        this.summaryTotalCases = summaryTotalCases;
    }

    @Override
    public int getTodayTotalCases() {
        return this.todayTotalCases;
    }

    @Override
    public void setTodayTotalCases(int todayTotalCases) {
        this.todayTotalCases = todayTotalCases;
    }

    @Override
    public int getSummaryActiveCases() {
        return this.summaryActiveCases;
    }

    @Override
    public void setSummaryActiveCases(int summaryActiveCases) {
        this.summaryActiveCases = summaryActiveCases;
    }

    @Override
    public int getTodayActiveCases() {
        return this.todayActiveCases;
    }

    @Override
    public void setTodayActiveCases(int todayActiveCases) {
        this.todayActiveCases = todayActiveCases;
    }

    @Override
    public int getTodayDeaths() {
        return this.todayDeaths;
    }

    @Override
    public void setTodayDeaths(int todayDeaths) {
        this.todayDeaths = todayDeaths;
    }

    @Override
    public int getSummaryRecovered() {
        return this.summaryRecovered;
    }

    @Override
    public void setSummaryRecovered(int summaryRecovered) {
        this.summaryRecovered = summaryRecovered;
    }

    @Override
    public int getTodayRecovered() {
        return this.todayRecovered;
    }

    @Override
    public void setTodayRecovered(int todayRecovered) {
        this.todayRecovered = todayRecovered;
    }
}
