package com.ivangy.lospaco.controller.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.ivangy.lospaco.R;
import com.ivangy.lospaco.helpers.adapter.ServiceOwnerdAdapter;
import com.ivangy.lospaco.helpers.adapter.ServicesScheduledAdapter;
import com.ivangy.lospaco.model.Employee;
import com.ivangy.lospaco.model.ServicesScheduled;
import com.ivangy.lospaco.controller.activity.MainActivity;

import java.net.URI;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Random;

import static com.ivangy.lospaco.helpers.AndroidHelper.setGridRecyclerConfig;
import static com.ivangy.lospaco.helpers.AndroidHelper.setRecyclerConfig;

public class ScheduleFragment extends Fragment{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        MainActivity.toolbar.setTitle("Meus Serviços");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_schedule, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ArrayList<ServicesScheduled> listSheduled = new ArrayList<>();

        for(int i=140; i<144; i++)
        listSheduled.add(new ServicesScheduled( i,"EmpName"+i, "Name", "https://picsum.photos/id/" + i + "/400/600","https://picsum.photos/id/" + Integer.valueOf(i+123) + "/400/600", "2020-10-"+(i-139)+" 12:30:00"));

        setRecyclerConfig(getActivity(), view.findViewById(R.id.recyclerScheduled), new ServicesScheduledAdapter(listSheduled), LinearLayout.HORIZONTAL);
        setGridRecyclerConfig(getActivity(), view.findViewById(R.id.recyclerServiceOwned),
                new ServiceOwnerdAdapter(/*MainActivity.listAllServices*/ new ArrayList<>()), 2);

    }

    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        super.onPrepareOptionsMenu(menu);
        menu.findItem(R.id.menuCart).setVisible(true);
        menu.findItem(R.id.menuSearch).setVisible(false);
    }
}