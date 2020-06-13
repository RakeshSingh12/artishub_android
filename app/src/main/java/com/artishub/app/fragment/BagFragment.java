package com.artishub.app.fragment;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.artishub.app.R;
import com.artishub.app.activities.HomeActivity;
import com.artishub.app.adapter.MyBagAdapter;
import com.artishub.app.constant.AppConstant;
import com.artishub.app.constant.ServerConstants;
import com.artishub.app.databinding.FragmentBagBinding;
import com.artishub.app.helpers.AlertUtil;
import com.artishub.app.helpers.RestClient;
import com.artishub.app.helpers.SharedPrefrencesManager;
import com.artishub.app.listener.ItemClickListener;
import com.artishub.app.model.CurrentUser;
import com.artishub.app.model.MyBagModel;
import com.artishub.app.model.MyCartModel;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;

import static com.artishub.app.helpers.RestClient.ApiRequest.METHOD_POST;

/**
 * A simple {@link Fragment} subclass.
 */
public class BagFragment extends Fragment {
    private FragmentBagBinding binding;
    private View view;
    CurrentUser currentUser;
    private boolean isLogin;
    private MyBagModel myBagModel;
    private ArrayList<MyBagModel.ResultBean> myBagList = new ArrayList<>();
    MyBagAdapter myBagAdapter;
    private RestClient.ApiRequest apiRequest;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_bag, container, false);
        view = binding.getRoot();
        currentUser = new Gson().fromJson(SharedPrefrencesManager.getInstance(HomeActivity.instance).getString(AppConstant.KEY_CURRENT_USER, ""), CurrentUser.class);
        isLogin = SharedPrefrencesManager.getInstance(HomeActivity.instance).getBoolean(AppConstant.IS_LOGIN, false);

        myBagAdapter = new MyBagAdapter(HomeActivity.instance, myBagList, position -> {

        });
        binding.recyclerCart.setLayoutManager(new LinearLayoutManager(HomeActivity.instance));
        binding.recyclerCart.setAdapter(myBagAdapter);
        if (isLogin)
            callGetAllBagApi();
        binding.swipeRefresh.setOnRefreshListener(() -> {
            if (isLogin)
                callGetAllBagApi();
            binding.swipeRefresh.setRefreshing(false);
        });
        return view;
    }

    private void callGetAllBagApi() {
        binding.rlLoader.setVisibility(View.VISIBLE);
        HashMap<String, String> params = new HashMap<>();
        params.put("method", "getMyorders");
        params.put("user_id", currentUser.getResult().getUser_id());


        apiRequest = new RestClient.ApiRequest(HomeActivity.instance);
        apiRequest.setUrl(ServerConstants.BASE_URL)
                .setMethod(METHOD_POST)
                .setParams(params)
                .setTag("getAllBag")
                .setResponseListener((tag, response) -> {
                    AlertUtil.hideProgressDialog();
                    binding.rlLoader.setVisibility(View.INVISIBLE);
                    if (response != null) {
                        myBagModel = new Gson().fromJson(response, MyBagModel.class);
                        if (myBagModel.getError_code() == 0) {
                            myBagList.clear();
                            myBagList.addAll(myBagModel.getResult());
                             if(myBagList.size()>0)
                                 binding.rlEmpty.setVisibility(View.GONE);

                            myBagAdapter.notifyDataSetChanged();
                        } else {
//                            AlertUtil.showToast(HomeActivity.instance, myBagModel.getError_string());
                        }

                    } else {
                        AlertUtil.showSnackBarLong(HomeActivity.instance, binding.getRoot(), getString(R.string.error_generic), "Retry", view -> callGetAllBagApi());
                    }

                })

                .setErrorListener((tag, errorMsg) -> {
                    binding.rlLoader.setVisibility(View.INVISIBLE);
                   // AlertUtil.showSnackBarLong(HomeActivity.instance, binding.getRoot(), errorMsg, "Retry", view -> callGetAllBagApi());
                })
                .execute();

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            if (isLogin)
                callGetAllBagApi();
        }
    }
}
