package com.learn2develope.popularmovies;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.learn2develope.popularmovies.adapters.FavoriteListAdapter;
import com.learn2develope.popularmovies.database.DatabaseRepo;
import com.learn2develope.popularmovies.databinding.ActivityFavoritesBinding;

import java.util.ArrayList;
import java.util.List;

public class FavoritesActivity extends AppCompatActivity {

    ActivityFavoritesBinding activityFavoritesBinding;
    List favoriteList;

    public static final String FAVORITE_MOVIE_TYPE="favoritemovie";
    public static final String FAVORITE_TV_TYPE="favoritetvshow";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityFavoritesBinding= DataBindingUtil.setContentView(this,R.layout.activity_favorites);
        favoriteList=new ArrayList();
        favoriteList.addAll(new DatabaseRepo(this).findByType(FAVORITE_MOVIE_TYPE));
        favoriteList.addAll(new DatabaseRepo(this).findByType(FAVORITE_TV_TYPE));
        if (favoriteList.size()==0){
            activityFavoritesBinding.emptyList.setVisibility(View.VISIBLE);
            activityFavoritesBinding.favoriteList.setVisibility(View.INVISIBLE);
        }else {
            FavoriteListAdapter adapter = new FavoriteListAdapter(favoriteList);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            activityFavoritesBinding.favoriteList.setLayoutManager(linearLayoutManager);
            activityFavoritesBinding.favoriteList.setAdapter(adapter);
        }
    }
}
