package com.example.peter.myapplication;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnMenuTabClickListener;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.IdRes;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    private LinearLayout addTodoTaskLayout;
    private EditText addTodoTaskEditText;
    private BottomBar mBottomBar;
    private RelativeLayout floatingActionsMenuLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_target_fab);
        addTodoTaskLayout = (LinearLayout) findViewById(R.id.add_todo_task_layout);
        addTodoTaskEditText = (EditText) findViewById(R.id.addTodoTaskEditText);
        floatingActionsMenuLayout = (RelativeLayout)findViewById(R.id.floatingActionsMenuLayout);


        final FloatingActionsMenu menuMultipleActions = (FloatingActionsMenu) findViewById(R.id.multiple_actions);
        menuMultipleActions.setOnFloatingActionsMenuUpdateListener(new FloatingActionsMenu.OnFloatingActionsMenuUpdateListener() {
            @Override
            public void onMenuExpanded() {
                floatingActionsMenuLayout.setBackgroundColor(Color.argb(55, 255, 255, 255));
            }

            @Override
            public void onMenuCollapsed() {
                floatingActionsMenuLayout.setBackgroundColor(Color.argb(0, 0, 255, 0));
            }
        });

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
                menuMultipleActions.collapse();
//                floatingActionsMenuLayout.setBackgroundColor(Color.argb(55, 0, 255, 0));
                addTodoTaskLayout.setVisibility(View.VISIBLE);
                addTodoTaskEditText.requestFocus();
                ((InputMethodManager) getSystemService(getBaseContext().INPUT_METHOD_SERVICE)).toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);

                //下方為顯示虛擬鍵盤
                //mInputMethodManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                addTodoTaskEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        //按下完成鍵要執行的動作

                        addTodoTaskLayout.setVisibility(View.INVISIBLE);
                        addTodoTaskEditText.setText("");
                        Toast.makeText(MainActivity.this, addTodoTaskEditText.getText(), Toast.LENGTH_SHORT).show();
                        return false;
                    }
//                addTodoTaskEditText.setImeActionLabel("完成", KeyEvent.KEYCODE_ENTER);
//                addTodoTaskLayout.start
//                addTargetButton.setTitle("Action A clicked");
                });
//                addTodoTaskEditText.setImeActionLabel("完成", KeyEvent.KEYCODE_ENTER);
//                addTodoTaskLayout.start
//                addTargetButton.setTitle("Action A clicked");
            }
        });

        mBottomBar = BottomBar.attach(this, savedInstanceState);


        //        mBottomBar = BottomBar.attach(this, savedInstanceState);
        mBottomBar.setItemsFromMenu(R.menu.bottombar_menu, new OnMenuTabClickListener() {
            @Override
            public void onMenuTabSelected(@IdRes int menuItemId) {
//                if (menuItemId == R.id.bb_menu_recents) {
//                    setContentView(R.layout.activity_main);
//                    startActivityForResult(new Intent(MainActivity.this, MainActivity.class), 0);
//                }
                if (menuItemId == R.id.bb_menu_frontPage) {
//                    setContentView(R.layout.add_target);
                }
                if (menuItemId == R.id.bb_menu_targetList) {
//                    setContentView(R.layout.process_good_target);

//                    startActivityForResult(new Intent(MainActivity.this, ProcessGoodTargetActivity.class), 0);
                }
            }

            @Override
            public void onMenuTabReSelected(@IdRes int menuItemId) {
//                if (resId == R.id.bottomBarItemOne) {
//                    // The user reselected item number one, scroll your content to top.
//                }
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

//        if (fragment == null) {
//            if (id == R.id.bb_menu_frontPage) {
//                fragment = new AddTargetActivity();
//            } else {
//                fragment = new ProcessGoodTargetActivity();
//            }
//            replaceFragment(fragment, bundle);
//        } else if (id == R.id.bb_menu_frontPage) {
//            replaceFragment(new AddTargetActivity(), bundle);
//        } else if (id == R.id.bb_menu_targetList) {
//            replaceFragment(new ProcessGoodTargetActivity(), bundle);
//        }

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
