package com.ivangy.lospaco.helpers.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
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
import com.ivangy.lospaco.model.Cart;
import com.ivangy.lospaco.model.Package;
import com.ivangy.lospaco.model.Service;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static com.ivangy.lospaco.controller.fragment.CartFragment.recyclerCart;
import static com.ivangy.lospaco.helpers.AndroidHelper.get;
import static com.ivangy.lospaco.helpers.AndroidHelper.getPackageByName;
import static com.ivangy.lospaco.helpers.AndroidHelper.getServiceByName;
import static com.ivangy.lospaco.helpers.AndroidHelper.setRecyclerConfig;

public class CartAdapter extends RecyclerView.Adapter {

    private Context context;
    private TextView lblTotalValue;
    private OnClickListener onClickListener = null;

    private final String typeService = "SERVICO", typePackage = "PACOTE";

    private SparseBooleanArray selected_items = new SparseBooleanArray();;
    private int current_selected_idx = -1;

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public CartAdapter(TextView lblTotalValue) {
        this.lblTotalValue = lblTotalValue;
    }

    @Override
    public int getItemViewType(int position) {
        if (getServiceByName("", new ArrayList<>()) != null)
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

        double totalValue = 0;
       // Cart cart = listAllCart.get(position);
       Cart cart = new Cart("a", 2, typePackage);

        if (cart.getType().equals(typeService)) {
            Service service = getServiceByName(cart.getNameItem(), /*listAllServices*/ new ArrayList<>());
            ViewHolderServices viewHolderServices = (ViewHolderServices) holder;

            assert service != null;
            EditText txt = viewHolderServices.txtQntItem;
            viewHolderServices.lblNameItem.setText(service.getName());
            viewHolderServices.lblPriceItem.setText(context.getString(R.string.lbl_money_symb) + service.getPrice());
//            Picasso.get().load(service.getImg()).into(viewHolderServices.imgItem);
            txt.setText(String.valueOf(cart.getQntItem()));

            viewHolderServices.itemView.setOnClickListener(v -> {
                if (onClickListener == null) return;
                onClickListener.onItemClick(v, cart, position);
            });

            viewHolderServices.itemView.setOnLongClickListener(v -> {
                if (onClickListener == null) return false;
                onClickListener.onItemLongClick(v, cart, position);
                return true;
            });

            toggleCheckedIcon(viewHolderServices.itemView, position);

            btnSubAdd(viewHolderServices.btnAdd, viewHolderServices.btnSub, viewHolderServices.txtQntItem, viewHolderServices.lblPriceItem, cart, position);

            totalValue=totalValue+(service.getPrice()*cart.getQntItem());

        } else
            if(cart.getType().equals(typePackage)){
            ViewHolderPackages viewHolderPackages = (ViewHolderPackages) holder;
            Package pack = getPackageByName(cart.getNameItem(), /*listAllPackages*/ new ArrayList<>());

            assert pack != null;
            Picasso.get().load(pack.getImgPackage()).into(viewHolderPackages.imgPackage);
            viewHolderPackages.lblNamePackage.setText(pack.getNamePackage());
            viewHolderPackages.lblPricePackage.setText(context.getString(R.string.lbl_money_symb) + pack.getPricepackage());
            viewHolderPackages.txtQntPackage.setText(String.valueOf(cart.getQntItem()));

            ItemPackageAdapter adapter = new ItemPackageAdapter(pack.getListServices());
            setRecyclerConfig(context, viewHolderPackages.recyclerItemsPackage, adapter, LinearLayout.VERTICAL);

            adapter.setOnClickListener(pos -> onClickListener.onItemLongClick(viewHolderPackages.itemView, cart, position));

            viewHolderPackages.itemView.setOnClickListener(v -> {
                if (onClickListener == null) return;
                onClickListener.onItemClick(v, cart, position);
            });

            viewHolderPackages.itemView.setOnLongClickListener(v -> {
                if (onClickListener == null) return false;
                onClickListener.onItemLongClick(v, cart, position);
                return true;
            });

            toggleCheckedIcon(viewHolderPackages.itemView, position);

            btnSubAdd(viewHolderPackages.btnAdd, viewHolderPackages.btnSub, viewHolderPackages.txtQntPackage, viewHolderPackages.lblPricePackage, cart, position);
            totalValue=totalValue+cart.getQntItem()*pack.getPricepackage();
        }

        lblTotalValue.setText(String.format("%.2f", totalValue).replace(",", "."));
    }

    @Override
    public int getItemCount() {
        return /*listAllCart.size()*/ 0;
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
        btnAdd.setOnClickListener(v -> {
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
        });
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

}
