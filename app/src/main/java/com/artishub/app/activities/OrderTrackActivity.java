package com.artishub.app.activities;

import android.databinding.DataBindingUtil;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.artishub.app.R;
import com.artishub.app.databinding.ActivityOrderTrackBinding;
import com.artishub.app.helpers.AppUtilis;
import com.artishub.app.model.MyBagModel;

import java.text.ParseException;

public class OrderTrackActivity extends AppCompatActivity {
ActivityOrderTrackBinding binding;
MyBagModel.ResultBean.ItemsBean resultBean;
private String supplierId;
private String PaymentId;
    double total = 0;
    float shippingCharge=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(android.R.color.transparent));
            window.setBackgroundDrawable(getDrawable(R.drawable.bg_statusbarone));
        }
        binding= DataBindingUtil.setContentView(OrderTrackActivity.this,R.layout.activity_order_track);
        resultBean= (MyBagModel.ResultBean.ItemsBean) getIntent().getSerializableExtra("Data");
        supplierId=getIntent().getStringExtra("supplierId");
        PaymentId=getIntent().getStringExtra("paymentId");

        binding.imgLeftSide.setOnClickListener(view -> finish());
        setData();
    }

    private void setData() {
        if(resultBean!=null){






            binding.txtOrderIdValue.setText(resultBean.getOrder_generated_id());
            try {
                binding.txtOrderDateValue.setText(AppUtilis.formatDateTime(AppUtilis.utcToLocal(resultBean.getOrdered()),"yyyy-MM-dd HH:mm:ss","dd MMM yyyy"));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            AppUtilis.setImagePicasso(OrderTrackActivity.this,binding.imgCart,resultBean.getOrder_item_image());
            binding.txtName.setText(resultBean.getOrder_item_name());
            binding.txtTotalPrice.setText("$ "+resultBean.getOrder_item_total());
            binding.txtShippingPrice.setText("$ "+resultBean.getShipping_charge());
            binding.txtVatPrice.setText("$ "+calculateVat(Integer.parseInt(resultBean.getVat()),Float.parseFloat(resultBean.getOrder_item_total())));



            binding.txtSellerName.setText(supplierId);
            binding.txtQuantityValue.setText(resultBean.getOrder_item_quantity());
            binding.txtDeliveryDate.setText("");
            try {
                binding.txtDeliveryDate.setText(AppUtilis.formatDateTime(AppUtilis.utcToLocal(resultBean.getDelivered_date()),"yyyy-MM-dd HH:mm:ss","dd MMM yyyy"));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            binding.txtPayment.setText(PaymentId);
            if(resultBean.getOrder_item_status().equals("Placed")){
                binding.imgPlaced.setImageResource(R.drawable.icon_filled_circle);
                binding.txtPlaced.setTextColor(getResources().getColor(R.color.app_color_two));
            }
            else if(resultBean.getOrder_item_status().equals("Dispatched")){
                binding.imgPlaced.setImageResource(R.drawable.icon_filled_circle);
                binding.imgDispatched.setImageResource(R.drawable.icon_filled_circle);
                binding.viewOne.setBackground(getResources().getDrawable(R.drawable.bg_button));
                binding.txtPlaced.setTextColor(getResources().getColor(R.color.app_color_two));
                binding.txtDispatched.setTextColor(getResources().getColor(R.color.app_color_two));
            }
            else {
                binding.imgPlaced.setImageResource(R.drawable.icon_filled_circle);
                binding.imgDispatched.setImageResource(R.drawable.icon_filled_circle);
                binding.imgDelivered.setImageResource(R.drawable.icon_filled_circle);
                binding.viewOne.setBackground(getResources().getDrawable(R.drawable.bg_button));
                binding.viewTwo.setBackground(getResources().getDrawable(R.drawable.bg_button));
                binding.txtPlaced.setTextColor(getResources().getColor(R.color.app_color_two));
                binding.txtDispatched.setTextColor(getResources().getColor(R.color.app_color_two));
                binding.txtDelivered.setTextColor(getResources().getColor(R.color.app_color_two));
            }
        }
    }

    private double calculateVat(int vat,Float total1){

        double vatPercentage=vat*.01;

        binding.txtVat.setText("Vat("+vat+"%)");
        total=total1*vatPercentage+total1+Integer.parseInt(resultBean.getShipping_charge());
        binding.txtTotalprice.setText("$ "+total);
        return (Math.round((total1*vatPercentage) * 100.0) / 100.0);
        //return total1*vatPercentage;


    }
}
