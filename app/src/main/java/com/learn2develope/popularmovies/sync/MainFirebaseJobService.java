package com.learn2develope.popularmovies.sync;


import android.os.AsyncTask;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;
import com.learn2develope.popularmovies.NetworkUtils.RetrofitNetworkUtils;

/**
 * Created by Ibrahim Elazb on 1/26/2018.
 */

public class MainFirebaseJobService extends JobService {

    private AsyncTask<Void, Void, Void> task;

    @Override
    public boolean onStartJob(final JobParameters job) {
        task=new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                RetrofitNetworkUtils.syncAllInformation(getApplicationContext());
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                jobFinished(job,false);
            }
        }.execute();
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters job) {
        if (task!=null){
            task.cancel(true);
        }
        return true;
    }
}
