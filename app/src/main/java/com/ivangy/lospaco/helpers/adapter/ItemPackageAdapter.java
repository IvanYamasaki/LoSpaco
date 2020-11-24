package com.ivangy.lospaco.helpers.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ivangy.lospaco.R;
import com.ivangy.lospaco.helpers.AndroidHelper;
import com.ivangy.lospaco.model.Service;

import java.util.ArrayList;

public class ItemPackageAdapter extends RecyclerView.Adapter<ItemPackageAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Service> listServices;
    private ItemPackageListener onClickListener=null;

    public ItemPackageAdapter(ArrayList<Service> listServices) {
        this.listServices = listServices;
    }

    public void setOnClickListener(ItemPackageListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public ItemPackageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context=parent.getContext();
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_package_items, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ItemPackageAdapter.ViewHolder holder, int position) {
        holder.lblNameService.setText(listServices.get(position).getName());

        holder.lblNameService.setOnClickListener(v -> {
            if (onClickListener == null) return;
            onClickListener.onClickListener(position);
        });
    }

    @Override
    public int getItemCount() {
        return listServices.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView lblNameService;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            lblNameService = itemView.findViewById(R.id.lblNamePackItemService);
        }
    }

    public interface ItemPackageListener{
        void onClickListener(int position);
    }

}
