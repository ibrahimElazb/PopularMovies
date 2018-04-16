package com.learn2develope.popularmovies.sync;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

import com.learn2develope.popularmovies.NetworkUtils.RetrofitNetworkUtils;

/**
 * Created by Ibrahim Elazb on 1/26/2018.
 */

public class MainDataSyncService extends IntentService {

    public MainDataSyncService()
    {
        super("MoviesIntentService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.d("xxxxxxxx","MainDataSyncService.onHandleIntent");
        RetrofitNetworkUtils.syncAllInformation(this);
    }
}
