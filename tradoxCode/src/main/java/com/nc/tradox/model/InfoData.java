package com.nc.tradox.model;

import com.nc.tradox.model.impl.*;

public interface InfoData {
    Boolean reloadData();

    Country getDestination();

    Country getDeparture();

    Documents getDocuments();

    SpeedLimits getSpeedLimits();

    Medicines getMedicines();

    Consulates getConsulates();

    News getNews();

    Exchange getExchanges();

    Status getStatus();
}