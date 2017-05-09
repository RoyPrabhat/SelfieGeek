package com.roy.selfiegeek.util;

import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;

/**
 * @author prabhat.roy.
 */

public class DropboxClientUtil {

    /**
     * This method helps in getting dropbox client information
     * @param ACCESS_TOKEN this is the access token for storing the current session information
     * @return
     */
    public static DbxClientV2 getClient(String ACCESS_TOKEN) {
        // Create Dropbox client
        DbxRequestConfig config = new DbxRequestConfig("dropbox/sample-app", "en_US");
        DbxClientV2 client = new DbxClientV2(config, ACCESS_TOKEN);
        return client;
    }
}
