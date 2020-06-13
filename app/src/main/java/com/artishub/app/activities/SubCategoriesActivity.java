package com.artishub.app.activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;

import com.artishub.app.R;
import com.artishub.app.adapter.SearchAdapter;
import com.artishub.app.adapter.SubCategoriesMainAdapter;
import com.artishub.app.constant.AppConstant;
import com.artishub.app.constant.ServerConstants;
import com.artishub.app.databinding.ActivitySubCategoriesBinding;
import com.artishub.app.helpers.AlertUtil;
import com.artishub.app.helpers.AppUtilis;
import com.artishub.app.helpers.RestClient;
import com.artishub.app.helpers.SharedPrefrencesManager;
import com.artishub.app.model.AppConfigurationModel;
import com.artishub.app.model.SearchDetailsModel;
import com.artishub.app.model.SubCategoriesModel;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.artishub.app.helpers.RestClient.ApiRequest.METHOD_POST;

public class SubCategoriesActivity extends AppCompatActivity {
    private RestClient.ApiRequest apiRequest;
    protected ActivitySubCategoriesBinding binding;
    private String subCategories, subCategoryId;
    private SubCategoriesModel subCategoriesModel;
    private ArrayList<SubCategoriesModel.ProductBySubcategoryBean> subcategoryBeans = new ArrayList<>();
    private SubCategoriesMainAdapter subCategoriesMainAdapter;
    private String textSearch;
    private int cartItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(android.R.color.transparent));
            window.setBackgroundDrawable(getDrawable(R.drawable.bg_statusbarone));
        }
        binding = DataBindingUtil.setContentView(SubCategoriesActivity.this, R.layout.activity_sub_categories);

        subCategories = getIntent().getStringExtra("SUBCATEGORY");
        subCategoryId = getIntent().getStringExtra("SUBCATEGORYID");

        subCategoriesMainAdapter = new SubCategoriesMainAdapter(subcategoryBeans, SubCategoriesActivity.this, subCategories);
        binding.recyclerViewSubCat.setLayoutManager(new GridLayoutManager(SubCategoriesActivity.this, 2));
        binding.recyclerViewSubCat.setAdapter(subCategoriesMainAdapter);
        callAppConfigurationApi();
        binding.txtHeading.setText(subCategories);

        /*back button press*/
        binding.imgLeftSide.setOnClickListener(view -> finish());

        binding.imgRightSide.setOnClickListener(view -> {
            Intent i = new Intent(SubCategoriesActivity.this, HomeActivity.class);
            i.putExtra("toFragment", "ToMyCart");
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
        });


        binding.imgSearch.setOnClickListener(view -> {
            textSearch = binding.edtSearch.getText().toString();
            if (textSearch != null && !textSearch.equals(""))
                callSearchApi();
        });

        binding.edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() == 0) {
                    callAppConfigurationApi();
                }
            }
        });

        binding.edtSearch.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                textSearch = binding.edtSearch.getText().toString();
                if (textSearch != null && !textSearch.equals(""))
                    callSearchApi();
                return true;
            }
            return false;
        });
        binding.edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() > 1) {
                    textSearch=binding.edtSearch.getText().toString();
                    callSearchApi();
                }
            }
        });


    }

    //****************Forgot password Api***************//
    private void callAppConfigurationApi() {
        //AlertUtil.showProgressDialog(this);
        //binding.llShimmer.startShimmer();
        binding.rlLoader.setVisibility(View.VISIBLE);
        HashMap<String, String> params = new HashMap<>();
        params.put("method", "productbysubcategory");
        params.put("subcategory_id", subCategoryId);


        apiRequest = new RestClient.ApiRequest(this);
        apiRequest.setUrl(ServerConstants.BASE_URL)
                .setMethod(METHOD_POST)
                .setParams(params)
                .setTag("subcategories")
                .setResponseListener((tag, response) -> {
                    AlertUtil.hideProgressDialog();
                    //binding.llShimmer.startShimmer();
                    binding.rlLoader.setVisibility(View.INVISIBLE);
                    if (response != null) {
                        subCategoriesModel = new Gson().fromJson(response, SubCategoriesModel.class);
                        if (subCategoriesModel.getError_code() == 0) {
                            subcategoryBeans.clear();
                            binding.txtNoItemfound.setVisibility(View.GONE);
                            subcategoryBeans.addAll(subCategoriesModel.getProduct_by_subcategory());
                            subCategoriesMainAdapter.notifyDataSetChanged();
                            if (subcategoryBeans.size() < 1) {
                                binding.txtNoItemfound.setVisibility(View.VISIBLE);

                            }
                        } else {
                            binding.txtNoItemfound.setVisibility(View.VISIBLE);
                            binding.txtNoItemfound.setText("No Item Found");

                        }

                    } else {

                        AlertUtil.showSnackBarLong(SubCategoriesActivity.this, binding.getRoot(), getString(R.string.error_generic), "Retry", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                callAppConfigurationApi();
                            }
                        });
                    }

                })

                .setErrorListener((tag, errorMsg) -> {
                    //AlertUtil.hideProgressDialog();
                    binding.rlLoader.setVisibility(View.INVISIBLE);

                    AlertUtil.showSnackBarLong(SubCategoriesActivity.this, binding.getRoot(), errorMsg, "Retry", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            callAppConfigurationApi();
                        }
                    });
                })
                .execute();

    }


    private void callSearchApi() {
        //AlertUtil.showProgressDialog(this);
        //binding.llShimmer.startShimmer();
        //AppUtilis.hideSoftKeyboard(this);
        //binding.rlLoader.setVisibility(View.VISIBLE);
        HashMap<String, String> params = new HashMap<>();
        params.put("method", "product_search_by_subcategory");
        params.put("name", textSearch);
        params.put("subcategory_id", subCategoryId);


        apiRequest = new RestClient.ApiRequest(this);
        apiRequest.setUrl(ServerConstants.BASE_URL)
                .setMethod(METHOD_POST)
                .setParams(params)
                .setTag("search")
                .setResponseListener((tag, response) -> {
                    AlertUtil.hideProgressDialog();
                    binding.rlLoader.setVisibility(View.INVISIBLE);
                    if (response != null) {
                        subCategoriesModel = new Gson().fromJson(response, SubCategoriesModel.class);
                        if (subCategoriesModel.getError_code() == 0) {
                            binding.txtNoItemfound.setVisibility(View.INVISIBLE);
                            subcategoryBeans.clear();
                            subcategoryBeans.addAll(subCategoriesModel.getProduct_by_subcategory());
                            subCategoriesMainAdapter.notifyDataSetChanged();
                        } else if (subCategoriesModel.getError_code() == 1) {
                            binding.txtNoItemfound.setVisibility(View.VISIBLE);
                            subcategoryBeans.clear();
                            binding.txtNoItemfound.setText("No Product found for " + textSearch);
                            subCategoriesMainAdapter.notifyDataSetChanged();
                        }

                    } else {

                        AlertUtil.showSnackBarLong(SubCategoriesActivity.this, binding.getRoot(), getString(R.string.error_generic), "Retry", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                callSearchApi();
                            }
                        });
                    }

                })

                .setErrorListener((tag, errorMsg) -> {
                    //AlertUtil.hideProgressDialog();
                    binding.rlLoader.setVisibility(View.INVISIBLE);

                    AlertUtil.showSnackBarLong(SubCategoriesActivity.this, binding.getRoot(), errorMsg, "Retry", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            callSearchApi();
                        }
                    });
                })
                .execute();

    }

    @Override
    protected void onResume() {
        cartItem = Integer.parseInt(SharedPrefrencesManager.getInstance(SubCategoriesActivity.this).getString("CartItem", "0"));
        if (cartItem > 0) {
            binding.txtCartNo.setVisibility(View.VISIBLE);
            binding.txtCartNo.setText("" + cartItem);
        }
        super.onResume();
    }
}
