package com.roy.selfiegeek.util;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;

/**
 * @author prabhat.roy
 */
public class APISupportUtil {

    /**
     * Te following method checks for the existence of multiple permissions at the same time
     * @param context The context from where the check is initiated
     * @param permissions the list of permissions to be checked
     * @return A true or false besed on the presence of these permissions
     */
    public static boolean hasPermissions(Context context, String... permissions) {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * The following function requests multiple permissions at the same time
     * @param activity The activity form where the permisiion is being requested
     * @param permissions The List pf permissions to be requested
     * @param permission_all The request used for permission requests
     */
    public static void getPermission(Activity activity, String[] permissions, int permission_all) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ActivityCompat.requestPermissions(activity, permissions, permission_all);;
        }
    }

}
