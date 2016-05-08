package com.example.peter.myapplication;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.daimajia.swipe.SimpleSwipeListener;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;

import java.util.List;

/**
 * Created by peter on 2016/5/3.
 */
public class RecyclerViewAdapter extends RecyclerSwipeAdapter<RecyclerViewAdapter.SimpleViewHolder> {

    public static class SimpleViewHolder extends RecyclerView.ViewHolder {
        SwipeLayout swipeLayout;
        TextView mTextView;

        public SimpleViewHolder(View v) {
            super(v);
            mTextView = (TextView) v.findViewById(R.id.SwipeItemTextView);
            swipeLayout = (SwipeLayout) v.findViewById(R.id.singleRecycleLinearLayout);
        }
    }

    private List<String> mData;
    private Context mContext;

    public RecyclerViewAdapter(List<String> data, Context context) {
        this.mData = data;
        this.mContext = context;
    }

    @Override
    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_recyler_view_layout, parent, false);
        return new SimpleViewHolder(view);
    }

//    @Override
//    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View v = LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.single_recyler_view_layout, parent, false);
//        return new ViewHolder(v);
//    }

    @Override
    public void onBindViewHolder(final SimpleViewHolder viewHolder, final int position) {
        viewHolder.mTextView.setText(mData.get(position));
        viewHolder.swipeLayout.setShowMode(SwipeLayout.ShowMode.LayDown);
        viewHolder.swipeLayout.addDrag(SwipeLayout.DragEdge.Left, viewHolder.swipeLayout.findViewById(R.id.bottom_wrapper));
        viewHolder.swipeLayout.addDrag(SwipeLayout.DragEdge.Right, viewHolder.swipeLayout.findViewById(R.id.bottom_wrapper_2));
        viewHolder.swipeLayout.addSwipeListener(new SimpleSwipeListener() {
            @Override
            public void onOpen(SwipeLayout layout) {
//                YoYo.with(Techniques.Tada).duration(500).delay(100).playOn(layout.findViewById(R.id.trash));
//                Toast.makeText(mContext, "OnOpen!", Toast.LENGTH_SHORT).show();
                mItemManger.removeShownLayouts(viewHolder.swipeLayout);
                mData.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, mData.size());
                mItemManger.closeAllItems();
            }
        });
//        mItemManger.bindView(viewHolder.v ,position);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.singleRecycleLinearLayout;
    }
}