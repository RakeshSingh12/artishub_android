package com.artishub.app.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.artishub.app.R;
import com.artishub.app.listener.ItemClickListener;
import com.artishub.app.model.AppConfigurationModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ashutosh on 8/2/2018.
 */
public class PopularSearchAdapter extends RecyclerView.Adapter<PopularSearchAdapter.ViewHolder> {
private LayoutInflater layoutInflater;
private Context context;
private List<AppConfigurationModel.ProductCategoryBean> popularSearchList=new ArrayList<>();
private ItemClickListener itemClickListener;


    public PopularSearchAdapter(Context context, List<AppConfigurationModel.ProductCategoryBean> popularSearchList, ItemClickListener itemClickListener) {
        this.context = context;
        this.popularSearchList = popularSearchList;
        this.layoutInflater=LayoutInflater.from(context);
        this.itemClickListener=itemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_popular_search, parent, false);
        return new PopularSearchAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
         holder.txtPopularSearch.setText(popularSearchList.get(position).getCategory_name());
         holder.txtPopularSearch.setOnClickListener(view -> {
             itemClickListener.onItemClick(position);
         });

    }

    @Override
    public int getItemCount() {
        return popularSearchList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtPopularSearch;
        RecyclerView recyclerView;


        ViewHolder(View itemView) {
            super(itemView);
            txtPopularSearch = itemView.findViewById(R.id.txtPopularSearch);
//            recyclerView = itemView.findViewById(R.id.recylerViewSubCategories);

        }
    }
}
