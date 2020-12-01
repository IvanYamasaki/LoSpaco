package com.ivangy.lospaco.controller.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.ivangy.lospaco.R;
import com.ivangy.lospaco.controller.dialog.CommentDialog;
import com.ivangy.lospaco.helpers.AndroidHelper;
import com.ivangy.lospaco.helpers.Converter;
import com.ivangy.lospaco.helpers.adapter.CommentsAdapter;
import com.ivangy.lospaco.model.Comments;
import com.ivangy.lospaco.model.Service;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Objects;

import static com.ivangy.lospaco.helpers.AndroidHelper.setRecyclerConfig;

public class ServiceDetailsActivity extends AppCompatActivity
        implements CommentDialog.CommentDialogListener {

    private ImageView imgService;
    public RecyclerView recyclerComments;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    public static ArrayList<Comments> listComments = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_details);
        invalidateOptionsMenu();
        Service service = (Service) Objects.requireNonNull(getIntent().getExtras()).getSerializable("Service");

        Toolbar toolbar = findViewById(R.id.toolbarService);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        assert service != null;
        toolbar.setTitle(service.getName());

        collapsingToolbarLayout = findViewById(R.id.collapsingTollbar);
        recyclerComments = findViewById(R.id.recyclerComments);
        imgService = findViewById(R.id.imgServiceOwned);
        TextView lblDesc = findViewById(R.id.lblDescription),
                lblClothing = findViewById(R.id.lblClothing),
                lblPrice = findViewById(R.id.lblServPrice),
                lblRating = findViewById(R.id.lblServiceStarRating),
                lblTime = findViewById(R.id.lblTime);
        RatingBar ratingBarService = findViewById(R.id.ratingBarService), ratingBarAdd = findViewById(R.id.ratingAdd);

        collapsingToolbarLayout.setTitle(service.getName());

        try {
            imgService.setImageBitmap(service.getImage());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        lblDesc.setText(service.getCompleteDesc());
        lblClothing.setText(service.getPropperClothing());
        lblPrice.setText(getApplicationContext().getString(R.string.lbl_money_symb) + service.getPrice());
        lblTime.setText("" + service.getTime());

        if (service.getStarRating() != null) {
            ratingBarService.setRating(Float.valueOf(service.getStarRating()));
            lblRating.setText(service.getStarRating());
        }else{
            lblRating.setText("Não Avaliado");
        }

        listComments.add(new Comments("Gostei disso aqui heinheinheinheinheinheinheinheinheinheinheinheinheinheinheinheinheinheinheinheinheinheinheinheinheinheinheinheinheinheinheinhein", "Jones", 4));
        listComments.add(new Comments("Enfiaram me o dedo heinheinheinheinheinheinheinheinheinheinheinheinheinheinheinheinheinheinheinheinheinheinheinheinheinheinheinheinheinheinheinhein", "Sars", 5));
        listComments.add(new Comments("Não gostei", "Jhon", 4));
        listComments.add(new Comments("Muito bom MB MB", "Malda", 1));

        setRecyclerConfig(getApplicationContext(), recyclerComments, new CommentsAdapter(listComments), LinearLayout.VERTICAL);

        ratingBarAdd.setOnRatingBarChangeListener((ratingBar, rating, fromUser) -> {
            CommentDialog commentDialog = new CommentDialog(rating);
            commentDialog.show(getSupportFragmentManager(), "comment_dialog");
        });

        findViewById(R.id.btnAddCart).setOnClickListener(v -> {
/*
            listAllCart.add(new Cart(service.getName(), 1, "SERVICO"));
*/
            invalidateOptionsMenu();
        });

        this.getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                finish();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    public void applyText(String commentText, float serviceStarRating) {
        listComments.add(1, new Comments(commentText, "MyName", serviceStarRating));
        AndroidHelper.notify(recyclerComments);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem menuItemCart = menu.findItem(R.id.menuCart);
        menuItemCart.setIcon(Converter.convertLayoutToImage(ServiceDetailsActivity.this,
                /*listAllCart.size()*/0, R.drawable.ic_baseline_shopping_cart_24));
        menu.getItem(0).setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menuCart) {
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
            finish();
        }
        //Navigation.findNavController(this, R.id.nav_host_content).navigate(R.id.cartFragment);

        return super.onOptionsItemSelected(item);
    }
}