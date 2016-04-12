package com.example.peter.myapplication;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;

import com.roughike.bottombar.BottomBar;

import java.util.ArrayList;
import java.util.List;

import it.neokree.materialnavigationdrawer.MaterialNavigationDrawer;

public class MainActivity extends MaterialNavigationDrawer {

    private BottomBar mBottomBar;
    private UserDAO userDAO;
    private UserEntity user;
    private LogDAO logDAO;
    private TargetDAO targetDAO;

    @Override
    public void init(Bundle savedInstanceState) {

        userDAO = new UserDAO(getApplicationContext());
        if (userDAO.getCount() == 0) {
            userDAO.sample();
        }

        user = userDAO.getByUserName("aaa");

        targetDAO = new TargetDAO(getApplicationContext());
        if (targetDAO.getCount() == 0) {
            targetDAO.sample();
        }

        logDAO = new LogDAO(getApplicationContext());
        if (logDAO.getCount() == 0) {
            logDAO.sample();
        }




//        mBottomBar = BottomBar.attach(this, savedInstanceState);
//        mBottomBar.setDefaultTabPosition(0);
//        mBottomBar.noResizeGoodness();
//        mBottomBar.setItemsFromMenu(R.menu.bottombar_menu, new OnMenuTabClickListener() {
//            @Override
//            public void onMenuTabSelected(@IdRes int menuItemId) {
//                onButtonClick(menuItemId);
//            }
//
//            @Override
//            public void onMenuTabReSelected(@IdRes int menuItemId) {
//            }
//        });

        // set header data
//        setDrawerHeaderImage(R.drawable.mat2);
        setUsername("Peter Huang");
        setUserEmail("peter760504@gmail.com");
//        setFirstAccountPhoto(getResources().getDrawable(R.drawable.photo));

        // create sections

        Bundle frontPageFragmentBundle = new Bundle();
        frontPageFragmentBundle.putSerializable("userEntity", user);
        FrontPageFragment frontPageFragment = new FrontPageFragment();
        frontPageFragment.setArguments(frontPageFragmentBundle);
        this.addSection(newSection("首頁", frontPageFragment));
        this.addDivisor();
        TargetListFragment goodTargetListFragment = new TargetListFragment();
        Bundle goodTargetListFragmentBundle = new Bundle();
        goodTargetListFragmentBundle.putSerializable("userEntity", user);
        goodTargetListFragmentBundle.putInt("targetAttributes", 0);
        goodTargetListFragment.setArguments(goodTargetListFragmentBundle);
        this.addSection(newSection("好習慣", goodTargetListFragment));
        TargetListFragment badTargetListFragment = new TargetListFragment();
        Bundle badTargetListFragmentBundle = new Bundle();
        goodTargetListFragmentBundle.putSerializable("userEntity", user);
        badTargetListFragmentBundle.putInt("targetAttributes", 1);
        badTargetListFragment.setArguments(badTargetListFragmentBundle);
        this.addSection(newSection("壞習慣", badTargetListFragment));
        this.addDivisor();
        TargetListFragment rewardListFragment = new TargetListFragment();
        Bundle RewardListFragmentBundle = new Bundle();
        RewardListFragmentBundle.putSerializable("userEntity", user);
        RewardListFragmentBundle.putInt("targetAttributes", 2);
        rewardListFragment.setArguments(RewardListFragmentBundle);
        this.addSection(newSection("兌換獎勵", rewardListFragment));
        this.addDivisor();
        LogFragment logFragment = new LogFragment();
        Bundle logFragmentBundle = new Bundle();
        logFragmentBundle.putSerializable("userEntity", user);
        logFragment.setArguments(logFragmentBundle);
        this.addSection(newSection("活動紀錄", logFragment));

//        ProcessGoodTargetFragment processGoodTargetFragment = new ProcessGoodTargetFragment();
//        processGoodTargetFragment.setArguments(bundle);
//        BadTargetListFragment badTargetListFragment = new BadTargetListFragment();
//        badTargetListFragment.setArguments(bundle);
//        RewardListFragment rewardListFragment = new RewardListFragment();
//        rewardListFragment.setArguments(bundle);


//        this.addDivisor();

//        this.addSection(newSection("Section 1", new FragmentIndex()));
//        this.addSection(newSection("Section 2",new FragmentIndex()));
//        this.addSection(newSection("Section 3",R.drawable.ic_mic_white_24dp,new FragmentButton()).setSectionColor(Color.parseColor("#9c27b0")));
//        this.addSection(newSection("Section",R.drawable.ic_hotel_grey600_24dp,new FragmentButton()).setSectionColor(Color.parseColor("#03a9f4")));

        // create bottom section
//        this.addBottomSection(newSection("Bottom Section", R.drawable.ic_settings_black_24dp, new Intent(this, Settings.class)));


    }


//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//
//    }

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
