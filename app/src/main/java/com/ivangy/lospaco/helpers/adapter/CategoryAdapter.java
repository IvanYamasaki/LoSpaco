package com.ivangy.lospaco.helpers.adapter;

import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ivangy.lospaco.R;
import com.ivangy.lospaco.controller.interfaces.JsonPlaceHolderApi;
import com.ivangy.lospaco.helpers.JSONRequest;
import com.ivangy.lospaco.helpers.Method;
import com.ivangy.lospaco.model.Category;
import com.ivangy.lospaco.model.Service;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.ivangy.lospaco.helpers.AndroidHelper.toastShort;
import static com.ivangy.lospaco.helpers.ServicesHelper.setServiceRecyclerConfig;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<Category> listCategories;

    public CategoryAdapter(Context mContext, ArrayList<Category> listCategories) {
        this.listCategories = listCategories;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public CategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CategoryAdapter.ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_recyclers, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Category category = listCategories.get(position);
        holder.lblCategory.setText(category.getName());

        new AsyncLoader(category.getId(), holder.recyclerItemService).execute();

    }

    @Override
    public int getItemCount() {
        return listCategories.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public RecyclerView recyclerItemService;
        TextView lblCategory;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            lblCategory = itemView.findViewById(R.id.lblCategory);
            recyclerItemService = itemView.findViewById(R.id.recyclerMain);
        }
    }


    private class AsyncLoader extends AsyncTask<Integer, Integer, List<Service>> {
        int position;
        RecyclerView recyclerItemService;

        public AsyncLoader(int position, RecyclerView recyclerItemService) {
            this.position = position;
            this.recyclerItemService = recyclerItemService;
        }

        @Override
        protected List<Service> doInBackground(Integer... integers) {
            new JSONRequest(
                    JSONRequest.getJsonPlaceHolderApi(mContext).getServicesByCategory(position),
                    new Method() {
                        @Override
                        public<T> void onResponse(Call<T> call, Response<T> response) {
                            if (!response.isSuccessful())
                                toastShort(mContext, "Código do erro: " + response.code());
                            else {
                                List<Service> listServices = (List<Service>) response.body();
                                setServiceRecyclerConfig(mContext, recyclerItemService,
                                        new ServicesAdapter(mContext, listServices),
                                        LinearLayout.HORIZONTAL, listServices.size());
                            }
                        }
                        @Override
                        public<T> void onFailure(Call<T> call, Throwable t) {
                            toastShort(mContext, "" + t.getMessage() +
                                    "\n Houve um problema ao verificar os dados, por favor, tente novamente");
                        }
                    });
            return null;
        }
    }
}
