package com.artishub.app.fragment.tutorial;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.artishub.app.R;
import com.artishub.app.databinding.FragmentTutorialScreen1Binding;
import com.squareup.picasso.Picasso;


/**
 * Created by techugo on 7/4/17.
 */

public class WalkthroughFragment4 extends Fragment {

    FragmentTutorialScreen1Binding binding;
    View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_tutorial_screen_1, container, false);
        view = binding.getRoot();
        Picasso.with(getActivity())
                .load(R.mipmap.screen_4)
                .into(binding.imgTutorial);
        return view;
    }
}
