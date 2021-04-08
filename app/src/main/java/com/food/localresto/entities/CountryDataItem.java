package com.food.localresto.entities;

import java.io.Serializable;
import java.util.ArrayList;

public class CountryDataItem implements Serializable {
    private String countryName;
    private ArrayList<ChildDataItem> childDataItems;
    public String getCountryName() {
        return countryName;
    }
    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }
    public ArrayList<ChildDataItem> getChildDataItems() {
        return childDataItems;
    }
    public void setChildDataItems(ArrayList<ChildDataItem> childDataItems) {
        this.childDataItems = childDataItems;
    }
}
