package com.ivangy.lospaco.helpers.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.flaviofaria.kenburnsview.KenBurnsView;
import com.ivangy.lospaco.R;
import com.ivangy.lospaco.controller.activity.ServiceDetailsActivity;
import com.ivangy.lospaco.model.Service;

import java.io.UnsupportedEncodingException;
import java.util.List;

import static android.content.Intent.FLAG_ACTIVITY_REORDER_TO_FRONT;

public class ServicesAdapter extends RecyclerView.Adapter<ServicesAdapter.ViewHolder> {

    private List<Service> list;
    private Context mContext;

    public ServicesAdapter(Context mContext, List<Service> list) {
        this.list = list;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_services, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Service service = list.get(position);

        try {
            holder.imgService.setImageBitmap(service.getImage());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        holder.lblServName.setText(service.getName());
        holder.lblServPrice.setText(mContext.getString(R.string.lbl_money_start_of)+service.getPrice());
        holder.lblServStarRating.setText(service.getStarRating());

        holder.cvItemService.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, ServiceDetailsActivity.class);
            intent.putExtra("Service", service);
            //intent.setFlags(FLAG_ACTIVITY_REORDER_TO_FRONT);
            mContext.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        KenBurnsView imgService;
        TextView lblServPrice, lblServName, lblServStarRating;
        CardView cvItemService;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgService = itemView.findViewById(R.id.imgServices);
            lblServName = itemView.findViewById(R.id.lblNameServices);
            lblServPrice = itemView.findViewById(R.id.lblPriceServices);
            lblServStarRating = itemView.findViewById(R.id.lblStarRating);
            cvItemService = itemView.findViewById(R.id.cvItemService);
        }
    }
}
