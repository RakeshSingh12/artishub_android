package com.artishub.app.fragment;


import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.artishub.app.R;
import com.artishub.app.activities.CheckoutActivity;
import com.artishub.app.activities.HomeActivity;
import com.artishub.app.activities.MyAddressActivity;
import com.artishub.app.activities.ProductDetailsActivity;
import com.artishub.app.activities.SubCategoriesActivity;
import com.artishub.app.adapter.MyCartAdapter;
import com.artishub.app.constant.AppConstant;
import com.artishub.app.constant.ServerConstants;
import com.artishub.app.databinding.FragmentCartBinding;
import com.artishub.app.helpers.AlertUtil;
import com.artishub.app.helpers.RestClient;
import com.artishub.app.helpers.SharedPrefrencesManager;
import com.artishub.app.listener.ItemClickListener;
import com.artishub.app.listener.UpdateCartListener;
import com.artishub.app.model.CurrentUser;
import com.artishub.app.model.MyCartModel;
import com.artishub.app.model.SubCategoriesModel;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import static com.artishub.app.helpers.RestClient.ApiRequest.METHOD_POST;

/**
 * A simple {@link Fragment} subclass.
 */
public class CartFragment extends Fragment {
    private FragmentCartBinding binding;
    private View view;
    private CurrentUser currentUser;
    private RestClient.ApiRequest apiRequest;
    private MyCartModel myCartModel;
    private ArrayList<MyCartModel.ResultBean> myCartList = new ArrayList<>();
    MyCartAdapter myCartAdapter;
    float totalPoints = 0.0f;
    private int i, j;

    private boolean isLogin;


