package com.ivangy.lospaco.helpers.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ivangy.lospaco.R;
import com.ivangy.lospaco.helpers.AndroidHelper;
import com.ivangy.lospaco.helpers.JSONRequest;
import com.ivangy.lospaco.helpers.Method;
import com.ivangy.lospaco.model.Cart;
import com.ivangy.lospaco.model.Package;
import com.ivangy.lospaco.model.Service;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

import static com.ivangy.lospaco.controller.fragment.CartFragment.recyclerCart;
import static com.ivangy.lospaco.helpers.AndroidHelper.setRecyclerConfig;
import static com.ivangy.lospaco.helpers.AndroidHelper.toastShort;

public class CartAdapter extends RecyclerView.Adapter {

    private Context context;
    private OnClickListener onClickListener = null;

    private List<Cart> listCart;

    private final String typeService = "SERVICO", typePackage = "PACOTE";

    private SparseBooleanArray selected_items = new SparseBooleanArray();
    private int current_selected_idx = -1;

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public CartAdapter(List<Cart> listCart) {
        this.listCart = listCart;
    }

    @Override
    public int getItemViewType(int position) {
        if (listCart.get(position).getType().equals(typeService))
            return 0;
        return 1;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        if (viewType == 0)
            return new ViewHolderServices(LayoutInflater.from(context).inflate(R.layout.item_cart_service, parent, false));
        return new ViewHolderPackages(LayoutInflater.from(context).inflate(R.layout.item_cart_package, parent, false));

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        Cart cart = listCart.get(position);

        if (getItemViewType(position)==0) {
            new ServiceLoader((ViewHolderServices) holder, cart, position, cart.getName());
        } else if (getItemViewType(position)==1) {
            new PackageLoader((ViewHolderPackages) holder, cart, position, cart.getName());
        }
    }

    @Override
    public int getItemCount() {
        return listCart.size();
    }

    public class ViewHolderServices extends RecyclerView.ViewHolder {
        private ImageView imgItem;
        private TextView lblNameItem, lblPriceItem;
        private EditText txtQntItem;
        private ImageButton btnSub, btnAdd;

        public ViewHolderServices(@NonNull View itemView) {
            super(itemView);
            txtQntItem = itemView.findViewById(R.id.txtQntItemCart);
            imgItem = itemView.findViewById(R.id.imgCartItem);
            lblNameItem = itemView.findViewById(R.id.lblNameItem);
            lblPriceItem = itemView.findViewById(R.id.lblPriceItem);
            btnSub = itemView.findViewById(R.id.btnSubtractItem);
            btnAdd = itemView.findViewById(R.id.btnAddItem);
        }
    }

    public class ViewHolderPackages extends RecyclerView.ViewHolder {
        private TextView lblNamePackage, lblPricePackage;
        private EditText txtQntPackage;
        private ImageView imgPackage;
        private RecyclerView recyclerItemsPackage;
        private ImageButton btnSub, btnAdd;

        public ViewHolderPackages(@NonNull View itemView) {
            super(itemView);
            lblNamePackage = itemView.findViewById(R.id.lblNamePackageCart);
            lblPricePackage = itemView.findViewById(R.id.lblPricePackageCart);
            txtQntPackage = itemView.findViewById(R.id.txtQntItemCart);
            imgPackage = itemView.findViewById(R.id.imgPackageCart);
            recyclerItemsPackage = itemView.findViewById(R.id.recyclerItemsPackageCart);
            btnSub = itemView.findViewById(R.id.btnSubtractItem);
            btnAdd = itemView.findViewById(R.id.btnAddItem);
        }
    }

    public void confirmDell(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setMessage("Tem certeza que deseja excluir o item do carrinho?")
                .setPositiveButton("Sim", (dialog, which) -> {
                    /*listAllCart.remove(position);*/
                    AndroidHelper.notify(recyclerCart);
                })
                .setNegativeButton("Não", null).create();

        builder.show();
    }

    private void btnSubAdd(ImageButton btnAdd, ImageButton btnSub, EditText txtQnt, TextView lblPrice, Cart cart, int position) {
/*        btnAdd.setOnClickListener(v -> {
            cart.setQntItem(Integer.parseInt(get(txtQnt)) + 1);
            txtQnt.setText(String.valueOf(cart.getQntItem()));
        });
        btnSub.setOnClickListener(v -> {
            if (Integer.parseInt(get(txtQnt)) == 1)
                confirmDell(position);
            else {
                if (Integer.parseInt(get(txtQnt)) > 1)
                    cart.setQntItem(Integer.parseInt(get(txtQnt)) - 1);
                txtQnt.setText(String.valueOf(cart.getQntItem()));
//                lblPrice.setText(context.getString(R.string.lbl_money_symb) +cart.getPriceTotal());
            }
        });*/
    }

    private void toggleCheckedIcon(View view, int position) {
        if (selected_items.get(position, false)) {
            view.setBackgroundColor(Color.parseColor("#65FF0000"));
        } else {
            view.setBackgroundColor(Color.parseColor("#FFFFFF"));
        }
        if (current_selected_idx == position) resetCurrentIndex();
    }

    public void toggleSelection(int pos) {
        current_selected_idx = pos;
        if (selected_items.get(pos, false)) {
            selected_items.delete(pos);
        } else {
            selected_items.put(pos, true);
        }
        notifyItemChanged(pos);
    }

