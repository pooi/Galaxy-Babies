package com.samsung.galaxy_babies.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.samsung.galaxy_babies.MainActivity;
import com.samsung.galaxy_babies.R;
import com.samsung.galaxy_babies.adapter.MainListCustomAdapter;
import com.samsung.galaxy_babies.adapter.NoteListCustomAdapter;
import com.samsung.galaxy_babies.obj.Board;
import com.samsung.galaxy_babies.obj.User;
import com.samsung.galaxy_babies.util.AppBarStateChangeListener;
import com.samsung.galaxy_babies.util.OnAdapterSupport;

import java.util.ArrayList;
import java.util.Random;

public class NoteFragment extends BaseFragment implements OnAdapterSupport {
    // UI
    private View view;
    private Context context;
    private Activity activity;

    private ArrayList<Board> tempList;
    private ArrayList<Board> list;

    private RecyclerView rv;
    private LinearLayoutManager mLinearLayoutManager;
    private NoteListCustomAdapter adapter;

    private User user;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        this.setRetainInstance(true);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // UI
        view = inflater.inflate(R.layout.fragment_note, container, false);
        context = container.getContext();
        activity = getActivity();

        user = ((MainActivity)getActivity()).getUser();

//        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
//        view.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
//        getActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);

        init();

        return view;

    }




    public void init(){

        list = new ArrayList<>();
        tempList = new ArrayList<>();

        mLinearLayoutManager = new LinearLayoutManager(context);
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv = (RecyclerView) view.findViewById(R.id.scrollableview);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(mLinearLayoutManager);

        getList();

    }

    private void getList(){

        makeDummyData();

//        for(int i=0; i<20; i++){
//            list.add(i+"");
//        }

        if(adapter != null){
            adapter.setLoaded();
        }

        makeList();
    }

    public void makeList(){

        adapter = new NoteListCustomAdapter(context, list, rv, this);

        rv.setAdapter(adapter);

        adapter.notifyDataSetChanged();

    }

    public void makeDummyData(){

        Random rand = new Random();

        Board board = new Board();
        board.setUser(user);
        board.setBaby(user.getBabies().get(0));
        board.setTitle("공자는 봄바람이다");
        board.setContent("Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.");
        board.setCommentCnt(rand.nextInt(10));
        board.setLikeCnt(rand.nextInt(10));
        board.setViewCnt(rand.nextInt(100));
        board.setMainImg("https://mn.kbs.co.kr/data/fckeditor/new/image/20170220_j6.jpg");
        list.add(board);

        board = new Board();
        board.setUser(user);
        board.setBaby(user.getBabies().get(1));
        board.setTitle("우리 같은 어디 인류");
        board.setContent("Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.");
        board.setCommentCnt(rand.nextInt(10));
        board.setLikeCnt(rand.nextInt(10));
        board.setViewCnt(rand.nextInt(100));
        board.setMainImg("https://img-s-msn-com.akamaized.net/tenant/amp/entityid/AAC1IcX.img?h=347&w=624&m=6&q=60&o=f&l=f&x=389&y=186");
        list.add(board);

        board = new Board();
        board.setUser(user);
        board.setBaby(user.getBabies().get(1));
        board.setTitle("가슴에 따뜻한 뛰노는 크고 약동");
        board.setContent("Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.");
        board.setCommentCnt(rand.nextInt(10));
        board.setLikeCnt(rand.nextInt(10));
        board.setViewCnt(rand.nextInt(100));
        board.setMainImg("https://img.huffingtonpost.com/asset/5bcc5f993c000025020e1c8c.png?ops=scalefit_630_noupscale");
        list.add(board);

        board = new Board();
        board.setUser(user);
        board.setBaby(user.getBabies().get(0));
        board.setTitle("내는 싶이 피고");
        board.setContent("Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.");
        board.setCommentCnt(rand.nextInt(10));
        board.setLikeCnt(rand.nextInt(10));
        board.setViewCnt(rand.nextInt(100));
        board.setMainImg("http://t1.daumcdn.net/tvpot/thumb/s7c23FxTLQFTxm2xLq3Xmbx/thumb.png?ts=1541434007");
        list.add(board);

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
        startActivity(intent);
    }
}
