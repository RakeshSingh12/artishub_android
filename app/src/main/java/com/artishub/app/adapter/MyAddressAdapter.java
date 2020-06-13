package com.artishub.app.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.artishub.app.R;
import com.artishub.app.activities.AddressMapActivity;
import com.artishub.app.activities.SubCategoriesActivity;
import com.artishub.app.helpers.AppUtilis;
import com.artishub.app.listener.CustomClickListener;
import com.artishub.app.listener.ItemClickListener;
import com.artishub.app.model.AppConfigurationModel;
import com.artishub.app.model.MyAddressModel;

import java.util.ArrayList;

/**
 * Created by ashutosh on 8/20/2018.
 */
public class MyAddressAdapter extends RecyclerView.Adapter<MyAddressAdapter.ViewHolder> {
    private LayoutInflater layoutInflater;
    private Context context;
    private ArrayList<MyAddressModel.ResultBean> addressList;
    ItemClickListener itemClickListener;
    boolean fromSelection;

    public MyAddressAdapter(Context context, ArrayList<MyAddressModel.ResultBean> addressList,boolean fromSelection,ItemClickListener itemClickListener) {
        this.context = context;
        this.layoutInflater=LayoutInflater.from(context);
        this.addressList = addressList;
        this.itemClickListener=itemClickListener;
        this.fromSelection=fromSelection;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_my_address, parent, false);
        return new MyAddressAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.txtName.setText(addressList.get(position).getName());
        holder.txtAddress.setText(addressList.get(position).getHouse_no()+","+addressList.get(position).getAddress()+","+addressList.get(position).getLandmark());
        holder.txtMobileNo.setText(addressList.get(position).getCountry_code()+" "+addressList.get(position).getMobile_number());

        holder.layoutOne.setOnClickListener(view ->
        {
            if(fromSelection) {
            holder.layoutTwo.setVisibility(View.VISIBLE);
            Intent intent = new Intent("addressSelection");
            intent.putExtra("position", position);
            LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
            ((Activity) context).finish();

        }
        });


        if(fromSelection){
            holder.imgDelete.setVisibility(View.GONE);
            holder.imgEdit.setVisibility(View.GONE);
        }
        else{
            holder.imgDelete.setVisibility(View.VISIBLE);
            holder.imgEdit.setVisibility(View.VISIBLE);
        }


      holder.imgEdit.setOnClickListener(view -> {
          Intent i=new Intent(context, AddressMapActivity.class);
          i.putExtra("address",addressList.get(position));
          context.startActivity(i);
      });

      holder.imgDelete.setOnClickListener(view -> itemClickListener.onItemClick(position));





    }

    @Override
    public int getItemCount() {
        if(addressList.size()<=3)
        return addressList.size();
        else
            return 3;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtName,txtAddress,txtMobileNo;
        RelativeLayout layoutOne;
        RelativeLayout layoutTwo;
        ImageView imgEdit,imgDelete;


        ViewHolder(View itemView) {
            super(itemView);

            txtName = itemView.findViewById(R.id.txtAddressName);
            txtAddress = itemView.findViewById(R.id.txtAddress);
            txtMobileNo = itemView.findViewById(R.id.txtAddressNumber);

            imgEdit = itemView.findViewById(R.id.imgEdit);
            imgDelete = itemView.findViewById(R.id.imgDelete);
            layoutOne = itemView.findViewById(R.id.rlLayerOne);

            layoutTwo = itemView.findViewById(R.id.rlLayerTwo);



        }
    }
    public void removeItem(int position) {
        addressList.remove(position);
        // notify the item removed by position
        // to perform recycler view delete animations
        // NOTE: don't call notifyDataSetChanged()
        notifyItemRemoved(position);
    }
}
