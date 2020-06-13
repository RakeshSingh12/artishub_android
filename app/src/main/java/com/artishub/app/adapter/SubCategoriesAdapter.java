package com.artishub.app.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.artishub.app.R;
import com.artishub.app.activities.SubCategoriesActivity;
import com.artishub.app.helpers.AppUtilis;
import com.artishub.app.model.AppConfigurationModel;

import java.util.ArrayList;

/**
 * Created by ashutosh on 7/30/2018.
 */
public class SubCategoriesAdapter extends RecyclerView.Adapter<SubCategoriesAdapter.ViewHolder> {
    private LayoutInflater layoutInflater;
    private Context context;
    private ArrayList<AppConfigurationModel.ProductSubcategoryBean>productSubcategoryBeans;

    public SubCategoriesAdapter(Context context, ArrayList<AppConfigurationModel.ProductSubcategoryBean> productSubcategoryBeans) {
        this.context = context;
        this.layoutInflater=LayoutInflater.from(context);
        this.productSubcategoryBeans = productSubcategoryBeans;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_sub_categories, parent, false);
        return new SubCategoriesAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtCategories.setText(productSubcategoryBeans.get(position).getSubcategory_name());
        AppUtilis.setImagePicasso(context,holder.imgSubcategories,productSubcategoryBeans.get(position).getSubcategory_image());



        holder.imgSubcategories.setOnClickListener(view -> {
            Intent intent = new Intent(context, SubCategoriesActivity.class);
            intent.putExtra("SUBCATEGORY", productSubcategoryBeans.get(position).getSubcategory_name());
            intent.putExtra("SUBCATEGORYID", productSubcategoryBeans.get(position).getSubcategory_id());

            context.startActivity(intent);
        });

        holder.txtCategories.setOnClickListener(view -> {
            Intent intent = new Intent(context, SubCategoriesActivity.class);
            intent.putExtra("SUBCATEGORY", productSubcategoryBeans.get(position).getSubcategory_name());
            intent.putExtra("SUBCATEGORYID", productSubcategoryBeans.get(position).getSubcategory_id());

            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return productSubcategoryBeans.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtCategories;
        ImageView imgSubcategories;


        ViewHolder(View itemView) {
            super(itemView);
            txtCategories = itemView.findViewById(R.id.txtSubCategories);
            imgSubcategories = itemView.findViewById(R.id.imgSubCategories);

        }
    }
}
