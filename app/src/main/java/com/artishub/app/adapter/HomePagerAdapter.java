package com.artishub.app.adapter;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import com.artishub.app.R;
import com.artishub.app.fragment.BagFragment;
import com.artishub.app.fragment.CartFragment;
import com.artishub.app.fragment.HomeFragment;
import com.artishub.app.fragment.ProfileFragment;


/**
 * Created by TechUgo on 8/10/2016.
 */
public class HomePagerAdapter extends FragmentStatePagerAdapter {
    TabLayout tabLayout;



    int NumbOfTabs = 4;
    // / Store the number of tabs, this will also be passed when the DashboardPagerAdapter is created

    //This will contain your Fragment references:
    public Fragment[] fragments = new Fragment[NumbOfTabs];

    // Build a Constructor and assign the passed Values to appropriate values in the class
    public HomePagerAdapter(FragmentManager fm, TabLayout tabLayout) {
        super(fm);
        this.tabLayout = tabLayout;



    }

    //This method return the fragment for the every position in the View Pager
    @Override
    public Fragment getItem(final int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                tabLayout.getTabAt(position).setCustomView(R.layout.tab_home);
                fragment = new HomeFragment();
                break;
            case 1:
                tabLayout.getTabAt(position).setCustomView(R.layout.tab_profile);
                fragment = new ProfileFragment();
                break;
            case 2:
                tabLayout.getTabAt(position).setCustomView(R.layout.tab_cart);
                fragment = new CartFragment();
                break;
            case 3:
                tabLayout.getTabAt(position).setCustomView(R.layout.tab_bag);
                fragment = new BagFragment();
                break;


        }

        return fragment;

    }

    // This method return the titles for the Tabs in the Tab Strip

    @Override
    public CharSequence getPageTitle(int position) {

        return "";
    }

    //This populates your Fragment reference array:
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment createdFragment = (Fragment) super.instantiateItem(container, position);
        fragments[position] = createdFragment;
        return createdFragment;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
    }

    // This method return the Number of tabs for the tabs Strip
    @Override
    public int getCount() {
        return NumbOfTabs;
    }
}
