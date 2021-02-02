package com.nc.tradox.model.impl;

import java.util.List;

abstract public class ListOfElements<T> {
    protected List<T> elementsList;

    public ListOfElements(List<T> elementsList) {
        this.elementsList = elementsList;
    }

    public List<T> getList() {
        return this.elementsList;
    }

    public Boolean add(T element) {
        if (elementsList.contains(element)) {
            return false;
        } else {
            elementsList.add(element);
            return true;
        }
    }
/*
    public Boolean removeElementById(Integer id) {
        for (T element : elementsList) {
            if (element.getElementId() == id) {
                elementsList.remove(element);
                return true;
            }
        }
        return false;
    }*/
}
