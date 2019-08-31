package com.samsung.galaxy_babies.fragment;

import android.content.Context;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;
import com.samsung.galaxy_babies.R;

/**
 * Created by tw on 2017-09-28.
 */

public class BaseFragment extends Fragment {

    public void setFadeInAnimation(View view){
        Animation animation = new AlphaAnimation(0, 1);
        animation.setDuration(500);
        view.setAnimation(animation);
    }

    public void setFadeOutAnimation(View view){
        Animation animation = new AlphaAnimation(1, 0);
        animation.setDuration(500);
        view.setAnimation(animation);
    }

    public void showSnackbar(View g_view, Context context, String msg){
        Snackbar snackbar = Snackbar.make(g_view, msg, Snackbar.LENGTH_SHORT);
        View view = snackbar.getView();
        view.setBackgroundColor(ContextCompat.getColor(context, R.color.snackbar_color));
        snackbar.show();
    }

    public void showSnackbar(View g_view, Context context, int id){
        Snackbar snackbar = Snackbar.make(g_view, getResources().getString(id), Snackbar.LENGTH_SHORT);
        View view = snackbar.getView();
        view.setBackgroundColor(ContextCompat.getColor(context, R.color.snackbar_color));
        snackbar.show();
    }

    public int getColorId(Context context, int id){
        return ContextCompat.getColor(context, id);
    }

}
