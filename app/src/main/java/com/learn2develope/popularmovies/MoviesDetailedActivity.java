package com.learn2develope.popularmovies;

import android.databinding.DataBindingUtil;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.learn2develope.popularmovies.adapters.MoviePagesAdapter;
import com.learn2develope.popularmovies.databinding.ActivityDetailedMoviesBinding;

public class MoviesDetailedActivity extends AppCompatActivity {

    ActivityDetailedMoviesBinding activityDetailedMoviesBinding;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    int itemId;
    String detailsType;

    public static final String ARG_MOVIE_ID="movie_id";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityDetailedMoviesBinding = DataBindingUtil.setContentView(this,R.layout.activity_detailed_movies);
        itemId=getIntent().getIntExtra(MainActivity.SELECTED_ITEM_ID_KEY,0);
        detailsType=getIntent().getStringExtra(MainActivity.SELECTED_OPTION_KEY);
        fragmentManager=getSupportFragmentManager();
        fragmentTransaction=fragmentManager.beginTransaction();
            if (detailsType.equals(MainActivity.SHOW_DETAILS)){
                fragmentTransaction.add(R.id.detailed_fragment_container, MovieInfoDetailsFragment.newInstance(itemId));
            }else if (detailsType.equals(MainActivity.SHOW_CAST)){
                fragmentTransaction.add(R.id.detailed_fragment_container,MovieCastFragment.newInstance(itemId));
            }else if (detailsType.equals(MainActivity.SHOW_REVIEWS)){
                fragmentTransaction.add(R.id.detailed_fragment_container,MovieReviewsFragment.newInstance(itemId));
            }else if (detailsType.equals(MainActivity.SHOW_VIDEOS)){
                fragmentTransaction.add(R.id.detailed_fragment_container,MovieVideosFragment.newInstance(itemId));
            }else {
                activityDetailedMoviesBinding.vpMoviePages.setAdapter(new MoviePagesAdapter(fragmentManager,itemId));
                activityDetailedMoviesBinding.tabContainer.setVisibility(View.VISIBLE);
                activityDetailedMoviesBinding.movieDetailsTabs.setupWithViewPager(activityDetailedMoviesBinding.vpMoviePages);
            }

        fragmentTransaction.commit();

    }

}
