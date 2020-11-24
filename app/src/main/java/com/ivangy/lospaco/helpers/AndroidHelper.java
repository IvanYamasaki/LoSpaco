package com.ivangy.lospaco.helpers;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import com.ivangy.lospaco.controller.activity.MainActivity;
import com.ivangy.lospaco.model.Package;
import com.ivangy.lospaco.model.Service;

import java.sql.Date;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;
import static java.util.Arrays.stream;

public abstract class AndroidHelper {

    public static void hideKeyboard(Activity activity) {
        InputMethodManager inputManager = (InputMethodManager) activity
                .getSystemService(Context.INPUT_METHOD_SERVICE);

        // check if no view has focus:
        View currentFocusedView = activity.getCurrentFocus();
        if (currentFocusedView != null) {
            assert inputManager != null;
            inputManager.hideSoftInputFromWindow(currentFocusedView.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    // Return true if the TextField or ArrayList is Empty
    public static boolean isEmpty(TextInputEditText... txt) { return stream(txt).anyMatch(t -> t.getText().toString().isEmpty()); }
    public static boolean isEmpty(ArrayList<String>... list){ return stream(list).anyMatch(l -> l.size()==0); }

    // Return true if the String have a match value on ArrayList
    public static boolean nameExists(ArrayList<String> list, String name) { return list.stream().anyMatch(e -> e.equals(name)); }

    // Toast
    public static void toastShort(Context context, String message) { Toast.makeText(context, message, Toast.LENGTH_SHORT).show(); }

    // Clean TextField and ArrayList
    public static void cls(TextInputEditText... txt){
        asList(txt).forEach(t -> t.setText(""));
    }
    @SafeVarargs
    public static void cls(ArrayList<String>... list){
        asList(list).forEach(ArrayList::clear);
    }

    // Get String from TextField
    public static String get(TextInputEditText txt){
        return Objects.requireNonNull(txt.getText()).toString();
    }
    public static String get(EditText txt){
        return Objects.requireNonNull(txt.getText()).toString();
    }

    // Notify Data changed on RecyclerView
    public static void notify(RecyclerView... recycler){ asList(recycler).forEach(r -> Objects.requireNonNull(r.getAdapter()).notifyDataSetChanged()); }
    // Notify item inserted on RecyclerView
    public static void notifyInserted(RecyclerView recycler, int position) {
    notifyInserted(recycler, position);
    }

    // Remove a value from an ArrayList of RecyclerView at determined position
    public static void remove(ArrayList<String> list, int position, RecyclerView recyclerView){
        list.remove(position);
        AndroidHelper.notify(recyclerView);
    }

    public static boolean between(int val, int valmin, int valmax){
        return val >=valmin && val<=valmax;
    }

    // Configure the RecyclerView
    public static void setRecyclerConfig(Context context, RecyclerView recyclerView, RecyclerView.Adapter adapter, int linearLayoutOrientation){
        recyclerView.setLayoutManager(new LinearLayoutManager(context, linearLayoutOrientation, false));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }
    public static void setGridRecyclerConfig(Context context, RecyclerView recyclerView, RecyclerView.Adapter adapter, int gridColumns){
        recyclerView.setLayoutManager(new GridLayoutManager(context, gridColumns));
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(adapter);
    }

    //new DividerItemDecoration(context, DividerItemDecoration.HORIZONTAL)
    public static void setRecyclerConfig(Context context, RecyclerView recyclerView, RecyclerView.Adapter adapter, int linearLayoutOrientation, DividerItemDecoration dividerItemDecoration){
        recyclerView.setLayoutManager(new LinearLayoutManager(context, linearLayoutOrientation, false));
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setAdapter(adapter);
    }

    public static void setSpinnerConfig(Context context, Spinner spinner, ArrayList<String> listName){
        ArrayAdapter spinnerAdapter = new ArrayAdapter(context,  android.R.layout.simple_list_item_1, listName);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);
    }

    // set a Color for a drawable. Ex: (context, R.drawable.draw, Color.WHITE)
    public static void setTintDrawable(Context context, int drawable, int color){
        Drawable unwrappedDrawable = AppCompatResources.getDrawable(context, drawable);
        assert unwrappedDrawable != null;
        Drawable wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable);
        DrawableCompat.setTint(wrappedDrawable, color);
    }

    @ColorInt
    public static int color(Context context, @ColorRes int res) {
        return ContextCompat.getColor(context, res);
    }

    public static void setOverflowButtonColor(final Toolbar toolbar, final int color) {
        Drawable drawable = toolbar.getOverflowIcon();
        if(drawable != null) {
            drawable = DrawableCompat.wrap(drawable);
            DrawableCompat.setTint(drawable.mutate(), color);
            toolbar.setOverflowIcon(drawable);
        }
    }

    // Get the object by name and returns the matches in an ArrayList
    public static ArrayList<Service> getServiceByList(ArrayList<String> list, ArrayList<Service> obj) {
        return obj.stream().filter(ob -> list.contains(ob.getCategory())).collect(Collectors.toCollection(ArrayList::new));
    }
    public static ArrayList<Service> getServiceByCategoryName(String name, ArrayList<Service> obj) {
        return obj.stream().filter(ob -> ob.getCategory().equals(name)).collect(Collectors.toCollection(ArrayList::new));
    }
    public static Service getServiceByName(String serviceName, ArrayList<Service> obj){
        List<Service> list =  obj.stream().filter(ob -> ob.getName().equals(serviceName)).collect(Collectors.toList());
        if(list.size() != 0)
            return list.get(0);
        return null;
    }

    public static Package getPackageByName(String packageName, ArrayList<Package> obj){
        List<Package> list = obj.stream().filter(ob->ob.getNamePackage().equals(packageName)).collect(Collectors.toList());
        if(list.size()!=0)
        return list.get(0);
        return null;

    }

    public static ArrayList<String> getServCategory(ArrayList<Service> listService){
        ArrayList<String> list = new ArrayList<>();

        listService.forEach(s ->{
            if(!list.contains(s.getCategory()))
            list.add(s.getCategory());
        });
        //return new ArrayList<>(listService.stream().map(c-> c.getCategory()).collect(Collectors.toList()));

        return list;
    }

    public static void setSystemBarColor(Activity act, @ColorRes int color) {
            Window window = act.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(act.getResources().getColor(color));
    }

    public static String getDateTime(Time time, Date date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd"), simpleTimeFormat = new SimpleDateFormat("HH:mm:00");
        return simpleDateFormat.format(date)+" "+simpleTimeFormat.format(time);
    }
}
