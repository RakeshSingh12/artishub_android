package com.artishub.app.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.artishub.app.R;
import com.artishub.app.helpers.AppUtilis;
import com.artishub.app.model.MyCartModel;

import java.util.ArrayList;

/**
 * Created by ashutosh on 9/10/2018.
 */
public class CheckoutAdapter extends RecyclerView.Adapter<CheckoutAdapter.ViewHolder> {
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<MyCartModel.ResultBean> myCartList = new ArrayList<>();


    public CheckoutAdapter(Context context, ArrayList<MyCartModel.ResultBean> myCartList) {
        this.context = context;
        this.myCartList = myCartList;
        this.inflater=LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_checkout, parent, false);
        return new CheckoutAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtProductName.setText(myCartList.get(position).getProduct_name());
        AppUtilis.setImagePicasso(context, holder.imgGift, myCartList.get(position).getProduct_image());
        holder.txtQuantity.setText(myCartList.get(position).getProduct_quantity());
        holder.txtProductSeller.setText(myCartList.get(position).getSupplier_id());

    }

    @Override
    public int getItemCount() {
        return myCartList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgGift;
        TextView txtProductName, txtProductPrice, txtQuantity, txtProductSeller;


        ViewHolder(View itemView) {
            super(itemView);
            imgGift = itemView.findViewById(R.id.imgCart);
            txtProductName = itemView.findViewById(R.id.txtProductName);
            txtQuantity = itemView.findViewById(R.id.txtProductQuantityValue);
            txtProductSeller = itemView.findViewById(R.id.txtProductSellerName);


        }
    }
}
