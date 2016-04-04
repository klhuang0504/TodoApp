package com.example.peter.myapplication;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnMenuTabClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by peter on 2016/3/29.
 */
public class ProcessGoodTargetActivity extends Fragment {

    private ListView goodTargetlistView;
    private TargetDAO targetDAO;
    private List<TargetEntity> goodTargetList;
    private TargetAdapter targetAdapter;
    private TargetEntity selectTargetEntity;
    private ItemDAO itemDAO;
    private Item userItem;


    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
//    private GoogleApiClient client;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.process_good_target);

//        userItem = (Item) getIntent().getSerializableExtra("userItem");
//        if(userItem == null){
//            Toast.makeText(this, "取得使用者資料失敗", Toast.LENGTH_LONG).show();
////            finish();
//        }

        targetDAO = new TargetDAO(getActivity());
        itemDAO = new ItemDAO(getActivity());

        Bundle bundle = getArguments();
        userItem = (Item) bundle.getSerializable("userItem");

//        processViews();

        goodTargetList = getGoodTargetList();

//        int layoutId = android.R.layout.simple_list_item_1;
        int layoutId = R.layout.single_target;
        targetAdapter = new TargetAdapter(getActivity(), layoutId, goodTargetList);


    }
//
//    private void processViews() {
//        goodTargetlistView = (ListView) findViewById(R.id.goodTargetListView);
//
//    }


    public void processGoodTarget(View view) {
//        startActivityForResult(
//                new Intent(this, TargetActionMenuActivity.class), 0);
//        setResult(Activity.RESULT_OK);
//        finish();
        userItem.setUserPoint(userItem.getUserPoint() + selectTargetEntity.getPoint());
        itemDAO.update(userItem);
    }

    public void clickGoodTarget(View view) {
//        Toast.makeText(this, "執行Target", Toast.LENGTH_LONG).show();
//        startActivityForResult(
//                new Intent(getActivity(), TargetActionMenuActivity.class), 0);
//        Fragment fragment = new TargetActionMenuActivity();
//        FragmentManager fm = getFragmentManager();

//        Fragment fragment = fm.findFragmentById(R.id.layout_container);


//        this.getFragmentManager()
//                .beginTransaction()
//                .setCustomAnimations(android.R.animator.fade_in,
//                        android.R.animator.fade_out)
//                .replace(R.id.layout_container, new TargetActionMenuActivity()).addToBackStack(null)
//                .commit();

    }


    public ArrayList<TargetEntity> getGoodTargetList() {
        return targetDAO.getGoodTargetList();
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        // 如果被啟動的Activity元件傳回確定的結果
//        if (resultCode == Activity.RESULT_OK) {
////            Toast.makeText(this, "執行Target : " + selectTargetEntity.getTargetName(), Toast.LENGTH_LONG).show();
//            userItem.setUserPoint(userItem.getUserPoint() + selectTargetEntity.getPoint());
//            itemDAO.update(userItem);
//        }
//    }

    public void done(View view) {
//        Intent result = getIntent();
//        result.putExtra("userItem", userItem);
//        setResult(Activity.RESULT_OK, result);
//        finish();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.process_good_target, container,
                false);
        goodTargetlistView = (ListView) view.findViewById(R.id.goodTargetListView);

        goodTargetlistView.setAdapter(targetAdapter);
        goodTargetlistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view,
                                    int position, long id) {
//                ListView listView = (ListView) parent;
//                Toast.makeText(
//                        ProcessGoodTargetActivity.this, "ClickItem", Toast.LENGTH_LONG).show();
                selectTargetEntity = (TargetEntity) parent.getAdapter().getItem(position);

                DialogFragment targetActionMenuActivity = new TargetActionMenuActivity();
                Bundle bundle = new Bundle();
                bundle.putSerializable("userItem", userItem);
                bundle.putSerializable("targetEntity", selectTargetEntity);

                targetActionMenuActivity.setArguments(bundle);
                targetActionMenuActivity.show(getActivity().getFragmentManager(), "targetActionMenuActivity");

//                selectTargetEntity = (TargetEntity) parent.getItemAtPosition(position);
//                startActivityForResult(
//                        new Intent(ProcessGoodTargetActivity.this.getActivity(), TargetActionMenuActivity.class), 0);
            }
        });
        return view;
    }
}
