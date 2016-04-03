package com.example.peter.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnMenuTabClickListener;

import java.io.Serializable;

public class MainActivity extends AppCompatActivity implements Serializable {

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    private EditText userNameEt, passWordEt;
    private ItemDAO itemDAO;
    private TargetDAO targetDAO;
    private BottomBar mBottomBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        startActivityForResult(new Intent(this, BottomBarActivity.class), 0);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

        processViews();

        itemDAO = new ItemDAO(getApplicationContext());
        if (itemDAO.getCount() == 0) {
            itemDAO.sample();
        }

        targetDAO = new TargetDAO(getApplicationContext());
        if (targetDAO.getCount() == 0) {
            targetDAO.sample();
        }

//        Intent intent = new Intent(this, AddTargetActivity.class);
//        Bundle bundle=new Bundle();
//        bundle.putSerializable("userItem", itemResult);
//        intent.putExtras(bundle);

        // 呼叫「startActivityForResult」，第二個參數「1」表示執行修改
//        startActivityForResult(intent, 0);
//        return;
        mBottomBar = BottomBar.attach(this, savedInstanceState);

        //        mBottomBar = BottomBar.attach(this, savedInstanceState);
        mBottomBar.setItemsFromMenu(R.menu.bottombar_menu, new OnMenuTabClickListener() {
            @Override
            public void onMenuTabSelected(@IdRes int menuItemId) {
//                if (menuItemId == R.id.bb_menu_recents) {
//                    setContentView(R.layout.activity_main);
//                    startActivityForResult(new Intent(MainActivity.this, MainActivity.class), 0);
//                }
                if (menuItemId == R.id.bb_menu_favorites) {
                    setContentView(R.layout.add_target);
                }
                if (menuItemId == R.id.bb_menu_nearby) {
                    setContentView(R.layout.process_good_target);

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

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void processViews() {
        userNameEt = (EditText) findViewById(R.id.userNameEt);
        passWordEt = (EditText) findViewById(R.id.passWordEt);
    }

    public String getUserNameEt() {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return userNameEt.getText().toString();
    }

    public String getPassWordEt() {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return passWordEt.getText().toString();
    }

    public void login(View view) {
        // 顯示訊息框，指定三個參數
        // Context：通常指定為「this」
        // String或int：設定顯示在訊息框裡面的訊息或文字資源
        // int：設定訊息框停留在畫面的時間
        Item itemResult = new Item();
        itemResult = itemDAO.getByUserName(getUserNameEt());
        if (itemResult.getPassWord() == null || itemResult.getPassWord().equals("")) {
            Toast.makeText(this, "查無使用者", Toast.LENGTH_LONG).show();
            return;
        }
        if (itemResult.getPassWord().equals(getPassWordEt())) {
            Toast.makeText(this, "登入成功", Toast.LENGTH_LONG).show();

            Intent intent = new Intent(this, AddTargetActivity.class);
            Bundle bundle=new Bundle();
            bundle.putSerializable("userItem", itemResult);
//            intent.putExtra("userItem", itemResult);
            intent.putExtras(bundle);

            // 呼叫「startActivityForResult」，第二個參數「1」表示執行修改
            startActivityForResult(intent, 0);
            return;
        }
        Toast.makeText(this, "出錯了", Toast.LENGTH_LONG).show();
    }

    public void signup(View view) {
        // 顯示訊息框，指定三個參數
        // Context：通常指定為「this」
        // String或int：設定顯示在訊息框裡面的訊息或文字資源
        // int：設定訊息框停留在畫面的時間
        Item signupItem = new Item();
        signupItem.setUserName(getUserNameEt());
        signupItem.setPassWord(getPassWordEt());
        itemDAO.insert(signupItem);
        Toast.makeText(this, "註冊成功", Toast.LENGTH_LONG).show();
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
                // TODO: Make sure this auto-generated app deep link URI is correct.
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
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.peter.myapplication/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Necessary to restore the BottomBar's state, otherwise we would
        // lose the current tab on orientation change.
        mBottomBar.onSaveInstanceState(outState);
    }
}
