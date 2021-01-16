package com.nc.tradox.model.impl;

import com.nc.tradox.model.Document;
import com.nc.tradox.model.Element;

import java.util.ArrayList;
import java.util.List;

abstract public class ListOfElements<T extends Element> {
    protected List<T> elementsList;

    public ListOfElements(List<T> elementsList) {
        this.elementsList = (ArrayList) elementsList;
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

    public Boolean removeElementById(Integer id) {
        for (T element : elementsList) {
            if (element.getElementId() == id) {
                elementsList.remove(element);
                return true;
            }
        }
        return false;
    }
}
