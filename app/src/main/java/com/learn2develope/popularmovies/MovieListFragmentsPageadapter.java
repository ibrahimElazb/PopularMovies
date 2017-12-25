package com.learn2develope.popularmovies;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import com.learn2develope.popularmovies.retrofitUtils.RetrofitNetworkUtils;

/**
 * Created by Ibrahim Elazb on 12/24/2017.
 */

public class MovieListFragmentsPageadapter extends FragmentStatePagerAdapter {

    Context mContext;
    int pagesCount =1;
    Bundle mBundle;
    public MovieListFragmentsPageadapter(Context context, FragmentManager fm, Bundle bundle) {
        super(fm);
        mContext=context;
        mBundle=bundle;
    }

    @Override
    public Fragment getItem(int position) {
        mBundle.putInt(RetrofitNetworkUtils.PAGE_NUMBER, position+1);
        return MoviesListFragment.newInstance(mContext, mBundle);
    }

    @Override
    public int getCount() {
        Log.d(MovieListFragmentsPageadapter.class.getName(),""+ pagesCount);
        return pagesCount;
    }

    public void numberOfPagesUpdate(int pages){
        if(pagesCount ==1) {
            pagesCount = pages;
            notifyDataSetChanged();
        }
    }

}
