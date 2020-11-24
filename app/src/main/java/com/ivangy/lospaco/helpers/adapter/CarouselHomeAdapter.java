package com.ivangy.lospaco.helpers.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.flaviofaria.kenburnsview.KenBurnsView;
import com.ivangy.lospaco.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CarouselHomeAdapter extends RecyclerView.Adapter<CarouselHomeAdapter.ViewHolder>{

    private Context mContext;
    private List<String> list;

    public CarouselHomeAdapter(Context context, List<String> list) {
        this.mContext = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_carousel_home ,parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Picasso.get().load(list.get(position)).into(holder.kenBurnsCarousel);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private KenBurnsView kenBurnsCarousel;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            kenBurnsCarousel = itemView.findViewById(R.id.kbvCarouselHome);
        }
    }

}
