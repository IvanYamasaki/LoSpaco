package com.ivangy.lospaco.helpers.adapter;

import android.app.Activity;
import android.app.ActivityOptions;
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

import com.flaviofaria.kenburnsview.KenBurnsView;
import com.ivangy.lospaco.R;
import com.ivangy.lospaco.controller.activity.MainActivity;
import com.ivangy.lospaco.controller.activity.ServiceDetailsActivity;
import com.ivangy.lospaco.model.Service;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ItemServicesAdapter extends RecyclerView.Adapter<ItemServicesAdapter.ViewHolder> {

    private List<Service> list;
    private Context mContext;

    public ItemServicesAdapter(Context mContext, List<Service> list) {
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
        Picasso.get().load(list.get(position).getImg()).into(holder.imgService);
        holder.lblServName.setText(list.get(position).getName());
        holder.lblServPrice.setText(mContext.getString(R.string.lbl_money_start_of)+list.get(position).getPrice());
        holder.lblServStarRating.setText(String.format("%.1f", list.get(position).getStarRating()));

        holder.cvItemService.setOnClickListener(v -> {
            Pair[] pairs = new Pair[3];

            // parameters: Object, "TransitionName" (Xml)
            pairs[0] = new Pair<View, String>(holder.imgService, "imgService");
            pairs[1] = new Pair<View, String>(holder.lblServPrice, "lblServPrice");
            pairs[2] = new Pair<View, String>(holder.lblServStarRating, "lblServStarRating");
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation((Activity) mContext, pairs);
            Intent intent = new Intent(mContext, ServiceDetailsActivity.class);
            intent.putExtra("Service", list.get(position));
            mContext.startActivity(intent, options.toBundle());
            ((Activity) mContext).finish();
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
