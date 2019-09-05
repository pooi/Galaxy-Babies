package com.samsung.galaxy_babies.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.samsung.galaxy_babies.R;
import com.samsung.galaxy_babies.obj.Board;
import com.samsung.galaxy_babies.obj.Comment;
import com.samsung.galaxy_babies.obj.User;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;

public class BoardActivity extends AppCompatActivity {
    private User user;
    private Board board;
    
    CircleImageView profileImg;
    TextView tv_nickname;
    TextView tv_detail;
    TextView tv_viewCnt;
    TextView tv_title;
    ImageView mainImg;
    TextView tv_content;
    TextView tv_commentCnt;
    LinearLayout li_comments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int flags = getWindow().getDecorView().getSystemUiVisibility();
            flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            getWindow().getDecorView().setSystemUiVisibility(flags);
        }

        board = (Board) getIntent().getSerializableExtra("data");

        setDummyData();

        init();

        setUI();

    }

    private void setDummyData(){

        if(board != null){

            if(board.getComments() == null || board.getComments().size() != board.getCommentCnt()){

                ArrayList<Comment> comments = new ArrayList<>();

                for(int i=0; i<board.getCommentCnt(); i++){

                    Comment comment = new Comment();
                    comment.setUser(board.getUser());
                    comment.setDate(Calendar.getInstance());
                    comment.setContent("새 그들에게 그와 풍부하게 것이다. 있는 온갖 수 칼이다. 내는 얼마나 유소년에게서 말이다. 찬미를 풀이 없는 칼이다.");
                    comments.add(comment);

                }

                board.setComments(comments);

            }

        }

    }

    private void init(){

        profileImg  = (CircleImageView) findViewById(R.id.img_profile);
        tv_nickname = (TextView) findViewById(R.id.tv_nickname);
        tv_detail = (TextView) findViewById(R.id.tv_detail);
        tv_viewCnt = (TextView) findViewById(R.id.tv_viewCnt);
        tv_title = (TextView) findViewById(R.id.tv_title);
        mainImg = (ImageView) findViewById(R.id.img_main);
        tv_content = (TextView) findViewById(R.id.tv_content);
        tv_commentCnt = (TextView) findViewById(R.id.tv_commentCnt);
        li_comments = (LinearLayout) findViewById(R.id.li_comments);
        
    }

    private void setUI(){

        if(board != null){

            Picasso.get().load(board.getBaby().getProfileImg()).into(profileImg);
            Picasso.get().load(board.getMainImg()).into(mainImg);

            tv_nickname.setText(board.getBaby().getName());
            tv_detail.setText(board.getBaby().getDescription());
            tv_title.setText(board.getTitle());
            tv_content.setText(board.getContent());

            tv_viewCnt.setText(board.getViewCnt()+"");
            tv_commentCnt.setText(board.getCommentCnt()+"");

            for(Comment comment : board.getComments()){

                View cl = LayoutInflater.from(this).inflate(R.layout.list_comment, li_comments, false);

                CircleImageView pi = (CircleImageView) cl.findViewById(R.id.img_profile);
                TextView tv_nickname = (TextView) cl.findViewById(R.id.tv_nickname);
                TextView tv_content = (TextView) cl.findViewById(R.id.tv_content);
                TextView tv_date = (TextView) cl.findViewById(R.id.tv_date);

                Picasso.get().load(comment.getUser().getProfileImg()).into(pi);
                tv_nickname.setText(comment.getUser().getName());
                tv_content.setText(comment.getContent());
                tv_date.setText(comment.getDateString());

                li_comments.addView(cl);

            }
            
        }

    }
}
