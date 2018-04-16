package com.learn2develope.popularmovies.adapters;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.learn2develope.popularmovies.MovieCastFragment;
import com.learn2develope.popularmovies.MovieInfoDetailsFragment;
import com.learn2develope.popularmovies.MovieReviewsFragment;
import com.learn2develope.popularmovies.MovieVideosFragment;

/**
 * Created by Ibrahim Elazb on 1/1/2018.
 */

public class MoviePagesAdapter extends FragmentStatePagerAdapter {

    int movieId;

    public MoviePagesAdapter(FragmentManager fragmentManager,int movieId){
        super(fragmentManager);
        this.movieId=movieId;
    }

    @Override
    public Fragment getItem(int position) {
        if (position==0){
           return MovieInfoDetailsFragment.newInstance(movieId);
        }
        else if (position==1){
            return MovieCastFragment.newInstance(movieId);
        }
        else if (position==2){
            return MovieReviewsFragment.newInstance(movieId);
        }
        else if (position==3){
            return MovieVideosFragment.newInstance(movieId);
        }
        return null;
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if (position==0){
            return "Details";
        }
        else if (position==1){
            return "Cast";
        }
        else if (position==2){
            return "Reviews";
        }
        else if (position==3){
            return "Videos";
        }

        return super.getPageTitle(position);
    }
}
