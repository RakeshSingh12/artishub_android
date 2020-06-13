package com.artishub.app.activities;

import android.databinding.DataBindingUtil;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Window;
import android.view.WindowManager;

import com.artishub.app.R;
import com.artishub.app.adapter.MyItemAdapter;
import com.artishub.app.databinding.ActivityOrdersBinding;
import com.artishub.app.model.MyBagModel;

import java.util.ArrayList;

public class OrdersActivity extends AppCompatActivity {
ActivityOrdersBinding binding;
MyBagModel.ResultBean itemsBean;
ArrayList<MyBagModel.ResultBean.ItemsBean> itemsList=new ArrayList<>();
MyItemAdapter myItemAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(android.R.color.transparent));
            window.setBackgroundDrawable(getDrawable(R.drawable.bg_statusbarone));
        }
        binding=DataBindingUtil.setContentView(this,R.layout.activity_orders);
        itemsBean= (MyBagModel.ResultBean) getIntent().getSerializableExtra("Data");
        itemsList.addAll(itemsBean.getItems());


        myItemAdapter=new MyItemAdapter(this,itemsList,itemsBean.getPayment_type(),itemsBean.getSupplier_id());
        binding.recyclerViewItems.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerViewItems.setAdapter(myItemAdapter);

        binding.txtHeading.setText(itemsBean.getOrder_id());
        binding.imgLeftSide.setOnClickListener(v -> {
            finish();
        });
    }
}

