package com.bignerdranch.android.photogallery;

import android.net.Uri;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class FlickrFetchr {
    private static final String TAG = "FlickrFetchr";

    byte[] getUrlBytes(String urlSpec) throws IOException {
        URL url = new URL(urlSpec);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            InputStream in = connection.getInputStream();

            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                return null;
            }

            int bytesRead = 0;
            byte[] buffer = new byte[1024];
            while ((bytesRead = in.read(buffer)) > 0) {
                out.write(buffer, 0, bytesRead);
            }
            out.close();
            return out.toByteArray();
        } finally {
            connection.disconnect();
        }
    }

    public String getUrl(String urlSpec) throws IOException {
        return new String(getUrlBytes(urlSpec));
    }

    public void fetchItems(){
        try {
            String url = Uri.parse("https://moment.douban.com/api/auth_authors/all").buildUpon()
                    .appendQueryParameter("count", "20")
                    .appendQueryParameter("start", "20").build().toString();
            String jsonString = getUrl(url);
            Log.e(TAG, "Received JSON: " + jsonString);
        }catch (IOException ex){
            Log.e(TAG, "Failed to fetch items", ex);
        }
    }
}
