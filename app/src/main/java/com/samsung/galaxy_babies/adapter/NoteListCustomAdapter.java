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
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.samsung.galaxy_babies.R;
import com.samsung.galaxy_babies.obj.Board;
import com.samsung.galaxy_babies.util.OnAdapterSupport;
import com.samsung.galaxy_babies.util.OnLoadMoreListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * Created by tw on 2017-10-01.
 */
public class NoteListCustomAdapter extends RecyclerView.Adapter<NoteListCustomAdapter.ViewHolder> {

    // UI
    private Context context;
//    private SearchPatientActivity activity;

    //    private MaterialNavigationDrawer activity;
    private OnAdapterSupport onAdapterSupport;

    public ArrayList<Board> list;

    // 무한 스크롤
    private OnLoadMoreListener onLoadMoreListener;
    private int visibleThreshold = 10;
    private int lastVisibleItem, totalItemCount;
    private boolean loading = false;

    // 생성자
    public NoteListCustomAdapter(Context context, ArrayList<Board> list, RecyclerView recyclerView, OnAdapterSupport listener) {
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
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_board,null);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Board board = list.get(position);
        final int pos = position;

        Picasso.get().load(board.getMainImg()).into(holder.mainImg);
        Picasso.get().load(board.getBaby().getProfileImg()).into(holder.profileImg);

        holder.tv_nickname.setText(board.getBaby().getName());
        holder.tv_detail.setText(board.getBaby().getDescription());
        holder.tv_title.setText(board.getTitle());
        holder.tv_content.setText(board.getContent());

        holder.tv_viewCnt.setText(board.getViewCnt()+"");
        holder.tv_commentCnt.setText(board.getCommentCnt()+"");
        holder.tv_likeCnt.setText(board.getLikeCnt()+"");

//        holder.tv_title.setText(log.getMsg());
//        holder.tv_date.setText(AdditionalFunc.getDateTimeSrtString(log.getRegisteredDate()));


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

        ImageView mainImg;
        CircleImageView profileImg;
        TextView tv_nickname;
        TextView tv_detail;
        TextView tv_viewCnt;
        TextView tv_title;
        TextView tv_content;
        TextView tv_commentCnt;
        TextView tv_likeCnt;


        public ViewHolder(View v) {
            super(v);
            mainImg = (ImageView) v.findViewById(R.id.img_main);
            profileImg = (CircleImageView) v.findViewById(R.id.img_profile);
            tv_nickname = (TextView) v.findViewById(R.id.tv_nickname);
            tv_detail = (TextView) v.findViewById(R.id.tv_detail);
            tv_viewCnt = (TextView) v.findViewById(R.id.tv_viewCnt);
            tv_title = (TextView) v.findViewById(R.id.tv_title);
            tv_content = (TextView) v.findViewById(R.id.tv_content);
            tv_commentCnt = (TextView) v.findViewById(R.id.tv_commentCnt);
            tv_likeCnt = (TextView) v.findViewById(R.id.tv_likeCnt);
        }

    }

}
