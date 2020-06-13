package com.artishub.app.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListPopupWindow;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.artishub.app.R;
import com.artishub.app.helpers.AppUtilis;
import com.artishub.app.listener.ItemClickListener;
import com.artishub.app.listener.UpdateCartListener;
import com.artishub.app.model.MyCartModel;
import com.artishub.app.model.SellerModel;

import java.util.ArrayList;

/**
 * Created by ashutosh on 9/17/2018.
 */
public class SellerAdapter extends RecyclerView.Adapter<SellerAdapter.ViewHolder> {
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<SellerModel.ListBean> sellerList = new ArrayList<>();
    private ItemClickListener itemClickListener;

    public SellerAdapter(Context context, ArrayList<SellerModel.ListBean> myCartList, ItemClickListener itemClickListener) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.sellerList = myCartList;
        this.itemClickListener = itemClickListener;

    }

    @NonNull
    @Override
    public SellerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_seller, parent, false);
        return new SellerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SellerAdapter.ViewHolder holder, int position) {

        holder.txtProductSeller.setText(sellerList.get(position).getSupplier_id());
        holder.txtProductPrice.setText("$" + sellerList.get(position).getSelling_price());

        holder.txtAddToCart.setOnClickListener(view -> {
            itemClickListener.onItemClick(position);
        });


    }

    @Override
    public int getItemCount() {
        return sellerList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtProductPrice, txtAddToCart, txtProductSeller;
        LinearLayout linearLayout;
        public RelativeLayout viewBackground, viewForeground;


        ViewHolder(View itemView) {
            super(itemView);

            txtProductPrice = itemView.findViewById(R.id.txtProductPriceValue);
            txtProductSeller = itemView.findViewById(R.id.txtProductSellerName);
            txtAddToCart = itemView.findViewById(R.id.txtAddToCart);

        }
    }

    public void removeItem(int position) {
        sellerList.remove(position);
        // notify the item removed by position
        // to perform recycler view delete animations
        // NOTE: don't call notifyDataSetChanged()
        notifyItemRemoved(position);
    }

}
