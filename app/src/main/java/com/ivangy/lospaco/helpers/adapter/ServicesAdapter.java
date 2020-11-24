package com.ivangy.lospaco.helpers.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ivangy.lospaco.R;
import com.ivangy.lospaco.model.Service;

import java.util.ArrayList;

import static com.ivangy.lospaco.helpers.AndroidHelper.getServCategory;
import static com.ivangy.lospaco.helpers.AndroidHelper.getServiceByCategoryName;
import static com.ivangy.lospaco.helpers.ServicesHelper.setServiceRecyclerConfig;

public class ServicesAdapter extends RecyclerView.Adapter<ServicesAdapter.ViewHolder> {

  private ArrayList<Service> listServices;
  private Context mContext;
  private ArrayList<String> listCategories;

  public ServicesAdapter(Context mContext, ArrayList<Service> listServices) {
    this.listServices = listServices;
    this.mContext = mContext;
    listCategories = new ArrayList<>(getServCategory(listServices));
  }

  @NonNull
  @Override
  public ServicesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    return new ServicesAdapter.ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_recyclers, parent, false));
  }

  @Override
  public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    holder.lblCategory.setText(getServCategory(listServices).get(position));
    setServiceRecyclerConfig(mContext, holder.recyclerItemService, new ItemServicesAdapter(mContext, getServiceByCategoryName(listCategories.get(position), listServices)), LinearLayout.HORIZONTAL, listServices.size());

  }

  @Override
  public int getItemCount() {
    return listCategories.size();
  }

  public class ViewHolder extends RecyclerView.ViewHolder{
    public RecyclerView recyclerItemService;
    TextView lblCategory;
    public ViewHolder(@NonNull View itemView) {
      super(itemView);
      lblCategory = itemView.findViewById(R.id.lblCategory);
      recyclerItemService = itemView.findViewById(R.id.recyclerMain);

    }
  }
}
