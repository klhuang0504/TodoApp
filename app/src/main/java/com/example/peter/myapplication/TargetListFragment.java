package com.example.peter.myapplication;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.util.Attributes;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by peter on 2016/3/29.
 */
public class TargetListFragment extends Fragment implements AdapterCallback {

    private ListView targetlistView;
    private TargetDAO targetDAO;
    private List<TargetEntity> targetEntityList;
    private TargetSwipeAdapter targetSwipeAdapter;

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

    public void processGoodTarget(View view) {
        userEntity.setUserPoint(userEntity.getUserPoint() + selectTargetEntity.getPoint());
        userDAO.update(userEntity);
    }


    public ArrayList<TargetEntity> getTargetEntityList(int targetAttributes) {
        if(targetAttributes == 0){
            return targetDAO.getGoodTargetList();
        }
        if(targetAttributes == 1){
            return targetDAO.getBadTargetList();
        }
        if(targetAttributes == 2){
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
        if(attributes>0){
            userEntity.setUserPoint(userEntity.getUserPoint()-targetEntity.getPoint());
        }else{
            userEntity.setUserPoint(userEntity.getUserPoint()+targetEntity.getPoint());
        }
        userDAO.update(userEntity);
        LogEntity logEntity = new LogEntity();
        logEntity.setEntityId(targetEntity.getId());
        logEntity.setDate(new Date());
        logDAO.insert(logEntity);
//        Toast.makeText(getActivity(), "onMethodCallback - " + targetEntity.getTargetName(), Toast.LENGTH_SHORT).show();
    }
}
