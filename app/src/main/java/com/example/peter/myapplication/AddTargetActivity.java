package com.example.peter.myapplication;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnMenuTabClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by peter on 2016/3/28.
 */
public class AddTargetActivity extends Fragment {

    private EditText targetNameEt, pointEt;
    private TargetDAO targetDAO;
    private Item userItem;
    private TextView rightNowPoint;
    private ItemDAO itemDAO;
    private BottomBar mBottomBar;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.add_target);

        targetDAO = new TargetDAO(getActivity());
        itemDAO = new ItemDAO(getActivity());


//        processViews();

//        userItem = (Item) getIntent().getSerializableExtra("userItem");
//        if (userItem == null) {
//            Toast.makeText(getActivity(), "取得使用者資料失敗", Toast.LENGTH_LONG).show();
////            finish();
//        } else {
//            Toast.makeText(getActivity(), "歡迎" + userItem.getUserName(), Toast.LENGTH_LONG).show();
//            rightNowPoint.setText(String.valueOf(userItem.getUserPoint()));
//
//        }
    }

//    private void processViews() {
//        targetNameEt = (EditText) getView().findViewById(R.id.targetNameEt);
//        pointEt = (EditText) getView().findViewById(R.id.pointEt);
//        rightNowPoint = (TextView) getView().findViewById(R.id.rightNowPoint);
//    }

    public void addGoodTarget(View view) {
        TargetEntity targetEntity = new TargetEntity();
        targetEntity.setTargetName(getTargetNameEt());
        targetEntity.setPoint(getPointEt());
        targetEntity.setAttributes(0);
        targetDAO.insert(targetEntity);
        targetNameEt.setText("", TextView.BufferType.EDITABLE);
        pointEt.setText("", TextView.BufferType.NORMAL);
        Toast.makeText(getActivity(), "新增好習慣成功", Toast.LENGTH_LONG).show();
    }

    public void addBadTarget(View view) {
        TargetEntity targetEntity = new TargetEntity();
        targetEntity.setTargetName(getTargetNameEt());
        targetEntity.setPoint(getPointEt());
        targetEntity.setAttributes(1);
        targetDAO.insert(targetEntity);
        targetNameEt.setText("", TextView.BufferType.EDITABLE);
        pointEt.setText("", TextView.BufferType.NORMAL);
        Toast.makeText(getActivity(), "新增壞習慣成功", Toast.LENGTH_LONG).show();
    }

    public void addReward(View view) {
        TargetEntity targetEntity = new TargetEntity();
        targetEntity.setTargetName(getTargetNameEt());
        targetEntity.setPoint(getPointEt());
        targetEntity.setAttributes(2);
        targetDAO.insert(targetEntity);
        targetNameEt.setText("", TextView.BufferType.EDITABLE);
        pointEt.setText("", TextView.BufferType.NORMAL);
        Toast.makeText(getActivity(), "新增獎勵成功", Toast.LENGTH_LONG).show();
    }

    public void showTargetList(View view) {
        Intent intent = new Intent(getActivity(), ProcessGoodTargetActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("userItem", userItem);
        intent.putExtras(bundle);
        startActivityForResult(intent, 0);
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        // 如果被啟動的Activity元件傳回確定的結果
//        if (resultCode == Activity.RESULT_OK) {
////            Toast.makeText(this, "執行Target : " + selectTargetEntity.getTargetName(), Toast.LENGTH_LONG).show();
//            userItem = (Item) data.getExtras().getSerializable("userItem");
//            rightNowPoint.setText(String.valueOf(userItem.getUserPoint()));
//        }
//    }

    public String getTargetNameEt() {
        return targetNameEt.getText().toString();
    }

    public int getPointEt() {
        return Integer.parseInt(pointEt.getText().toString());
    }

    //    @Override
//    protected void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//
//        // Necessary to restore the BottomBar's state, otherwise we would
//        // lose the current tab on orientation change.
//        mBottomBar.onSaveInstanceState(outState);
//    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_target, container,
                false);
        targetNameEt = (EditText) view.findViewById(R.id.targetNameEt);
        pointEt = (EditText) view.findViewById(R.id.pointEt);
        rightNowPoint = (TextView) view.findViewById(R.id.rightNowPoint);
        return view;
    }
}
