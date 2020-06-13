package com.artishub.app.activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;

import com.artishub.app.R;
import com.artishub.app.adapter.ProductImagePagerAdapter;
import com.artishub.app.constant.AppConstant;
import com.artishub.app.constant.ServerConstants;
import com.artishub.app.databinding.ActivityProductDetailsBinding;
import com.artishub.app.helpers.AlertUtil;
import com.artishub.app.helpers.AppUtilis;
import com.artishub.app.helpers.RestClient;
import com.artishub.app.helpers.SharedPrefrencesManager;
import com.artishub.app.model.CurrentUser;
import com.artishub.app.model.ProductDetailsModel;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import static com.artishub.app.helpers.RestClient.ApiRequest.METHOD_POST;

public class ProductDetailsActivity extends AppCompatActivity {
    private ActivityProductDetailsBinding binding;
    private RestClient.ApiRequest apiRequest;
    private String productId;
    private ProductDetailsModel productDetailsModel;
    private ArrayList<String> list = new ArrayList<>();
    private ProductImagePagerAdapter adapter;
    private CurrentUser currentUser;
    private int cartItem;
    private boolean isLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(android.R.color.transparent));
            window.setBackgroundDrawable(getDrawable(R.drawable.bg_statusbarone));
        }
        binding = DataBindingUtil.setContentView(ProductDetailsActivity.this, R.layout.activity_product_details);
        currentUser = new Gson().fromJson(SharedPrefrencesManager.getInstance(ProductDetailsActivity.this).getString(AppConstant.KEY_CURRENT_USER, ""), CurrentUser.class);
        isLogin = SharedPrefrencesManager.getInstance(HomeActivity.instance).getBoolean(AppConstant.IS_LOGIN, false);

        cartItem = Integer.parseInt(SharedPrefrencesManager.getInstance(ProductDetailsActivity.this).getString("CartItem", "0"));
        if (cartItem > 0) {
            binding.txtCartNo.setVisibility(View.VISIBLE);
            binding.txtCartNo.setText("" + cartItem);
        }
        productId = getIntent().getStringExtra("productId");
        callProfileApi();

        /*back button listener*/
        binding.imgLeftSide.setOnClickListener(view -> finish());


        binding.imgRightSide.setOnClickListener(view -> {
            Intent i = new Intent(ProductDetailsActivity.this, HomeActivity.class);
            i.putExtra("toFragment", "ToMyCart");
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
        });
        /*compare button click*/
        binding.btnComparePrice.setOnClickListener(view ->{
            Intent intent=new Intent(this,ComparePriceActivity.class);
            intent.putExtra("productId",productDetailsModel.getProduct_details().getProduct_id());
            startActivity(intent);
        });
        /*checkout button click*/
        binding.btnCheckout.setOnClickListener(view -> {
            if (isLogin)
                callAddToCartApi();
            else {
                AlertUtil.showToast(ProductDetailsActivity.this, "Please login");
            }
        });


    }

    private void callProfileApi() {
        AppUtilis.hideSoftKeyboard(this);
        //AlertUtil.showProgressDialog(this);
        binding.rlLoader.setVisibility(View.VISIBLE);
        HashMap<String, String> params = new HashMap<>();

        params.put("method", "product_details");
        params.put("product_id", productId);


        apiRequest = new RestClient.ApiRequest(ProductDetailsActivity.this);
        apiRequest.setUrl(ServerConstants.BASE_URL)
                .setMethod(METHOD_POST)
                .setParams(params)
                .setTag("Signup")
                .setResponseListener((tag, response) -> {
                    // AlertUtil.hideProgressDialog();
                    binding.rlLoader.setVisibility(View.INVISIBLE);
                    if (response != null) {
                        productDetailsModel = new Gson().fromJson(response, ProductDetailsModel.class);
                        if (productDetailsModel.getError_code() == 0) {

                            list.addAll(productDetailsModel.getProduct_details().getProduct_images());
                            setData();

                        }


                    } else
                        AlertUtil.showSnackBarLong(ProductDetailsActivity.this, binding.getRoot(), getString(R.string.error_generic), "Retry", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                callProfileApi();
                            }
                        });

                })
                .setErrorListener((tag, errorMsg) -> {
                    //AlertUtil.hideProgressDialog();
                    binding.rlLoader.setVisibility(View.INVISIBLE);
                    AlertUtil.showSnackBarLong(ProductDetailsActivity.this, binding.getRoot(), getString(R.string.error_generic), "Retry", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            callProfileApi();
                        }
                    });
                })
                .execute();
    }

    private void setData() {

        adapter = new ProductImagePagerAdapter(ProductDetailsActivity.this, list);
        binding.viewPager.setAdapter(adapter);
        initIndicator();
        binding.txtProductName.setText(productDetailsModel.getProduct_details().getProduct_name());
        binding.txtProductPrice.setText("$ " + productDetailsModel.getProduct_details().getSelling_price());
        binding.txtProductDescriptionInfo.setText(productDetailsModel.getProduct_details().getProduct_description());
        binding.txtProductSellerName.setText(productDetailsModel.getProduct_details().getSupplier_id());
        binding.txtProductCategory.setText(" Material : " + productDetailsModel.getProduct_details().getCategory());
        binding.txtProductModel.setText(" Model : " + productDetailsModel.getProduct_details().getSubcategory());
    }

    private void initIndicator() {
        binding.pageIndicator.setViewPager(binding.viewPager);
        final float density = getResources().getDisplayMetrics().density;
        binding.pageIndicator.setRadius(3 * density);
        binding.pageIndicator.setFillColor(Color.parseColor("#39b54a"));
        binding.pageIndicator.setPageColor(Color.parseColor("#ffffff"));
    }

    private void callAddToCartApi() {
        AppUtilis.hideSoftKeyboard(this);
        //AlertUtil.showProgressDialog(this);
        binding.rlLoader.setVisibility(View.VISIBLE);
        HashMap<String, String> params = new HashMap<>();

        params.put("method", "add_to_cart");
        params.put("product_id", productId);
        params.put("user_id", currentUser.getResult().getUser_id());


        apiRequest = new RestClient.ApiRequest(ProductDetailsActivity.this);
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
                                SharedPrefrencesManager.getInstance(ProductDetailsActivity.this).setString("CartItem", "" + jsonObject.getString("count"));
                                AlertUtil.showToast(ProductDetailsActivity.this, message);
                                binding.btnCheckout.setText("Added");
                                setDataToCart();

                            } else {
                                message = jsonObject.getString("error_string");
                                AlertUtil.showToast(ProductDetailsActivity.this, message);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                })
                .setErrorListener((tag, errorMsg) -> {
                    //AlertUtil.hideProgressDialog();
                    binding.rlLoader.setVisibility(View.INVISIBLE);
                    AlertUtil.showSnackBarLong(ProductDetailsActivity.this, binding.getRoot(), getString(R.string.error_generic), "Retry", new View.OnClickListener() {
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
        binding.rlCart.setAnimation(AnimationUtils.loadAnimation(ProductDetailsActivity.this, R.anim.vibrate_anim));
        binding.txtCartNo.setVisibility(View.VISIBLE);
        binding.txtCartNo.setText("" + cartItem);


    }


    @Override
    protected void onResume() {
        cartItem = Integer.parseInt(SharedPrefrencesManager.getInstance(ProductDetailsActivity.this).getString("CartItem", "0"));
        if (cartItem > 0) {
            binding.txtCartNo.setVisibility(View.VISIBLE);
            binding.txtCartNo.setText("" + cartItem);
        }
        super.onResume();
    }
}
