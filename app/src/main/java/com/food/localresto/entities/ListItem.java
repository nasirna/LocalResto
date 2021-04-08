package com.food.localresto.entities;

public class ListItem {
    public int level;
    public String text;

    public int listPosition;

    public boolean isGroupHeader = false;

    public ListItem(String text) {
        this(-1,text);
        isGroupHeader = true;
    }

    public ListItem(int level, String text) {
        this.level = level;
        this.text = text;
    }

    @Override public String toString() {
        return text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isGroupHeader() {
        return isGroupHeader;
    }
    public void setGroupHeader(boolean isGroupHeader) {
        this.isGroupHeader = isGroupHeader;
    }
}
