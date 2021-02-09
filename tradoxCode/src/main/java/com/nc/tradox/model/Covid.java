package com.nc.tradox.model;

public interface Covid {
    int getSummaryTotalCases();

    void setSummaryTotalCases(int summaryTotalCases);

    int getTodayTotalCases();

    void setTodayTotalCases(int todayTotalCases);

    int getSummaryActiveCases();

    void setSummaryActiveCases(int summaryActiveCases);

    int getTodayActiveCases();

    void setTodayActiveCases(int todayActiveCases);

    int getSummaryTotalDeaths();

    void setSummaryTotalDeaths(int summaryTotalDeaths);

    int getTodayDeaths();

    void setTodayDeaths(int todayDeaths);

    int getSummaryRecovered();

    void setSummaryRecovered(int summaryRecovered);

    int getTodayRecovered();

    void setTodayRecovered(int todayRecovered);
}