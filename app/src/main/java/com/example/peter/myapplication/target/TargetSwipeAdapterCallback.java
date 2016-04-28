package com.example.peter.myapplication.target;

import com.daimajia.swipe.SwipeLayout;
import com.example.peter.myapplication.data.TargetEntity;

/**
 * Created by peter on 2016/4/11.
 */
public interface TargetSwipeAdapterCallback {
    void onClickLaterButtonMethodCallback(TargetEntity targetEntity);
    void onClickEditButtonCallBack(TargetEntity targetEntity);
    void onClickUndoButtonCallBack(TargetEntity targetEntity);


    void onSwipeLayoutOpenCallback(SwipeLayout swipeLayout);
    void onSwipeLayoutCloseCallback(SwipeLayout swipeLayout);

    void doTarget(TargetEntity targetEntity);


}
