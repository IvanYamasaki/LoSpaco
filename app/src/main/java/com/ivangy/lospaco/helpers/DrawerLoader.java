package com.ivangy.lospaco.helpers;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ivangy.lospaco.R;
import com.ivangy.lospaco.controller.menu.DrawerAdapter;
import com.ivangy.lospaco.controller.menu.DrawerItem;
import com.ivangy.lospaco.controller.menu.SimpleItem;
import com.ivangy.lospaco.controller.menu.SpaceItem;

import java.util.Arrays;

import static com.ivangy.lospaco.helpers.AndroidHelper.color;
import static com.ivangy.lospaco.helpers.Constants.POS_ABOUT_US;
import static com.ivangy.lospaco.helpers.Constants.POS_HOME;
import static com.ivangy.lospaco.helpers.Constants.POS_LOGOUT;
import static com.ivangy.lospaco.helpers.Constants.POS_MY_PROFILE;
import static com.ivangy.lospaco.helpers.Constants.POS_SCHEDULES;
import static com.ivangy.lospaco.helpers.Constants.POS_SERVICES;

public class DrawerLoader {

    private String[] screenTitles;
    private Drawable[] screenIcons;

    private Context context;
    private RecyclerView recyclerMenu;
    private DrawerAdapter.OnItemSelectedListener listener;
    private int position;
    public static DrawerAdapter adapter;
    public DrawerLoader(Context context, RecyclerView recyclerMenu, DrawerAdapter.OnItemSelectedListener listener, int position) {
        this.context = context;
        this.recyclerMenu = recyclerMenu;
        this.listener = listener;
        this.position=position;
        configDrawerNavigation();
    }

    private void configDrawerNavigation() {
        screenIcons = loadScreenIcons();
        screenTitles = context.getResources().getStringArray(R.array.ld_activityScreenTitles);

        adapter = new DrawerAdapter(Arrays.asList(
                createItemFor(POS_HOME),
                createItemFor(POS_SERVICES),
                createItemFor(POS_SCHEDULES),
                createItemFor(POS_ABOUT_US),
                createItemFor(POS_MY_PROFILE),
                new SpaceItem(48),
                createItemFor(POS_LOGOUT)));
        adapter.setListener(listener);
        adapter.setSelected(position);

        recyclerMenu.setNestedScrollingEnabled(false);
        recyclerMenu.setLayoutManager(new LinearLayoutManager(context));
        recyclerMenu.setAdapter(adapter);
    }

    private DrawerItem createItemFor(int position) {
        return new SimpleItem(screenIcons[position], screenTitles[position])
                .withIconTint(color(context, R.color.textColorSecondary))
                .withTextTint(color(context, R.color.textColorSecondary))
                .withSelectedIconTint(color(context, R.color.colorPrimary))
                .withSelectedTextTint(color(context, R.color.colorPrimary));
    }

    private Drawable[] loadScreenIcons() {
        TypedArray ta = context.getResources().obtainTypedArray(R.array.ld_activityScreenIcons);
        Drawable[] icons = new Drawable[ta.length()];
        for (int i = 0; i < ta.length(); i++) {
            int id = ta.getResourceId(i, 0);
            if (id != 0) {
                icons[i] = ContextCompat.getDrawable(context, id);
            }
        }
        ta.recycle();
        return icons;
    }

    public void unselectItems(){
        adapter.unselectItems();
    }


}
