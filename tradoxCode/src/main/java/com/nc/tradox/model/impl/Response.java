package com.nc.tradox.model.impl;

public class Response {

    private Object object;
    private String error;

    public Response() {

    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

}