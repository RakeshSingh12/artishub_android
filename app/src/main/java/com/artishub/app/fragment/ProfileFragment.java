package com.artishub.app.fragment;


import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.artishub.app.R;
import com.artishub.app.activities.AddressMapActivity;
import com.artishub.app.activities.CompleteProfileActivity;
import com.artishub.app.activities.HomeActivity;
import com.artishub.app.activities.MyAddressActivity;
import com.artishub.app.activities.OrderConfirmationActivity;
import com.artishub.app.activities.SelectOptionActivity;
import com.artishub.app.constant.AppConstant;
import com.artishub.app.databinding.FragmentProfileBinding;
import com.artishub.app.helpers.AppUtilis;
import com.artishub.app.helpers.SharedPrefrencesManager;
import com.artishub.app.model.CurrentUser;
import com.google.gson.Gson;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {
    private CurrentUser currentUser;
    protected FragmentProfileBinding binding;
    private View view;
    private boolean isLogin;
    public ProfileFragment instance;


    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        instance=this;
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false);
        view = binding.getRoot();

        //*************current user***********//
        isLogin = SharedPrefrencesManager.getInstance(HomeActivity.instance).getBoolean(AppConstant.IS_LOGIN, false);


//        if (isLogin){
//            binding.rlGuest.setVisibility(View.GONE);
//            setData();}
//        else{
//            binding.rlLogin.setVisibility(View.GONE);
//        }

        binding.rlMyOrder.setOnClickListener(view1 -> {
            Intent i = new Intent(HomeActivity.instance, HomeActivity.class);
            i.putExtra("toFragment","ToMyBag");
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
        });


        binding.txtSignUp.setOnClickListener(view ->{
            if(!isLogin){
                AppUtilis.goToActivity(HomeActivity.instance, SelectOptionActivity.class);}});
        binding.rlMyAddress.setOnClickListener(v ->{
         AppUtilis.goToActivity(HomeActivity.instance, MyAddressActivity.class);

        });


        return view;
    }

    @Override
    public void onResume() {
        if (isLogin){
            binding.rlGuest.setVisibility(View.GONE);
            setData();}
        else{
            binding.rlLogin.setVisibility(View.GONE);
        }
        super.onResume();
    }

    public void setData() {
        currentUser = new Gson().fromJson(SharedPrefrencesManager.getInstance(HomeActivity.instance).getString(AppConstant.KEY_CURRENT_USER, ""), CurrentUser.class);
        binding.txtProfileName.setText(currentUser.getResult().getName());
        binding.txtProfileEmail.setText(currentUser.getResult().getEmail());
        binding.txtProfilePhoneNo.setText(currentUser.getResult().getMobile_number());
    }

}
