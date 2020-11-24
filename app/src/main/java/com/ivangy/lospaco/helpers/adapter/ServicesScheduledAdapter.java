package com.ivangy.lospaco.helpers.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.ivangy.lospaco.R;
import com.ivangy.lospaco.controller.dialog.CommentDialog;
import com.ivangy.lospaco.controller.dialog.ScheduledServiceDialog;
import com.ivangy.lospaco.model.ServicesScheduled;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import cn.iwgang.countdownview.CountdownView;

public class ServicesScheduledAdapter extends RecyclerView.Adapter<ServicesScheduledAdapter.ViewHolder> {

    private ArrayList<ServicesScheduled> listServices;
    private Context context;

    public ServicesScheduledAdapter(ArrayList<ServicesScheduled> listServices) {
        this.listServices = listServices;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_scheduled_services, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ServicesScheduled service = listServices.get(position);

        holder.lblNameService.setText(service.getServName());
        Picasso.get().load(service.getServImage()).into(holder.imgService);

        try {
            holder.countdownTimeRemaining.start(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(service.getDateTimeSched()).getTime() - new Date().getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        holder.countdownTimeRemaining.setOnCountdownEndListener(cv -> {

        });

        holder.itemView.setOnClickListener(v -> {
            ScheduledServiceDialog scheduledServiceDialog = new ScheduledServiceDialog(service);
            scheduledServiceDialog.show(((FragmentActivity) context).getSupportFragmentManager(), "comment_dialog");
        });

    }

    @Override
    public int getItemCount() {
        return listServices.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private CardView cardView;
        private TextView lblNameService;
        private ImageView imgService;
        private CountdownView countdownTimeRemaining;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardService);
            lblNameService = itemView.findViewById(R.id.lblNameService);
            imgService = itemView.findViewById(R.id.imgServiceOwned);
            countdownTimeRemaining = itemView.findViewById(R.id.countdownTimeRemaining);
        }
    }
}
