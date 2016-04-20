package com.example.peter.myapplication.target;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.util.Attributes;
import com.example.peter.myapplication.R;
import com.example.peter.myapplication.data.LogDAO;
import com.example.peter.myapplication.data.LogEntity;
import com.example.peter.myapplication.data.TargetDAO;
import com.example.peter.myapplication.data.TargetEntity;
import com.example.peter.myapplication.data.UserDAO;
import com.example.peter.myapplication.data.UserEntity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.graphics.Color.WHITE;

/**
 * Created by peter on 2016/3/29.
 */
public class TargetListFragment extends Fragment implements TargetSwipeAdapterCallback {

    private ListView targetlistView;
    private TargetDAO targetDAO;
    private List<TargetEntity> targetEntityList;
    private TargetSwipeAdapter targetSwipeAdapter;
    private ImageView targetDoneImageView;
    private TextView targetDoneTextView;

    private TargetEntity selectTargetEntity;
    private UserDAO userDAO;
    private UserEntity userEntity;
    private LogDAO logDAO;

    private int targetAttributes;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        targetDAO = new TargetDAO(getActivity());
        userDAO = new UserDAO(getActivity());
        logDAO = new LogDAO(getActivity());

        Bundle bundle = getArguments();
        userEntity = (UserEntity) bundle.getSerializable("userEntity");
        targetAttributes = bundle.getInt("targetAttributes");

        targetEntityList = getTargetEntityList(targetAttributes);
//        int layoutId = R.layout.single_target;
        targetSwipeAdapter = new TargetSwipeAdapter(getActivity(), targetEntityList, this);
    }

//    public void processGoodTarget(View view) {
//        userEntity.setUserPoint(userEntity.getUserPoint() + selectTargetEntity.getPoint());
//        userDAO.update(userEntity);
//    }


    public ArrayList<TargetEntity> getTargetEntityList(int targetAttributes) {
        if (targetAttributes == 0) {
            return targetDAO.getGoodTargetList();
        }
        if (targetAttributes == 1) {
            return targetDAO.getBadTargetList();
        }
        if (targetAttributes == 2) {
            return targetDAO.getRewardList();
        }
        return new ArrayList<TargetEntity>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.target_list_layout, container,
                false);
        targetlistView = (ListView) view.findViewById(R.id.goodTargetListView);
        targetSwipeAdapter.setMode(Attributes.Mode.Single);
        targetlistView.setAdapter(targetSwipeAdapter);

