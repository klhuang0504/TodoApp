package com.example.peter.myapplication;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.daimajia.swipe.util.Attributes;

import java.util.ArrayList;

/**
 * Created by peter on 2016/3/29.
 */
public class RecyclerFragment extends Fragment {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.target_recycler_layout, container, false);
        ArrayList<String> myDataset = new ArrayList<>();
        for(int i = 0; i < 100; i++){
            myDataset.add(i + "");
        }
        RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(myDataset, getContext());
        recyclerViewAdapter.setMode(Attributes.Mode.Single);
        RecyclerView mList = (RecyclerView) view.findViewById(R.id.targetRecyclerView);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this.getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mList.setLayoutManager(layoutManager);
        mList.setAdapter(recyclerViewAdapter);
        return view;
    }


}
