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
import com.artishub.app.activities.ProductDetailsActivity;
import com.artishub.app.helpers.AppUtilis;
import com.artishub.app.model.SearchDetailsModel;
import com.artishub.app.model.SubCategoriesModel;

import java.util.ArrayList;

/**
 * Created by ashutosh on 7/31/2018.
 */
public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {

    ArrayList<SearchDetailsModel.ProductDetailsBean> productBySubcategoryBeans=new ArrayList<>();
    private Context context;
    private LayoutInflater layoutInflater;

    public SearchAdapter(ArrayList<SearchDetailsModel.ProductDetailsBean> productBySubcategoryBeans, Context context) {
        this.productBySubcategoryBeans = productBySubcategoryBeans;
        this.context = context;
        this.layoutInflater=LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_sub_categories_main, parent, false);
        return new SearchAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtProductName.setText(productBySubcategoryBeans.get(position).getProduct_name());
        holder.txtProductPriceValue.setText("$ "+productBySubcategoryBeans.get(position).getSelling_price());
        holder.txtCategoriesName.setText(productBySubcategoryBeans.get(position).getProduct_name());
        AppUtilis.setImagePicasso(context,holder.imgSubcategories,productBySubcategoryBeans.get(position).getProduct_image());

        holder.relativeLayout.setOnClickListener(view -> {
            Intent i=new Intent(context, ProductDetailsActivity.class);
            i.putExtra("productId",productBySubcategoryBeans.get(position).getProduct_id());
            context.startActivity(i);
        });

    }


    @Override
    public int getItemCount() {
        return productBySubcategoryBeans.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtProductName,txtCategoriesName,txtProductPriceValue;
        ImageView imgSubcategories;
        RelativeLayout relativeLayout;


        ViewHolder(View itemView) {
            super(itemView);
            txtProductName=itemView.findViewById(R.id.productName);
            txtCategoriesName=itemView.findViewById(R.id.txtProductCategoryName);
            txtProductPriceValue=itemView.findViewById(R.id.txtProductPriceValue);
            relativeLayout=itemView.findViewById(R.id.rlClick);
            imgSubcategories = itemView.findViewById(R.id.imgSubCategory);

        }
    }
}
