package com.ivangy.lospaco.controller.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.transition.Scene;
import android.transition.Slide;
import android.transition.TransitionManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ivangy.lospaco.R;
import com.ivangy.lospaco.controller.activity.MainActivity;
import com.ivangy.lospaco.helpers.adapter.ServicesAdapter;

import static com.ivangy.lospaco.helpers.AndroidHelper.setRecyclerConfig;
import static com.ivangy.lospaco.controller.activity.MainActivity.listAllServices;

public class ServicesFragment extends Fragment {

  public  RecyclerView recyclerService;
  private FloatingActionButton fabFilter;
  private Scene sceneFilter, sceneEmpty;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setHasOptionsMenu(true);
    MainActivity.toolbar.setTitle("Serviços");
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_services, container, false);

    recyclerService = view.findViewById(R.id.recyclerService);
    new AsyncLoader().execute();

    return view;
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    ConstraintLayout layoutRootServices = view.findViewById(R.id.layoutRootServices),
            layoutFilter = view.findViewById(R.id.layoutFilter);
    fabFilter = view.findViewById(R.id.fabFilter);
    sceneFilter = Scene.getSceneForLayout(layoutFilter, R.layout.scene_filter, getActivity());
    sceneEmpty = Scene.getSceneForLayout(layoutFilter, R.layout.scene_empty, getActivity());

    fabFilter.setOnClickListener(v -> {
      if(layoutFilter.getVisibility()==View.GONE) {
        layoutFilter.setVisibility(View.VISIBLE);
        TransitionManager.go(sceneFilter, new Slide(Gravity.BOTTOM));
      }
      else{
        TransitionManager.go(sceneEmpty, new Slide(Gravity.BOTTOM));
        layoutFilter.setVisibility(View.GONE);
      }
    });
    //spinRover.setAdapter(ArrayAdapter.createFromResource(MainActivity.this, R.array.rovers, R.layout.my_spinner));

  }

  private class AsyncLoader extends AsyncTask<Integer, Integer, String> {
    @Override
    protected String doInBackground(Integer... integers) {
      setRecyclerConfig(getActivity(), recyclerService, new ServicesAdapter(getActivity(), listAllServices), LinearLayout.VERTICAL);

      return null;
    }
  }

  @Override
  public void onPrepareOptionsMenu(@NonNull Menu menu) {
    super.onPrepareOptionsMenu(menu);
    menu.findItem(R.id.menuCart).setVisible(true);
    menu.findItem(R.id.menuSearch).setVisible(true);
  }

}