package com.example.peter.myapplication;

import android.app.Fragment;
import android.os.Bundle;

import com.example.peter.myapplication.data.LogDAO;
import com.example.peter.myapplication.data.TargetDAO;
import com.example.peter.myapplication.data.UserDAO;
import com.example.peter.myapplication.data.UserEntity;
import com.example.peter.myapplication.frontpage.FrontPageFragment;
import com.example.peter.myapplication.log.LogFragment;
import com.example.peter.myapplication.target.TargetListFragment;
import com.roughike.bottombar.BottomBar;

import it.neokree.materialnavigationdrawer.MaterialNavigationDrawer;

public class MainActivity extends MaterialNavigationDrawer implements BackHandledFragment.BackHandlerInterface {

    private BottomBar mBottomBar;
    private UserDAO userDAO;
    private UserEntity user;
    private LogDAO logDAO;
    private TargetDAO targetDAO;
    private FrontPageFragment frontPageFragment;
    private TargetListFragment goodTargetListFragment, badTargetListFragment, rewardListFragment;
    private LogFragment logFragment;

    private BackHandledFragment selectedFragment;

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
        setUsername("Peter Huang");
        setUserEmail("peter760504@gmail.com");

        Bundle frontPageFragmentBundle = new Bundle();
        frontPageFragmentBundle.putSerializable("userEntity", user);
        frontPageFragment = new FrontPageFragment();
        frontPageFragment.setArguments(frontPageFragmentBundle);
        this.addSection(newSection("首頁", frontPageFragment));
        this.addDivisor();
        goodTargetListFragment = new TargetListFragment();
        Bundle goodTargetListFragmentBundle = new Bundle();
        goodTargetListFragmentBundle.putSerializable("userEntity", user);
        goodTargetListFragmentBundle.putInt("targetAttributes", 0);
        goodTargetListFragment.setArguments(goodTargetListFragmentBundle);
        this.addSection(newSection("好習慣", goodTargetListFragment));
        badTargetListFragment = new TargetListFragment();
        Bundle badTargetListFragmentBundle = new Bundle();
        goodTargetListFragmentBundle.putSerializable("userEntity", user);
        badTargetListFragmentBundle.putInt("targetAttributes", 1);
        badTargetListFragment.setArguments(badTargetListFragmentBundle);
        this.addSection(newSection("壞習慣", badTargetListFragment));
        this.addDivisor();
        rewardListFragment = new TargetListFragment();
        Bundle RewardListFragmentBundle = new Bundle();
        RewardListFragmentBundle.putSerializable("userEntity", user);
        RewardListFragmentBundle.putInt("targetAttributes", 2);
        rewardListFragment.setArguments(RewardListFragmentBundle);
        this.addSection(newSection("兌換獎勵", rewardListFragment));
        this.addDivisor();
        logFragment = new LogFragment();
        Bundle logFragmentBundle = new Bundle();
        logFragmentBundle.putSerializable("userEntity", user);
        logFragment.setArguments(logFragmentBundle);
        this.addSection(newSection("活動紀錄", logFragment));

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

    @Override
    public void onBackPressed() {
        if(selectedFragment.equals(frontPageFragment) && frontPageFragment.addTodoTaskLayoutIsShow()) {
            frontPageFragment.setAddTodoTaskLayoutVisVisible(false);
//        } else if(selectedFragment.equals(fragmentA) && fragmentA.isShowingLoginView()) {
//            fragmentA.hideLoginView();
//        } else if(selectedFragment.equals(fragmentA)) {
//            popBackStack();
//        } else if(selectedFragment.equals(fragmentB) && fragmentB.hasCondition1()) {
//            fragmentB.reverseCondition1();
//        } else if(selectedFragment.equals(fragmentB) && fragmentB.hasCondition2()) {
//            fragmentB.reverseCondition2();
//        } else if(selectedFragment.equals(fragmentB)) {
//            popBackStack();
        } else {
            // handle by activity
            super.onBackPressed();
        }
    }

    @Override
    public void setSelectedFragment(BackHandledFragment backHandledFragment) {
        this.selectedFragment = selectedFragment;
    }


}
