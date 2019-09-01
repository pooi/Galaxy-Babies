package com.samsung.galaxy_babies;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.tabs.TabLayout;
import com.samsung.galaxy_babies.fragment.EmptyFragment;
import com.samsung.galaxy_babies.fragment.MainFragment;
import com.samsung.galaxy_babies.fragment.MyBabyFragment;

public class MainActivity extends AppCompatActivity{

    private Activity activity;

    private TabLayout tabLayout;

    final MainFragment mainFragment = new MainFragment();
    final MyBabyFragment myBabyFragment = new MyBabyFragment();
    final EmptyFragment emptyFragment = new EmptyFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);

        activity = this;

        tabLayout = (TabLayout) findViewById(R.id.tab) ;
        tabLayout.addTab(tabLayout.newTab().setCustomView(createTabView("홈")));
        tabLayout.addTab(tabLayout.newTab().setCustomView(createTabView("게임")));
        tabLayout.addTab(tabLayout.newTab().setCustomView(createTabView("영화")));
        tabLayout.addTab(tabLayout.newTab().setCustomView(createTabView("우리 아이")));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                selectTab(tab);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                selectTab(tab);
            }
        });

        tabLayout.getTabAt(0).select();

    }

    private void selectTab(TabLayout.Tab tab){
        int position = tab.getPosition();
        System.out.println(position);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int flags = activity.getWindow().getDecorView().getSystemUiVisibility();
            flags &= ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            activity.getWindow().getDecorView().setSystemUiVisibility(flags);
        }
        switch (position) {
            case 0:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_layout, mainFragment).commit();
                break;
            case 3:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    int flags = activity.getWindow().getDecorView().getSystemUiVisibility();
                    flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
                    activity.getWindow().getDecorView().setSystemUiVisibility(flags);
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_layout, myBabyFragment).commit();
                break;
            default:

                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_layout, emptyFragment).commit();
                break;
        }
    }


    private View createTabView(String tabName) {
        View tabView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.main_tab, null);
        TextView txt_name = (TextView) tabView.findViewById(R.id.txt_name);
        txt_name.setText(tabName);
        return tabView;

    }
}