    public CartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_cart, container, false);
        view = binding.getRoot();
        currentUser = new Gson().fromJson(SharedPrefrencesManager.getInstance(HomeActivity.instance).getString(AppConstant.KEY_CURRENT_USER, ""), CurrentUser.class);
        isLogin = SharedPrefrencesManager.getInstance(HomeActivity.instance).getBoolean(AppConstant.IS_LOGIN, false);

        myCartAdapter = new MyCartAdapter(HomeActivity.instance, myCartList, position -> {
            i = position;
            callUpdateCartApi();
        }, (int position) -> {
            AlertUtil.showAlertDialog(HomeActivity.instance, "ArtisHub", "Are you sure you want to remove this product from cart?", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    j = position;
                    callDeleteCartApi();
                }
            }, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });

        });
        binding.recyclerCart.setLayoutManager(new LinearLayoutManager(HomeActivity.instance));
        binding.recyclerCart.setAdapter(myCartAdapter);

        binding.btnCheckout.setOnClickListener(view1 -> {

            Intent i = new Intent(HomeActivity.instance, CheckoutActivity.class);
            i.putExtra("list", new Gson().toJson(myCartList));
            i.putExtra("price", totalPoints);
            startActivity(i);

        });
        if (isLogin)
            callGetAllCartApi();
        return view;
    }

    private void callGetAllCartApi() {
        binding.rlLoader.setVisibility(View.VISIBLE);
        HashMap<String, String> params = new HashMap<>();
        params.put("method", "getAllcart");
        params.put("user_id", currentUser.getResult().getUser_id());


        apiRequest = new RestClient.ApiRequest(HomeActivity.instance);
        apiRequest.setUrl(ServerConstants.BASE_URL)
                .setMethod(METHOD_POST)
                .setParams(params)
                .setTag("getAllCart")
                .setResponseListener((tag, response) -> {
                    AlertUtil.hideProgressDialog();
                    binding.rlLoader.setVisibility(View.INVISIBLE);
                    if (response != null) {
                        myCartModel = new Gson().fromJson(response, MyCartModel.class);
                        if (myCartModel.getError_code() == 0) {
                            myCartList.clear();
                            myCartList.addAll(myCartModel.getResult());
                            totalpPoints();
                            myCartAdapter.notifyDataSetChanged();
                        } else {

                        }

                    } else {
                        AlertUtil.showSnackBarLong(HomeActivity.instance, binding.getRoot(), getString(R.string.error_generic), "Retry", view -> callGetAllCartApi());
                    }

                })

                .setErrorListener((tag, errorMsg) -> {
                    binding.rlLoader.setVisibility(View.INVISIBLE);
//                    AlertUtil.showSnackBarLong(HomeActivity.instance, binding.getRoot(), errorMsg, "Retry", view -> callGetAllCartApi());
                })
                .execute();

    }

    private void callUpdateCartApi() {
        binding.rlLoader.setVisibility(View.VISIBLE);
        HashMap<String, String> params = new HashMap<>();
        params.put("method", "update_cart");
        params.put("product_id", myCartList.get(i).getProduct_id());
        params.put("cart_id", myCartList.get(i).getCart_id());
        params.put("value", myCartList.get(i).getProduct_quantity());


        apiRequest = new RestClient.ApiRequest(HomeActivity.instance);
        apiRequest.setUrl(ServerConstants.BASE_URL)
                .setMethod(METHOD_POST)
                .setParams(params)
                .setTag("getAllCart")
                .setResponseListener((tag, response) -> {
                    AlertUtil.hideProgressDialog();
                    binding.rlLoader.setVisibility(View.INVISIBLE);
                    if (response != null) {
                        try {

                            JSONObject jsonObject = new JSONObject(response);
                            String msg;
                            if (jsonObject.getInt("error_code") == 0) {
                                msg = jsonObject.getString("error_string");
                                AlertUtil.showToast(HomeActivity.instance, msg);
                                totalpPoints();
                                myCartAdapter.notifyDataSetChanged();


                            } else {
                                msg = jsonObject.getString("error_string");
                                AlertUtil.showToast(HomeActivity.instance, msg);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    } else {
                        AlertUtil.showSnackBarLong(HomeActivity.instance, binding.getRoot(), getString(R.string.error_generic), "Retry", view -> callUpdateCartApi());
                    }

                })

                .setErrorListener((tag, errorMsg) -> {
                    binding.rlLoader.setVisibility(View.INVISIBLE);
                    AlertUtil.showSnackBarLong(HomeActivity.instance, binding.getRoot(), errorMsg, "Retry", view -> callUpdateCartApi());
                })
                .execute();

    }

    private void callDeleteCartApi() {
        binding.rlLoader.setVisibility(View.VISIBLE);
        HashMap<String, String> params = new HashMap<>();
        params.put("method", "delete_cart");
        params.put("product_id", myCartList.get(j).getProduct_id());
        params.put("cart_id", myCartList.get(j).getCart_id());



        apiRequest = new RestClient.ApiRequest(HomeActivity.instance);
        apiRequest.setUrl(ServerConstants.BASE_URL)
                .setMethod(METHOD_POST)
                .setParams(params)
                .setTag("getdeleteCart")
                .setResponseListener((tag, response) -> {
                    AlertUtil.hideProgressDialog();
                    binding.rlLoader.setVisibility(View.INVISIBLE);
                    if (response != null) {
                        try {

                            JSONObject jsonObject = new JSONObject(response);
                            String msg;
                            if (jsonObject.getInt("error_code") == 0) {
                                msg = jsonObject.getString("error_string");
                                AlertUtil.showToast(HomeActivity.instance, msg);
                                myCartAdapter.removeItem(j);
                                totalpPoints();



                            } else {
                                msg = jsonObject.getString("error_string");
                                AlertUtil.showToast(HomeActivity.instance, msg);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    } else {
                        AlertUtil.showSnackBarLong(HomeActivity.instance, binding.getRoot(), getString(R.string.error_generic), "Retry", view -> callDeleteCartApi());
                    }

                })

                .setErrorListener((tag, errorMsg) -> {
                    binding.rlLoader.setVisibility(View.INVISIBLE);
                    AlertUtil.showSnackBarLong(HomeActivity.instance, binding.getRoot(), errorMsg, "Retry", view -> callDeleteCartApi());
                })
                .execute();

    }


    private void totalpPoints() {
        totalPoints = 0.0f;
        for (int pos = 0; myCartList.size() > pos; pos++) {
            totalPoints = totalPoints + (Float.parseFloat(myCartList.get(pos).getProduct_quantity()) * Float.parseFloat(myCartList.get(pos).getProduct_unit_price()));
        }
        if (totalPoints < 1) {
            binding.rlTotalPrice.setVisibility(View.GONE);
            binding.rlEmpty.setVisibility(View.VISIBLE);
        } else {
            binding.rlTotalPrice.setVisibility(View.VISIBLE);
            binding.rlEmpty.setVisibility(View.GONE);
        }
        binding.txtTotalPrice.setText("$ " + totalPoints);
        SharedPrefrencesManager.getInstance(HomeActivity.instance).setString("CartItem",myCartList.size()+"");
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            if (isLogin)
                callGetAllCartApi();
        } else {
        }
    }
}
