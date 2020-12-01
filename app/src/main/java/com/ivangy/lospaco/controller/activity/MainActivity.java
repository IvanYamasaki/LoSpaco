package com.ivangy.lospaco.controller.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.ivangy.lospaco.R;
import com.ivangy.lospaco.controller.menu.DrawerAdapter;
import com.ivangy.lospaco.helpers.Converter;
import com.ivangy.lospaco.helpers.DrawerLoader;
import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.yarolegovich.slidingrootnav.SlidingRootNav;
import com.yarolegovich.slidingrootnav.SlidingRootNavBuilder;

import java.util.Locale;

import static com.ivangy.lospaco.helpers.Constants.POS_ABOUT_US;
import static com.ivangy.lospaco.helpers.Constants.POS_HOME;
import static com.ivangy.lospaco.helpers.Constants.POS_LOGOUT;
import static com.ivangy.lospaco.helpers.Constants.POS_MY_PROFILE;
import static com.ivangy.lospaco.helpers.Constants.POS_SCHEDULES;
import static com.ivangy.lospaco.helpers.Constants.POS_SERVICES;

/*
    filter service
    retoque no allEmps
    Perfil User
    Sobre nos
    layout carrinho
    algo especial para o sheduled service
    nested scroll view no sheduled services tá quebrado
    confirmar compra carrinho

*/

public class MainActivity extends AppCompatActivity
        implements DrawerAdapter.OnItemSelectedListener {

    public static Toolbar toolbar;
    private DrawerLoader drawerLoader;
    private SlidingRootNav slidingRootNav;
    private MaterialSearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Locale locale = new Locale("POR");
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getApplicationContext().getResources().updateConfiguration(config, null);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setBackgroundColor(Color.BLACK);

        slidingRootNav = new SlidingRootNavBuilder(this)
                .withToolbarMenuToggle(toolbar)
                .withMenuOpened(false)
                .withContentClickableWhenMenuOpened(true)
                .withSavedState(savedInstanceState)
                .withMenuLayout(R.layout.menu_left_drawer)
                .inject();

        RecyclerView recyclerDrawer = findViewById(R.id.list);

        Bundle extras = getIntent().getExtras();
        int fragmentPosition=POS_HOME;
        if(extras!=null){
            fragmentPosition = extras.getInt("FragmentPosition");
        }

        drawerLoader = new DrawerLoader(this, recyclerDrawer, this, fragmentPosition);

        searchView = findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }

        });


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuCart:
                Navigation.findNavController(this, R.id.nav_host_content).navigate(R.id.cartFragment);
                drawerLoader.unselectItems();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onItemSelected(int position) {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_content);
        switch (position) {
            case POS_HOME:
                navController.navigate(R.id.homeFragment);
                break;
            case POS_SERVICES:
                navController.navigate(R.id.servicesFragment);
                break;
            case POS_SCHEDULES:
                navController.navigate(R.id.scheduleFragment);
                break;
            case POS_ABOUT_US:
                navController.navigate(R.id.aboutUsFragment);
                break;
            case POS_MY_PROFILE:
                navController.navigate(R.id.myProfileFragment);
                break;
            case POS_LOGOUT:
                startActivity(new Intent(this, LoginActivity.class));
                finish();
                break;
        }
        slidingRootNav.closeMenu();
        invalidateOptionsMenu();
    }


    @Override
    protected void onResume() {
        super.onResume();
/*        MenuItem menuCart = findViewById(R.id.menuCart);
        onOptionsItemSelected(menuCart);*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        MenuItem menuItemCart = menu.findItem(R.id.menuCart);
        Drawable iconCart = Converter.convertLayoutToImage(MainActivity.this,/* listAllCart.size()*/ 1, R.drawable.ic_baseline_shopping_cart_24);
        menuItemCart.setIcon(iconCart);

        searchView.setMenuItem(menu.findItem(R.id.menuSearch));
        menu.findItem(R.id.menuSearch).setVisible(false);

        return super.onPrepareOptionsMenu(menu);
    }


}