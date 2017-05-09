package com.roy.selfiegeek.util;

import android.content.Context;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.dropbox.core.v2.users.FullAccount;
import com.squareup.picasso.Picasso;

/**
 * @author prabhat.roy.
 */

public class ViewUtil {
    public static void setButtonText(Button button, String text) {
        button.setText(text);
    }

    /**
     * The following method is to update ui after getting the user information from dropbox
     * @param context context from where the ui is to be updated
     * @param account ser account details
     * @param mProfileImageView user profile image
     * @param mNameView view to display name of the user
     * @param mEmailView view to display email of the user
     */
    public static void updateUI(Context context, FullAccount account, ImageView mProfileImageView, TextView mNameView, TextView mEmailView) {
        mNameView.setText(account.getName().getDisplayName());
        mEmailView.setText(account.getEmail());
        Picasso.with(context)
                .load(account.getProfilePhotoUrl())
                .resize(200, 200)
                .into(mProfileImageView);

    }
}
