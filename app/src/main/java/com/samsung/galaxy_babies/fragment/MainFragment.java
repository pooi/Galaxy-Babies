package com.samsung.galaxy_babies.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.samsung.galaxy_babies.R;
import com.samsung.galaxy_babies.adapter.MainListCustomAdapter;
import com.samsung.galaxy_babies.util.AppBarStateChangeListener;
import com.samsung.galaxy_babies.util.OnAdapterSupport;

import java.util.ArrayList;

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

    private RecyclerView rv;
    private LinearLayoutManager mLinearLayoutManager;
    private MainListCustomAdapter adapter;

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

//        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
//        view.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
//        getActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);

        init();

        return view;

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



        mLinearLayoutManager = new LinearLayoutManager(context);
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv = (RecyclerView) view.findViewById(R.id.scrollableview);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(mLinearLayoutManager);


//        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
//                R.drawable.header);
//        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
//            @Override
//            public void onGenerated(Palette palette) {
//                mutedColor = palette.getMutedColor(R.attr.colorPrimary);
//                collapsingToolbar.setContentScrimColor(mutedColor);
//            }
//        });

        getList();

    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_menu_example, menu);
//        return true;
//    }

    private void getList(){

        for(int i=0; i<20; i++){
            list.add(i+"");
        }

        if(adapter != null){
            adapter.setLoaded();
        }

        makeList();
    }

    public void makeList(){

        adapter = new MainListCustomAdapter(context, list, rv, this);

        rv.setAdapter(adapter);

        adapter.notifyDataSetChanged();

    }

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
