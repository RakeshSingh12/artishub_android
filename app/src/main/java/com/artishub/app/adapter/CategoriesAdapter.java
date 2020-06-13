package com.artishub.app.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.artishub.app.R;
import com.artishub.app.model.AppConfigurationModel;

import java.util.ArrayList;

/**
 * Created by ashutosh on 7/30/2018.
 */
public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.ViewHolder> {

    private LayoutInflater layoutInflater;
    private Context context;
    private ArrayList<AppConfigurationModel.ProductCategoryBean> productCategoryBeans;
    private ArrayList<AppConfigurationModel.ProductSubcategoryBean> productSubcategoryBean;
    private ArrayList<AppConfigurationModel.ProductSubcategoryBean> productSubcategoryBeanArrayList;
    private SubCategoriesAdapter subCategoriesAdapter;


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_category, parent, false);
        return new CategoriesAdapter.ViewHolder(view);
    }

    public CategoriesAdapter(Context context, ArrayList<AppConfigurationModel.ProductCategoryBean> productCategoryBeans, ArrayList<AppConfigurationModel.ProductSubcategoryBean> productSubcategoryBeans) {
        this.context = context;
        this.productCategoryBeans = productCategoryBeans;
        this.productSubcategoryBean = productSubcategoryBeans;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtCategories.setText(productCategoryBeans.get(position).getCategory_name());
        /*setup Adapter*/

        productSubcategoryBeanArrayList=new ArrayList<>();
        for(int i=0;i<productSubcategoryBean.size();i++){
            if(productCategoryBeans.get(position).getCategory_id().equals(productSubcategoryBean.get(i).getParent_category_id())){
                productSubcategoryBeanArrayList.add(productSubcategoryBean.get(i));
            }
        }
        subCategoriesAdapter=new SubCategoriesAdapter(context,productSubcategoryBeanArrayList);
        holder.recyclerView.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL, false));
        holder.recyclerView.setAdapter(subCategoriesAdapter);





    }

    @Override
    public int getItemCount() {
        return productCategoryBeans.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtCategories;
        RecyclerView recyclerView;


        ViewHolder(View itemView) {
            super(itemView);
            txtCategories = itemView.findViewById(R.id.txtCategory);
            recyclerView = itemView.findViewById(R.id.recylerViewSubCategories);

        }
    }
}
