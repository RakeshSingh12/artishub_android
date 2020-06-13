package com.artishub.app.activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;

import com.artishub.app.R;
import com.artishub.app.adapter.SellerAdapter;
import com.artishub.app.constant.AppConstant;
import com.artishub.app.constant.ServerConstants;
import com.artishub.app.databinding.ActivityComparePriceBinding;
import com.artishub.app.helpers.AlertUtil;
import com.artishub.app.helpers.AppUtilis;
import com.artishub.app.helpers.RestClient;
import com.artishub.app.helpers.SharedPrefrencesManager;
import com.artishub.app.listener.ItemClickListener;
import com.artishub.app.model.CurrentUser;
import com.artishub.app.model.SellerModel;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import static com.artishub.app.helpers.RestClient.ApiRequest.METHOD_POST;

public class ComparePriceActivity extends AppCompatActivity {
    protected ActivityComparePriceBinding binding;
    private CurrentUser currentUser;
    private boolean isLogin;
    private RestClient.ApiRequest apiRequest;
    private ArrayList<SellerModel.ListBean> listBeans=new ArrayList<>();
    private String productId="";
    private String productID="";
    private SellerAdapter sellerAdapter;
    int cartItem=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(android.R.color.transparent));
            window.setBackgroundDrawable(getDrawable(R.drawable.bg_statusbarone));
        }
        binding = DataBindingUtil.setContentView(this, R.layout.activity_compare_price);
        currentUser = new Gson().fromJson(SharedPrefrencesManager.getInstance(this).getString(AppConstant.KEY_CURRENT_USER, ""), CurrentUser.class);
        isLogin = SharedPrefrencesManager.getInstance(this).getBoolean(AppConstant.IS_LOGIN, false);
        productId=getIntent().getStringExtra("productId");


        cartItem = Integer.parseInt(SharedPrefrencesManager.getInstance(this).getString("CartItem", "0"));
        if (cartItem > 0) {
            binding.txtCartNo.setVisibility(View.VISIBLE);
            binding.txtCartNo.setText("" + cartItem);
        }

        binding.imgLeftSide.setOnClickListener(view -> finish());

        binding.imgRightSide.setOnClickListener(view -> {
            Intent i = new Intent(this, HomeActivity.class);
            i.putExtra("toFragment", "ToMyCart");
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
        });

        sellerAdapter=new SellerAdapter(this, listBeans, position -> {
            productID=listBeans.get(position).getProduct_id();
           callAddToCartApi();
        });
        binding.recyclerPriceCompare.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerPriceCompare.setAdapter(sellerAdapter);


        if(productId!=null&&!productId.equals(""))
        callGetAllCartApi();


    }
    private void callGetAllCartApi() {
        binding.rlLoader.setVisibility(View.VISIBLE);
        HashMap<String, String> params = new HashMap<>();
        params.put("method", "compare_price");
        params.put("product_id", productId);


        apiRequest = new RestClient.ApiRequest(this);
        apiRequest.setUrl(ServerConstants.BASE_URL)
                .setMethod(METHOD_POST)
                .setParams(params)
                .setTag("get All Seller")
                .setResponseListener((tag, response) -> {
                    binding.rlLoader.setVisibility(View.INVISIBLE);
                    if (response != null) {
                        SellerModel sellerModel=new Gson().fromJson(response,SellerModel.class);
                        if(sellerModel.getError_code()==0){
                            listBeans.addAll(sellerModel.getList());

                            binding.txtProductName.setText(sellerModel.getList().get(0).getProduct_name());
                            sellerAdapter.notifyDataSetChanged();

                        }


                    } else {
                        AlertUtil.showSnackBarLong(this, binding.getRoot(), getString(R.string.error_generic), "Retry", view -> callGetAllCartApi());
                    }

                })

                .setErrorListener((tag, errorMsg) -> {
                    binding.rlLoader.setVisibility(View.INVISIBLE);
                    AlertUtil.showSnackBarLong(this, binding.getRoot(), errorMsg, "Retry", view -> callGetAllCartApi());
                })
                .execute();

    }


    private void callAddToCartApi() {
        AppUtilis.hideSoftKeyboard(this);
        binding.rlLoader.setVisibility(View.VISIBLE);
        HashMap<String, String> params = new HashMap<>();

        params.put("method", "add_to_cart");
        params.put("product_id", productID);
        params.put("user_id", currentUser.getResult().getUser_id());


        apiRequest = new RestClient.ApiRequest(this);
        apiRequest.setUrl(ServerConstants.BASE_URL)
                .setMethod(METHOD_POST)
                .setParams(params)
                .setTag("addToCart")
                .setResponseListener((tag, response) -> {
                    // AlertUtil.hideProgressDialog();
                    binding.rlLoader.setVisibility(View.INVISIBLE);
                    if (response != null) {
                        try {
                            String message;
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getInt("error_code") == 0) {
                                message = jsonObject.getString("error_string");
                                cartItem = jsonObject.getInt("count");
                                SharedPrefrencesManager.getInstance(this).setString("CartItem", "" + jsonObject.getString("count"));
                                AlertUtil.showToast(this, message);
                                setDataToCart();

                            } else {
                                message = jsonObject.getString("error_string");
                                AlertUtil.showToast(this, message);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                })
                .setErrorListener((tag, errorMsg) -> {
                    binding.rlLoader.setVisibility(View.INVISIBLE);
                    AlertUtil.showSnackBarLong(this, binding.getRoot(), getString(R.string.error_generic), "Retry", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            callAddToCartApi();
                        }
                    });
                })
                .execute();
    }

    private void setDataToCart() {
        binding.rlCart.setVisibility(View.GONE);
        binding.rlCart.setVisibility(View.VISIBLE);
        binding.rlCart.setAnimation(AnimationUtils.loadAnimation(this, R.anim.vibrate_anim));
        binding.txtCartNo.setVisibility(View.VISIBLE);
        binding.txtCartNo.setText("" + cartItem);


    }
}
