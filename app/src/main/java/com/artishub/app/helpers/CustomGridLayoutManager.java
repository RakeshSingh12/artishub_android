package com.artishub.app.helpers;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;

/**
 * Created by ashutosh on 7/30/2018.
 */
public class CustomGridLayoutManager extends LinearLayoutManager {
    private boolean isScrollEnabled = false;

    public CustomGridLayoutManager(Context context) {
        super(context);
    }

    public void setScrollEnabled(boolean flag) {
        this.isScrollEnabled = flag;
    }

    @Override
    public boolean canScrollVertically() {
        //Similarly you can customize "canScrollHorizontally()" for managing horizontal scroll
        return isScrollEnabled && super.canScrollVertically();
    }
}
