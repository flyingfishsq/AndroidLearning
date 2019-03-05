package com.bignerdranch.android.photogallery;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class PollService extends IntentService {
    private static final String TAG = PollService.class.getSimpleName();

    private static final int POLL_INTERVAL = 1000 * 15;

    public PollService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        //检查后台网络的可用性
        ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

        boolean isNetworkAvailable = cm.getBackgroundDataSetting() && cm.getActiveNetworkInfo() != null;
        if(!isNetworkAvailable)
            return;

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String query = prefs.getString(FlickrFetchr.PREF_SEARCH_QUERY, null);
        String lastResultId = prefs.getString(FlickrFetchr.PREF_LAST_RESULT_ID, null);

        List<GalleryItem> items;
        if(query !=null){
            items = new FlickrFetchr().searchPhotos(query);
        }else{
            items = new FlickrFetchr().fetchRecentPhotos();
        }

        if(items.size() == 0)
            return;

        String resultId = items.get(0).getId();

        if(!resultId.equals(lastResultId)){
            Log.i(TAG, "Got a new result: " + resultId);
        }else{
            Log.i(TAG, "Got an old result: " + resultId);
        }

        prefs.edit().putString(FlickrFetchr.PREF_LAST_RESULT_ID, resultId).commit();
    }

    public static void setServiceAlarm(Context context, boolean isOn){
        Intent intent = new Intent(context, PollService.class);
        PendingIntent pendingIntent = PendingIntent.getService(context, 0, intent, 0);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        if(isOn){
            alarmManager.setRepeating(AlarmManager.RTC, System.currentTimeMillis(), POLL_INTERVAL, pendingIntent);
        }else{
            alarmManager.cancel(pendingIntent);
            pendingIntent.cancel();
        }
    }
}
