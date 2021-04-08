package com.food.localresto.entities;

import java.io.Serializable;

public class ChildDataItem implements Serializable {
    private String stateName;
    public String getStateName() {
        return stateName;
    }
    public void setStateName(String stateName) {
        this.stateName = stateName;
    }
}
