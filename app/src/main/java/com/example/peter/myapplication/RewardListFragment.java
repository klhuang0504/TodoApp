package com.example.peter.myapplication;

import android.app.DialogFragment;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by peter on 2016/3/29.
 */
public class RewardListFragment extends Fragment {

    private ListView rewardListView;
    private TargetDAO targetDAO;
    private List<TargetEntity> RewardList;
    private TargetAdapter targetAdapter;
    private TargetEntity selectTargetEntity;
    private UserDAO userDAO;
    private UserEntity userEntity;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        targetDAO = new TargetDAO(getActivity());
        userDAO = new UserDAO(getActivity());

//        Bundle bundle = getArguments();
//        userEntity = (UserEntity) bundle.getSerializable("userEntity");
        RewardList = getRewardList();
        int layoutId = R.layout.single_target;
        targetAdapter = new TargetAdapter(getActivity(), layoutId, RewardList);
    }

    public void processGoodTarget(View view) {
        userEntity.setUserPoint(userEntity.getUserPoint() + selectTargetEntity.getPoint());
        userDAO.update(userEntity);
    }


    public ArrayList<TargetEntity> getRewardList() {

        return targetDAO.getRewardList();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.process_good_target, container,
                false);
        rewardListView = (ListView) view.findViewById(R.id.goodTargetListView);

        rewardListView.setAdapter(targetAdapter);
        rewardListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view,
                                    int position, long id) {
//                ListView listView = (ListView) parent;
//                Toast.makeText(
                selectTargetEntity = (TargetEntity) parent.getAdapter().getItem(position);

                DialogFragment targetActionMenuActivity = new TargetActionMenuFragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable("userEntity", userEntity);
                bundle.putSerializable("targetEntity", selectTargetEntity);

                targetActionMenuActivity.setArguments(bundle);
                targetActionMenuActivity.show(getActivity().getFragmentManager(), "targetActionMenuActivity");
            }
        });
        return view;
    }
}
