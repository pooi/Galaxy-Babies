package com.samsung.galaxy_babies.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;

import com.github.mikephil.charting.charts.LineChart;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.samsung.galaxy_babies.MainActivity;
import com.samsung.galaxy_babies.R;
import com.samsung.galaxy_babies.data.HeightData;
import com.samsung.galaxy_babies.data.WeightData;
import com.samsung.galaxy_babies.obj.Baby;
import com.samsung.galaxy_babies.obj.BabyData;
import com.samsung.galaxy_babies.obj.User;
import com.samsung.galaxy_babies.util.AppBarStateChangeListener;
import com.samsung.galaxy_babies.util.OnAdapterSupport;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainFragment extends BaseFragment implements OnAdapterSupport {
    // UI
    private View view;
    private Context context;
    private Activity activity;

    private ArrayList<String> tempList;
    private ArrayList<String> list;

    private Toolbar toolbar;
    private TextView toolbarTitle;
    private CollapsingToolbarLayout collapsingToolbar;
    private LinearLayout li_babies;




    private NestedScrollView scrollView;
    private LinearLayout li_sv;

//    private RecyclerView rv;
//    private LinearLayoutManager mLinearLayoutManager;
//    private MainListCustomAdapter adapter;

    private User user;
    private Baby selectedBaby;
    private BabyData babyData = new BabyData();

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        this.setRetainInstance(true);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // UI
        view = inflater.inflate(R.layout.fragment_main, container, false);
        context = container.getContext();
        activity = getActivity();
        user = ((MainActivity)getActivity()).getUser();
        selectedBaby = user.getBabies().get(0);

//        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
//        view.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
//        getActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);

        init();

        return view;

    }

    public void updatePage(){
        if(selectedBaby != null) {
            li_sv.removeAllViews();
            addProfileCard();
            addCards();
        }
    }

    public void addProfileCard(){
        View card = LayoutInflater.from(context).inflate(R.layout.list_card_profile, li_sv, false);

        TextView tv_name = (TextView) card.findViewById(R.id.tv_name);
        TextView tv_description = (TextView) card.findViewById(R.id.tv_description);
        CircleImageView profileImg = (CircleImageView) card.findViewById(R.id.profile_image);

        tv_name.setText(selectedBaby.getName());
        tv_description.setText(selectedBaby.getDescription());
        Picasso.get().load(selectedBaby.getProfileImg()).into(profileImg);

        li_sv.addView(card);
    }

    public void addCards(){
        {
            View card = LayoutInflater.from(context).inflate(R.layout.list_card_info, li_sv, false);

            ImageView logo = (ImageView) card.findViewById(R.id.img_logo);
            TextView tv_subtitle = (TextView) card.findViewById(R.id.tv_subtitle);
            TextView tv_title = (TextView) card.findViewById(R.id.tv_title);
            TextView tv_content = (TextView) card.findViewById(R.id.tv_content);
            LineChart lineChart = (LineChart) card.findViewById(R.id.chart);
            lineChart.setVisibility(View.VISIBLE);

            int wperIndex = babyData.getKgPercentileIndex(selectedBaby.getGender(), selectedBaby.getLastWeight(), selectedBaby.getBirthday());

            int centerIndex = BabyData.getHeader().length/2;

            double diff = WeightData.getValues(selectedBaby.getGender(), selectedBaby.getBirthday())[wperIndex]
                    - WeightData.getValues(selectedBaby.getGender(), selectedBaby.getBirthday())[centerIndex];

            logo.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.baseline_info_24_blue));
            tv_title.setText("정보");
            tv_subtitle.setText("몸무게 백분율 정보");
            tv_content.setText(String.format("%d개월 유아 평균 몸무게보다 %s%.1fkg 차이납니다.", BabyData.getAge(selectedBaby.getBirthday(), Calendar.getInstance()), (diff > 0.0 ? "+" : ""), diff));
            babyData.drawPerChart(context, lineChart, WeightData.getValues(selectedBaby.getGender(), selectedBaby.getBirthday()), wperIndex, "kg");

            li_sv.addView(card);
        }

        {
            View card = LayoutInflater.from(context).inflate(R.layout.list_card_info, li_sv, false);

            ImageView logo = (ImageView) card.findViewById(R.id.img_logo);
            TextView tv_subtitle = (TextView) card.findViewById(R.id.tv_subtitle);
            TextView tv_title = (TextView) card.findViewById(R.id.tv_title);
            TextView tv_content = (TextView) card.findViewById(R.id.tv_content);
            LineChart lineChart = (LineChart) card.findViewById(R.id.chart);
            lineChart.setVisibility(View.GONE);

            logo.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.baseline_warning_24_orange));
            tv_title.setText("경고");
            tv_subtitle.setText("몸무게 측정 한달 경과");
            tv_content.setText("몸무게를 측정해주세요.");

            li_sv.addView(card);

        }

        {
            View card = LayoutInflater.from(context).inflate(R.layout.list_card_info, li_sv, false);

            ImageView logo = (ImageView) card.findViewById(R.id.img_logo);
            TextView tv_subtitle = (TextView) card.findViewById(R.id.tv_subtitle);
            TextView tv_title = (TextView) card.findViewById(R.id.tv_title);
            TextView tv_content = (TextView) card.findViewById(R.id.tv_content);
            LineChart lineChart = (LineChart) card.findViewById(R.id.chart);
            lineChart.setVisibility(View.VISIBLE);

            int hperIndex = babyData.getCmPercentileIndex(selectedBaby.getGender(), selectedBaby.getLastHeight(), selectedBaby.getBirthday());

            int centerIndex = BabyData.getHeader().length/2;

            double diff = HeightData.getValues(selectedBaby.getGender(), selectedBaby.getBirthday())[hperIndex]
                    - HeightData.getValues(selectedBaby.getGender(), selectedBaby.getBirthday())[centerIndex];

            logo.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.baseline_info_24_blue));
            tv_title.setText("정보");
            tv_subtitle.setText("키 백분율 정보");
            tv_content.setText(String.format("%d개월 유아 평균 키보다 %s%.1fcm 차이납니다.", BabyData.getAge(selectedBaby.getBirthday(), Calendar.getInstance()), (diff > 0.0 ? "+" : ""), diff));
            babyData.drawPerChart(context, lineChart, HeightData.getValues(selectedBaby.getGender(), selectedBaby.getBirthday()), hperIndex, "cm");

            li_sv.addView(card);
        }

        {
            View card = LayoutInflater.from(context).inflate(R.layout.list_card_info, li_sv, false);

            ImageView logo = (ImageView) card.findViewById(R.id.img_logo);
            TextView tv_subtitle = (TextView) card.findViewById(R.id.tv_subtitle);
            TextView tv_title = (TextView) card.findViewById(R.id.tv_title);
            TextView tv_content = (TextView) card.findViewById(R.id.tv_content);
            LineChart lineChart = (LineChart) card.findViewById(R.id.chart);
            lineChart.setVisibility(View.GONE);

            logo.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.baseline_warning_24_orange));
            tv_title.setText("경고");
            tv_subtitle.setText("키 측정 한달 경과");
            tv_content.setText("키를 측정해주세요.");

            li_sv.addView(card);

        }

    }


    public void init(){

        list = new ArrayList<>();
        tempList = new ArrayList<>();

        toolbar = (Toolbar) view.findViewById(R.id.anim_toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        toolbarTitle = (TextView) view.findViewById(R.id.toolbar_title);

        collapsingToolbar = (CollapsingToolbarLayout)    view.findViewById(R.id.collapsing_toolbar);
//        collapsingToolbar.setTitle("Galaxy Babies");
        ImageView header = (ImageView) view.findViewById(R.id.header);


        AppBarLayout appBarLayout = (AppBarLayout) view.findViewById(R.id.appbar);
        appBarLayout.addOnOffsetChangedListener(new AppBarStateChangeListener() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state) {
                switch (state.name()){
                    case "EXPANDED":
                        toolbarTitle.setTextColor(Color.WHITE);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            int flags = view.getSystemUiVisibility();
                            flags &= ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
                            view.setSystemUiVisibility(flags);
                        }
                        break;
                    case "IDLE":
                    case "COLLAPSED":
                    default:
                        toolbarTitle.setTextColor(Color.BLACK);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            int flags = view.getSystemUiVisibility();
                            flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
                            view.setSystemUiVisibility(flags);
                        }
                        break;
                }
            }
        });

        li_babies = (LinearLayout) view.findViewById(R.id.li_babies);
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

        scrollView = (NestedScrollView) view.findViewById(R.id.sv);
        li_sv = (LinearLayout) view.findViewById(R.id.li_sv);

        updatePage();

//        mLinearLayoutManager = new LinearLayoutManager(context);
//        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//        rv = (RecyclerView) view.findViewById(R.id.scrollableview);
//        rv.setHasFixedSize(true);
//        rv.setLayoutManager(mLinearLayoutManager);


//        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
//                R.drawable.header);
//        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
//            @Override
//            public void onGenerated(Palette palette) {
//                mutedColor = palette.getMutedColor(R.attr.colorPrimary);
//                collapsingToolbar.setContentScrimColor(mutedColor);
//            }
//        });

//        getList();

    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_menu_example, menu);
//        return true;
//    }

//    private void getList(){
//
//        for(int i=0; i<20; i++){
//            list.add(i+"");
//        }
//
//        if(adapter != null){
//            adapter.setLoaded();
//        }
//
//        makeList();
//    }
//
//    public void makeList(){
//
//        adapter = new MainListCustomAdapter(context, list, rv, this);
//
//        rv.setAdapter(adapter);
//
//        adapter.notifyDataSetChanged();
//
//    }

    @Override
    public void showView() {

    }

    @Override
    public void hideView() {

    }

    @Override
    public void redirectActivityForResult(Intent intent) {

    }

    @Override
    public void redirectActivity(Intent intent) {

    }
}
