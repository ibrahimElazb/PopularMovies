package com.learn2develope.popularmovies.adapters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.learn2develope.popularmovies.NetworkUtils.RetrofitNetworkUtils;
import com.learn2develope.popularmovies.SearchResultFragment;

/**
 * Created by Ibrahim Elazb on 2/25/2018.
 */

public class SearchFragmentListAdapter extends FragmentStatePagerAdapter {

    int mTotalPages;
    String mQuery;
    String mCategory;

    public SearchFragmentListAdapter(FragmentManager fm, String query,String category, int totalPages){
        super(fm);
        mQuery=query;
        mCategory=category;
        mTotalPages=totalPages;
    }

    @Override
    public Fragment getItem(int position) {
        return SearchResultFragment.newInstance(mQuery,mCategory,position+1);
    }

    @Override
    public int getCount() {
        return mTotalPages;
    }
}
