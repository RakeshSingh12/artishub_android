package com.artishub.app.adapter;

import android.content.Context;
import android.content.Intent;
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
import com.artishub.app.activities.HomeActivity;
import com.artishub.app.activities.OrderTrackActivity;
import com.artishub.app.activities.OrdersActivity;
import com.artishub.app.helpers.AppUtilis;
import com.artishub.app.listener.ItemClickListener;
import com.artishub.app.model.MyBagModel;

import java.text.ParseException;
import java.util.ArrayList;

/**
 * Created by ashutosh on 9/13/2018.
 */
public class MyBagAdapter extends RecyclerView.Adapter<MyBagAdapter.ViewHolder> {
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<MyBagModel.ResultBean> myBagList = new ArrayList<>();
    private ItemClickListener itemClickListener;

    public MyBagAdapter(Context context, ArrayList<MyBagModel.ResultBean> myBagList, ItemClickListener itemClickListener) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.myBagList = myBagList;

        this.itemClickListener = itemClickListener;


    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_my_bag, parent, false);
        return new MyBagAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.txtOrder.setText(myBagList.get(position).getOrder_id());
        holder.txtPrice.setText("$ " +myBagList.get(position).getTotal_amount());
        holder.txtItem.setText(""+myBagList.get(position).getCount());
        holder.txtPayment.setText(myBagList.get(position).getPayment_type());


        try {
            holder.txtDate.setText(AppUtilis.formatDateTime(AppUtilis.utcToLocal(myBagList.get(position).getOrdered()),"yyyy-MM-dd HH:mm:ss","dd MMM yyyy"));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        holder.viewForeground.setOnClickListener(view -> {
            Intent i=new Intent(context, OrdersActivity.class);
            i.putExtra("Data",myBagList.get(position));
            context.startActivity(i);


        });
    }

    @Override
    public int getItemCount() {
        return myBagList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtOrder, txtItem, txtDate, txtPayment, viewAll, txtPrice;

        public RelativeLayout viewForeground;


        ViewHolder(View itemView) {
            super(itemView);

            txtOrder = itemView.findViewById(R.id.txtOrderIdValue);
            txtItem = itemView.findViewById(R.id.txtItemsValue);
            txtDate = itemView.findViewById(R.id.txtDateValue);
            txtPrice = itemView.findViewById(R.id.txtPriceValue);
            txtPayment = itemView.findViewById(R.id.txtPaymentValue);
            viewAll = itemView.findViewById(R.id.txtViewAll);
            viewForeground = itemView.findViewById(R.id.view_foreground);

        }
    }

}

