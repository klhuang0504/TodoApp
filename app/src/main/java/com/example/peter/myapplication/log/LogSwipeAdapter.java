package com.example.peter.myapplication.log;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.BaseSwipeAdapter;
import com.example.peter.myapplication.R;
import com.example.peter.myapplication.data.LogEntity;
import com.example.peter.myapplication.data.TargetDAO;
import com.example.peter.myapplication.data.TargetEntity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class LogSwipeAdapter extends BaseSwipeAdapter {

    private Context mContext;
    private LogEntity selectLogEntity;
    private ArrayList<LogEntity> logEntityList;
    private ArrayList<TargetEntity> targetEntityList;

    private TargetDAO targetDAO;
    private LogSwipeAdapterCallback mAdapterCallback;

    private Map<Long, TargetEntity> targetEntityMap;

    public LogSwipeAdapter(Context mContext) {
        this.mContext = mContext;
    }



    public LogSwipeAdapter(Context mContext, ArrayList<LogEntity> logEntityList, ArrayList<TargetEntity> targetEntityList) {
        this.mContext = mContext;
        this.logEntityList = logEntityList;
        this.targetEntityList = targetEntityList;
    }

    public LogSwipeAdapter(Context mContext, ArrayList<LogEntity> logEntityList, ArrayList<TargetEntity> targetEntityList, LogSwipeAdapterCallback callback) {
        this.mContext = mContext;
        this.logEntityList = logEntityList;
        this.mAdapterCallback = callback;
        this.targetEntityList = targetEntityList;
        targetEntityMap = new HashMap<Long, TargetEntity>();
        for (TargetEntity targetEntity : targetEntityList) {
            targetEntityMap.put(targetEntity.getId(), targetEntity);
        }

    }


    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
//        return 0;
    }

    @Override
    public View generateView(final int position, ViewGroup parent) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.single_log_swipe_layout, null);
        final SwipeLayout swipeLayout = (SwipeLayout) v.findViewById(getSwipeLayoutResourceId(position));
        swipeLayout.setShowMode(SwipeLayout.ShowMode.LayDown);

//        swipeLayout.addDrag(SwipeLayout.DragEdge.Left, swipeLayout.findViewById(R.id.bottom_wrapper));
        swipeLayout.addDrag(SwipeLayout.DragEdge.Right, swipeLayout.findViewById(R.id.bottom_wrapper_2));
//        swipeLayout.addDrag(SwipeLayout.DragEdge.Top, starBottView);
//        swipeLayout.addDrag(SwipeLayout.DragEdge.Bottom, starBottView);
//        swipeLayout.addRevealListener(R.id.delete, new SwipeLayout.OnRevealListener() {
//            @Override
//            public void onReveal(View child, SwipeLayout.DragEdge edge, float fraction, int distance) {
////                Toast.makeText(mContext, "Reveal", Toast.LENGTH_SHORT).show();
//
//            }
//        });

        swipeLayout.addSwipeListener(new SwipeLayout.SwipeListener() {
            private boolean smallOpen = true;

            @Override
            public void onStartOpen(SwipeLayout layout) {
//                if (!smallOpen) {
//                    layout.close();
//                }
            }

            @Override
            public void onOpen(SwipeLayout layout) {
            }

            @Override
            public void onStartClose(SwipeLayout layout) {

            }

            @Override
            public void onClose(SwipeLayout layout) {

            }

            @Override
            public void onUpdate(SwipeLayout layout, int leftOffset, int topOffset) {
//                Log.i("Swipe OnUpdate", String.valueOf(leftOffset));

            }

            @Override
            public void onHandRelease(SwipeLayout layout, float xvel, float yvel) {
//                if (xvel < (layout.getWidth() / 10 * 8)) {
//                    smallOpen = false;
//                    layout.close();
//                } else {
//                    smallOpen = true;
//                }
            }
        });

        swipeLayout.getSurfaceView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(mContext, "Click on surface", Toast.LENGTH_SHORT).show();
//                Log.d(MyActivity.class.getName(), "click on surface");
            }
        });
        swipeLayout.getSurfaceView().setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
//                Toast.makeText(mContext, "longClick on surface", Toast.LENGTH_SHORT).show();
//                Log.d(MyActivity.class.getName(), "longClick on surface");
                return true;
            }
        });
//        swipeLayout.findViewById(R.id.star2).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(mContext, "Star", Toast.LENGTH_SHORT).show();
//            }
//        });

        swipeLayout.findViewById(R.id.trash2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "Trash Bin", Toast.LENGTH_SHORT).show();
            }
        });

//        swipeLayout.findViewById(R.id.magnifier2).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(mContext, "Magnifier", Toast.LENGTH_SHORT).show();
//            }
//        });

//        swipeLayout.addRevealListener(R.id.starbott, new SwipeLayout.OnRevealListener() {
//            @Override
//            public void onReveal(View child, SwipeLayout.DragEdge edge, float fraction, int distance) {
//                View star = child.findViewById(R.id.star);
//                float d = child.getHeight() / 2 - star.getHeight() / 2;
////                ViewHelper.setTranslationY(star, d * fraction);
////                ViewHelper.setScaleX(star, fraction + 0.6f);
////                ViewHelper.setScaleY(star, fraction + 0.6f);
//            }
//        });
//        swipeLayout.addSwipeListener(new SimpleSwipeListener() {
//            @Override
//            public void onOpen(SwipeLayout layout) {
////                YoYo.with(Techniques.Tada).duration(500).delay(100).playOn(layout.findViewById(R.id.trash));
//            }
//        });
//        swipeLayout.setOnDoubleClickListener(new SwipeLayout.DoubleClickListener() {
//            @Override
//            public void onDoubleClick(SwipeLayout layout, boolean surface) {
//                Toast.makeText(mContext, "DoubleClick", Toast.LENGTH_SHORT).show();
//            }
//        });

//        v.findViewById(R.id.delete).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(mContext, "click delete", Toast.LENGTH_SHORT).show();
//            }
//        });
        return v;
    }

    @Override
    public void fillValues(int position, View convertView) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        LogEntity logEntity = logEntityList.get(position);
        TextView logNameTextView = (TextView) convertView.findViewById(R.id.logNameText);
        logNameTextView.setText(targetEntityMap.get(logEntity.getEntityId()).getTargetName());
        TextView logPointTextView = (TextView) convertView.findViewById(R.id.logPointText);
        logPointTextView.setText(String.valueOf(targetEntityMap.get(logEntity.getEntityId()).getPoint()));
        TextView logDateTextView = (TextView) convertView.findViewById(R.id.logDateText);
        logDateTextView.setText(dateFormat.format(logEntity.getDate()));
    }

    @Override
    public int getCount() {
        return logEntityList.size();
    }

    @Override
    public LogEntity getItem(int position) {
        return logEntityList.get(position);
    }

    @Override
    public long getItemId(int position) {
        LogEntity logEntity = logEntityList.get(position);
        return logEntity.getId();
    }
}
