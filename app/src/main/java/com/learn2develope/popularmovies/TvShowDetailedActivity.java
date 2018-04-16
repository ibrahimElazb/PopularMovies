package com.learn2develope.popularmovies;

import android.databinding.DataBindingUtil;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.learn2develope.popularmovies.adapters.TvShowPagesAdapter;
import com.learn2develope.popularmovies.databinding.ActivityDetailedTvShowBinding;

public class TvShowDetailedActivity extends AppCompatActivity {

    ActivityDetailedTvShowBinding activityDetailedTvShowBinding;

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    int itemId;
    String detailsType;

    public static final String TV_SHOW_ID="tv_show_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityDetailedTvShowBinding= DataBindingUtil.setContentView(this,R.layout.activity_detailed_tv_show);
        itemId=getIntent().getIntExtra(MainActivity.SELECTED_ITEM_ID_KEY,0);
        detailsType=getIntent().getStringExtra(MainActivity.SELECTED_OPTION_KEY);
        fragmentManager=getSupportFragmentManager();
        fragmentTransaction=fragmentManager.beginTransaction();
            if (detailsType.equals(MainActivity.SHOW_DETAILS)){
                fragmentTransaction.add(R.id.detailed_tv_fragment_container, TvShowInfoDetailsFragment.newInstance(itemId));
            }else if (detailsType.equals(MainActivity.SHOW_CAST)){
                fragmentTransaction.add(R.id.detailed_tv_fragment_container, TvShowCastFragment.newInstance(itemId));
            }else {
                activityDetailedTvShowBinding.tabContainer.setVisibility(View.VISIBLE);
                activityDetailedTvShowBinding.vpTvShowPages.setAdapter(new TvShowPagesAdapter(fragmentManager,itemId));
                activityDetailedTvShowBinding.tvShowDetailsTabs.setupWithViewPager(activityDetailedTvShowBinding.vpTvShowPages);
            }

        fragmentTransaction.commit();
    }
}
