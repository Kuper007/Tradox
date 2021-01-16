package com.nc.tradox.model;

import com.nc.tradox.model.impl.*;

import java.util.List;

public interface InfoData extends FullRoute {
    Boolean reloadData();

    Documents getDocuments();
    SpeedLimits getSpeedLimits();
    Medicines getMedicines();
    Consulates getConsulates();
    News getNews();
    List<Exchange> getExchanges();
    Status getStatus();
}
