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
import com.samsung.galaxy_babies.obj.Baby;
import com.samsung.galaxy_babies.obj.Measure;
import com.samsung.galaxy_babies.obj.User;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Random;

public class MainActivity extends AppCompatActivity{

    private Activity activity;

    private TabLayout tabLayout;

    final MainFragment mainFragment = new MainFragment();
    final MyBabyFragment myBabyFragment = new MyBabyFragment();
    final EmptyFragment emptyFragment = new EmptyFragment();

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        activity = this;


        setDummyData();


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

    private void setDummyData(){
        user = new User();
        user.setId("11111111");
        user.setFirstName("길동");
        user.setLastName("홍");
        user.setEmail("galaxy.babiez@gmail.com");

        Random random = new Random();

        Baby baby1 = new Baby();
        {
            baby1.setId("2222222");
            ArrayList<Measure> heights = new ArrayList<>();
            ArrayList<Measure> weights = new ArrayList<>();
            heights.add(new Measure(new GregorianCalendar(2019, Calendar.JANUARY, 8), 3.7));
            weights.add(new Measure(new GregorianCalendar(2019, Calendar.JANUARY, 8), 3.5));
            for (int i = 1; i < 10; i++) {
                Measure preH = heights.get(i-1);
                Measure preW = weights.get(i-1);
                heights.add(new Measure(
                        new GregorianCalendar(
                                preH.getDate().get(Calendar.YEAR),
                                preH.getDate().get(Calendar.MONTH),
                                preH.getDate().get(Calendar.DAY_OF_MONTH)+1),
                        preH.getValue()+random.nextDouble()/10
                ));
                weights.add(new Measure(
                        new GregorianCalendar(
                                preW.getDate().get(Calendar.YEAR),
                                preW.getDate().get(Calendar.MONTH),
                                preW.getDate().get(Calendar.DAY_OF_MONTH)+1),
                        preW.getValue()+random.nextDouble()/10
                ));
            }
            baby1.setHeights(heights);
            baby1.setWeights(weights);
            baby1.setFirstName("영지");
            baby1.setLastName("이");
            baby1.setGender(Baby.Gender.WOMEN);
            baby1.setBirthday(new GregorianCalendar(2019, Calendar.JANUARY, 8));
            baby1.setProfileImg("https://img.momtalk.kr/image/information/2018/08/16/cbf38829-f4fb-4262-9ca3-1d7ea84051b2_1534382010.jpg");
        }

        Baby baby2 = new Baby();
        {
            baby2.setId("3333333");
            ArrayList<Measure> heights = new ArrayList<>();
            ArrayList<Measure> weights = new ArrayList<>();
            heights.add(new Measure(new GregorianCalendar(2019, Calendar.MARCH, 2), 2.7));
            weights.add(new Measure(new GregorianCalendar(2019, Calendar.MARCH, 2), 2.9));
            for (int i = 1; i < 10; i++) {
                Measure preH = heights.get(i-1);
                Measure preW = weights.get(i-1);
                heights.add(new Measure(
                        new GregorianCalendar(
                                preH.getDate().get(Calendar.YEAR),
                                preH.getDate().get(Calendar.MONTH),
                                preH.getDate().get(Calendar.DAY_OF_MONTH)+1),
                        preH.getValue()+random.nextDouble()/10
                ));
                weights.add(new Measure(
                        new GregorianCalendar(
                                preW.getDate().get(Calendar.YEAR),
                                preW.getDate().get(Calendar.MONTH),
                                preW.getDate().get(Calendar.DAY_OF_MONTH)+1),
                        preW.getValue()+random.nextDouble()/10
                ));
            }
            baby2.setHeights(heights);
            baby2.setWeights(weights);
            baby2.setFirstName("승일");
            baby2.setLastName("강");
            baby2.setGender(Baby.Gender.MEN);
            baby2.setBirthday(new GregorianCalendar(2019, Calendar.FEBRUARY, 10));
            baby2.setProfileImg("https://i.pinimg.com/236x/1e/f8/77/1ef8778371a2b9df27770b4c8f4a2962.jpg");
        }

        ArrayList<Baby> babies = new ArrayList<>();
        babies.add(baby1);
        babies.add(baby2);
        user.setBabies(babies);
    }

    public User getUser(){
        return this.user;
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
