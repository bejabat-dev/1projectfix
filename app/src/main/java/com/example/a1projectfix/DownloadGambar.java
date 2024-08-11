package com.example.a1projectfix;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

public class DownloadGambar extends AsyncTask<String, Void, Boolean> {

    private static final String TAG = "DownloadTask";
    private Context context;
    private DownloadCallback callback;

    public DownloadGambar(Context context) {
        this.context = context;
    }

    @Override
    protected Boolean doInBackground(String... strings) {
        String fileUrl = strings[0]; // URL of the file to download

        try {
            URL url = new URL(fileUrl);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setDoOutput(true);

            urlConnection.connect();

            File folder = context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS);
            if (folder == null) {
                Log.e(TAG, "External storage not mounted. Unable to download.");
                return false;
            }
            int r = new Random().nextInt(1000000);
            String name = r+".jpg";
            File file = new File(folder, name);

            FileOutputStream fileOutput = new FileOutputStream(file);
            InputStream inputStream = urlConnection.getInputStream();

            byte[] buffer = new byte[1024];
            int bufferLength;

            while ((bufferLength = inputStream.read(buffer)) > 0) {
                fileOutput.write(buffer, 0, bufferLength);
            }
            fileOutput.close();
            return true;
        } catch (Exception e) {
            Log.e(TAG, "Error downloading file: " + e.getMessage());
            return false;
        }
    }

    @Override
    protected void onPostExecute(Boolean success) {
        super.onPostExecute(success);
        if (callback != null) {
            callback.onDownloadComplete(success);
        }
    }

    public interface DownloadCallback {
        void onDownloadComplete(boolean success);
    }
}
