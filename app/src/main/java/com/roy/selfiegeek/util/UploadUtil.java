package com.roy.selfiegeek.util;

import android.content.Context;
import com.roy.selfiegeek.asynctask.UploadTask;
import java.io.File;

/**
 * @author prabhat.roy.
 */

public class UploadUtil {

    /**
     * This method initiates the upload process of a file to dropbox
     * @param context The context from where the upload is initiated
     * @param fileName the filename of the file to be uploaded
     * @param message the message to be displayed once the file has been uploaded successfully
     */
    public static void uploadFile(Context context, String fileName, String message) {
        new UploadTask(DropboxClientUtil.getClient(SharedPrefsUtil.retrieveAccessToken(context)),
                new File(fileName), context, message).execute();
    }
}
