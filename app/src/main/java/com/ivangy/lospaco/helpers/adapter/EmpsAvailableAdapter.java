package com.ivangy.lospaco.helpers.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.ivangy.lospaco.R;
import com.ivangy.lospaco.controller.dialog.EmployeeDialog;
import com.ivangy.lospaco.model.Employee;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class EmpsAvailableAdapter extends RecyclerView.Adapter<EmpsAvailableAdapter.ViewHolder> {

    private ArrayList<Employee> listEmps;
    private OnClickListener onClickListener = null;
    private int selected = -1;
    private Context context;

    public EmpsAvailableAdapter(ArrayList<Employee> listEmps) {
        this.listEmps = listEmps;
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public EmpsAvailableAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_emps_available, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull EmpsAvailableAdapter.ViewHolder holder, int position) {
        Picasso.get().load(listEmps.get(position).getImage()).into(holder.imgEmp);
        holder.lblNameEmp.setText(listEmps.get(position).getName());

        if (selected == position)
            holder.itemView.setBackgroundColor(Color.parseColor("#65FF0000"));
        else
            holder.itemView.setBackgroundColor(Color.parseColor("#FFFFFF"));

        holder.itemView.setOnClickListener(v -> {
            if (position == selected) {

                openEmpInfo(listEmps.get(position));
            } else {
                if (onClickListener == null) return;
                onClickListener.onItemClick(listEmps.get(position).getEmail());
                selected = position;
                notifyDataSetChanged();
            }
        });

        holder.itemView.setOnLongClickListener(v -> {
            openEmpInfo(listEmps.get(position));
            return true;
        });

    }

    @Override
    public int getItemCount() {
        return listEmps.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imgEmp;
        TextView lblNameEmp;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgEmp = itemView.findViewById(R.id.imgEmployee);
            lblNameEmp = itemView.findViewById(R.id.lblNameEmployee);
        }
    }

    public interface OnClickListener {
        void onItemClick(String email);
    }

    public void openEmpInfo(Employee employee){
        new EmployeeDialog(employee).show(((FragmentActivity) context).getSupportFragmentManager(), "employee_dialog");
    }
}
