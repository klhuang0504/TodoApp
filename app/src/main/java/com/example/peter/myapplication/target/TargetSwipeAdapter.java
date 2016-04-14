package com.example.peter.myapplication.target;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.BaseSwipeAdapter;
import com.example.peter.myapplication.AdapterCallback;
import com.example.peter.myapplication.R;
import com.example.peter.myapplication.data.TargetDAO;
import com.example.peter.myapplication.data.TargetEntity;

import java.util.List;


public class TargetSwipeAdapter extends BaseSwipeAdapter {

    private Context mContext;
    private TargetEntity selectTargetEntity;
    private List<TargetEntity> targetEntityList;
    private TargetDAO targetDAO;
    private AdapterCallback mAdapterCallback;
    private LinearLayout blankLinearLayout;

    private ImageView targetDoneImageView;
    private TextView targetDoneTextView;

    public TargetSwipeAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public TargetSwipeAdapter(Context mContext, List<TargetEntity> targetEntityList) {
        this.mContext = mContext;
        this.targetEntityList = targetEntityList;
    }

    public TargetSwipeAdapter(Context mContext, List<TargetEntity> targetEntityList, AdapterCallback callback) {
        this.mContext = mContext;
        this.targetEntityList = targetEntityList;
        this.mAdapterCallback = callback;
    }


    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
//        return 0;
    }

    @Override
    public View generateView(final int position, ViewGroup parent) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.single_target_swipe_layout, null);
        final SwipeLayout swipeLayout = (SwipeLayout) v.findViewById(getSwipeLayoutResourceId(position));
        swipeLayout.setShowMode(SwipeLayout.ShowMode.LayDown);

        swipeLayout.addDrag(SwipeLayout.DragEdge.Left, swipeLayout.findViewById(R.id.bottom_wrapper));
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
                if (!smallOpen) {
                    layout.close();
                }
                mAdapterCallback.onSwipeLayoutStartOpenCallback(layout);


            }

            @Override
            public void onOpen(SwipeLayout layout) {
//                Toast.makeText(mContext, "Click on onOpen", Toast.LENGTH_SHORT).show();
//                layout.close();
                selectTargetEntity = targetEntityList.get(position);
                mAdapterCallback.onMethodCallback(selectTargetEntity);

                mAdapterCallback.onSwipeLayoutOpenCallback(layout);

//                Toast.makeText(mContext, selectTargetEntity.getTargetName(), Toast.LENGTH_SHORT).show();
//                Log.i("",selectTargetEntity.getTargetName());
            }

            @Override
            public void onStartClose(SwipeLayout layout) {
                mAdapterCallback.onSwipeLayoutStartCloseCallback(layout);

            }

            @Override
            public void onClose(SwipeLayout layout) {
                mAdapterCallback.onSwipeLayoutCloseCallback(layout);
            }

            @Override
            public void onUpdate(SwipeLayout layout, int leftOffset, int topOffset) {
//                Log.i("Swipe OnUpdate", String.valueOf(leftOffset));

            }

            @Override
            public void onHandRelease(SwipeLayout layout, float xvel, float yvel) {
                if (xvel < (layout.getWidth() / 10 * 8)) {
                    smallOpen = false;
                    layout.close();
                } else {
                    smallOpen = true;
                }
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
                return false;
            }
        });
        swipeLayout.findViewById(R.id.plusOneImageView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectTargetEntity = targetEntityList.get(position);
                mAdapterCallback.onMethodCallback(selectTargetEntity);
//                Toast.makeText(mContext, "Star", Toast.LENGTH_SHORT).show();
            }
        });
        swipeLayout.findViewById(R.id.undoImageView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                swipeLayout.close();
//                Toast.makeText(mContext, "Star", Toast.LENGTH_SHORT).show();
            }
        });
        swipeLayout.findViewById(R.id.laterImageView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectTargetEntity = targetEntityList.get(position);
                targetEntityList.remove(selectTargetEntity);
                mAdapterCallback.onSwipeLayoutStartCloseCallback(swipeLayout);
//                Toast.makeText(mContext, "Star"-0, Toast.LENGTH_SHORT).show();
            }
        });

        swipeLayout.findViewById(R.id.editImageView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(mContext, "Trash Bin", Toast.LENGTH_SHORT).show();
            }
        });


//
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
//        View listView = LayoutInflater.from(mContext).inflate(R.layout.target_list_layout, null);
//
//        blankLinearLayout = (LinearLayout)listView.findViewById(R.id.blankLinearLayout);
//        blankLinearLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                swipeLayout.close();
//            }
//        });
        return v;
    }

    @Override
    public void fillValues(int position, View convertView) {
        TargetEntity targetEntity = targetEntityList.get(position);
        TextView swipeItemTextView = (TextView) convertView.findViewById(R.id.SwipeItemTextView);
//        t.setText((position + 1) + ".");
        swipeItemTextView.setText(targetEntity.getTargetName() + "  (" + String.valueOf(targetEntity.getPoint()) + "分)");
    }

    @Override
    public int getCount() {
        return targetEntityList.size();
    }

    @Override
    public TargetEntity getItem(int position) {
        return targetEntityList.get(position);
    }

    @Override
    public long getItemId(int position) {
        TargetEntity targetEntity = targetEntityList.get(position);
        return targetEntity.getId();
    }
}
