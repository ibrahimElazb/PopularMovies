package com.learn2develope.popularmovies.sync;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;

import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;

import com.firebase.jobdispatcher.Driver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.Trigger;
import com.learn2develope.popularmovies.database.MovieDatabaseOperations;

import java.util.concurrent.TimeUnit;

/**
 * Created by Ibrahim Elazb on 1/26/2018.
 */

public class MainSyncUtil {

    private static final int SYNC_INTERVAL_HOURS = 3;
    private static final int SYNC_INTERVAL_SECONDS = (int) TimeUnit.HOURS.toSeconds(SYNC_INTERVAL_HOURS);
    private static final int SYNC_FLEXTIME_SECONDS = SYNC_INTERVAL_SECONDS / 3;

    private static boolean sInitialized;

    static void scheduleFirebaseJobDispatcherSync(@NonNull final Context context) {

        Driver driver = new GooglePlayDriver(context);
        FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(driver);
        Job syncMoviesTvActorsJob = dispatcher.newJobBuilder()
                .setService(MainFirebaseJobService.class)
                .setTag("Movies_APP_SYNC_TAG")
                .setConstraints(Constraint.ON_ANY_NETWORK)
                .setLifetime(Lifetime.FOREVER)
                .setRecurring(true)
                .setTrigger(Trigger.executionWindow(
                SYNC_INTERVAL_SECONDS,
                SYNC_INTERVAL_SECONDS + SYNC_FLEXTIME_SECONDS))
                .setReplaceCurrent(true)
                .build();

        dispatcher.schedule(syncMoviesTvActorsJob);
    }

    synchronized public static void initialize(@NonNull final Context context) {
        if (sInitialized) return;

        sInitialized = true;
        scheduleFirebaseJobDispatcherSync(context);
        Thread checkForEmpty = new Thread(new Runnable() {
            @Override
            public void run() {
                if (MovieDatabaseOperations.isDatabaseEmpty(context))
                    startImmediateSync(context);

            }
        });
        checkForEmpty.start();
    }


    public static void startImmediateSync(@NonNull final Context context) {
        Intent intentToSyncImmediately = new Intent(context, MainDataSyncService.class);
        context.startService(intentToSyncImmediately);
    }

}
