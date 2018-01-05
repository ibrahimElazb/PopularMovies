package com.learn2develope.popularmovies;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import com.learn2develope.popularmovies.retrofitUtils.RetrofitNetworkUtils;

/**
 * Created by Ibrahim Elazb on 12/24/2017.
 */

public class MainListPageadapter extends FragmentStatePagerAdapter {

    int mTotalPages;
    Bundle mBundle;

    public MainListPageadapter(FragmentManager fm, Bundle bundle, int totalPages) {
        super(fm);
        mBundle=bundle;
        mTotalPages=totalPages;
    }

    @Override
    public Fragment getItem(int position) {
        mBundle.putInt(RetrofitNetworkUtils.PAGE_NUMBER, position+1);
        Log.d(MainListPageadapter.class.getName(),position+"");
        String category=mBundle.getString(RetrofitNetworkUtils.LIST_MAIN_CATEGORY);
        String subCategory=mBundle.getString(RetrofitNetworkUtils.LIST_SUBCATEGORY);
        int pageNumber=mBundle.getInt(RetrofitNetworkUtils.PAGE_NUMBER);
        return MainListFragment.newInstance(category,subCategory,pageNumber);
    }

    @Override
    public int getCount() {
       // Log.d(MainListPageadapter.class.getName(),""+ pagesCount);
        return mTotalPages;
    }

}
