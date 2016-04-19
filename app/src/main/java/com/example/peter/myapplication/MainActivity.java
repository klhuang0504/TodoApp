package com.example.peter.myapplication;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import com.example.peter.myapplication.data.LogDAO;
import com.example.peter.myapplication.data.TargetDAO;
import com.example.peter.myapplication.data.UserDAO;
import com.example.peter.myapplication.data.UserEntity;
import com.example.peter.myapplication.frontpage.FrontPageFragment;
import com.example.peter.myapplication.login.LoginFragment;
import com.example.peter.myapplication.log.LogFragment;
import com.example.peter.myapplication.target.TargetListFragment;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import it.neokree.materialnavigationdrawer.MaterialNavigationDrawer;
import it.neokree.materialnavigationdrawer.elements.MaterialAccount;
import it.neokree.materialnavigationdrawer.elements.MaterialSection;

//import com.roughike.bottombar.BottomBar;

public class MainActivity extends MaterialNavigationDrawer implements BackHandledFragment.BackHandlerInterface, LoginFragment.OnFacebookLoginSuccessListener {

    //    private BottomBar mBottomBar;
    private UserDAO userDAO;
    private UserEntity user;
    private LogDAO logDAO;
    private TargetDAO targetDAO;
    private FrontPageFragment frontPageFragment;
    private TargetListFragment goodTargetListFragment, badTargetListFragment, rewardListFragment;
    private LogFragment logFragment;
    private MaterialSection frontPageMaterialSection, goodTargetListSection, badTargetListSection, rewardListSection;
    private String fbProfilePhotoUrl;

    private BackHandledFragment selectedFragment;
    private Bitmap fbProfilePhoto;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    public void init(Bundle savedInstanceState) {
//        this.setBackPattern(MaterialNavigationDrawer.BACKPATTERN_CUSTOM);
        this.setBackPattern(MaterialNavigationDrawer.BACKPATTERN_BACK_TO_FIRST);

        this.disableLearningPattern();
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

        MaterialAccount account = new MaterialAccount(this.getResources(), "test", "test@gmail.com", null, null);
        this.addAccount(account);
//        setUsername("");
//        setUserEmail("peter760504@gmail.com");

        this.addSection(newSection("登入", new LoginFragment()));
        Bundle frontPageFragmentBundle = new Bundle();
        frontPageFragmentBundle.putSerializable("userEntity", user);
        frontPageFragment = new FrontPageFragment();
        frontPageFragment.setArguments(frontPageFragmentBundle);
        frontPageMaterialSection = newSection("首頁", frontPageFragment);
        this.addSection(frontPageMaterialSection);
        this.addDivisor();
        goodTargetListFragment = new TargetListFragment();
        Bundle goodTargetListFragmentBundle = new Bundle();
        goodTargetListFragmentBundle.putSerializable("userEntity", user);
        goodTargetListFragmentBundle.putInt("targetAttributes", 0);
        goodTargetListFragment.setArguments(goodTargetListFragmentBundle);
        goodTargetListSection = newSection("好習慣", goodTargetListFragment);
        this.addSection(goodTargetListSection);
        badTargetListFragment = new TargetListFragment();
        Bundle badTargetListFragmentBundle = new Bundle();
        goodTargetListFragmentBundle.putSerializable("userEntity", user);
        badTargetListFragmentBundle.putInt("targetAttributes", 1);
        badTargetListFragment.setArguments(badTargetListFragmentBundle);
        badTargetListSection = newSection("壞習慣", badTargetListFragment);
        this.addSection(badTargetListSection);
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
        if (selectedFragment == null || !selectedFragment.onBackPressed()) {
            super.onBackPressed();
        } else if (selectedFragment.equals(frontPageFragment) && frontPageFragment.isFloatingActionsMenuOpen()) {
            frontPageFragment.setFloatingActionsMenuOpen(false);
        } else if (selectedFragment.equals(frontPageFragment) && frontPageFragment.isAddTodoTaskLayoutisVisible()) {
            frontPageFragment.setAddTodoTaskLayoutVisible(false);
        } else if (selectedFragment.equals(frontPageFragment) && frontPageFragment.isAddTargetLayoutIsVisible()) {
            frontPageFragment.setAddTargetLayoutVisible(false);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void setSelectedFragment(BackHandledFragment selectedFragment) {
        this.selectedFragment = selectedFragment;
    }

    @Override
    public void onFacebookLoginSuccess(JSONObject object) {
        try {

            fbProfilePhotoUrl = "https://graph.facebook.com/" + object.getString("id") + "/picture?type=large";
            new Thread(runnable).start();
            this.getCurrentAccount().setTitle(object.getString("name"));
//            setUsername(object.getString("name"));
            this.getCurrentAccount().setSubTitle(object.getString("email"));
//            setFirstAccountPhoto();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            try {
                fbProfilePhoto = BitmapFactory.decodeStream((InputStream) new URL(fbProfilePhotoUrl).getContent());
                MainActivity.this.getCurrentAccount().setPhoto(fbProfilePhoto);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };

//    public static Drawable drawableFromUrl(String url) throws IOException {
//        Bitmap x;
//
//        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
//        connection.connect();
//        InputStream input = connection.getInputStream();
//
//        x = BitmapFactory.decodeStream(input);
//        return new BitmapDrawable(x);
//    }
//
//    public static Drawable LoadImageFromWebOperations(String url) {
//        try {
//            InputStream is = (InputStream) new URL(url).getContent();
//            Drawable d = Drawable.createFromStream(is, "src name");
//            return d;
//        } catch (Exception e) {
//            return null;
//        }
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.peter.myapplication/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.peter.myapplication/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }

//    @Override
//    public void onFacebookLoginSuccess(JSONObject object) {
//
//    }
}
