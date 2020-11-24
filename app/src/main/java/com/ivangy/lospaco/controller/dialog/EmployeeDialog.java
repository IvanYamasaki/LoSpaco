package com.ivangy.lospaco.controller.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.ivangy.lospaco.R;
import com.ivangy.lospaco.controller.activity.ServiceDetailsActivity;
import com.ivangy.lospaco.helpers.AndroidHelper;
import com.ivangy.lospaco.helpers.adapter.CommentsAdapter;
import com.ivangy.lospaco.model.Employee;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class EmployeeDialog extends AppCompatDialogFragment {

    private ImageView imgEmp;
    private TextView lblNameEmp, lblAgeEmp, lblRatingEmp, lblTimesWorked, lblServicesWorking;
    private RatingBar ratingEmp;
    private RecyclerView recyclerCommentEmp;
    private Employee employee;

    public EmployeeDialog(Employee employee) {
        this.employee = employee;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_emp_info, null);

        builder.setView(view);
        AlertDialog alertDialog = builder.create();



        imgEmp = view.findViewById(R.id.imgEmp);
        lblNameEmp = view.findViewById(R.id.lblNameEmp);
        lblAgeEmp = view.findViewById(R.id.lblAgeEmp);
        lblRatingEmp = view.findViewById(R.id.lblRatingEmp);
        lblTimesWorked = view.findViewById(R.id.lblTimesScheduled);
        lblServicesWorking = view.findViewById(R.id.lblServicesWorking);
        ratingEmp = view.findViewById(R.id.ratingEmp);
        recyclerCommentEmp = view.findViewById(R.id.recyclerCommentsEmp);

        Picasso.get().load(employee.getImage()).into(imgEmp);
        lblNameEmp.setText(employee.getName());
        lblRatingEmp.setText(String.valueOf(employee.getStarRating()));
        ratingEmp.setRating(employee.getStarRating());
        lblTimesWorked.setText("Consultas Realizadas:" + employee.getTimesWorked());
        lblAgeEmp.setText(employee.getAge()+" anos");

        ArrayList<String> listServicesWorking=new ArrayList<>();
        for(int i=0; i<3; i++)
        listServicesWorking.add("Serv"+i);

        for(int i=0; i<listServicesWorking.size(); i++){
            lblServicesWorking.append(listServicesWorking.get(i));
            if( i!=listServicesWorking.size()-1)
                lblServicesWorking.append(", ");

        }

        AndroidHelper.setRecyclerConfig(getActivity(), recyclerCommentEmp, new CommentsAdapter(ServiceDetailsActivity.listComments), LinearLayout.VERTICAL);

        view.findViewById(R.id.btnCloseEmpInfo).setOnClickListener(v -> alertDialog.dismiss());

        return alertDialog;

    }
}
