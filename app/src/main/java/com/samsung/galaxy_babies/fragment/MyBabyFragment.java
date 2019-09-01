package com.samsung.galaxy_babies.fragment;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.samsung.galaxy_babies.R;

public class MyBabyFragment extends BaseFragment {
    // UI
    private View view;
    private Context context;


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        this.setRetainInstance(true);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // UI
        view = inflater.inflate(R.layout.fragment_mybaby, container, false);
        context = container.getContext();

        init();

        return view;

    }


    public void init(){


    }

}
