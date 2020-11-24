package com.ivangy.lospaco.controller.activity;

import android.content.Intent;
import android.content.res.Configuration;
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
import com.ivangy.lospaco.model.Cart;
import com.ivangy.lospaco.model.Package;
import com.ivangy.lospaco.model.Service;
import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.yarolegovich.slidingrootnav.SlidingRootNav;
import com.yarolegovich.slidingrootnav.SlidingRootNavBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import static com.ivangy.lospaco.helpers.AndroidHelper.toastShort;
import static com.ivangy.lospaco.helpers.Constants.POS_ABOUT_US;
import static com.ivangy.lospaco.helpers.Constants.POS_CART;
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

    **Pergunta pra Aline
    -como abrir activity com um fragment específico aberto?
    -como evitar q a MainActivity seja destruída qnd vai p ServiceDetails?
        click do carrinho e btnBack no serviceDetails
*/

public class MainActivity extends AppCompatActivity
        implements DrawerAdapter.OnItemSelectedListener {

    public static ArrayList<Service> listAllServices = new ArrayList<>();
    public static ArrayList<Cart> listAllCart = new ArrayList<>();
    public static ArrayList<Package> listAllPackages = new ArrayList<>();
    public static Toolbar toolbar;

    private SlidingRootNav slidingRootNav;
    private MaterialSearchView searchView;
    RecyclerView recyclerDrawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Locale locale = new Locale("POR");
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getApplicationContext().getResources().updateConfiguration(config, null);

        for (int i = 65; i < 105; i++) {
            listAllServices.add(new Service("Name" + i, " Lorem Ipsum is that it has a more-or-less normal distribution of letters, as opposed to using 'Content here, content here', making it look to everyone who is trying to sleep", new Random().nextFloat() * 5, "https://picsum.photos/id/" + i + "/400/600", "Categoria" + new Random().nextInt(5), "Roupas" + i, new java.sql.Time(new Date().getTime()), (new Random().nextInt(200))));
        }
        for (int i = 12; i < 14; i++) {
            listAllPackages.add(new Package("Pack" + (i - 11), "https://picsum.photos/id/" + (i + 200) + "/400/600", new ArrayList<>(Arrays.asList(listAllServices.get(i + 10), listAllServices.get(i + 11), listAllServices.get(i + 12), listAllServices.get(i + 13))), i + 100));
            listAllCart.add(new Cart(listAllPackages.get(i - 12).getNamePackage(), new Random().nextInt(5), 11.20));
            listAllCart.add(new Cart(listAllServices.get(i).getName(), new Random().nextInt(5), 123.56));
        }

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        slidingRootNav = new SlidingRootNavBuilder(this)
                .withToolbarMenuToggle(toolbar)
                .withMenuOpened(false)
                .withContentClickableWhenMenuOpened(true)
                .withSavedState(savedInstanceState)
                .withMenuLayout(R.layout.menu_left_drawer)
                .inject();

        recyclerDrawer = findViewById(R.id.list);

        new DrawerLoader(this, recyclerDrawer, this, POS_HOME);

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

                DrawerLoader.adapter.setSelected(POS_CART);
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
            case POS_CART:
                navController.navigate(R.id.cartFragment);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return false;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        MenuItem menuItemCart = menu.findItem(R.id.menuCart);
        menuItemCart.setIcon(Converter.convertLayoutToImage(MainActivity.this, listAllCart.size(), R.drawable.ic_baseline_shopping_cart_24));
        searchView.setMenuItem(menu.findItem(R.id.menuSearch));

        menu.findItem(R.id.menuSearch).setVisible(false);

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        toastShort(this, "destroy");
    }
}