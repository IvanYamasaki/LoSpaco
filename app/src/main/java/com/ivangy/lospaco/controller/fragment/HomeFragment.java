package com.ivangy.lospaco.controller.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SearchEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.util.ViewPreloadSizeProvider;
import com.glide.slider.library.SliderLayout;
import com.glide.slider.library.animations.DescriptionAnimation;
import com.glide.slider.library.slidertypes.BaseSliderView;
import com.glide.slider.library.slidertypes.TextSliderView;
import com.glide.slider.library.tricks.InfiniteViewPager;
import com.glide.slider.library.tricks.ViewPagerEx;
import com.ivangy.lospaco.R;
import com.ivangy.lospaco.controller.activity.MainActivity;
import com.ivangy.lospaco.helpers.AndroidHelper;
import com.ivangy.lospaco.helpers.adapter.ItemServicesAdapter;
import com.ivangy.lospaco.model.Service;

import java.util.ArrayList;

import static com.ivangy.lospaco.controller.activity.MainActivity.listAllServices;
import static com.ivangy.lospaco.helpers.ServicesHelper.setServiceRecyclerConfig;

public class HomeFragment extends Fragment implements BaseSliderView.OnSliderClickListener,
        ViewPagerEx.OnPageChangeListener{

    public static ArrayList<String> listImagesCarousel;
    private SliderLayout mDemoSlider;
    private InfiniteViewPager mViewPager;
    private RecyclerView recyclerServicesHighlights;
    private Boolean isScrolling = false;
    private int currtentItems, totalItems, scrollOutItems;
    private ArrayList<Service> listService = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainActivity.toolbar.setTitle("Home");
        listImagesCarousel = new ArrayList<>();
        for (int i = 10; i < 15; i++) {
            listImagesCarousel.add("https://picsum.photos/id/" + i + "/500/300");
        }
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mDemoSlider = view.findViewById(R.id.slider);
        mViewPager = view.findViewById(R.id.glide_slider_viewpager);
        recyclerServicesHighlights = view.findViewById(R.id.recyclerServicesHighlights);

        for(int i=0; i<6; i++){
            listService.add(listAllServices.get(i));
        }
        setServiceRecyclerConfig(getActivity(), recyclerServicesHighlights, new ItemServicesAdapter(getActivity(), listService), LinearLayout.HORIZONTAL, listService.size());

        recyclerServicesHighlights.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL)
                    isScrolling=true;
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if(isScrolling && (currtentItems+scrollOutItems == totalItems)){
                    isScrolling=false;
                    fetchData();
                }
            }
        });



        ArrayList<String> listUrl = new ArrayList<>();
        ArrayList<String> listName = new ArrayList<>();

        for (int i = 10; i < 15; i++) {
            listUrl.add("https://picsum.photos/id/" + i + "/500/300");
            listName.add("Name " + i);
        }

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.centerCrop();
        //.diskCacheStrategy(DiskCacheStrategy.NONE)
        //.placeholder(R.drawable.placeholder)
        //.error(R.drawable.placeholder);

        for (int i = 0; i < listUrl.size(); i++) {
            TextSliderView sliderView = new TextSliderView(getActivity());

            sliderView
                    .image(listUrl.get(i))
                    .description(listName.get(i))
                    .setRequestOption(requestOptions)
                    .setProgressBarVisible(true)
            .setOnSliderClickListener(this);

            //add your extra information
            sliderView.bundle(new Bundle());
            sliderView.getBundle().putString("extra", listName.get(i));
            mDemoSlider.addSlider(sliderView);
        }

        // set Slider Transition Animation
        // mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Default);
        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);

        mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mDemoSlider.setCustomAnimation(new DescriptionAnimation());
        mDemoSlider.setDuration(3000);
        mDemoSlider.addOnPageChangeListener(this);
        mDemoSlider.stopCyclingWhenTouch(true);
    }

    private void fetchData() {
        new Handler().postDelayed(() -> {
            int size = listService.size();
            while (size<listAllServices.size() && size<(size+5)) {
                listService.add(listAllServices.get(size));

                AndroidHelper.notify(recyclerServicesHighlights);
                size++;
            }
            }, 5000);
    }

    @Override
    public void onStop() {
        // To prevent a memory leak on rotation, make sure to call stopAutoCycle()
        // on the slider before activity or fragment is destroyed
        mDemoSlider.stopAutoCycle();
        super.onStop();
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {
        Toast.makeText(getActivity(), slider.getBundle().getString("extra") + "", Toast.LENGTH_SHORT).show();
        mViewPager.setCurrentItem(mViewPager.getCurrentItem() - 1, true);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        super.onPrepareOptionsMenu(menu);

    }
}
