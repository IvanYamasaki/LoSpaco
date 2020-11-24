package com.ivangy.lospaco.controller.dialog;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.ivangy.lospaco.R;
import com.ivangy.lospaco.controller.fragment.ScheduleFragment;
import com.ivangy.lospaco.helpers.adapter.EmpsAvailableAdapter;
import com.ivangy.lospaco.helpers.adapter.TimesAdapter;
import com.ivangy.lospaco.model.Employee;
import com.ivangy.lospaco.model.Service;
import com.ivangy.lospaco.model.ServicesScheduled;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import static com.ivangy.lospaco.helpers.AndroidHelper.setGridRecyclerConfig;
import static com.ivangy.lospaco.helpers.AndroidHelper.setRecyclerConfig;

public class ScheduleDialog extends AppCompatDialogFragment {

    private Context context;
    private Button btnTime, btnCalendar, btnConfirmSchedule;
    private LinearLayout lyt;
    private Service service;
    private Employee employee=null;
    private RecyclerView recyclerEmps, recyclerTimes;

    public ScheduleDialog(Context context, Service service) {
        this.context = context;
        this.service = service;
    }

    @SuppressLint("SimpleDateFormat")
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_schedule, null);

        builder.setView(view);
        AlertDialog alertDialog = builder.create();

        recyclerEmps = view.findViewById(R.id.recyclerEmpsAvailable);
        recyclerTimes = view.findViewById(R.id.recyclerSugesetedTimes);
        btnCalendar = view.findViewById(R.id.btnCalendar);
        btnTime = view.findViewById(R.id.btnTimer);
        btnConfirmSchedule = view.findViewById(R.id.btnConfirmSchedule);
        lyt = view.findViewById(R.id.lytEmpsAvailables);

        ArrayList<Time> listTime = new ArrayList<>();
        for (int i = 15; i < 25; i++)
            listTime.add(Time.valueOf(i + ":" + i + ":" + i));

        TimesAdapter timesAdapter = new TimesAdapter(listTime);

        setRecyclerConfig(getActivity(), recyclerTimes, timesAdapter, LinearLayout.HORIZONTAL);

        btnCalendar.setOnClickListener(v -> showDateDialog(btnCalendar));
        btnTime.setOnClickListener(v -> showTimeDialog(btnTime));


        EmpsAvailableAdapter empsAvailableAdapter = new EmpsAvailableAdapter(ScheduleFragment.listEmps);
        setGridRecyclerConfig(context, recyclerEmps, empsAvailableAdapter, 2);
        empsAvailableAdapter.setOnClickListener(email -> btnConfirmSchedule.setVisibility(View.VISIBLE));

        timesAdapter.setOnClickListener(time -> {
            btnTime.setText(new SimpleDateFormat("HH:mm").format(time));
            employee = null;
            showEmps();
        });

        view.findViewById(R.id.btnCloseSchedule).setOnClickListener(v -> alertDialog.dismiss());

        btnConfirmSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ServicesScheduled(234 ,"Name"+234, "Name", "https://picsum.photos/"+234+"/140/400/600", "https://picsum.photos/"+235+"/140/400/600", "2020-09-"+(12)+" 12:30:00");
            }
        });

        return alertDialog;
    }

    public void showTimeDialog(Button btnTime) {
        final Calendar calendar = Calendar.getInstance();

        new TimePickerDialog(context, (view, hourOfDay, minute) -> {
            calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
            calendar.set(Calendar.MINUTE, minute);
            btnTime.setText(new SimpleDateFormat("HH:mm").format(calendar.getTime()));
            showEmps();
        }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show();
    }

    public void showDateDialog(Button btnCalendar) {
        final Calendar calendar = Calendar.getInstance();

        new DatePickerDialog(context, (view, year, month, dayOfMonth) -> {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
            btnCalendar.setText(simpleDateFormat.format(calendar.getTime()));
            showEmps();

        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    public void showEmps() {
        if (!btnTime.getText().equals("") && !btnCalendar.getText().equals("")){
            lyt.setVisibility(View.VISIBLE);
            //new LoadEmpsAvailable().execute(getDateTime(Time.valueOf(btnTime.getText().toString()+":00"), Date.valueOf(btnCalendar.getText().toString())));
            new LoadEmpsAvailable().execute();
        }
    }

    public class LoadEmpsAvailable extends AsyncTask<String, Void, Void>{

        @Override
        protected Void doInBackground(String... strings) {

            return null;
        }
    }

}
