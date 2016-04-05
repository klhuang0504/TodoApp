package com.example.peter.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

public class MainActivity_version_2 extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_target_fab);

        final FloatingActionsMenu menuMultipleActions = (FloatingActionsMenu) findViewById(R.id.multiple_actions);

        final FloatingActionButton addTargetButton = (FloatingActionButton) findViewById(R.id.add_target);
        addTargetButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
//                addTargetButton.setTitle("Action A clicked");
            }
        });

        final View addTodoTaskButton = findViewById(R.id.add_todo_task);
        addTodoTaskButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
//                addTargetButton.setTitle("Action A clicked");
            }
        });


//        findViewById(R.id.pink_icon).setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(MainActivity.this, "Clicked pink Floating Action Button", Toast.LENGTH_SHORT).show();
//            }
//        });

//        FloatingActionButton button = (FloatingActionButton) findViewById(R.id.setter);
//        button.setSize(FloatingActionButton.SIZE_MINI);
//        button.setColorNormalResId(R.color.pink);
//        button.setColorPressedResId(R.color.pink_pressed);
//        button.setIcon(R.drawable.ic_fab_star);
//        button.setStrokeVisible(false);


//        FloatingActionButton actionC = new FloatingActionButton(getBaseContext());
//        actionC.setTitle("Hide/Show Action above");
//        actionC.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                actionB.setVisibility(actionB.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
//            }
//        });

//        menuMultipleActions.addButton(actionC);

//        final FloatingActionButton removeAction = (FloatingActionButton) findViewById(R.id.button_remove);
//        removeAction.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ((FloatingActionsMenu) findViewById(R.id.multiple_actions_down)).removeButton(removeAction);
//            }
//        });

//        ShapeDrawable drawable = new ShapeDrawable(new OvalShape());
//        drawable.getPaint().setColor(getResources().getColor(R.color.white));
//        ((FloatingActionButton) findViewById(R.id.setter_drawable)).setIconDrawable(drawable);



        // Test that FAMs containing FABs with visibility GONE do not cause crashes
//        findViewById(R.id.button_gone).setVisibility(View.GONE);
//
//        final FloatingActionButton actionEnable = (FloatingActionButton) findViewById(R.id.action_enable);
//        actionEnable.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                menuMultipleActions.setEnabled(!menuMultipleActions.isEnabled());
//            }
//        });

//        FloatingActionsMenu rightLabels = (FloatingActionsMenu) findViewById(R.id.right_labels);
//        FloatingActionButton addedOnce = new FloatingActionButton(this);
//        addedOnce.setTitle("Added once");
//        rightLabels.addButton(addedOnce);
//
//        FloatingActionButton addedTwice = new FloatingActionButton(this);
//        addedTwice.setTitle("Added twice");
//        rightLabels.addButton(addedTwice);
//        rightLabels.removeButton(addedTwice);
//        rightLabels.addButton(addedTwice);
    }
}
