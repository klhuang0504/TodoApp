package com.example.peter.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by peter on 2016/3/29.
 */
public class ProcessGoodTargetActivity extends AppCompatActivity {

    private ListView goodTargetlistView;
    private TargetDAO targetDAO;
    private List<TargetEntity> goodTargetList;
    private TargetAdapter targetAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.process_good_target);

        targetDAO = new TargetDAO(getApplicationContext());

        processViews();

        goodTargetList = getGoodTargetList();

//        int layoutId = android.R.layout.simple_list_item_1;
        int layoutId = R.layout.single_target;
        targetAdapter = new TargetAdapter(this, layoutId, goodTargetList);
        goodTargetlistView.setAdapter(targetAdapter);
    }

    private void processViews() {
        goodTargetlistView = (ListView) findViewById(R.id.goodTargetListView);

    }

    public void processGoodTarget(View view) {

    }

    public ArrayList<TargetEntity> getGoodTargetList() {
        return targetDAO.getGoodTargetList();
    }

}
