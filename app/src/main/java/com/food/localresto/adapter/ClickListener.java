package com.food.localresto.adapter;

import android.view.View;

public interface ClickListener {
    void onClick(View view, int position);

    boolean onLongClick(View view, int position);
}
