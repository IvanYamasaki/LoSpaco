package com.ivangy.lospaco.helpers.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ivangy.lospaco.R;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class TimesAdapter extends RecyclerView.Adapter<TimesAdapter.ViewHolder> {

    private ArrayList<Time> listTimes;
    private int hide = -1;
    private Time lastTime = null;
    private OnClickListener onClickListener = null;

    public void setOnClickListener(TimesAdapter.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public TimesAdapter(ArrayList<Time> listTimes) {
        this.listTimes = listTimes;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_times, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (listTimes.get(position) != null) {

            String time = new SimpleDateFormat("HH:mm").format(listTimes.get(position));
            holder.lblTime.setText(time);
        }

        holder.itemView.setVisibility(View.VISIBLE);
        if (hide == position)
            holder.itemView.setVisibility(View.GONE);

        holder.itemView.setOnClickListener(v -> {
            if (onClickListener == null) return;
            onClickListener.onItemClick(listTimes.get(position));

            hide = position;
            notifyDataSetChanged();

        });

    }

    @Override
    public int getItemCount() {
        return listTimes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView lblTime;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            lblTime = itemView.findViewById(R.id.lblTime);
        }
    }

    public interface OnClickListener {
        void onItemClick(Time time);
    }

}
