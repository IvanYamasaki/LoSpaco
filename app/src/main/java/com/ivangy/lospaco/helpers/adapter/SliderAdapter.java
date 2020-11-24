package com.ivangy.lospaco.helpers.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ivangy.lospaco.R;
import com.ivangy.lospaco.model.ItemSlider;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SliderAdapter extends RecyclerView.Adapter<SliderAdapter.SliderViewHolder>{

    ArrayList<ItemSlider> list;

    public SliderAdapter(ArrayList<ItemSlider> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public SliderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SliderViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_slider, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SliderViewHolder holder, int position) {
        holder.lblTitle.setText(list.get(position).getTitle());
        holder.lblDesc.setText(list.get(position).getDesc());
        Picasso.get().load(list.get(position).getImg()).into(holder.imgSlider);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class SliderViewHolder extends RecyclerView.ViewHolder{
        private TextView lblTitle, lblDesc;
        private ImageView imgSlider;

        public SliderViewHolder(@NonNull View itemView) {
            super(itemView);
            lblDesc = itemView.findViewById(R.id.lblDesc);
            lblTitle = itemView.findViewById(R.id.lblTitle);
            imgSlider = itemView.findViewById(R.id.imgSlider);
        }
    }
}
