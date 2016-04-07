package com.example.peter.myapplication;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.IdRes;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnMenuTabClickListener;

public class MainActivity extends Activity {

    private BottomBar mBottomBar;
    private UserDAO userDAO;
    private UserEntity user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userDAO = new UserDAO(getApplicationContext());
        if (userDAO.getCount() == 0) {
            userDAO.sample();
        }

        user = userDAO.getByUserName("aaa");


        mBottomBar = BottomBar.attach(this, savedInstanceState);
        mBottomBar.setDefaultTabPosition(0);
        mBottomBar.noResizeGoodness();
//        mBottomBar.noNavBarGoodness();
//        mBottomBar.noTabletGoodness();
        mBottomBar.setItemsFromMenu(R.menu.bottombar_menu, new OnMenuTabClickListener() {
            @Override
            public void onMenuTabSelected(@IdRes int menuItemId) {
                onButtonClick(menuItemId);
            }

            @Override
            public void onMenuTabReSelected(@IdRes int menuItemId) {
            }
        });
    }

    private void onButtonClick(int id) {
        FragmentManager fm = getFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.layout_container);

        Bundle bundle = new Bundle();
        bundle.putSerializable("userEntity", user);

        if (fragment == null) {
//            if (id == R.id.bb_menu_frontPage)
//                fragment = new AddTargetActivity();
//            else
//                fragment = new ProcessGoodTargetFragment();
            fragment = new FrontPageFragment();
            replaceFragment(fragment, bundle);
        } else if (id == R.id.bb_menu_frontPage) {
            replaceFragment(new FrontPageFragment(), bundle);
        } else if (id == R.id.bb_menu_targetList) {
            replaceFragment(new ProcessGoodTargetFragment(), bundle);
        } else if (id == R.id.bb_menu_badTargetList) {
            replaceFragment(new BadTargetListFragment(), bundle);
        } else if (id == R.id.bb_menu_reward) {
            replaceFragment(new RewardListFragment(), bundle);
        } else if (id == R.id.bb_menu_log) {
            replaceFragment(new AddTargetActivity(), bundle);
        }

    }

    private void replaceFragment(Fragment fragment, Bundle bundle) {
        fragment.setArguments(bundle);
        this.getFragmentManager()
                .beginTransaction()
                .setCustomAnimations(android.R.animator.fade_in,
                        android.R.animator.fade_out)
                .replace(R.id.layout_container, fragment).addToBackStack(null)
                .commit();
    }
}
