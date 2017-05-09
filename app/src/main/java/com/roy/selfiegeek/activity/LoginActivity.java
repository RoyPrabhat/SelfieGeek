package com.roy.selfiegeek.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.dropbox.core.android.Auth;
import com.roy.selfiegeek.R;
import com.roy.selfiegeek.util.SharedPrefsUtil;

/**
 * @author prabhat.roy.
 */

public class LoginActivity extends AppCompatActivity {

    private Button mSignInButton;
    public static final String TAG = LoginActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initViews();
    }

    /**
     * This method takes care of setting up of views like adding click listeners etc. to views
     */
    private void initViews() {
        mSignInButton = (Button) findViewById(R.id.sign_in_button);
        mSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Auth.startOAuth2Authentication(LoginActivity.this, getString(R.string.APP_KEY));
            }
        });
    }

    /**
     * This is a lifecycle method of the activity which is called once the activity is in the foreground
     * This helps in releasing media recorder and camera instance
     */
    @Override
    protected void onResume() {
        super.onResume();
        getAccessToken();
    }

    /**
     * This method is used to get the access token and save it into shared preferences for persistent usage
     */
    public void getAccessToken() {
        String accessToken = Auth.getOAuth2Token(); //generate Access Token
        if (accessToken != null) {
            //Store accessToken in SharedPreferences
            SharedPrefsUtil.saveAccessToken(LoginActivity.this, accessToken);
            //Proceed to MainActivity
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }
}