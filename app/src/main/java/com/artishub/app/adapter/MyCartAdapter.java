package com.artishub.app.adapter;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListPopupWindow;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.artishub.app.R;
import com.artishub.app.activities.CompleteProfileActivity;
import com.artishub.app.helpers.AppUtilis;
import com.artishub.app.listener.ItemClickListener;
import com.artishub.app.listener.UpdateCartListener;
import com.artishub.app.model.MyCartModel;

import java.util.ArrayList;

/**
 * Created by ashutosh on 9/6/2018.
 */
public class MyCartAdapter extends RecyclerView.Adapter<MyCartAdapter.ViewHolder> {
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<MyCartModel.ResultBean> myCartList = new ArrayList<>();
    private int totalPrice;
    ArrayList<String> quantityList = new ArrayList<>();
    private UpdateCartListener updateCartListener;
    private ItemClickListener itemClickListener;

    public MyCartAdapter(Context context, ArrayList<MyCartModel.ResultBean> myCartList, UpdateCartListener updateCartListener, ItemClickListener itemClickListener) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.myCartList = myCartList;
        this.updateCartListener=updateCartListener;
        this.itemClickListener=itemClickListener;
        quantityList.add("1");
        quantityList.add("2");
        quantityList.add("3");
        quantityList.add("4");
        quantityList.add("5");

    }

    @NonNull
    @Override
    public MyCartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_cart, parent, false);
        return new MyCartAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.txtProductName.setText(myCartList.get(position).getProduct_name());
        AppUtilis.setImagePicasso(context, holder.imgGift, myCartList.get(position).getProduct_image());
        holder.txtQuantity.setText(myCartList.get(position).getProduct_quantity());
        holder.txtProductPrice.setText("$ "+myCartList.get(position).getProduct_unit_price());
        holder.txtProductSeller.setText(myCartList.get(position).getSupplier_id());
        holder.txtQuantity.setOnClickListener(view -> {
            ArrayAdapter spinnerDelivery = new ArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item, quantityList);
            final ListPopupWindow popupWindow = new ListPopupWindow(context);

            popupWindow.setOnItemClickListener((adapterView, v, i, l) -> {
                holder.txtQuantity.setText(quantityList.get(i));
                myCartList.get(position).setProduct_quantity(quantityList.get(i));
                updateCartListener.onItemClick(position);
                if (position != 0) {

                } else {

                }
                popupWindow.dismiss();
            });
            popupWindow.setContentWidth(android.support.v7.widget.ListPopupWindow.WRAP_CONTENT);
            popupWindow.setHeight(200);
            popupWindow.setAnchorView(holder.linearLayout);
            popupWindow.setAdapter(spinnerDelivery);
            popupWindow.show();

        });

        holder.imgDelete.setOnClickListener(view -> itemClickListener.onItemClick(position));



    }

    @Override
    public int getItemCount() {
        return myCartList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgGift,imgDelete;
        Spinner spinner;
        TextView txtProductName, txtProductPrice, txtQuantity, txtProductSeller;
        LinearLayout linearLayout;
        public RelativeLayout viewBackground, viewForeground;


        ViewHolder(View itemView) {
            super(itemView);
            imgGift = itemView.findViewById(R.id.imgCart);
            txtProductName = itemView.findViewById(R.id.txtProductName);
            linearLayout = itemView.findViewById(R.id.llQuantity);
            txtProductPrice = itemView.findViewById(R.id.txtProductPriceValue);
            imgDelete=itemView.findViewById(R.id.imgDelete);
            txtProductSeller = itemView.findViewById(R.id.txtProductSellerName);
            txtQuantity = itemView.findViewById(R.id.txtProductQuantityValue);

        }
    }

    public void removeItem(int position) {
        myCartList.remove(position);
        // notify the item removed by position
        // to perform recycler view delete animations
        // NOTE: don't call notifyDataSetChanged()
        notifyItemRemoved(position);
    }

}