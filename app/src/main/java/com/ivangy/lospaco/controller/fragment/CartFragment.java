package com.ivangy.lospaco.controller.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.view.ActionMode;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.ivangy.lospaco.R;
import com.ivangy.lospaco.controller.activity.ItemsCartAdapter;
import com.ivangy.lospaco.controller.activity.MainActivity;
import com.ivangy.lospaco.helpers.adapter.CartAdapter;
import com.ivangy.lospaco.model.Cart;

import java.util.List;

import static com.ivangy.lospaco.helpers.AndroidHelper.setRecyclerConfig;
import static com.ivangy.lospaco.helpers.AndroidHelper.setSystemBarColor;

public class CartFragment extends Fragment {

    public static RecyclerView recyclerCart;
    private  RecyclerView recyclerItem;
    private ActionModeCallback actionModeCallback;
    private ActionMode actionMode;
    private CartAdapter mCartAdapter= new CartAdapter();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        MainActivity.toolbar.setTitle("Carrinho");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);

        recyclerCart = view.findViewById(R.id.recyclerCart);
        recyclerItem = view.findViewById(R.id.recyclerItemsCart);
        setRecyclerConfig(getActivity(), recyclerCart, mCartAdapter, LinearLayout.VERTICAL);

        mCartAdapter.setOnClickListener(new CartAdapter.OnClickListener() {
            @Override
            public void onItemClick(View view, Cart obj, int pos) {
                if (mCartAdapter.getSelectedItemCount() > 0)
                    enableActionMode(pos);
                else{
                    //on Item click
                }
            }

            @Override
            public void onItemLongClick(View view, Cart obj, int pos) {
                enableActionMode(pos);
            }
        });

        Button btnBuy = view.findViewById(R.id.btnBuy);
        btnBuy.setOnClickListener(v -> {
            setRecyclerConfig(getActivity(), recyclerItem,
                    new ItemsCartAdapter(), LinearLayout.VERTICAL);
            recyclerItem.setVisibility(View.VISIBLE);
        });

        actionModeCallback = new ActionModeCallback();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView lblTotalValue = view.findViewById(R.id.lblTotalValue);
        lblTotalValue.setText(getActivity().getString(R.string.str_total_value)+getActivity().getString(R.string.lbl_money_symb)+"1231");
    }

    private void enableActionMode(int position) {
        if (actionMode == null) {
            actionMode = ((MainActivity) getActivity()).startSupportActionMode(actionModeCallback);
        }
        toggleSelection(position);
    }

    private void toggleSelection(int position) {
        mCartAdapter.toggleSelection(position);
        int count = mCartAdapter.getSelectedItemCount();

        if (count == 0) {
            actionMode.finish();
        } else {
            actionMode.setTitle(String.valueOf(count));
            actionMode.invalidate();
        }
    }

    private class ActionModeCallback implements ActionMode.Callback {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            setSystemBarColor(getActivity(), R.color.colorPrimary);
            mode.getMenuInflater().inflate(R.menu.menu_items_selected_cart, menu);
            MainActivity.toolbar.setVisibility(View.GONE);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            if (item.getItemId() == R.id.menuDelete) {
                deleteInboxes();
                mode.finish();
                return true;
            }
            return false;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            mCartAdapter.clearSelections();
            actionMode = null;
            setSystemBarColor(getActivity(), R.color.colorPrimaryDark);
            MainActivity.toolbar.setVisibility(View.VISIBLE);
        }
    }

    private void deleteInboxes() {
        List<Integer> selectedItemPositions = mCartAdapter.getSelectedItems();
        for (int i = selectedItemPositions.size() - 1; i >= 0; i--) {
            mCartAdapter.removeData(selectedItemPositions.get(i));
        }
        mCartAdapter.notifyDataSetChanged();
    }

    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        super.onPrepareOptionsMenu(menu);
        menu.findItem(R.id.menuCart).setVisible(false);
        menu.findItem(R.id.menuSearch).setVisible(false);
    }

}