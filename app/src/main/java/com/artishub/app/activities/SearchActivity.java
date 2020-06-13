package com.artishub.app.activities;

import android.databinding.DataBindingUtil;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.artishub.app.R;
import com.artishub.app.adapter.PopularSearchAdapter;
import com.artishub.app.adapter.SearchAdapter;
import com.artishub.app.adapter.SubCategoriesMainAdapter;
import com.artishub.app.constant.AppConstant;
import com.artishub.app.constant.ServerConstants;
import com.artishub.app.databinding.ActivitySearchBinding;
import com.artishub.app.helpers.AlertUtil;
import com.artishub.app.helpers.AppUtilis;
import com.artishub.app.helpers.RestClient;
import com.artishub.app.helpers.SharedPrefrencesManager;
import com.artishub.app.listener.ItemClickListener;
import com.artishub.app.model.AppConfigurationModel;
import com.artishub.app.model.SearchDetailsModel;
import com.artishub.app.model.SubCategoriesModel;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.artishub.app.helpers.RestClient.ApiRequest.METHOD_POST;

public class SearchActivity extends AppCompatActivity {
    private ActivitySearchBinding binding;
    private AppConfigurationModel appConfigurationModel;
    private PopularSearchAdapter popularSearchAdapter;
    private SearchAdapter subCategoriesMainAdapter;
    private SearchDetailsModel subCategoriesModel;
    private RestClient.ApiRequest apiRequest;
    private ArrayList<SearchDetailsModel.ProductDetailsBean> productListBySearch = new ArrayList<>();
    private List<AppConfigurationModel.ProductCategoryBean> popularSearchList = new ArrayList<>();
    private String textSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(android.R.color.transparent));
            window.setBackgroundDrawable(getDrawable(R.drawable.bg_statusbarone));
        }
        binding = DataBindingUtil.setContentView(SearchActivity.this, R.layout.activity_search);

        appConfigurationModel = new Gson().fromJson(SharedPrefrencesManager.getInstance(HomeActivity.instance).getString(AppConstant.APP_CONFIGURATION, ""), AppConfigurationModel.class);
        popularSearchList.addAll(appConfigurationModel.getProduct_category());
        popularSearchAdapter = new PopularSearchAdapter(SearchActivity.this, popularSearchList, position -> {
            binding.txtHeading.setText(popularSearchList.get(position).getCategory_name());
            textSearch=binding.txtHeading.getText().toString();
            callSearchApi();
        });
        binding.recyclerViewSearch.setLayoutManager(new LinearLayoutManager(SearchActivity.this, LinearLayoutManager.HORIZONTAL, false));
        binding.recyclerViewSearch.setAdapter(popularSearchAdapter);


        /**/
        subCategoriesMainAdapter = new SearchAdapter(productListBySearch, SearchActivity.this);
        binding.recyclerViewProduct.setLayoutManager(new GridLayoutManager(SearchActivity.this, 2));
        binding.recyclerViewProduct.setAdapter(subCategoriesMainAdapter);

        binding.imgLeftSide.setOnClickListener(view -> {
            finish();
        });


        binding.txtHeading.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                textSearch = binding.txtHeading.getText().toString();
                callSearchApi();
                return true;
            }
            return false;
        });

        binding.txtHeading.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
              if(editable.length()>1){
                  textSearch=binding.txtHeading.getText().toString();
                  callSearchApi();
              }
            }
        });

    }

    private void callSearchApi() {
        //AlertUtil.showProgressDialog(this);
        //binding.llShimmer.startShimmer();
        //AppUtilis.hideSoftKeyboard(this);
        binding.rlLoader.setVisibility(View.VISIBLE);
        HashMap<String, String> params = new HashMap<>();
        params.put("method", "product_search");
        params.put("name", textSearch);


        apiRequest = new RestClient.ApiRequest(this);
        apiRequest.setUrl(ServerConstants.BASE_URL)
                .setMethod(METHOD_POST)
                .setParams(params)
                .setTag("subcategories")
                .setResponseListener((tag, response) -> {
                    AlertUtil.hideProgressDialog();
                    binding.rlLoader.setVisibility(View.INVISIBLE);
                    if (response != null) {
                        subCategoriesModel = new Gson().fromJson(response, SearchDetailsModel.class);
                        if (subCategoriesModel.getError_code() == 0) {
                            binding.txtNoItemfound.setVisibility(View.INVISIBLE);
                            productListBySearch.clear();
                            productListBySearch.addAll(subCategoriesModel.getProduct_details());
                            subCategoriesMainAdapter.notifyDataSetChanged();
                        }
                        else if(subCategoriesModel.getError_code()==1){
                            productListBySearch.clear();
                            binding.txtNoItemfound.setVisibility(View.VISIBLE);
                           binding.txtNoItemfound.setText("No Product found for "+textSearch);
                        }

                    } else {

                        AlertUtil.showSnackBarLong(SearchActivity.this, binding.getRoot(), getString(R.string.error_generic), "Retry", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                callSearchApi();
                            }
                        });
                    }

                })

                .setErrorListener((tag, errorMsg) -> {
                    binding.rlLoader.setVisibility(View.INVISIBLE);

                    AlertUtil.showSnackBarLong(SearchActivity.this, binding.getRoot(), errorMsg, "Retry", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            callSearchApi();
                        }
                    });
                })
                .execute();

    }
}
