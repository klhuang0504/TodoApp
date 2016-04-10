package com.example.peter.myapplication;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.util.Attributes;

import java.util.ArrayList;
import java.util.List;

public class MainActivity_version20160410 extends Activity {

    private SwipeLayout sample1, sample2, sample3;
    private TargetDAO targetDAO;

    private ListView rewardListView;
    private List<TargetEntity> RewardList;
    private TargetSwipeAdapter targetSwipeAdapter;
    private TargetEntity selectTargetEntity;
    private Context mContext = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.target_list_layout);

        rewardListView = (ListView) findViewById(R.id.goodTargetListView);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            ActionBar actionBar = getActionBar();
            if (actionBar != null) {
                actionBar.setTitle("ListView");
            }
        }

        targetDAO = new TargetDAO(getApplicationContext());
        RewardList = getRewardList();
        targetSwipeAdapter = new TargetSwipeAdapter(this,RewardList);
        rewardListView.setAdapter(targetSwipeAdapter);
        targetSwipeAdapter.setMode(Attributes.Mode.Single);
        rewardListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ((SwipeLayout)(rewardListView.getChildAt(position - rewardListView.getFirstVisiblePosition()))).open(true);
            }
        });
        rewardListView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
//                Log.e("ListView", "OnTouch");
                return false;
            }
        });
        rewardListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(mContext, "OnItemLongClickListener", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        rewardListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
//                Log.e("ListView", "onScrollStateChanged");
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });

        rewardListView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                Log.e("ListView", "onItemSelected:" + position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
//                Log.e("ListView", "onNothingSelected:");
            }
        });
        //

//        targetDAO = new TargetDAO(getApplicationContext());
//        RewardList = getRewardList();
//        int layoutId = R.layout.swipe_layout;
//        arraySwipeAdapter = new ArraySwipeAdapter(getApplicationContext(), layoutId, RewardList);
//
//        sample1 = (SwipeLayout) findViewById(R.id.sample1);
//        sample1.setShowMode(SwipeLayout.ShowMode.PullOut);
//        View starBottView = sample1.findViewById(R.id.starbott);
//        sample1.addDrag(SwipeLayout.DragEdge.Left, sample1.findViewById(R.id.bottom_wrapper));
//        sample1.addDrag(SwipeLayout.DragEdge.Right, sample1.findViewById(R.id.bottom_wrapper_2));
//        sample1.addDrag(SwipeLayout.DragEdge.Top, starBottView);
//        sample1.addDrag(SwipeLayout.DragEdge.Bottom, starBottView);
//        sample1.addRevealListener(R.id.delete, new SwipeLayout.OnRevealListener() {
//            @Override
//            public void onReveal(View child, SwipeLayout.DragEdge edge, float fraction, int distance) {
//
//            }
//        });
//
//        sample1.getSurfaceView().setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(MainActivity.this, "Click on surface", Toast.LENGTH_SHORT).show();
////                Log.d(MyActivity.class.getName(), "click on surface");
//            }
//        });
//        sample1.getSurfaceView().setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//                Toast.makeText(MainActivity.this, "longClick on surface", Toast.LENGTH_SHORT).show();
////                Log.d(MyActivity.class.getName(), "longClick on surface");
//                return true;
//            }
//        });
//        sample1.findViewById(R.id.star2).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(MainActivity.this, "Star", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        sample1.findViewById(R.id.trash2).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(MainActivity.this, "Trash Bin", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        sample1.findViewById(R.id.magnifier2).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(MainActivity.this, "Magnifier", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        sample1.addRevealListener(R.id.starbott, new SwipeLayout.OnRevealListener() {
//            @Override
//            public void onReveal(View child, SwipeLayout.DragEdge edge, float fraction, int distance) {
//                View star = child.findViewById(R.id.star);
//                float d = child.getHeight() / 2 - star.getHeight() / 2;
////                ViewHelper.setTranslationY(star, d * fraction);
////                ViewHelper.setScaleX(star, fraction + 0.6f);
////                ViewHelper.setScaleY(star, fraction + 0.6f);
//            }
//        });

    }



    public ArrayList<TargetEntity> getRewardList() {
        return targetDAO.getRewardList();
    }
}