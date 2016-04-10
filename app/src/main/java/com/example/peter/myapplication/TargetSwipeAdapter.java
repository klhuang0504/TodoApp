package com.example.peter.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.swipe.SimpleSwipeListener;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.BaseSwipeAdapter;

import java.util.List;


public class TargetSwipeAdapter extends BaseSwipeAdapter {

    private Context mContext;
    private TargetEntity selectTargetEntity;
    private List<TargetEntity> targetEntityList;
    private TargetDAO targetDAO;

    public TargetSwipeAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public TargetSwipeAdapter(Context mContext, List<TargetEntity> targetEntityList) {
        this.mContext = mContext;
        this.targetEntityList = targetEntityList;
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
//        return 0;
    }

    @Override
    public View generateView(int position, ViewGroup parent) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.swipe_layout, null);
        SwipeLayout swipeLayout = (SwipeLayout) v.findViewById(getSwipeLayoutResourceId(position));
        swipeLayout.addSwipeListener(new SimpleSwipeListener() {
            @Override
            public void onOpen(SwipeLayout layout) {
//                YoYo.with(Techniques.Tada).duration(500).delay(100).playOn(layout.findViewById(R.id.trash));
            }
        });
        swipeLayout.setOnDoubleClickListener(new SwipeLayout.DoubleClickListener() {
            @Override
            public void onDoubleClick(SwipeLayout layout, boolean surface) {
                Toast.makeText(mContext, "DoubleClick", Toast.LENGTH_SHORT).show();
            }
        });
        v.findViewById(R.id.delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "click delete", Toast.LENGTH_SHORT).show();
            }
        });
        return v;
    }

    @Override
    public void fillValues(int position, View convertView) {
        TargetEntity targetEntity = targetEntityList.get(position);
        TextView swipeItemTextView = (TextView) convertView.findViewById(R.id.SwipeItemTextView);
//        t.setText((position + 1) + ".");
        swipeItemTextView.setText("名稱：" + targetEntity.getTargetName() + " - 點數：" + String.valueOf(targetEntity.getPoint()));
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
        return position;
    }
}
