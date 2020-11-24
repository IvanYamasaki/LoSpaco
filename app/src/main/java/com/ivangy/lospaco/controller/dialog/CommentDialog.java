package com.ivangy.lospaco.controller.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.ivangy.lospaco.R;

import static com.ivangy.lospaco.helpers.AndroidHelper.toastShort;

public class CommentDialog extends AppCompatDialogFragment {

    private RatingBar ratingBar;
    private EditText txtComment;
    private CommentDialogListener listener;
    private float ratingBarStars;

    public CommentDialog(float ratingBarStars) {
        this.ratingBarStars = ratingBarStars;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_comment, null);

        builder.setView(view);
        AlertDialog alertDialog = builder.create();

        TextView lblFeedBack = view.findViewById(R.id.lblFeedbackRating);
        ratingBar = view.findViewById(R.id.ratingBarComment);
        txtComment = view.findViewById(R.id.txtComment);
        ImageView img = view.findViewById(R.id.imgAvaliation);

        view.findViewById(R.id.btnComment).setOnClickListener(v -> {
            if (ratingBar.getRating() == 0) {
                String text = txtComment.getText().toString();
                ratingBarStars = ratingBar.getRating();
                listener.applyText(text, ratingBarStars);
                alertDialog.dismiss();
            } else toastShort(getActivity(), "Campos obrigatórios");
        });


/*
        int[] drawable = getResources().getIntArray(R.array.sentimental_faces);
        String[] feedback = getResources().getStringArray(R.array.rating_feedback_comment);
*/
        int[] drawable =  {R.drawable.ic_baseline_sentiment_very_dissatisfied_24, R.drawable.ic_baseline_sentiment_dissatisfied_24, R.drawable.ic_baseline_sentiment_satisfied_24, R.drawable.ic_baseline_sentiment_satisfied_alt_24, R.drawable.ic_baseline_sentiment_very_satisfied_24};
        String[] feedback = {"poxa... como podemos melhoar para a próxima vez?", "que pena, no que podemos melhorar?", "no que podemos melhorar?", "Bom, mas no que acha que podemos melhorar?", "Ótimo! Compartilhe suas experiências com outros:"};

        ratingBar.setOnRatingBarChangeListener((ratingBar, rating, fromUser) -> {
            img.setImageBitmap(BitmapFactory.decodeResource(getResources(), drawable[Math.round(rating) -1]));
            lblFeedBack.setText(feedback[Math.round(rating) -1]);
        });

        ratingBar.setRating(ratingBarStars);

        view.findViewById(R.id.btnCloseAddComment).setOnClickListener(v -> alertDialog.dismiss());

        return alertDialog;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (CommentDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement CommentDialogListener"
            );
        }
    }

    public interface CommentDialogListener {
        void applyText(String commentText, float serviceStarRating);
    }

}
