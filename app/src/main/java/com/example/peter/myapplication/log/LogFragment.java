package com.example.peter.myapplication.log;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.util.Attributes;
import com.example.peter.myapplication.AdapterCallback;
import com.example.peter.myapplication.R;
import com.example.peter.myapplication.data.LogDAO;
import com.example.peter.myapplication.data.LogEntity;
import com.example.peter.myapplication.data.TargetDAO;
import com.example.peter.myapplication.data.TargetEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by peter on 2016/3/29.
 */
public class LogFragment extends Fragment implements AdapterCallback {

    private ListView logListView;
    private TargetDAO targetDAO;
    private LogDAO logDAO;

    private ArrayList<LogEntity> logEntityList;
    private ArrayList<TargetEntity> targetEntityList;
    private Map<Long, TargetEntity> targetEntityMap;

    private LogSwipeAdapter logSwipeAdapter;

//    private UserDAO userDAO;
//    private LogEntity selectTargetEntity;
//    private UserEntity userEntity;
//    private int targetAttributes;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        targetDAO = new TargetDAO(getActivity());
//        userDAO = new UserDAO(getActivity());
        logDAO = new LogDAO(getActivity());

        Bundle bundle = getArguments();
//        userEntity = (UserEntity) bundle.getSerializable("userEntity");
//        targetAttributes = bundle.getInt("targetAttributes");

//        int layoutId = R.layout.single_target;
        logEntityList = getLogEntityList();
        targetEntityList = getTargetList();
        targetEntityMap = new HashMap<Long, TargetEntity>();
        logSwipeAdapter = new LogSwipeAdapter(getActivity(), logEntityList, targetEntityList, this);
        for(TargetEntity targetEntity : targetEntityList){
            targetEntityMap.put(targetEntity.getId(), targetEntity);
        }


    }

//    @Override
//    public void onResume(){
//        Toast.makeText(getActivity(), "onResume", Toast.LENGTH_SHORT).show();
//    }


    public void processGoodTarget(View view) {
//        userEntity.setUserPoint(userEntity.getUserPoint() + selectTargetEntity.getPoint());
//        userDAO.update(userEntity);
    }


    public ArrayList<LogEntity> getLogEntityList() {
//        LogEntity tmpLogEntity = new LogEntity();
//        tmpLogEntity.setDate(new Date());
//        tmpLogEntity.setId(0);
//        tmpLogEntity.setEntityId(0);
//        ArrayList<LogEntity> tmpList = new ArrayList<LogEntity>();
//        tmpList.add(tmpLogEntity);
        return logDAO.getAll();
    }


    public ArrayList<TargetEntity> getTargetList() {
        return targetDAO.getAll();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.log_list_layout, container,
                false);
        logListView = (ListView) view.findViewById(R.id.logListView);
        logSwipeAdapter.setMode(Attributes.Mode.Single);
        logListView.setAdapter(logSwipeAdapter);
        return view;
    }

    @Override
    public void onMethodCallback(TargetEntity targetEntity) {

    }

    @Override
    public void onSwipeLayoutStartOpenCallback(SwipeLayout swipeLayout) {

    }

    @Override
    public void onSwipeLayoutOpenCallback(SwipeLayout swipeLayout) {

    }

    @Override
    public void onSwipeLayoutCloseCallback(SwipeLayout swipeLayout) {

    }

    @Override
    public void onSwipeLayoutStartCloseCallback(SwipeLayout swipeLayout) {

    }
}
