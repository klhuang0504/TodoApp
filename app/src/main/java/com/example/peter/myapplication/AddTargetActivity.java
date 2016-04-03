package com.example.peter.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
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
public class AddTargetActivity extends AppCompatActivity {

    private EditText targetNameEt, pointEt;
    private TargetDAO targetDAO;
    private Item userItem;
    private TextView rightNowPoint;
    private ItemDAO itemDAO;
    private BottomBar mBottomBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_target);

        targetDAO = new TargetDAO(getApplicationContext());
        itemDAO = new ItemDAO(getApplicationContext());


        processViews();

        userItem = (Item) getIntent().getSerializableExtra("userItem");
        if (userItem == null) {
            Toast.makeText(this, "取得使用者資料失敗", Toast.LENGTH_LONG).show();
//            finish();
        } else {
            Toast.makeText(this, "歡迎" + userItem.getUserName(), Toast.LENGTH_LONG).show();
            rightNowPoint.setText(String.valueOf(userItem.getUserPoint()));

        }

        mBottomBar = BottomBar.attach(this, savedInstanceState);

        //        mBottomBar = BottomBar.attach(this, savedInstanceState);
        mBottomBar.setItemsFromMenu(R.menu.bottombar_menu, new OnMenuTabClickListener() {
            @Override
            public void onMenuTabSelected(@IdRes int menuItemId) {
                if (menuItemId == R.id.bb_menu_recents) {
//                    setContentView(R.layout.add_target);
                    startActivityForResult(new Intent(AddTargetActivity.this, MainActivity.class), 0);
                }
//                if (menuItemId == R.id.bb_menu_favorites) {
//                    startActivityForResult(new Intent(AddTargetActivity.this, AddTargetActivity.class), 0);
//                }
                if (menuItemId == R.id.bb_menu_nearby) {
                    startActivityForResult(new Intent(AddTargetActivity.this, ProcessGoodTargetActivity.class), 0);
                }
            }

            @Override
            public void onMenuTabReSelected(@IdRes int menuItemId) {
//                if (resId == R.id.bottomBarItemOne) {
//                    // The user reselected item number one, scroll your content to top.
//                }
            }
        });

    }

    private void processViews() {
        targetNameEt = (EditText) findViewById(R.id.targetNameEt);
        pointEt = (EditText) findViewById(R.id.pointEt);
        rightNowPoint = (TextView) findViewById(R.id.rightNowPoint);
    }

    public void addGoodTarget(View view) {
        TargetEntity targetEntity = new TargetEntity();
        targetEntity.setTargetName(getTargetNameEt());
        targetEntity.setPoint(getPointEt());
        targetEntity.setAttributes(0);
        targetDAO.insert(targetEntity);
        targetNameEt.setText("", TextView.BufferType.EDITABLE);
        pointEt.setText("", TextView.BufferType.NORMAL);
        Toast.makeText(this, "新增好習慣成功", Toast.LENGTH_LONG).show();
    }

    public void addBadTarget(View view) {
        TargetEntity targetEntity = new TargetEntity();
        targetEntity.setTargetName(getTargetNameEt());
        targetEntity.setPoint(getPointEt());
        targetEntity.setAttributes(1);
        targetDAO.insert(targetEntity);
        targetNameEt.setText("", TextView.BufferType.EDITABLE);
        pointEt.setText("", TextView.BufferType.NORMAL);
        Toast.makeText(this, "新增壞習慣成功", Toast.LENGTH_LONG).show();
    }

    public void addReward(View view) {
        TargetEntity targetEntity = new TargetEntity();
        targetEntity.setTargetName(getTargetNameEt());
        targetEntity.setPoint(getPointEt());
        targetEntity.setAttributes(2);
        targetDAO.insert(targetEntity);
        targetNameEt.setText("", TextView.BufferType.EDITABLE);
        pointEt.setText("", TextView.BufferType.NORMAL);
        Toast.makeText(this, "新增獎勵成功", Toast.LENGTH_LONG).show();
    }

    public void showTargetList(View view) {
//        List<TargetEntity> targetList = new ArrayList<TargetEntity>();
//        targetList = targetDAO.getAll();
//        for (TargetEntity targetEntity : targetList) {
//            System.out.println(targetEntity.getTargetName() + " : " + targetEntity.getPoint());
//        }
//        Toast.makeText(this, "清單查詢成功，共有 " + targetList.size() + " 筆資料", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, ProcessGoodTargetActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("userItem", userItem);
//            intent.putExtra("userItem", itemResult);
        intent.putExtras(bundle);
//        intent = new Intent(this, ProcessGoodTargetActivity.class);
//        Bundle bundle=new Bundle();
//        bundle.putSerializable("userItem", userItem);
//            intent.putExtra("userItem", itemResult);
//        intent.putExtras(bundle);

        // 呼叫「startActivityForResult」，第二個參數「1」表示執行修改
        startActivityForResult(intent, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // 如果被啟動的Activity元件傳回確定的結果
        if (resultCode == Activity.RESULT_OK) {
//            Toast.makeText(this, "執行Target : " + selectTargetEntity.getTargetName(), Toast.LENGTH_LONG).show();
            userItem = (Item) data.getExtras().getSerializable("userItem");
            rightNowPoint.setText(String.valueOf(userItem.getUserPoint()));
        }
    }

    public String getTargetNameEt() {
        return targetNameEt.getText().toString();
    }

    public int getPointEt() {
        return Integer.parseInt(pointEt.getText().toString());
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Necessary to restore the BottomBar's state, otherwise we would
        // lose the current tab on orientation change.
        mBottomBar.onSaveInstanceState(outState);
    }
}
