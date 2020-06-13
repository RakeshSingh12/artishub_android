package com.artishub.app.activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.artishub.app.R;
import com.artishub.app.constant.AppConstant;
import com.artishub.app.databinding.ActivityTutorialBinding;
import com.artishub.app.fragment.tutorial.WalkthroughFragment1;
import com.artishub.app.fragment.tutorial.WalkthroughFragment2;
import com.artishub.app.fragment.tutorial.WalkthroughFragment3;
import com.artishub.app.fragment.tutorial.WalkthroughFragment4;
import com.artishub.app.helpers.CirclePageIndicator;
import com.artishub.app.helpers.SharedPrefrencesManager;

public class TutorialActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    private ActivityTutorialBinding binding;

    private CirclePageIndicator indicator;
    int mCurrentPosition;
    private int mScrollState;
    int pos;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(android.R.color.transparent));
            window.setBackgroundDrawable(getDrawable(R.drawable.bg_statusbarone));
        }
        binding = DataBindingUtil.setContentView(this, R.layout.activity_tutorial);

        binding.viewPager.setOffscreenPageLimit(4);
        binding.viewPager.setAdapter(new PagerAdapter(getSupportFragmentManager()));
        indicator();

        binding.viewPager.addOnPageChangeListener(this);

        binding.txtFinish.setOnClickListener(view -> {
            if (binding.txtFinish.getVisibility() == View.VISIBLE) {
                SharedPrefrencesManager.getInstance(TutorialActivity.this).setString(AppConstant.TUTORIAL_VIEW_STATUS, "true");
                Intent intent = new Intent(TutorialActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });

        binding.txtSkip.setOnClickListener(view -> {
            SharedPrefrencesManager.getInstance(TutorialActivity.this).setString(AppConstant.TUTORIAL_VIEW_STATUS, "true");
            Intent intent = new Intent(TutorialActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        });

        binding.txtNext.setOnClickListener(view -> {
            binding.viewPager.setCurrentItem(binding.viewPager.getCurrentItem()+1,true);

        });
        binding.txtTitle.setText(getResources().getString(R.string.screenName1));
        binding.txtDescription.setText(getResources().getString(R.string.screen1Description));
    }

    private void indicator() {
        indicator = binding.pageIndicator;
        binding.pageIndicator.setViewPager(binding.viewPager);
        final float density = getResources().getDisplayMetrics().density;
        binding.pageIndicator.setRadius(3 * density);
        binding.pageIndicator.setFillColor(ContextCompat.getColor(this, R.color.colorAccent));

//        if (Locale.getDefault().getLanguage().equals("ar")) {
//            binding.pageIndicator.setRotation(180);
//
//            binding.viewPager.setRotationY(180);
//        }

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {


    }

    @Override
    public void onPageSelected(int position) {
        mCurrentPosition = position;
        if (position == 0) {
            binding.txtFinish.setVisibility(View.GONE);
            binding.txtSkip.setVisibility(View.VISIBLE);
            binding.txtNext.setVisibility(View.VISIBLE);

            binding.txtTitle.setText(getResources().getString(R.string.screenName1));
            binding.txtDescription.setText(getResources().getString(R.string.screen1Description));
        } else if (position == 1) {

            binding.txtFinish.setVisibility(View.GONE);
            binding.txtSkip.setVisibility(View.VISIBLE);
            binding.txtNext.setVisibility(View.VISIBLE);

            binding.txtTitle.setText(getResources().getString(R.string.screenName2));
            binding.txtDescription.setText(getResources().getString(R.string.screen2Description));
        } else if (position == 2) {

            binding.txtFinish.setVisibility(View.GONE);
            binding.txtSkip.setVisibility(View.VISIBLE);
            binding.txtNext.setVisibility(View.VISIBLE);
            binding.txtTitle.setText(getResources().getString(R.string.screenName3));
            binding.txtDescription.setText(getResources().getString(R.string.screen3Description));
        } else if (position == 3) {
            binding.txtFinish.setVisibility(View.VISIBLE);
            binding.txtSkip.setVisibility(View.GONE);
            binding.txtTitle.setText(getResources().getString(R.string.screenName4));
            binding.txtDescription.setText(getResources().getString(R.string.screen4Description));
            binding.txtNext.setVisibility(View.GONE);

        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        mScrollState = state;
    }

    private boolean isScrollStateSettling() {
        return mScrollState == ViewPager.SCROLL_STATE_SETTLING;
    }

    public class PagerAdapter extends FragmentPagerAdapter {

        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new WalkthroughFragment1();
                case 1:
                    return new WalkthroughFragment2();

                case 2:
                    return new WalkthroughFragment3();

                case 3:
                    return new WalkthroughFragment4();
            }


            return null;
        }

        @Override
        public int getCount() {
            return 4;
        }
    }

}
