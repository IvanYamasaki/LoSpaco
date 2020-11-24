package com.ivangy.lospaco.controller.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ivangy.lospaco.R;
import com.ivangy.lospaco.helpers.adapter.SliderAdapter;
import com.ivangy.lospaco.model.ItemSlider;

import java.util.ArrayList;

public class SliderActivity extends AppCompatActivity {

    private ArrayList<ItemSlider> listSlider = new ArrayList<>();
    private LinearLayout layoutIndicator;
    Button btnNext, btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slider);

        for (int i = 50; i < 53; i++) {
            listSlider.add(new ItemSlider("https://picsum.photos/id/" + i + "/200/300", "Titlee", "Description"));
        }

        ViewPager2 viewPager = findViewById(R.id.viewPagerSlider);
        viewPager.setAdapter(new SliderAdapter(listSlider));

        btnNext = findViewById(R.id.btnNext);
        btnBack = findViewById(R.id.btnBack);

        layoutIndicator = findViewById(R.id.layoutIndicator);

        ImageView[] indicators = new ImageView[listSlider.size()];
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(8, 8, 8, 8);

        for (int i = 0; i < indicators.length; i++) {
            indicators[i] = new ImageView(getApplicationContext());
            indicators[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.slider_indicator));
            indicators[i].setLayoutParams(layoutParams);
            layoutIndicator.addView(indicators[i]);
        }

        setCurrentSliderIndicator(0);

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                setCurrentSliderIndicator(position);
            }
        });

        btnNext.setOnClickListener(v -> {
            if (viewPager.getCurrentItem() + 1 < listSlider.size())
                viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
            else {
                startMain();
            }
        });

        btnBack.setVisibility(View.INVISIBLE);
        btnBack.setText(R.string.slider_back);
        btnBack.setOnClickListener(v ->{
            if (viewPager.getCurrentItem() >0)
                viewPager.setCurrentItem(viewPager.getCurrentItem() -1);
        });

    }

    private void setCurrentSliderIndicator(int index) {
        int childCount = layoutIndicator.getChildCount();
        for (int i = 0; i < childCount; i++) {
            ImageView imageView = (ImageView) layoutIndicator.getChildAt(i);

            if (i == index)
                imageView.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.slider_long_indicator));
            else
                imageView.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.slider_indicator));
        }

        if(index == listSlider.size()-1)
            btnNext.setText(R.string.slider_start);
        else
            btnNext.setText(R.string.slider_next);

        if(index>0)
            btnBack.setVisibility(View.VISIBLE);
        else
            btnBack.setVisibility(View.INVISIBLE);
    }

    public void startMain(){
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        finish();
    }

}