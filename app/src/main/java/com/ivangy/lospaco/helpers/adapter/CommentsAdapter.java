package com.ivangy.lospaco.helpers.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ivangy.lospaco.R;
import com.ivangy.lospaco.model.Comments;

import java.util.ArrayList;

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.ViewHolder> {

    ArrayList<Comments> list;

    public CommentsAdapter(ArrayList<Comments> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comments, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.lblNameCli.setText(list.get(position).getNameClient());
        holder.lblTextComment.setText(list.get(position).getCommentText());
        holder.ratingBar.setRating(list.get(position).getStarRating());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView lblNameCli, lblTextComment;
        RatingBar ratingBar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            lblNameCli = itemView.findViewById(R.id.lblNameCli);
            lblTextComment = itemView.findViewById(R.id.lblTextComment);
            ratingBar =itemView.findViewById(R.id.ratingBarComments);
        }
    }
}
