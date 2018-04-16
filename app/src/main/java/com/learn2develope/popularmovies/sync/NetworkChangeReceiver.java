package com.learn2develope.popularmovies.sync;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.learn2develope.popularmovies.MainActivity;
import com.learn2develope.popularmovies.NetworkUtils.NetworkConnectivity;

/**
 * Created by Ibrahim Elazb on 1/26/2018.
 */

public class NetworkChangeReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
       if(NetworkConnectivity.isNetworkConnectivityAvailable(context.getApplicationContext())) {
           if (context instanceof MainActivity)
               ((MainActivity)context).refreshData();
       }else{
           Toast.makeText(context,"No Internet available",Toast.LENGTH_SHORT).show();
       }
    }
}
