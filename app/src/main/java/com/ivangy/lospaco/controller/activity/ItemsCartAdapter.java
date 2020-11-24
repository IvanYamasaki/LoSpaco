package com.ivangy.lospaco.controller.activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ivangy.lospaco.R;
import com.ivangy.lospaco.model.Cart;

import static com.ivangy.lospaco.controller.activity.MainActivity.listAllCart;

public class ItemsCartAdapter extends RecyclerView.Adapter<ItemsCartAdapter.ViewHolder> {

    Context context;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context=parent.getContext();
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_cart_items, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Cart cart = listAllCart.get(position);
        holder.lblPriceItem.setText(context.getString(R.string.lbl_money_symb)+(Double.valueOf(cart.getPriceTotal())*Integer.valueOf(cart.getQntItem())));
        holder.lblNameItem.setText(cart.getNameItem()+" x"+ cart.getPriceTotal());
    }

    @Override
    public int getItemCount() {
        return listAllCart.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView lblNameItem, lblPriceItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            lblNameItem = itemView.findViewById(R.id.lblItemName);
            lblPriceItem = itemView.findViewById(R.id.lblItemPrice);
        }
    }
}
