package com.artishub.app.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.artishub.app.R;
import com.artishub.app.activities.OrderTrackActivity;
import com.artishub.app.helpers.AppUtilis;
import com.artishub.app.listener.ItemClickListener;
import com.artishub.app.model.MyBagModel;

import java.text.ParseException;
import java.util.ArrayList;

public class MyItemAdapter extends RecyclerView.Adapter<MyItemAdapter.ViewHolder> {
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<MyBagModel.ResultBean.ItemsBean> myBagList = new ArrayList<>();
    private String SupplierId;
    private String paymentMode;
    //private ItemClickListener itemClickListener;

    public MyItemAdapter(Context context, ArrayList<MyBagModel.ResultBean.ItemsBean> myBagList,String paymentId,String supplierId) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.myBagList = myBagList;
        this.paymentMode=paymentId;
        this.SupplierId=supplierId;

        //this.itemClickListener = itemClickListener;


    }

    @NonNull
    @Override
    public MyItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_my_item, parent, false);
        return new MyItemAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyItemAdapter.ViewHolder holder, int position) {

        holder.txtProductName.setText(myBagList.get(position).getOrder_item_name());
        holder.txtPrice.setText("$ " +myBagList.get(position).getOrder_item_price());
        holder.txtItem.setText(""+myBagList.get(position).getOrder_item_quantity());
        AppUtilis.setImagePicasso(context,holder.imgProduct,myBagList.get(position).getOrder_item_image());
        holder.txtSeller.setText(myBagList.get(position).getSeller());


        holder.viewForeground.setOnClickListener(view -> {
            Intent i=new Intent(context, OrderTrackActivity.class);
            i.putExtra("Data",myBagList.get(position));
            i.putExtra("paymentId",paymentMode);
            i.putExtra("supplierId",myBagList.get(position).getSeller());
            context.startActivity(i);


        });
    }

    @Override
    public int getItemCount() {
        return myBagList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtProductName, txtItem,txtPrice,txtSeller;
        ImageView imgProduct;

        public RelativeLayout viewForeground;


        ViewHolder(View itemView) {
            super(itemView);
            imgProduct=itemView.findViewById(R.id.imgProduct);
            txtPrice=itemView.findViewById(R.id.txtProductPriceValue);
            txtProductName=itemView.findViewById(R.id.txtProductName);
           txtSeller = itemView.findViewById(R.id.txtSellerName);
            txtItem = itemView.findViewById(R.id.txtProductQuantityValue);
//            txtDate = itemView.findViewById(R.id.txtDateValue);
//            txtPrice = itemView.findViewById(R.id.txtPriceValue);
//            txtPayment = itemView.findViewById(R.id.txtPaymentValue);
//            viewAll = itemView.findViewById(R.id.txtViewAll);
            viewForeground = itemView.findViewById(R.id.view_foreground);

        }
    }

}
