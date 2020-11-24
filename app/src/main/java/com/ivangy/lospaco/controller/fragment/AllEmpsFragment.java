package com.ivangy.lospaco.controller.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

import com.ivangy.lospaco.R;
import com.ivangy.lospaco.controller.activity.MainActivity;
import com.ivangy.lospaco.helpers.AndroidHelper;
import com.ivangy.lospaco.helpers.adapter.EmployeeAdapter;
import com.ivangy.lospaco.model.Employee;

import java.util.ArrayList;
import java.util.Random;

public class AllEmpsFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        MainActivity.toolbar.setTitle("Nossos Funcionários");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_all_emps, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ArrayList<Employee> listEmps = new ArrayList<>();

        for (int i = 210; i < 245; i++)
            listEmps.add(new Employee("Name" + i, "https://picsum.photos/id/" + i + "/150/100", (new Random().nextBoolean() ? 'M' : 'F'), "emp" + i + "@gemail.com", "(11) 98734-5887", 4.2f, 123, 21));

        AndroidHelper.setGridRecyclerConfig(getActivity(), view.findViewById(R.id.recyclerEmps), new EmployeeAdapter(listEmps), 4);
    }

    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        super.onPrepareOptionsMenu(menu);
        menu.findItem(R.id.menuCart).setVisible(true);
        menu.findItem(R.id.menuSearch).setVisible(true);
    }
}