package com.artishub.app.adapter;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.artishub.app.R;
import com.artishub.app.helpers.AppUtilis;
import com.artishub.app.model.AppConfigurationModel;

import java.util.ArrayList;
import java.util.List;


public class AutoScrollPageAdapter extends PagerAdapter {
    Context context;
    LayoutInflater inflater;
    ArrayList<AppConfigurationModel.BannersBean> list;

    public AutoScrollPageAdapter(Context context, ArrayList<AppConfigurationModel.BannersBean> list) {
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = inflater.inflate(R.layout.slider_item, container, false);
        ImageView imgSlider = (ImageView) itemView.findViewById(R.id.imgSlider);
        AppUtilis.setImagePicasso(context,imgSlider,list.get(position).getBanner_image());
        container.addView(itemView);
        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }
}