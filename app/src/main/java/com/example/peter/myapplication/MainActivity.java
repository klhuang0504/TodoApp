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
    private ItemDAO itemDAO;
    Item userItem = new Item();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        itemDAO = new ItemDAO(getApplicationContext());
        userItem = itemDAO.getByUserName("aaa");
        mBottomBar = BottomBar.attach(this, savedInstanceState);
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
        bundle.putSerializable("userItem", userItem);

        if (fragment == null) {
            if (id == R.id.bb_menu_frontPage) {
                fragment = new AddTargetActivity();
            } else {
                fragment = new ProcessGoodTargetActivity();
            }
            replaceFragment(fragment, bundle);
        } else if (id == R.id.bb_menu_frontPage) {
            replaceFragment(new AddTargetActivity(), bundle);
        } else if (id == R.id.bb_menu_targetList) {
            replaceFragment(new ProcessGoodTargetActivity(), bundle);
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
