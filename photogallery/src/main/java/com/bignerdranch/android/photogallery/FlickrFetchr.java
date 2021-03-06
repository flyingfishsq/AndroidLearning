package com.bignerdranch.android.photogallery;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class FlickrFetchr {
    private static final String TAG = "FlickrFetchr";

    public static final String PREF_SEARCH_QUERY = "searchQuery";
    public static final String PREF_LAST_RESULT_ID = "lastResultId";

    private static final String FETCH_RECENTS_METHOD = "fetch_recents_method";
    private static final String SEARCH_METHOD = "search_method";
    private static final Uri ENDPOINT = Uri.parse("https://moment.douban.com/api/auth_authors/all").buildUpon()
            .appendQueryParameter("count", "20").build();

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

    public List<GalleryItem> fetchRecentPhotos(){
        String url = buildUrl(FETCH_RECENTS_METHOD, null);
        return downloadGalleryItems(url);
    }

    public List<GalleryItem> searchPhotos(String query){
        String url = buildUrl(SEARCH_METHOD, query);
        return downloadGalleryItems(url);
    }

    private List<GalleryItem> downloadGalleryItems(String url) {

        List<GalleryItem> items = new ArrayList<GalleryItem>();

        try {
//            String url = Uri.parse("https://moment.douban.com/api/auth_authors/all").buildUpon()
//                    .appendQueryParameter("count", "20")
//                    .appendQueryParameter("start", "20").build().toString();

            String jsonString = getUrl(url);
            Log.e(TAG, "Received JSON: " + jsonString);
            JSONObject jsonBody = new JSONObject(jsonString);
            parseItems(items, jsonBody);

        } catch (IOException ioe) {
            Log.e(TAG, "Failed to fetch items", ioe);
        } catch (JSONException je) {
            Log.e(TAG, "Failed to parse JSON", je);
        }

        return items;
    }

    private void parseItems(List<GalleryItem> items, JSONObject jsonBody)
            throws IOException, JSONException {
        JSONArray photoJsonArray = jsonBody.getJSONArray("authors");

        for (int i = 0; i < photoJsonArray.length(); i++) {
            JSONObject photoJsonObject = photoJsonArray.getJSONObject(i);
            GalleryItem item = new GalleryItem();
            item.setId(photoJsonObject.getString("id"));
            item.setCaption(photoJsonObject.getString("editor_notes"));

            if (!photoJsonObject.has("large_avatar")) {
                continue;
            }
            item.setUrl(photoJsonObject.getString("large_avatar"));
            items.add(item);
        }
    }

    private String buildUrl(String method, String query){
        Uri.Builder builder = null;
        if(!method.equals(SEARCH_METHOD)) {
            builder = ENDPOINT.buildUpon().appendQueryParameter("start", "20");
        }else{
            builder = ENDPOINT.buildUpon().appendQueryParameter("start", query);
        }

        return  builder.build().toString();
    }
}
