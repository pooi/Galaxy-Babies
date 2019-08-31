package com.samsung.galaxy_babies.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.samsung.galaxy_babies.R;
import com.samsung.galaxy_babies.util.OnAdapterSupport;
import com.samsung.galaxy_babies.util.OnLoadMoreListener;

import java.util.ArrayList;


/**
 * Created by tw on 2017-10-01.
 */
public class MainListCustomAdapter extends RecyclerView.Adapter<MainListCustomAdapter.ViewHolder> {

    // UI
    private Context context;
//    private SearchPatientActivity activity;

    //    private MaterialNavigationDrawer activity;
    private OnAdapterSupport onAdapterSupport;

    public ArrayList<String> list;

    // 무한 스크롤
    private OnLoadMoreListener onLoadMoreListener;
    private int visibleThreshold = 10;
    private int lastVisibleItem, totalItemCount;
    private boolean loading = false;

    // 생성자
    public MainListCustomAdapter(Context context, ArrayList<String> list, RecyclerView recyclerView, OnAdapterSupport listener) {
        this.context = context;
        this.list = list;
        this.onAdapterSupport = listener;
//        this.activity = (SearchPatientActivity) activity;

        if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
            recyclerView.addOnScrollListener(new ScrollListener() {
                @Override
                public void onHide() {
                    hideViews();
                }

                @Override
                public void onShow() {
                    showViews();
                }
            });
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //recycler view에 반복될 아이템 레이아웃 연결
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list,parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final String log = list.get(position);
        final int pos = position;


    }

    @Override
    public int getItemCount() {
        return this.list.size();
    }

    private void hideViews() {
        if (onAdapterSupport != null) {
            onAdapterSupport.hideView();
        }
    }

    private void showViews() {
        if (onAdapterSupport != null) {
            onAdapterSupport.showView();
        }
    }

    // 무한 스크롤
    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }
    public void setLoaded() {
        loading = false;
    }

    public abstract class ScrollListener extends RecyclerView.OnScrollListener {
        private static final int HIDE_THRESHOLD = 30;
        private int scrolledDistance = 0;
        private boolean controlsVisible = true;

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);

            final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

            totalItemCount = linearLayoutManager.getItemCount();
            lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
            if (!loading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                // End has been reached
                // Do something
                loading = true;
                if (onLoadMoreListener != null) {
                    onLoadMoreListener.onLoadMore();
                }
            }
            // 여기까지 무한 스크롤

            if (scrolledDistance > HIDE_THRESHOLD && controlsVisible) {
                onHide();
                controlsVisible = false;
                scrolledDistance = 0;
            } else if (scrolledDistance < -HIDE_THRESHOLD && !controlsVisible) {
                onShow();
                controlsVisible = true;
                scrolledDistance = 0;
            }

            if((controlsVisible && dy>0) || (!controlsVisible && dy<0)) {
                scrolledDistance += dy;
            }
            // 여기까지 툴바 숨기기
        }

        public abstract void onHide();
        public abstract void onShow();

    }

    public final static class ViewHolder extends RecyclerView.ViewHolder {

//        TextView tv_title;
//        TextView tv_date;

        public ViewHolder(View v) {
            super(v);
//            tv_title = (TextView)v.findViewById(R.id.tv_title);
//            setCardButtonOnTouchAnimation(tv_title, cv);
//            tv_date = (TextView)v.findViewById(R.id.tv_date);
//            setCardButtonOnTouchAnimation(tv_date, cv);
        }
    }

}
