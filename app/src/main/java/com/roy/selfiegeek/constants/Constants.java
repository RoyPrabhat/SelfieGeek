package com.roy.selfiegeek.constants;

import android.Manifest;

/**
 *This class is used to store constants
 *
 * @author prabhat.roy.
 */

public class Constants {

    // CameraActivity constants
    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;

    // SharedPreferences constants
    public static final String SELFIE_GEEK  = "SELFIE_GEEK";
    public static final String ACCESS_TOKEN  = "ACCESS_TOKEN";

    // permission constants
    public static final String[] PERMISSIONS = {Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE};
    public static final int PERMISSION_ALL = 1;


}
