package com.learn2develope.popularmovies;

import android.databinding.DataBindingUtil;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.learn2develope.popularmovies.adapters.MoviePagesAdapter;
import com.learn2develope.popularmovies.databinding.ActivityDetailsBinding;
import com.learn2develope.popularmovies.retrofitUtils.RetrofitNetworkUtils;

public class DetailsActivity extends AppCompatActivity {

    ActivityDetailsBinding detailsBinding;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    int itemId;
    String detailsType;
    String category;

    public static final String ARG_MOVIE_ID="movie_id";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        detailsBinding= DataBindingUtil.setContentView(this,R.layout.activity_details);
        itemId=getIntent().getIntExtra(MainActivity.SELECTED_ITEM_ID_KEY,0);
        detailsType=getIntent().getStringExtra(MainActivity.SELECTED_TYPE_KEY);
        category=getIntent().getStringExtra(MainActivity.CURRENT_MAINCATEGORY_KEY);
        fragmentManager=getSupportFragmentManager();
        fragmentTransaction=fragmentManager.beginTransaction();
        if (category.equals(RetrofitNetworkUtils.MOVIES_CATEGORY)){
            if (detailsType.equals(MainActivity.SHOW_DETAILS)){
                fragmentTransaction.add(R.id.detailed_fragment_container,MovieDetailedFragment.newInstance(itemId));
            }else if (detailsType.equals(MainActivity.SHOW_CAST)){
                fragmentTransaction.add(R.id.detailed_fragment_container,MovieCastFragment.newInstance(itemId));
            }else if (detailsType.equals(MainActivity.SHOW_REVIEWS)){
                fragmentTransaction.add(R.id.detailed_fragment_container,MovieReviewsFragment.newInstance(itemId));
            }else if (detailsType.equals(MainActivity.SHOW_VIDEOS)){
                fragmentTransaction.add(R.id.detailed_fragment_container,MovieVideosFragment.newInstance(itemId));
            }else {
                detailsBinding.vpMoviePages.setAdapter(new MoviePagesAdapter(fragmentManager,itemId));
                detailsBinding.vpMoviePages.setVisibility(View.VISIBLE);
                detailsBinding.movieDetailsTabs.setupWithViewPager(detailsBinding.vpMoviePages);
            }
        }

        fragmentTransaction.commit();

    }

}
