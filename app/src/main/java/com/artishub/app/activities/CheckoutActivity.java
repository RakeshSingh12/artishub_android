package com.artishub.app.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CompoundButton;

import com.artishub.app.R;
import com.artishub.app.adapter.CheckoutAdapter;
import com.artishub.app.constant.AppConstant;
import com.artishub.app.constant.ServerConstants;
import com.artishub.app.databinding.ActivityCheckoutBinding;
import com.artishub.app.helpers.AlertUtil;
import com.artishub.app.helpers.AppUtilis;
import com.artishub.app.helpers.RestClient;
import com.artishub.app.helpers.SharedPrefrencesManager;
import com.artishub.app.model.CurrentUser;
import com.artishub.app.model.MyAddressModel;
import com.artishub.app.model.MyCartModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.artishub.app.helpers.RestClient.ApiRequest.METHOD_POST;

public class CheckoutActivity extends AppCompatActivity {
    ActivityCheckoutBinding binding;
    String list;
    CheckoutAdapter checkoutAdapter;
    float totalPoints = 0;
    double total = 0;
    float shippingCharge=0;
    ArrayList<MyCartModel.ResultBean> myCartList = new ArrayList<>();
    private CurrentUser currentUser;
    private RestClient.ApiRequest apiAddAddressRequest;
    private RestClient.ApiRequest apiOrderPlaceRequest;
    MyAddressModel myAddressModel;
    ArrayList<MyAddressModel.ResultBean> myAddressList = new ArrayList<>();
    String response = "";
    int addressID = 0;
    String deliveryType = "";
    String paymentType="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(android.R.color.transparent));
            window.setBackgroundDrawable(getDrawable(R.drawable.bg_statusbarone));
        }
        binding = DataBindingUtil.setContentView(CheckoutActivity.this, R.layout.activity_checkout);
        currentUser = new Gson().fromJson(SharedPrefrencesManager.getInstance(CheckoutActivity.this).getString(AppConstant.KEY_CURRENT_USER, ""), CurrentUser.class);
        response = SharedPrefrencesManager.getInstance(this).getString(AppConstant.ADDRESS, "");

        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
                new IntentFilter("addressSelection"));


        //********************checkbox tick*********************//
        binding.checkboxDelivery.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                if (response != null && !response.equals("")) {
                    setAddress();
                    binding.checkboxDelivery.setTextColor(getResources().getColor(R.color.app_color_two));
                    deliveryType = "1";
                } else {
                    callAddAddressApi();

                }
            } else {
                binding.rlAddress.setVisibility(View.GONE);
                binding.txtAddAddress.setVisibility(View.GONE);
                binding.checkboxDelivery.setTextColor(getResources().getColor(R.color.gray_color));
                deliveryType = "";

            }
        });

        list = getIntent().getStringExtra("list");
        totalPoints = getIntent().getFloatExtra("price", 0);

        myCartList.addAll(new Gson().fromJson(list, new TypeToken<List<MyCartModel.ResultBean>>() {}.getType()));
        checkoutAdapter = new CheckoutAdapter(CheckoutActivity.this, myCartList);
        binding.recyclerCheckout.setLayoutManager(new LinearLayoutManager(CheckoutActivity.this));
        binding.recyclerCheckout.setAdapter(checkoutAdapter);
        binding.txtTotalPrice.setText("$ " + totalPoints);

        for(int pos = 0; myCartList.size() > pos; pos++){
            shippingCharge = shippingCharge + Float.parseFloat(myCartList.get(pos).getShipping_charges());
        }

        binding.txtShippingPrice.setText("$ "+shippingCharge);


        binding.txtVatPrice.setText("$ "+(calculateVat(myCartList.get(0).getVat(),totalPoints)));






        binding.recyclerCheckout.setNestedScrollingEnabled(true);

        binding.imgBanking.setOnClickListener(view -> {
            binding.imgBanking.setImageResource(R.drawable.banking_card_active);
            binding.imgWallet.setImageResource(R.drawable.wallet_normal);
            binding.imgCashOnDelivery.setImageResource(R.drawable.cash_normal);

            binding.txtBanking.setTextColor(getResources().getColor(R.color.app_color_two));
            binding.txtCashOnDelivery.setTextColor(getResources().getColor(R.color.gray_color));
            binding.txtWallet.setTextColor(getResources().getColor(R.color.gray_color));

            paymentType="Net Banking";
        });
        binding.imgWallet.setOnClickListener(view -> {
            binding.imgBanking.setImageResource(R.drawable.banking_card_normal);
            binding.imgWallet.setImageResource(R.drawable.wallet_active);
            binding.imgCashOnDelivery.setImageResource(R.drawable.cash_normal);

            binding.txtBanking.setTextColor(getResources().getColor(R.color.gray_color));
            binding.txtCashOnDelivery.setTextColor(getResources().getColor(R.color.gray_color));
            binding.txtWallet.setTextColor(getResources().getColor(R.color.app_color_two));

            paymentType="Wallet";
        });
        binding.imgCashOnDelivery.setOnClickListener(view -> {
            binding.imgBanking.setImageResource(R.drawable.banking_card_normal);
            binding.imgWallet.setImageResource(R.drawable.wallet_normal);
            binding.imgCashOnDelivery.setImageResource(R.drawable.cash_active);

            binding.txtBanking.setTextColor(getResources().getColor(R.color.gray_color));
            binding.txtCashOnDelivery.setTextColor(getResources().getColor(R.color.app_color_two));
            binding.txtWallet.setTextColor(getResources().getColor(R.color.gray_color));

            paymentType="COD";
        });


       if(currentUser.getResult().getPayment_mode().equals("COD")){
           binding.imgBanking.setImageResource(R.drawable.banking_card_normal);
           binding.imgWallet.setImageResource(R.drawable.wallet_normal);
           binding.imgCashOnDelivery.setImageResource(R.drawable.cash_active);

           binding.txtBanking.setTextColor(getResources().getColor(R.color.gray_color));
           binding.txtCashOnDelivery.setTextColor(getResources().getColor(R.color.app_color_two));
           binding.txtWallet.setTextColor(getResources().getColor(R.color.gray_color));

           paymentType="COD";

       }else if(currentUser.getResult().getPayment_mode().equals("Wallet")){
           binding.imgBanking.setImageResource(R.drawable.banking_card_normal);
           binding.imgWallet.setImageResource(R.drawable.wallet_active);
           binding.imgCashOnDelivery.setImageResource(R.drawable.cash_normal);

           binding.txtBanking.setTextColor(getResources().getColor(R.color.gray_color));
           binding.txtCashOnDelivery.setTextColor(getResources().getColor(R.color.gray_color));
           binding.txtWallet.setTextColor(getResources().getColor(R.color.app_color_two));

           paymentType="Wallet";

       }else if(currentUser.getResult().getPayment_mode().equals("Net Banking")){
           binding.imgBanking.setImageResource(R.drawable.banking_card_active);
           binding.imgWallet.setImageResource(R.drawable.wallet_normal);
           binding.imgCashOnDelivery.setImageResource(R.drawable.cash_normal);

           binding.txtBanking.setTextColor(getResources().getColor(R.color.app_color_two));
           binding.txtCashOnDelivery.setTextColor(getResources().getColor(R.color.gray_color));
           binding.txtWallet.setTextColor(getResources().getColor(R.color.gray_color));

           paymentType="Net Banking";

       }


        binding.txtAddAddress.setOnClickListener(view -> {
            Intent i = new Intent(this, MyAddressActivity.class);
            i.putExtra("from", "selection");
            startActivity(i);
        });
        binding.imgEditAddress.setOnClickListener(view -> {
            Intent i = new Intent(this, MyAddressActivity.class);
            i.putExtra("from", "selection");
            startActivity(i);
        });

        binding.btnOrderPlace.setOnClickListener(view -> {
            if(deliveryType.equals("")){
                AlertUtil.showToast(CheckoutActivity.this,"Please select your Address");
            }else{
                if(myAddressList.size()<1){
                    AlertUtil.showToast(CheckoutActivity.this,"Please select your Address");

                }else{
                    if(paymentType.equals("")){
                        AlertUtil.showToast(CheckoutActivity.this,"Please select your Payment");

                    }
                    else{
                        callOrderPlaceApi();
                    }
                }
            }


        });

        binding.imgLeftSide.setOnClickListener(view -> finish());

    }

    private void callAddAddressApi() {
        AppUtilis.hideSoftKeyboard(this);
        binding.rlLoader.setVisibility(View.VISIBLE);
        HashMap<String, String> params = new HashMap<>();
        params.put("user_id", currentUser.getResult().getUser_id());
        params.put("method", "getAlladdress");


        apiAddAddressRequest = new RestClient.ApiRequest(this);
        apiAddAddressRequest.setUrl(ServerConstants.BASE_URL)
                .setMethod(METHOD_POST)
                .setParams(params)
                .setTag("get All address")
                .setResponseListener((tag, response) -> {
                    binding.rlLoader.setVisibility(View.INVISIBLE);
                    if (response != null) {
                        myAddressModel = new Gson().fromJson(response, MyAddressModel.class);
                        if (myAddressModel.getError_code() == 0) {
                            myAddressList.addAll(myAddressModel.getResult());
                            SharedPrefrencesManager.getInstance(CheckoutActivity.this).setString(AppConstant.ADDRESS, response);
                            setAddress();
                        } else {
                            AlertUtil.showToast(this, myAddressModel.getError_string());
                        }


                    } else {

                    }


                })
                .setErrorListener((tag, errorMsg) -> {
                    //AlertUtil.hideProgressDialog();
                    binding.rlLoader.setVisibility(View.INVISIBLE);

                })
                .execute();
    }

    private void setAddress() {
        response = SharedPrefrencesManager.getInstance(this).getString(AppConstant.ADDRESS, "");
        if (response != null & !response.equals("")) {
            myAddressModel = new Gson().fromJson(response, MyAddressModel.class);
            myAddressList.clear();
            myAddressList.addAll(myAddressModel.getResult());
        }
        if (myAddressList.size() > 0) {
            binding.txtAddAddress.setVisibility(View.GONE);
            binding.txtAddressName.setText(myAddressList.get(addressID).getName());
            binding.txtAddress.setText(myAddressList.get(addressID).getHouse_no() + "," + myAddressList.get(addressID).getAddress() + "," + myAddressList.get(addressID).getLandmark());
            binding.txtAddressNumber.setText(myAddressList.get(addressID).getCountry_code()+" " + myAddressList.get(addressID).getMobile_number());
            binding.rlAddress.setVisibility(View.VISIBLE);
        } else {
            binding.txtAddAddress.setVisibility(View.VISIBLE);
        }

    }


    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            addressID = intent.getIntExtra("position", 0);
            setAddress();

        }
    };

    private void callOrderPlaceApi() {
        AppUtilis.hideSoftKeyboard(this);
        binding.rlLoader.setVisibility(View.VISIBLE);
        HashMap<String, String> params = new HashMap<>();
        params.put("user_id", currentUser.getResult().getUser_id());
        params.put("method", "checkout");
        params.put("address_id", myAddressList.get(addressID).getAddress_id());
        params.put("delivery_type",deliveryType);
        params.put("payment_type",paymentType);


        apiOrderPlaceRequest = new RestClient.ApiRequest(this);
        apiOrderPlaceRequest.setUrl(ServerConstants.BASE_URL)
                .setMethod(METHOD_POST)
                .setParams(params)
                .setTag("orderPlace")
                .setResponseListener((tag, response) -> {
                    binding.rlLoader.setVisibility(View.INVISIBLE);
                    if (response != null) {
                        try {
                            String message;
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getInt("error_code") == 0) {
                                AppUtilis.goToActivity(CheckoutActivity.this, OrderConfirmationActivity.class);
                                finish();


                            } else {
                                message = jsonObject.getString("error_string");
                                AlertUtil.showToast(CheckoutActivity.this, message);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    } else {
                        AlertUtil.showSnackBarLong(CheckoutActivity.this, binding.getRoot(), getString(R.string.error_generic), "Retry", view -> callOrderPlaceApi());
                    }


                })
                .setErrorListener((tag, errorMsg) -> {

                    binding.rlLoader.setVisibility(View.INVISIBLE);
                    AlertUtil.showSnackBarLong(CheckoutActivity.this, binding.getRoot(), getString(R.string.error_generic), "Retry", view -> callOrderPlaceApi());

                })
                .execute();
    }

    private double calculateVat(int vat,Float total1){

        double vatPercentage=vat*.01;

        binding.txtVat.setText("Vat("+vat+"%)");
        total=total1*vatPercentage+total1+shippingCharge;
        binding.txtTotalprice.setText("$ "+total);
        return (Math.round(total1*vatPercentage * 100.0) / 100.0);


    }

}
