package com.ivangy.lospaco.helpers.adapter;

import android.content.Context;
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
import com.ivangy.lospaco.controller.dialog.ScheduleDialog;
import com.ivangy.lospaco.model.Service;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ServiceOwnerdAdapter extends RecyclerView.Adapter<ServiceOwnerdAdapter.ViewHolder> {

    ArrayList<Service> listService;
    Context context;

    public ServiceOwnerdAdapter(ArrayList<Service> listService) {
        this.listService = listService;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_services_owned, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Service service = listService.get(position);

        holder.lblNameService.setText(service.getName());
        Picasso.get().load(service.getImg()).into(holder.imgService);

        holder.cardView.setOnClickListener(v -> {
            new ScheduleDialog(context, service).show(((FragmentActivity) context).getSupportFragmentManager(), "comment_dialog");
        });
    }

    @Override
    public int getItemCount() {
        return listService.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private CardView cardView;
        private TextView lblNameService;
        private ImageView imgService;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardService);
            lblNameService = itemView.findViewById(R.id.lblNameService);
            imgService = itemView.findViewById(R.id.imgServiceOwned);
        }
    }
}
