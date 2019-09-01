package com.samsung.galaxy_babies.fragment;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.samsung.galaxy_babies.MainActivity;
import com.samsung.galaxy_babies.R;
import com.samsung.galaxy_babies.obj.Baby;
import com.samsung.galaxy_babies.obj.User;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyBabyFragment extends BaseFragment {
    // UI
    private View view;
    private Context context;

    private TextView tv_name;
    private TextView tv_description;
    private CircleImageView profileImg;
    private LinearLayout li_babies;
    private GridLayout gl_growth;

    private TextView tv_weight;
    private TextView tv_height;

    private User user;
    private Baby selectedBaby;

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

        user = ((MainActivity)getActivity()).getUser();
        selectedBaby = user.getBabies().get(0);

        init();

        updatePage();

        return view;

    }


    public void init(){

        tv_name = (TextView) view.findViewById(R.id.tv_name);
        tv_description = (TextView) view.findViewById(R.id.tv_description);
        profileImg = (CircleImageView) view.findViewById(R.id.profile_image);
        li_babies = (LinearLayout) view.findViewById(R.id.li_babies);
        gl_growth = (GridLayout) view.findViewById(R.id.gl_growth);

        tv_weight = (TextView) view.findViewById(R.id.tv_weight);
        tv_height = (TextView) view.findViewById(R.id.tv_height);


        for(Baby baby : user.getBabies()){
            final Baby b = baby;
            CircleImageView imageView = new CircleImageView(context);

            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                    getDP(context, 40),
                    RelativeLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(getDP(context, 5),0,0,0); // left, top, right, bottom
            imageView.setLayoutParams(params);

            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setBorderWidth(1);
            imageView.setBorderColor(getColorId(context, R.color.white));

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectedBaby = b;
                    updatePage();
                }
            });

            Picasso.get().load(baby.getProfileImg()).into(imageView);

            li_babies.addView(imageView);
        }

        for(int i=0; i<12; i++){ // for gl_growth

        }

    }

    public void updatePage(){

        if(selectedBaby != null){
            tv_name.setText(selectedBaby.getName());
            tv_description.setText(selectedBaby.getDescription());
            Picasso.get().load(selectedBaby.getProfileImg()).into(profileImg);

            tv_weight.setText(selectedBaby.getWeightString());
            tv_height.setText(selectedBaby.getHeightString());
        }

    }

}
