package com.example.peter.myapplication;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.daimajia.swipe.util.Attributes;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by peter on 2016/3/29.
 */
public class LogFragment extends Fragment implements AdapterCallback {

    private ListView logListView;
    private TargetDAO targetDAO;
    private List<LogEntity> logEntityList;
    private LogSwipeAdapter logSwipeAdapter;

    private LogEntity selectTargetEntity;
    private UserDAO userDAO;
    private UserEntity userEntity;
//    private int targetAttributes;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        targetDAO = new TargetDAO(getActivity());
        userDAO = new UserDAO(getActivity());

        Bundle bundle = getArguments();
        userEntity = (UserEntity) bundle.getSerializable("userEntity");
//        targetAttributes = bundle.getInt("targetAttributes");

//        int layoutId = R.layout.single_target;
        logEntityList = getLogEntityList();
        logSwipeAdapter = new LogSwipeAdapter(getActivity(), logEntityList, this);
    }

    public void processGoodTarget(View view) {
//        userEntity.setUserPoint(userEntity.getUserPoint() + selectTargetEntity.getPoint());
//        userDAO.update(userEntity);
    }


    public ArrayList<LogEntity> getLogEntityList() {
        LogEntity tmpLogEntity = new LogEntity();
        tmpLogEntity.setDate(new Date());
        tmpLogEntity.setId(0);
        tmpLogEntity.setLogId(0);
        ArrayList<LogEntity> tmpList = new ArrayList<LogEntity>();
        tmpList.add(tmpLogEntity);
        return tmpList;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.log_layout, container,
                false);
        logListView = (ListView) view.findViewById(R.id.logListView);
        logSwipeAdapter.setMode(Attributes.Mode.Single);
        logListView.setAdapter(logSwipeAdapter);
        return view;
    }

    @Override
    public void onMethodCallback(TargetEntity targetEntity) {

    }
}
