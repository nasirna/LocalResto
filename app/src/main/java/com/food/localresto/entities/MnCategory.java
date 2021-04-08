package com.food.localresto.entities;

import com.food.localresto.adapter.ParentListItem;

import java.util.List;

public class MnCategory implements ParentListItem {
    private String mName;
    private List<MenuItemObject> mMovies;

    public MnCategory(String name, List<MenuItemObject> movies) {
        mName = name;
        mMovies = movies;
    }

    public String getName() {
        return mName;
    }

    @Override
    public List<?> getChildItemList() {
        return mMovies;
    }

    @Override
    public boolean isInitiallyExpanded() {
        return false;
    }
}
