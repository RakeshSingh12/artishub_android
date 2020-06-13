package com.artishub.app.activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.artishub.app.R;
import com.artishub.app.databinding.ActivityOrderConfirmationBinding;
import com.artishub.app.helpers.AppUtilis;

public class OrderConfirmationActivity extends AppCompatActivity {
ActivityOrderConfirmationBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(android.R.color.transparent));
            window.setBackgroundDrawable(getDrawable(R.drawable.bg_statusbarone));
        }
      binding= DataBindingUtil.setContentView(OrderConfirmationActivity.this,R.layout.activity_order_confirmation);
      binding.txtContinueShopping.setOnClickListener(view -> {
          AppUtilis.clearAllgoToActivity(OrderConfirmationActivity.this,HomeActivity.class);

      });

      binding.btnTrackOrder.setOnClickListener(view -> {
          Intent i = new Intent(OrderConfirmationActivity.this, HomeActivity.class);
          i.putExtra("toFragment","ToMyBag");
          i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
          startActivity(i);
      });

    }

    @Override
    public void onBackPressed() {
        AppUtilis.clearAllgoToActivity(OrderConfirmationActivity.this,HomeActivity.class);
    }
}
