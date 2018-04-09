package com.test.apnacomplex.utils;

import android.os.Environment;

/**
 * Created by sc on 8/4/18.
 */

public class FileUtil {

    /**
     * Gives filename extracted from url
     * @param url
     * @return
     */
    public static String getFileNameFromUrl(String url) {
        String name = null;
        int start;
        start = url.lastIndexOf('/');
        name = url.substring((start + 1), url.length());
        return name;
    }

    /**
     *
     * @return - public download directory
     */
    public static String getDirPath() {
        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath();
    }
}
