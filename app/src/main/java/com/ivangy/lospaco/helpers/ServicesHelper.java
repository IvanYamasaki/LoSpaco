package com.ivangy.lospaco.helpers;

import android.content.Context;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public abstract class ServicesHelper {

    public static void setServiceRecyclerConfig(Context context, RecyclerView recyclerView, RecyclerView.Adapter adapter, int linearLayoutOrientation, int prefetchItemCount) {
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context, linearLayoutOrientation, false);
        layoutManager.setInitialPrefetchItemCount(prefetchItemCount);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setRecycledViewPool(new RecyclerView.RecycledViewPool());
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(adapter);
    }
}
