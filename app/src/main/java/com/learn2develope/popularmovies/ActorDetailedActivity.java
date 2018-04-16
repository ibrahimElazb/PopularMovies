package com.learn2develope.popularmovies;

import android.databinding.DataBindingUtil;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.learn2develope.popularmovies.adapters.ActorPagesAdapter;
import com.learn2develope.popularmovies.databinding.ActivityDetailedActorBinding;

public class ActorDetailedActivity extends AppCompatActivity {

    ActivityDetailedActorBinding activityBinding;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    int itemId;
    String detailsType;

    public static final String ARG_ACTOR_ID="actor_id";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityBinding = DataBindingUtil.setContentView(this,R.layout.activity_detailed_actor);
        itemId=getIntent().getIntExtra(MainActivity.SELECTED_ITEM_ID_KEY,0);
        detailsType=getIntent().getStringExtra(MainActivity.SELECTED_OPTION_KEY);
        fragmentManager=getSupportFragmentManager();
        fragmentTransaction=fragmentManager.beginTransaction();
        if (detailsType.equals(MainActivity.SHOW_DETAILS)){
            fragmentTransaction.add(R.id.detailed_fragment_container, ActorInfoDetailsFragment.newInstance(itemId));
        }else if (detailsType.equals(MainActivity.SHOW_WORKS)) {
            fragmentTransaction.add(R.id.detailed_fragment_container, ActorWorksFragment.newInstance(itemId));
        }else {
            activityBinding.vpMoviePages.setAdapter(new ActorPagesAdapter(fragmentManager,itemId));
            activityBinding.tabContainer.setVisibility(View.VISIBLE);
            activityBinding.movieDetailsTabs.setupWithViewPager(activityBinding.vpMoviePages);
        }

        fragmentTransaction.commit();
    }
}
