package com.nc.tradox.model;

public interface Document extends Element, FullRoute {
    String getName();
    String getDescription();
    String getFileLink();
}
