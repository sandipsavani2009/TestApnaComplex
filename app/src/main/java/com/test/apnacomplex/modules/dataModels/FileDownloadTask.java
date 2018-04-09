package com.test.apnacomplex.modules.dataModels;

import android.os.AsyncTask;
import android.util.Log;

import com.test.apnacomplex.utils.FileUtil;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by sc on 9/4/18.
 */

public class FileDownloadTask extends AsyncTask<Void, Void, Void> {

    private final String TAG = FileDownloadTask.class.getSimpleName();

    private FileDownloadTaskListener mTaskListener;
    private File mFileWithPath;
    private String mServerUrl;

    public FileDownloadTask(String serverUrl,
                            FileDownloadTaskListener fileDownloadTaskListener) {
        mTaskListener = fileDownloadTaskListener;
        mFileWithPath = new File(FileUtil.getDirPath(), FileUtil.getFileNameFromUrl(serverUrl));
        mServerUrl = serverUrl;
    }

    @Override
    protected Void doInBackground(Void... params) {
        try {
            URL url = new URL(mServerUrl);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setChunkedStreamingMode(10485760);  // 10MB chunk size
            urlConnection.connect();

            InputStream input = new BufferedInputStream(url.openStream());
            OutputStream output = new FileOutputStream(mFileWithPath);

            long fileContentTotalLength = urlConnection.getContentLength();

            byte buffer[] = new byte[1024];
            int bufferLength;
            long downloadLengthCompleted = 0;
            int lastPercent = 0;

            while ((bufferLength = input.read(buffer)) != -1) {
                downloadLengthCompleted += bufferLength;
                output.write(buffer, 0, bufferLength);
            }

            output.flush();
            output.close();
            input.close();
            urlConnection.disconnect();

            Log.d(TAG, "Download Successful : " + mFileWithPath.getPath());
        } catch (Exception ex) {
            Log.e(TAG, "Error while downloading Files");

            if (mFileWithPath.exists()) {
                mFileWithPath.delete();
            }
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        if (mTaskListener != null) {
            mTaskListener.onDownloadSuccess();
        }
    }
}
