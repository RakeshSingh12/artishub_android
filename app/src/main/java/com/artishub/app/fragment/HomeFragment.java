package com.artishub.app.fragment;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.artishub.app.R;
import com.artishub.app.activities.HomeActivity;
import com.artishub.app.activities.SearchActivity;
import com.artishub.app.adapter.AutoScrollPageAdapter;
import com.artishub.app.adapter.CategoriesAdapter;
import com.artishub.app.constant.AppConstant;
import com.artishub.app.databinding.FragmentHomeBinding;
import com.artishub.app.helpers.AppUtilis;
import com.artishub.app.helpers.AutoScrollViewPager;
import com.artishub.app.helpers.CustomGridLayoutManager;
import com.artishub.app.helpers.SharedPrefrencesManager;
import com.artishub.app.model.AppConfigurationModel;
import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;
    private View view;
    private AppConfigurationModel appConfigurationModel;
    private ArrayList<AppConfigurationModel.ProductCategoryBean> productCategoryBeans=new ArrayList<>();
    private ArrayList<AppConfigurationModel.ProductSubcategoryBean> productSubcategoryBeans=new ArrayList<>();
    private ArrayList<AppConfigurationModel.BannersBean> bannerList=new ArrayList<>();
    private CategoriesAdapter categoriesAdapter;
    private CustomGridLayoutManager customGridLayoutManager;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_home,container,false);
        view=binding.getRoot();

        //***********get app configuration************//
        appConfigurationModel=new Gson().fromJson(SharedPrefrencesManager.getInstance(HomeActivity.instance).getString(AppConstant.APP_CONFIGURATION,""),AppConfigurationModel.class);



        binding.search.setOnClickListener(view1 -> {
            AppUtilis.goToActivity(HomeActivity.instance, SearchActivity.class);
        });

        binding.search.setOnFocusChangeListener((view, b) -> {

            if(b){
                AppUtilis.goToActivity(HomeActivity.instance, SearchActivity.class);
            }
        });


       if(AppUtilis.isNetworkAvailable(HomeActivity.instance)){
           binding.rlNoInternet.setVisibility(View.GONE);
           binding.rlMain.setVisibility(View.VISIBLE);
           //iniView();
           //setSliderOnViewPager();

       }else{
           binding.rlMain.setVisibility(View.GONE);
           binding.rlNoInternet.setVisibility(View.VISIBLE);
       }



        return view;


    }
    //***********************adapter setUp**********************//
    private void iniView(){

        customGridLayoutManager=new CustomGridLayoutManager(HomeActivity.instance);
        /*add list*/
        productCategoryBeans.addAll(appConfigurationModel.getProduct_category());
        productSubcategoryBeans.addAll(appConfigurationModel.getProduct_subcategory());

        //*************adapter setUp***********//
        categoriesAdapter=new CategoriesAdapter(HomeActivity.instance,productCategoryBeans,productSubcategoryBeans);
        binding.recylerView.setLayoutManager(new LinearLayoutManager(HomeActivity.instance));
        //binding.recylerView.setNestedScrollingEnabled(false);
        binding.recylerView.setAdapter(categoriesAdapter);
    }


    //****************Auto slider pager****************//
    private void setSliderOnViewPager() {
        bannerList.addAll(appConfigurationModel.getBanners());
        AutoScrollPageAdapter myAdapter = new AutoScrollPageAdapter(HomeActivity.instance, bannerList);
        binding.viewPagerSlider.setAdapter(myAdapter);
        //initIndicator();
        binding.viewPagerSlider.startAutoScroll(5000);
        binding.viewPagerSlider.setInterval(5000);
    }



}
