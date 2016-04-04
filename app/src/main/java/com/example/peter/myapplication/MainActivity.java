package com.example.peter.myapplication;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnMenuTabClickListener;

public class MainActivity extends Activity {

    private BottomBar mBottomBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Button button1 = (Button) this.findViewById(R.id.btn_fragment1);
//        Button button2 = (Button) this.findViewById(R.id.btn_fragment2);

//        button1.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                onButtonClick(view.getId());
//            }
//        });
//
//        button2.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                onButtonClick(view.getId());
//            }
//        });

        mBottomBar = BottomBar.attach(this, savedInstanceState);


        //        mBottomBar = BottomBar.attach(this, savedInstanceState);
        mBottomBar.setItemsFromMenu(R.menu.bottombar_menu, new OnMenuTabClickListener() {
            @Override
            public void onMenuTabSelected(@IdRes int menuItemId) {
                onButtonClick(menuItemId);
//                if (menuItemId == R.id.bb_menu_recents) {
//                    onButtonClick(R.id.btn_fragment1);
//                }
//                if (menuItemId == R.id.bb_menu_favorites) {
//                    onButtonClick(R.id.btn_fragment1);
//                }
//                if (menuItemId == R.id.bb_menu_nearby) {
//                    onButtonClick(R.id.btn_fragment2);
//                }
            }

            @Override
            public void onMenuTabReSelected(@IdRes int menuItemId) {
//                if (resId == R.id.bottomBarItemOne) {
//                    // The user reselected item number one, scroll your content to top.
//                }
            }
        });
    }

    private void onButtonClick(int id) {
        FragmentManager fm = getFragmentManager();

        Fragment fragment = fm.findFragmentById(R.id.layout_container);

        if (fragment == null) {
            if (id == R.id.bb_menu_recents)
                fragment = new AddTargetActivity();
            else
                fragment = new ProcessGoodTargetActivity();
            replaceFragment(fragment);
        } else if (id == R.id.bb_menu_recents) {
            replaceFragment(new AddTargetActivity());
        } else if (id == R.id.bb_menu_favorites) {
            replaceFragment(new ProcessGoodTargetActivity());
        }

    }

    private void replaceFragment(Fragment fragment) {
        this.getFragmentManager()
                .beginTransaction()
                .setCustomAnimations(android.R.animator.fade_in,
                        android.R.animator.fade_out)
                .replace(R.id.layout_container, fragment).addToBackStack(null)
                .commit();
    }
}
