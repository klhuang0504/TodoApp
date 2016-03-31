package com.example.peter.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;

/**
 * Created by peter on 2016/3/31.
 */
public class TargetActionMenuActivity extends Activity {

    private LinearLayout target_action_menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.target_action_menu);

        processViews();

        Button button = new Button(this);
        button.setText("執行");
        LinearLayout.LayoutParams layout =
                new LinearLayout.LayoutParams(300, 150);
        layout.setMargins(6, 6, 6, 6);
        button.setLayoutParams(layout);



        target_action_menu.addView(button);
    }

    private void processViews() {
        target_action_menu = (LinearLayout) findViewById(R.id.targetActionMenu);
    }

}

