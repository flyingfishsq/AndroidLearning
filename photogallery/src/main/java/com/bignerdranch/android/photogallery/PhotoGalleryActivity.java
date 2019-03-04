package com.bignerdranch.android.photogallery;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;

public class PhotoGalleryActivity extends SingleFragmentActivity {
    private static final String TAG = PhotoGalleryActivity.class.getSimpleName();

    @Override
    protected Fragment createFragment() {
        return PhotoGalleryFragment.newInstance();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        PhotoGalleryFragment fragment = (PhotoGalleryFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentContainer);

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            Log.e(TAG, "Received a new search query: " + query);

            PreferenceManager.getDefaultSharedPreferences(this)
                    .edit().putString(FlickrFetchr.PREF_SEARCH_QUERY, query).commit();
        }
        fragment.updateItems();

    }
}
