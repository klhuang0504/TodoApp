package com.example.peter.myapplication;

import android.app.ActionBar;
import android.app.DialogFragment;
import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
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
import java.util.List;

/**
 * Created by peter on 2016/3/29.
 */
public class ProcessGoodTargetFragment extends Fragment {

    private ListView goodTargetlistView;
    private TargetDAO targetDAO;
    private List<TargetEntity> goodTargetList;
//    private TargetAdapter targetAdapter;
    private TargetSwipeAdapter targetSwipeAdapter;

    private TargetEntity selectTargetEntity;
    private UserDAO userDAO;
    private UserEntity userEntity;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        targetDAO = new TargetDAO(getActivity());
        userDAO = new UserDAO(getActivity());

        Bundle bundle = getArguments();
//        userEntity = (UserEntity) bundle.getSerializable("userEntity");
        goodTargetList = getGoodTargetList();
        int layoutId = R.layout.single_target;
//        targetAdapter = new TargetAdapter(getActivity(), layoutId, goodTargetList);
        targetSwipeAdapter = new TargetSwipeAdapter(getActivity(),goodTargetList);


    }

    public void processGoodTarget(View view) {
        userEntity.setUserPoint(userEntity.getUserPoint() + selectTargetEntity.getPoint());
        userDAO.update(userEntity);
    }


    public ArrayList<TargetEntity> getGoodTargetList() {
        return targetDAO.getGoodTargetList();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.target_list_layout, container,
                false);
        goodTargetlistView = (ListView) view.findViewById(R.id.goodTargetListView);

        goodTargetlistView.setAdapter(targetSwipeAdapter);

        targetSwipeAdapter.setMode(Attributes.Mode.Single);
        goodTargetlistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ((SwipeLayout)(goodTargetlistView.getChildAt(position - goodTargetlistView.getFirstVisiblePosition()))).open(true);
            }
        });
        goodTargetlistView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
//                Log.e("ListView", "OnTouch");
                return false;
            }
        });
        goodTargetlistView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(), "OnItemLongClickListener", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        goodTargetlistView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
//                Log.e("ListView", "onScrollStateChanged");
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });

        goodTargetlistView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                Log.e("ListView", "onItemSelected:" + position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
//                Log.e("ListView", "onNothingSelected:");
            }
        });


//        goodTargetlistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView parent, View view,
//                                    int position, long id) {
////                ListView listView = (ListView) parent;
////                Toast.makeText(
//                selectTargetEntity = (TargetEntity) parent.getAdapter().getItem(position);
//
//                DialogFragment targetActionMenuActivity = new TargetActionMenuFragment();
//                Bundle bundle = new Bundle();
//                bundle.putSerializable("userEntity", userEntity);
//                bundle.putSerializable("targetEntity", selectTargetEntity);
//
//                targetActionMenuActivity.setArguments(bundle);
//                targetActionMenuActivity.show(getActivity().getFragmentManager(), "targetActionMenuActivity");
//            }
//        });

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
//            ActionBar actionBar = getActionBar();
//            if (actionBar != null) {
//                actionBar.setTitle("ListView");
//            }
//        }

        return view;
    }
}
