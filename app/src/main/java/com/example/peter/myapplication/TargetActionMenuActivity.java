package com.example.peter.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

/**
 * Created by peter on 2016/3/31.
 */
public class TargetActionMenuActivity extends Activity {

    private LinearLayout target_action_menu;
    private TargetDAO targetDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.target_action_menu);

        targetDAO = new TargetDAO(getApplicationContext());

        processViews();

//        LinearLayout.LayoutParams layout =
//                new LinearLayout.LayoutParams(300, 150);
//        layout.setMargins(6, 6, 6, 6);
//
//        Button doButton = new Button(this);
//        doButton.setText("執行");
//        doButton.setLayoutParams(layout);
//
//        Button editButton = new Button(this);
//        editButton.setText("編輯");
//        editButton.setLayoutParams(layout);
//
//        Button cancelbutton = new Button(this);
//        cancelbutton.setText("取消");
//        cancelbutton.setLayoutParams(layout);
//
//
//        target_action_menu.addView(doButton);
//        target_action_menu.addView(editButton);
//        target_action_menu.addView(cancelbutton);

    }

    private void processViews() {
        target_action_menu = (LinearLayout) findViewById(R.id.targetActionMenu);
    }

    public void processGoodTarget(View view) {
//        Toast.makeText(this, "執行Target", Toast.LENGTH_LONG).show();
//        startActivityForResult(
//                new Intent(this, TargetActionMenuActivity.class), 0);
        setResult(Activity.RESULT_OK);
        finish();
    }

    public void cancel(View view){
        finish();
    }

}

