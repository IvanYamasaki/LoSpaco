package com.ivangy.lospaco.helpers.adapter;

import android.content.Context;
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

public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.ViewHolder> {

    private ArrayList<Employee> listEmps;
    private Context context;

    public EmployeeAdapter(ArrayList<Employee> listEmps) {
        this.listEmps = listEmps;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_employee, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Employee employee = listEmps.get(position);
        Picasso.get().load(employee.getImage()).into(holder.imgEmployee);
        holder.lblNameEmp.setText(employee.getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new EmployeeDialog(employee).show(((FragmentActivity) context).getSupportFragmentManager(), "employee_dialog");
            }
        });
    }

    @Override
    public int getItemCount() {
        return listEmps.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgEmployee;
        private TextView lblNameEmp;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgEmployee=itemView.findViewById(R.id.imgEmployee);
            lblNameEmp=itemView.findViewById(R.id.lblNameEmp);
        }
    }
}
