package com.artishub.app.activities;

import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.artishub.app.R;
import com.artishub.app.adapter.MyAddressAdapter;
import com.artishub.app.constant.AppConstant;
import com.artishub.app.constant.ServerConstants;
import com.artishub.app.databinding.ActivityMyAddressBinding;
import com.artishub.app.helpers.AlertUtil;
import com.artishub.app.helpers.AppUtilis;
import com.artishub.app.helpers.RestClient;
import com.artishub.app.helpers.SharedPrefrencesManager;
import com.artishub.app.listener.CustomClickListener;
import com.artishub.app.listener.ItemClickListener;
import com.artishub.app.model.CurrentUser;
import com.artishub.app.model.MyAddressModel;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import static com.artishub.app.helpers.RestClient.ApiRequest.METHOD_POST;

public class MyAddressActivity extends AppCompatActivity {
    ActivityMyAddressBinding binding;
    private CurrentUser currentUser;
    private RestClient.ApiRequest apiAddAddressRequest;
    private MyAddressAdapter addressAdapter;
    private MyAddressModel myAddressModel;
    private int p;
    private ArrayList<MyAddressModel.ResultBean> addressList = new ArrayList<>();
    private boolean fromSelection = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(android.R.color.transparent));
            window.setBackgroundDrawable(getDrawable(R.drawable.bg_statusbarone));
        }
        binding = DataBindingUtil.setContentView(MyAddressActivity.this, R.layout.activity_my_address);
        currentUser = new Gson().fromJson(SharedPrefrencesManager.getInstance(MyAddressActivity.this).getString(AppConstant.KEY_CURRENT_USER, ""), CurrentUser.class);

        if (getIntent().hasExtra("from")) {
            fromSelection = true;
        }
        addressAdapter = new MyAddressAdapter(MyAddressActivity.this, addressList, fromSelection, position -> {
            AlertUtil.showAlertDialog(this, "ArtisHub", "Are you sure you want to remove this address from My Address?", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                   p = position;
                    callDeleteAddressApi();
                }
            }, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });

        });

            // addressAdapter.removeItem(position);

        binding.recyclerViewAddress.setLayoutManager(new LinearLayoutManager(MyAddressActivity.this));
        binding.recyclerViewAddress.setAdapter(addressAdapter);


        binding.imgLeftSide.setOnClickListener(view -> finish());


        binding.txtAddMoreAddress.setOnClickListener(view -> {
            AppUtilis.goToActivity(MyAddressActivity.this, AddressMapActivity.class);

        });


    }

    @Override
    protected void onResume() {
        callGetAllAddressApi();
        super.onResume();
    }

    private void callDeleteAddressApi() {
        AppUtilis.hideSoftKeyboard(this);
        //AlertUtil.showProgressDialog(this);
        binding.rlLoader.setVisibility(View.VISIBLE);
        HashMap<String, String> params = new HashMap<>();
        params.put("address_id", myAddressModel.getResult().get(p).getAddress_id());
        params.put("method", "delete_address");


        apiAddAddressRequest = new RestClient.ApiRequest(MyAddressActivity.this);
        apiAddAddressRequest.setUrl(ServerConstants.BASE_URL)
                .setMethod(METHOD_POST)
                .setParams(params)
                .setTag("delete address")
                .setResponseListener((tag, response) -> {
                    // AlertUtil.hideProgressDialog();
                    binding.rlLoader.setVisibility(View.INVISIBLE);
                    if (response != null) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String msg;
                            if (jsonObject.getInt("error_code") == 0) {
                                msg = jsonObject.getString("error_string");
                                AlertUtil.showToast(MyAddressActivity.this, msg);
                                addressAdapter.removeItem(p);
                            } else {
                                msg = jsonObject.getString("error_string");
                                AlertUtil.showToast(MyAddressActivity.this, msg);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    } else
                        AlertUtil.showSnackBarLong(MyAddressActivity.this, binding.getRoot(), getString(R.string.error_generic), "Retry", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                callDeleteAddressApi();
                            }
                        });

                })
                .setErrorListener((tag, errorMsg) -> {
                    //AlertUtil.hideProgressDialog();
                    binding.rlLoader.setVisibility(View.INVISIBLE);
                    AlertUtil.showSnackBarLong(MyAddressActivity.this, binding.getRoot(), getString(R.string.error_generic), "Retry", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            callDeleteAddressApi();
                        }
                    });
                })
                .execute();
    }

    private void callGetAllAddressApi() {
        AppUtilis.hideSoftKeyboard(this);
        //AlertUtil.showProgressDialog(this);
        binding.rlLoader.setVisibility(View.VISIBLE);
        HashMap<String, String> params = new HashMap<>();
        params.put("user_id", currentUser.getResult().getUser_id());
        params.put("method", "getAlladdress");


        apiAddAddressRequest = new RestClient.ApiRequest(MyAddressActivity.this);
        apiAddAddressRequest.setUrl(ServerConstants.BASE_URL)
                .setMethod(METHOD_POST)
                .setParams(params)
                .setTag("get All address")
                .setResponseListener((tag, response) -> {
                    binding.rlLoader.setVisibility(View.INVISIBLE);
                    if (response != null) {
                        myAddressModel = new Gson().fromJson(response, MyAddressModel.class);
                        if (myAddressModel.getError_code() == 0) {
                            addressList.clear();
                            addressList.addAll(myAddressModel.getResult());
                            SharedPrefrencesManager.getInstance(MyAddressActivity.this).setString(AppConstant.ADDRESS, response);
                            addressAdapter.notifyDataSetChanged();
                        } else {
                            AlertUtil.showToast(MyAddressActivity.this, myAddressModel.getError_string());
                        }


                    } else
                        AlertUtil.showSnackBarLong(MyAddressActivity.this, binding.getRoot(), getString(R.string.error_generic), "Retry", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                callGetAllAddressApi();
                            }
                        });

                })
                .setErrorListener((tag, errorMsg) -> {
                    //AlertUtil.hideProgressDialog();
                    binding.rlLoader.setVisibility(View.INVISIBLE);
                    AlertUtil.showSnackBarLong(MyAddressActivity.this, binding.getRoot(), getString(R.string.error_generic), "Retry", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            callGetAllAddressApi();
                        }
                    });
                })
                .execute();
    }
}
