package com.example.peter.myapplication;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.peter.myapplication.data.TargetDAO;
import com.example.peter.myapplication.data.TargetEntity;
import com.example.peter.myapplication.data.UserDAO;
import com.example.peter.myapplication.data.UserEntity;
import com.roughike.bottombar.BottomBar;

/**
 * Created by peter on 2016/3/28.
 */
public class AddTargetActivity extends Fragment {

    private EditText targetNameEt, pointEt;
    private TargetDAO targetDAO;
    private UserEntity userEntity;
    private TextView rightNowPoint;
    private UserDAO userDAO;
    private BottomBar mBottomBar;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        targetDAO = new TargetDAO(getActivity());
        userDAO = new UserDAO(getActivity());


        Bundle bundle = getArguments();
        userEntity = (UserEntity) bundle.getSerializable("userEntity");
        if (userEntity == null) {
            Toast.makeText(getActivity(), "取得使用者資料失敗", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getActivity(), "歡迎" + userEntity.getUserName(), Toast.LENGTH_LONG).show();
        }
    }

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


    public String getTargetNameEt() {
        return targetNameEt.getText().toString();
    }

    public int getPointEt() {
        return Integer.parseInt(pointEt.getText().toString());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_target_layout, container,
                false);
        targetNameEt = (EditText) view.findViewById(R.id.targetNameEt);
        pointEt = (EditText) view.findViewById(R.id.pointEt);
//        rightNowPoint = (TextView) view.findViewById(R.id.rightNowPoint);
        rightNowPoint.setText(String.valueOf(userEntity.getUserPoint()));
        return view;
    }
}
