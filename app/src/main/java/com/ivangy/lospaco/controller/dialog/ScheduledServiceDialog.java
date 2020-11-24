package com.ivangy.lospaco.controller.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.bumptech.glide.Glide;
import com.google.android.material.timepicker.TimeFormat;
import com.ivangy.lospaco.R;
import com.ivangy.lospaco.model.ServicesScheduled;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Date;

import cn.iwgang.countdownview.CountdownView;

public class ScheduledServiceDialog extends AppCompatDialogFragment {

    private ServicesScheduled service;

    public ScheduledServiceDialog(ServicesScheduled service) {
        this.service = service;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_sheduled_service, null);

        builder.setView(view);
        AlertDialog alertDialog = builder.create();

        TextView lblNameService = view.findViewById(R.id.lblService),
                lblNameEmp = view.findViewById(R.id.lblEmployee),
                lblDateTime = view.findViewById(R.id.lblDateTime),
                lblCodeNumber = view.findViewById(R.id.lblCode);
        ImageView imgEmp = view.findViewById(R.id.imgEmp), imgService = view.findViewById(R.id.imgService);
        CountdownView countdownTimeRemaining = view.findViewById(R.id.countdownTimeRemaining);

        lblCodeNumber.setText("Código: " +service.getCode());

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            Date parsedDate = dateFormat.parse(service.getDateTimeSched());
            countdownTimeRemaining.start(parsedDate.getTime() - new Date().getTime());
            lblDateTime.setText(String.format(String.format("Agendado para: " + new SimpleDateFormat("dd/MM/yyyy").format(parsedDate) + " às " + new SimpleDateFormat("HH:mm").format(parsedDate))));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        lblNameEmp.setText(service.getEmpName());
        lblNameService.setText(service.getServName());

        Picasso.get().load(service.getEmpImage()).into(imgEmp);
        Picasso.get().load(service.getServImage()).into(imgService);

        view.findViewById(R.id.btnClose).setOnClickListener(v -> alertDialog.dismiss());

        return alertDialog;
    }
}
