package com.example.peter.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnMenuTabClickListener;

/**
 * Created by peter on 4/2/16.
 */
public class BottomBarActivity extends AppCompatActivity {
    private BottomBar mBottomBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        mBottomBar = BottomBar.attach(this, savedInstanceState);

        //        mBottomBar = BottomBar.attach(this, savedInstanceState);
        mBottomBar.setItemsFromMenu(R.menu.bottombar_menu, new OnMenuTabClickListener() {
            @Override
            public void onMenuTabSelected(@IdRes int menuItemId) {
//                if (menuItemId == R.id.bb_menu_recents) {
////                    setContentView(R.layout.add_target);
//                    startActivityForResult(new Intent(this, MainActivity.class), 0);
//                }
//                if (menuItemId == R.id.bb_menu_favorites) {
//                    startActivityForResult(new Intent(this, AddTargetActivity.class), 0);
//                }
//                if (menuItemId == R.id.bb_menu_nearby) {
//                    startActivityForResult(new Intent(this, ProcessGoodTargetActivity.class), 0);
//                }
            }

            @Override
            public void onMenuTabReSelected(@IdRes int menuItemId) {
//                if (resId == R.id.bottomBarItemOne) {
//                    // The user reselected item number one, scroll your content to top.
//                }
            }
        });
//
//        // Setting colors for different tabs when there's more than three of them.
//        // You can set colors for tabs in three different ways as shown below.
//        mBottomBar.mapColorForTab(0, ContextCompat.getColor(this, R.color.colorAccent));
//        mBottomBar.mapColorForTab(1, 0xFF5D4037);
//        mBottomBar.mapColorForTab(2, "#7B1FA2");
//        mBottomBar.mapColorForTab(3, "#FF5252");
//        mBottomBar.mapColorForTab(4, "#FF9800");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Necessary to restore the BottomBar's state, otherwise we would
        // lose the current tab on orientation change.
        mBottomBar.onSaveInstanceState(outState);
    }

}
