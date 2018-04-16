package com.learn2develope.popularmovies.adapters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import com.learn2develope.popularmovies.MainListFragment;
import com.learn2develope.popularmovies.NetworkUtils.RetrofitNetworkUtils;

/**
 * Created by Ibrahim Elazb on 12/24/2017.
 */

public class MainListPageAdapter extends FragmentStatePagerAdapter {

    int mTotalPages;
    Bundle mBundle;

    public MainListPageAdapter(FragmentManager fm, Bundle bundle, int totalPages) {
        super(fm);
        mBundle=bundle;
        mTotalPages=totalPages;
    }

    @Override
    public Fragment getItem(int position) {
        //mBundle.putInt(RetrofitNetworkUtils.PAGE_NUMBER, position+1);
        String category=mBundle.getString(RetrofitNetworkUtils.LIST_MAIN_CATEGORY);
        String subCategory=mBundle.getString(RetrofitNetworkUtils.LIST_SUBCATEGORY);
        int pageNumber=position+1;
        return MainListFragment.newInstance(category,subCategory,pageNumber);
    }

    @Override
    public int getCount() {
       // Log.d(MainListPageAdapter.class.getName(),""+ pagesCount);
        return mTotalPages;
    }

}
