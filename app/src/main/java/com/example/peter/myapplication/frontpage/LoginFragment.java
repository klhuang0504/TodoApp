package com.example.peter.myapplication.frontpage;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.peter.myapplication.BackHandledFragment;
import com.example.peter.myapplication.R;
import com.example.peter.myapplication.data.TargetDAO;
import com.example.peter.myapplication.data.TargetEntity;
import com.example.peter.myapplication.data.UserDAO;
import com.example.peter.myapplication.data.UserEntity;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

/**
 * Created by peter on 2016/3/29.
 */
public class LoginFragment extends BackHandledFragment {

    private LoginButton facebookLoginButton;
    private CallbackManager callbackManager;
    private AccessToken accessToken;
    private EditText userNameEditText;


    @Override
    public String getTagText() {
        return null;
    }

    @Override
    public boolean onBackPressed() {
        return true;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(getActivity());

        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        AccessToken accessToken = loginResult.getAccessToken();
                        Profile profile = Profile.getCurrentProfile();

                        // Facebook Email address
                        GraphRequest request = GraphRequest.newMeRequest(
                                loginResult.getAccessToken(),
                                new GraphRequest.GraphJSONObjectCallback() {
                                    @Override
                                    public void onCompleted(
                                            JSONObject object,
                                            GraphResponse response) {
                                        Log.v("LoginActivity Response ", response.toString());

                                        try {
//                                    Name = object.getString("name");
//
//                                    FEmail = object.getString("email");
//                                    Log.v("Email = ", " " + FEmail);
                                            Toast.makeText(LoginFragment.this.getActivity(), object.getString("name"), Toast.LENGTH_LONG).show();


                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                        Bundle parameters = new Bundle();
                        parameters.putString("fields", "id,name,email,gender, birthday");
                        request.setParameters(parameters);
                        request.executeAsync();
                    }

                    @Override
                    public void onCancel() {
                        // App code
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                    }
                });


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.log_in_layout, container,
                false);

        userNameEditText = (EditText) view.findViewById(R.id.userNameEt);

        facebookLoginButton = (LoginButton) view.findViewById(R.id.login_button);
        facebookLoginButton.setReadPermissions(Arrays.asList(
                "public_profile", "email", "user_birthday", "user_friends"));

        callbackManager = CallbackManager.Factory.create();

        // If using in a fragment
        facebookLoginButton.setFragment(this);
        // Other app specific specialization

        // Callback registration
        facebookLoginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                AccessToken accessToken = loginResult.getAccessToken();
                Profile profile = Profile.getCurrentProfile();

                // Facebook Email address
                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(
                                    JSONObject object,
                                    GraphResponse response) {
                                Log.v("LoginActivity Response ", response.toString());

                                try {
//                                    Name = object.getString("name");
//
//                                    FEmail = object.getString("email");
//                                    Log.v("Email = ", " " + FEmail);
                                    Toast.makeText(LoginFragment.this.getActivity(), object.getString("name"), Toast.LENGTH_LONG).show();


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,gender, birthday");
                request.setParameters(parameters);
                request.executeAsync();

            }

            @Override
            public void onCancel() {
                // App code
                Log.d("FB", "CANCEL");
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
                Log.d("FB", exception.toString());
            }
        });

        return view;
    }

}