//        targetlistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                ((SwipeLayout) (targetlistView.getChildAt(position - targetlistView.getFirstVisiblePosition()))).open(true);
//            }
//        });
//        targetlistView.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
////                Log.e("ListView", "OnTouch");
//                return false;
//            }
//        });
//        targetlistView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(getActivity(), "OnItemLongClickListener", Toast.LENGTH_SHORT).show();
//                return true;
//            }
//        });
//        targetlistView.setOnScrollListener(new AbsListView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(AbsListView view, int scrollState) {
////                Log.e("ListView", "onScrollStateChanged");
//            }
//
//            @Override
//            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
//
//            }
//        });
//
//        targetlistView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
////                Log.e("ListView", "onItemSelected:" + position);
//                selectTargetEntity = targetEntityList.get(position);
//                Log.i("select Entity", selectTargetEntity.getTargetName());
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
////                Log.e("ListView", "onNothingSelected:");
//            }
//        });
        return view;
    }

    @Override
    public void onMethodCallback(TargetEntity targetEntity) {
        int attributes = targetEntity.getAttributes();
        if (attributes > 0) {
            userEntity.setUserPoint(userEntity.getUserPoint() - targetEntity.getPoint());
        } else {
            userEntity.setUserPoint(userEntity.getUserPoint() + targetEntity.getPoint());
        }
        userDAO.update(userEntity);
        LogEntity logEntity = new LogEntity();
        logEntity.setEntityId(targetEntity.getId());
        logEntity.setDate(new Date());
        logDAO.insert(logEntity);
//        Toast.makeText(getActivity(), "onMethodCallback - " + targetEntity.getTargetName(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void removeItemOnMethodCallback(TargetEntity targetEntity) {
        targetEntityList.remove(targetEntity);
        targetSwipeAdapter.notifyDataSetChanged();

    }

    @Override
    public void onSwipeLayoutStartOpenCallback(SwipeLayout swipeLayout) {

//        targetDoneImageView = (ImageView) swipeLayout.findViewById(R.id.target_done_image);
//        targetDoneTextView = (TextView) swipeLayout.findViewById(R.id.target_done_textView);
////        targetDoneImageView.setImageResource(R.drawable.ic_done_black_48dp);
//        int imageFrom = R.drawable.ic_check_white_48dp;
//        int imageTo = R.drawable.ic_done_black_48dp;
//        ValueAnimator imageAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), imageFrom, imageTo);
//        imageAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator animator) {
//                targetDoneImageView.setImageResource((int) animator.getAnimatedValue());
//            }
//        });
//        imageAnimation.setDuration(3000); //設定速度
//        imageAnimation.start();
////        targetDoneTextView.setTextColor(BLACK);
//
////        AnimatorSet set = (AnimatorSet) AnimatorInflater.loadAnimator(getActivity(),
////                R.animator.property_animator);
////        set.setTarget(targetDoneTextView);
////        set.start();
//
//        int colorFrom = Color.WHITE;
//        int colorTo = Color.BLACK;
//        ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);
//        colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator animator) {
//                targetDoneTextView.setTextColor((int) animator.getAnimatedValue());
//            }
//        });
//        colorAnimation.setDuration(3000); //設定速度
//        colorAnimation.start();
    }

    @Override
    public void onSwipeLayoutOpenCallback(SwipeLayout swipeLayout) {
        targetDoneImageView = (ImageView) swipeLayout.findViewById(R.id.target_done_image);
        targetDoneTextView = (TextView) swipeLayout.findViewById(R.id.target_done_textView);
        targetDoneImageView.setImageResource(R.drawable.ic_done_black_48dp);
        targetDoneTextView.setTextColor(Color.BLACK);

    }

    @Override
    public void onSwipeLayoutCloseCallback(SwipeLayout swipeLayout) {
        targetDoneImageView = (ImageView) swipeLayout.findViewById(R.id.target_done_image);
        targetDoneTextView = (TextView) swipeLayout.findViewById(R.id.target_done_textView);
        targetDoneImageView.setImageResource(R.drawable.ic_check_white_48dp);
        targetDoneTextView.setTextColor(WHITE);
    }

    @Override
    public void onSwipeLayoutStartCloseCallback(SwipeLayout swipeLayout) {

        targetSwipeAdapter.removeShownLayouts(swipeLayout);
        targetSwipeAdapter.notifyDataSetChanged();
//        targetSwipeAdapter.notify();
//        targetDoneImageView = (ImageView) swipeLayout.findViewById(R.id.target_done_image);
//        targetDoneTextView = (TextView) swipeLayout.findViewById(R.id.target_done_textView);
//        targetDoneImageView.setImageResource(R.drawable.ic_done_black_48dp);
//        targetDoneTextView.setTextColor(BLACK);

//        int imageTo = R.drawable.ic_check_white_48dp;
//        int imageFrom = R.drawable.ic_done_black_48dp;
//        ValueAnimator imageAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), imageFrom, imageTo);
//        imageAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator animator) {
//                targetDoneImageView.setImageResource((int) animator.getAnimatedValue());
//            }
//        });
//        imageAnimation.setDuration(3000); //設定速度
//        imageAnimation.start();

//        AnimatorSet set = (AnimatorSet) AnimatorInflater.loadAnimator(getActivity(),
//                R.animator.property_animator);
//        set.setTarget(targetDoneTextView);
//        set.start();

//        int colorTo = Color.WHITE;
//        int colorFrom = Color.BLACK;
//        ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);
//        colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator animator) {
//                targetDoneTextView.setTextColor((int) animator.getAnimatedValue());
//            }
//        });
//        colorAnimation.setDuration(3000); //設定速度
//        colorAnimation.start();
    }
}
