package com.artishub.app.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.artishub.app.model.Country;
import com.artishub.app.R;
import com.artishub.app.model.Country;

import java.util.List;

/**
 * Created by ADMIN on 2/2/2018.
 */

public class CountryPickerAdapter extends BaseAdapter {
    private Context mContext;
    List<Country> countries;


    public CountryPickerAdapter(Context mContext, List<Country> countries) {
       this.mContext=mContext;
       this.countries= countries;
    }
    private class ViewHolder {

        TextView txtTitle,txtCode;

    }


    @Override
    public int getCount() {
        return countries.size();
    }

    @Override
    public Object getItem(int position) {
        return countries.get(position);
    }

    @Override
    public long getItemId(int position) {
        return countries.indexOf(getItem(position));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        LayoutInflater Inflater = (LayoutInflater)
                mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = Inflater.inflate(R.layout.item_country_picker, null);
            holder = new ViewHolder();
            holder.txtTitle = (TextView) convertView.findViewById(R.id.countryName);
            holder.txtCode = (TextView) convertView.findViewById(R.id.countryCode);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

       Country country = (Country) getItem(position);

        holder.txtTitle.setText(country.getCountryName());
        holder.txtCode.setText(country.getDileCode());

        return convertView;
    }
}
