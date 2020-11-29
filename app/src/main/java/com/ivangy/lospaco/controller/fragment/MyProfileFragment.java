package com.ivangy.lospaco.controller.fragment;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;
import com.ivangy.lospaco.R;
import com.ivangy.lospaco.controller.activity.MainActivity;
import com.ivangy.lospaco.helpers.JSONRequest;
import com.ivangy.lospaco.helpers.Method;
import com.ivangy.lospaco.model.Category;
import com.ivangy.lospaco.model.Customer;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

import static com.ivangy.lospaco.helpers.AndroidHelper.get;
import static com.ivangy.lospaco.helpers.AndroidHelper.isEmpty;
import static com.ivangy.lospaco.helpers.AndroidHelper.setRecyclerConfig;
import static com.ivangy.lospaco.helpers.AndroidHelper.toastShort;

public class MyProfileFragment extends Fragment {

    private TextInputEditText txtName, txtEmail, txtPhone;
    private Button btnUpdateCustomer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainActivity.toolbar.setTitle("Meu Perfil");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_my_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        txtName = view.findViewById(R.id.txtNameCust);
        txtEmail = view.findViewById(R.id.txtEmailCust);
        txtPhone = view.findViewById(R.id.txtPhoneCust);
        btnUpdateCustomer = view.findViewById(R.id.btnUpdateCust);
        new AsyncLoader().execute();

        btnUpdateCustomer.setOnClickListener(v -> {
            if(isEmpty(txtName) || isEmpty(txtEmail)){
                toastShort(getActivity(), "");
            }
        });

    }

    private class AsyncLoader extends AsyncTask<Integer, Integer, List<Category>> {
        @Override
        protected List<Category> doInBackground(Integer... integers) {

            SharedPreferences sharedPreferences = getActivity().getSharedPreferences(getResources().getString(R.string.shared_preferences_cust_login), 0);
            int id = sharedPreferences.getInt(getResources().getString(R.string.sp_cust_id), 0);

            new JSONRequest(
                    JSONRequest.getJsonPlaceHolderApi(getActivity()).getCustomerById(id),
                    new Method() {
                        @Override
                        public <T> void onResponse(Call<T> call, Response<T> response) {
                            if (!response.isSuccessful()) {
                                toastShort(getActivity(), "Código do erro: " + response.code());
                                return;
                            }
                            Customer jResponse = (Customer) response.body();
                            completeFields(jResponse);
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

    public void completeFields(Customer customer) {
        txtName.setText(customer.getNome());
        txtEmail.setText(customer.getEmail());
        txtPhone.setText(customer.getPhoneNumber());
    }


}