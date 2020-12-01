package com.ivangy.lospaco.controller.fragment;

import android.os.AsyncTask;
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
import androidx.appcompat.view.ActionMode;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.ivangy.lospaco.R;
import com.ivangy.lospaco.controller.activity.ItemsCartAdapter;
import com.ivangy.lospaco.controller.activity.MainActivity;
import com.ivangy.lospaco.helpers.JSONRequest;
import com.ivangy.lospaco.helpers.Method;
import com.ivangy.lospaco.helpers.adapter.CartAdapter;
import com.ivangy.lospaco.model.Account;
import com.ivangy.lospaco.model.Cart;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

import static com.ivangy.lospaco.helpers.AndroidHelper.setRecyclerConfig;
import static com.ivangy.lospaco.helpers.AndroidHelper.setSystemBarColor;
import static com.ivangy.lospaco.helpers.AndroidHelper.toastShort;

public class CartFragment extends Fragment {

    public static RecyclerView recyclerCart;
    private RecyclerView recyclerItem;
    private ActionModeCallback actionModeCallback;
    private ActionMode actionMode;
    private CartAdapter mCartAdapter;
    private TextView lblTotalValue;

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
        lblTotalValue = view.findViewById(R.id.lblTotalValue);

        new ListCartLoader().execute();
        new TotalPrice().execute();


        Button btnBuy = view.findViewById(R.id.btnBuy);
        btnBuy.setOnClickListener(v -> {
            setRecyclerConfig(getActivity(), recyclerItem,
                    new ItemsCartAdapter(), LinearLayout.VERTICAL);
            recyclerItem.setVisibility(View.VISIBLE);
        });

        actionModeCallback = new ActionModeCallback();
        return view;
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
            //setSystemBarColor(getActivity(), R.color.colorPrimary);
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


    private class ListCartLoader extends AsyncTask<Integer, Integer, List<Cart>> {
        @Override
        protected List<Cart> doInBackground(Integer... integers) {
            int id = Account.getInternalAccount(getActivity()).getId();

            new JSONRequest(
                    JSONRequest.getJsonPlaceHolderApi(getActivity()).getCartById(id),
                    new Method() {
                        @Override
                        public <T> void onResponse(Call<T> call, Response<T> response) {
                            if (!response.isSuccessful())
                                toastShort(getActivity(), "Código do erro: " + response.code());
                            else {
                                List<Cart> listCart = (List<Cart>) response.body();
                                mCartAdapter = new CartAdapter(listCart);
                                setRecyclerConfig(getActivity(), recyclerCart, mCartAdapter, LinearLayout.VERTICAL);

                                mCartAdapter.setOnClickListener(new CartAdapter.OnClickListener() {
                                    @Override
                                    public void onItemClick(View view, Cart obj, int pos) {
                                        if (mCartAdapter.getSelectedItemCount() > 0)
                                            enableActionMode(pos);
                                        else {
                                            //on Item click
                                        }
                                    }

                                    @Override
                                    public void onItemLongClick(View view, Cart obj, int pos) {
                                        enableActionMode(pos);
                                    }
                                });
                            }
                        }

                        @Override
                        public <T> void onFailure(Call<T> call, Throwable t) {
                            toastShort(getActivity(), "" + t.getMessage() +
                                    "\n Houve um problema ao verificar os dados, por favor, tente novamente");
                        }
                    });

            return null;
        }
    }

    private class TotalPrice extends AsyncTask<Integer, Integer, Double> {
        @Override
        protected Double doInBackground(Integer... integers) {
            int id = Account.getInternalAccount(getActivity()).getId();

            new JSONRequest(
                    JSONRequest.getJsonPlaceHolderApi(getActivity()).getCarTotalPrice(id),
                    new Method() {
                        @Override
                        public <T> void onResponse(Call<T> call, Response<T> response) {
                            if (!response.isSuccessful())
                                toastShort(getActivity(), "Código do erro: " + response.code());
                            else {
                                Double price = (Double) response.body();
                                lblTotalValue.setText(getResources().getString(R.string.lbl_money_symb)+price);
                            }
                        }

                        @Override
                        public <T> void onFailure(Call<T> call, Throwable t) {
                            toastShort(getActivity(), "" + t.getMessage() +
                                    "\n Houve um problema ao verificar os dados, por favor, tente novamente");
                        }
                    });

            return null;
        }
    }

}