    public void clearSelections() {
        selected_items.clear();
        notifyDataSetChanged();
    }

    public int getSelectedItemCount() {
        return selected_items.size();
    }

    public List<Integer> getSelectedItems() {
        List<Integer> items = new ArrayList<>(selected_items.size());
        for (int i = 0; i < selected_items.size(); i++) {
            items.add(selected_items.keyAt(i));
        }
        return items;
    }

    public void removeData(int position) {
        /*listAllCart.remove(position);*/
        resetCurrentIndex();
    }

    private void resetCurrentIndex() {
        current_selected_idx = -1;
    }

    public interface OnClickListener {
        void onItemClick(View view, Cart obj, int pos);

        void onItemLongClick(View view, Cart obj, int pos);
    }


    private class ServiceLoader extends AsyncTask<Integer, Integer, Service> {

        private ViewHolderServices holder;
        private Cart cart;
        private int position;
        private String name;

        public ServiceLoader(ViewHolderServices holder, Cart cart, int position, String name) {
            this.holder = holder;
            this.cart = cart;
            this.position = position;
            this.name = name;
        }

        @Override
        protected Service doInBackground(Integer... integers) {
            new JSONRequest(
                    JSONRequest.getJsonPlaceHolderApi(context).getServiceByName(name),
                    new Method() {
                        @Override
                        public <T> void onResponse(Call<T> call, Response<T> response) {
                            if (!response.isSuccessful())
                                toastShort(context, "Código do erro: " + response.code());
                            else {
                                Service service = (Service) response.body();
                                toastShort(context, "SERV: " + service.getName()+service.getPrice());

                                holder.lblNameItem.setText(service.getName());
                                holder.lblPriceItem.setText(context.getString(R.string.lbl_money_symb) + cart.getPrice());
                                holder.txtQntItem.setText(String.valueOf(cart.getQnt()));
                                try {
                                    holder.imgItem.setImageBitmap(service.getImage());
                                } catch (UnsupportedEncodingException e) {
                                    e.printStackTrace();
                                }

                                holder.itemView.setOnClickListener(v -> {
                                    if (onClickListener == null) return;
                                    onClickListener.onItemClick(v, cart, position);
                                });

                                holder.itemView.setOnLongClickListener(v -> {
                                    if (onClickListener == null) return false;
                                    onClickListener.onItemLongClick(v, cart, position);
                                    return true;
                                });

                                toggleCheckedIcon(holder.itemView, position);
                                btnSubAdd(holder.btnAdd, holder.btnSub, holder.txtQntItem, holder.lblPriceItem, cart, position);
                            }
                        }

                        @Override
                        public <T> void onFailure(Call<T> call, Throwable t) {
                            toastShort(context, "" + t.getMessage() +
                                    "\n Houve um problema ao verificar os dados, por favor, tente novamente");
                        }
                    });
            return null;
        }
    }

    private class PackageLoader extends AsyncTask<Integer, Integer, Package> {

        private ViewHolderPackages holder;
        private Cart cart;
        private int position;
        private String name;

        public PackageLoader(ViewHolderPackages holder, Cart cart, int position, String name) {
            this.holder = holder;
            this.cart = cart;
            this.position = position;
            this.name = name;
        }

        @Override
        protected Package doInBackground(Integer... integers) {
            new JSONRequest(
                    JSONRequest.getJsonPlaceHolderApi(context).getAllCategories(),
                    new Method() {
                        @Override
                        public <T> void onResponse(Call<T> call, Response<T> response) throws UnsupportedEncodingException {
                            if (!response.isSuccessful())
                                toastShort(context, "Código do erro: " + response.code());
                            else {

                                Package pack = (Package) response.body();

                                holder.imgPackage.setImageBitmap(pack.getImage());
                                holder.lblNamePackage.setText(pack.getName());
                                holder.lblPricePackage.setText(context.getString(R.string.lbl_money_symb) + pack.getPrice());
                                holder.txtQntPackage.setText(String.valueOf(cart.getQnt()));

                                ItemPackageAdapter adapter = new ItemPackageAdapter(pack.getServices());
                                setRecyclerConfig(context, holder.recyclerItemsPackage, adapter, LinearLayout.VERTICAL);

                                adapter.setOnClickListener(pos -> onClickListener.onItemLongClick(holder.itemView, cart, position));

                                holder.itemView.setOnClickListener(v -> {
                                    if (onClickListener == null) return;
                                    onClickListener.onItemClick(v, cart, position);
                                });

                                holder.itemView.setOnLongClickListener(v -> {
                                    if (onClickListener == null) return false;
                                    onClickListener.onItemLongClick(v, cart, position);
                                    return true;
                                });

                                toggleCheckedIcon(holder.itemView, position);

                                btnSubAdd(holder.btnAdd, holder.btnSub, holder.txtQntPackage, holder.lblPricePackage, cart, position);
                            }
                        }

                        @Override
                        public <T> void onFailure(Call<T> call, Throwable t) {
                            toastShort(context, "" + t.getMessage() +
                                    "\n Houve um problema ao verificar os dados, por favor, tente novamente");
                        }
                    });
            return null;
        }
    }


}
