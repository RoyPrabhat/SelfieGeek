package com.roy.selfiegeek.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.dropbox.core.v2.users.FullAccount;
import com.roy.selfiegeek.R;
import com.roy.selfiegeek.asynctask.UserAccountTask;
import com.roy.selfiegeek.util.DropboxClientUtil;
import com.roy.selfiegeek.util.SharedPrefsUtil;
import com.roy.selfiegeek.util.ViewUtil;

/**
 * @author prabhat.roy.
 */

public class MainActivity extends AppCompatActivity {

    private String ACCESS_TOKEN;
    private ImageView mProfileImageView;
    private TextView mNameView;
    private TextView mEmailView;
    private Button mClickAndSaveButton;
    private Button mViewUploadedButton;

    public static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkForAccessToken();
        getUserAccount();
        initViews();
    }

    /**
     * This method takes care of setting up of views like adding click listeners etc. to views
     */
    private void initViews() {
        mProfileImageView = (ImageView) findViewById(R.id.imageView);
        mNameView = (TextView) findViewById(R.id.name_textView);
        mEmailView = (TextView) findViewById(R.id.email_textView);
        setUpClickAndSaveButton();
        setUpViewUploadedButton();
    }

    /**
     * This method is used for checking the presence of access token in shared reference
     * and takes necessary acction accordingly
     */
    private void checkForAccessToken() {
        if (!SharedPrefsUtil.tokenExists(MainActivity.this)) {
            //No token
            //Back to LoginActivity
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        } else ACCESS_TOKEN = SharedPrefsUtil.retrieveAccessToken(MainActivity.this);
    }

    /**
     * This method is to setup listeners for button that starts uploaded activity
     */
    private void setUpViewUploadedButton() {
        mViewUploadedButton = (Button) findViewById(R.id.view_uploaded_items);
        mViewUploadedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, UploadedActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * This method is to setup listeners for button that stats camera activity
     */
    private void setUpClickAndSaveButton() {
        mClickAndSaveButton = (Button) findViewById(R.id.click_and_save);
        mClickAndSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CameraActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * This methods gets us the information about user account details and updates the ui accordingly
     */
    /**
     * This methods gets us the information about user account details and updates the ui accordingly
     */
    protected void getUserAccount() {
        if (ACCESS_TOKEN == null) return;
        new UserAccountTask(DropboxClientUtil.getClient(ACCESS_TOKEN), new UserAccountTask.TaskDelegate() {
            @Override
            public void onAccountReceived(FullAccount account) {
                ViewUtil.updateUI(MainActivity.this, account, mProfileImageView, mNameView, mEmailView);
            }

            @Override
            public void onError(Exception error) {
                Log.d(TAG, "Error receiving account details.");
            }
        }).execute();
    }
}
