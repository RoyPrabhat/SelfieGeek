package com.roy.selfiegeek.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import com.roy.selfiegeek.constants.Constants;

/**
 * @author prabhat.roy.
 */

public class SharedPrefsUtil {

    public static final String TAG = SharedPrefsUtil.class.getSimpleName();
    /**
     * This method returns the Shared Preferences attached to The application
     *
     * @param context A context is required to access the shared preferences in an application
     * @return returns the shared preferences
     */
    private static SharedPreferences getSettings(Context context) {
        return context.getSharedPreferences(Constants.SELFIE_GEEK, Context.MODE_PRIVATE);
    }

    /**
     * This method retrieves the access token attached with the current dropbox session
     * @param context the context from where the session is being stored
     * @return The access token attached with the current session is retrieved
     */
    public static String retrieveAccessToken(Context context) {
        //check if ACCESS_TOKEN is previously stored on previous app launches
        SharedPreferences prefs = getSettings(context);
        String accessToken = prefs.getString(Constants.ACCESS_TOKEN, null);
        if (accessToken == null) {
            Log.d(TAG, "AccessToken Status : No token found");
            return null;
        } else {
            //accessToken already exists
            Log.d(TAG, "AccessToken Status: Token exists");
            return accessToken;
        }
    }

    /**
     * This method saves the access token attached with the current dropbox session
     * @param context The context from where the access token is saved
     * @param accessToken The access token to be stored
     */
    public static void saveAccessToken(Context context, String accessToken) {
        //check if ACCESS_TOKEN is previously stored on previous app launches
        SharedPreferences prefs = getSettings(context);
        prefs.edit().putString(Constants.ACCESS_TOKEN, accessToken).apply();
    }

    /**
     * This method checks whether an access toke exists or not
     * @param context The context that checks for the existence of the access token
     * @return returns true or false depending on the presence of the access token
     */
    public static boolean tokenExists(Context context) {
        SharedPreferences prefs = getSettings(context);
        String accessToken = prefs.getString(Constants.ACCESS_TOKEN, null);
        return accessToken != null;
    }
}
