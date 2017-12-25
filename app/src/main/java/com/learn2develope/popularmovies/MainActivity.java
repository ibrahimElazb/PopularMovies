package com.learn2develope.popularmovies;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.databinding.DataBindingUtil;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;


import com.learn2develope.popularmovies.databinding.ActivityMainBinding;
import com.learn2develope.popularmovies.retrofitUtils.RetrofitNetworkUtils;

public class MainActivity extends AppCompatActivity implements MoviesListFragment.onChangingNumberOfPages {

    public static final int MOVIES_CATEGORY = 0;
    public static final int TV_CATEGORY = 1;
    public static final int ACTORS_CATEGORY = 2;


    ActivityMainBinding mainBinding;
    FragmentManager fragmentManager;
    MovieListFragmentsPageadapter pageAdapter;
    ActionBarDrawerToggle navigationDrawerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        navigationDrawerButton = new ActionBarDrawerToggle
                (this, mainBinding.drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mainBinding.drawerLayout.addDrawerListener(navigationDrawerButton);
        navigationDrawerButton.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        fragmentManager = getSupportFragmentManager();
        nowPlayingMoviesChosen();
        getSupportActionBar().setTitle(getString(R.string.now_playing_movies));
        if (savedInstanceState != null) {
            pageAdapter.restoreState(savedInstanceState.getParcelable("savedstate"), getClassLoader());
            // Toast.makeText(this, "in restoring state", Toast.LENGTH_SHORT).show();
        }
        mainBinding.vpShowPages.setAdapter(pageAdapter);
        mainBinding.nvCategorySelect.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                getSupportActionBar().setTitle(item.getTitle());
                switch (item.getItemId()) {
                    case R.id.now_playing_movies_menu_item:
                        nowPlayingMoviesChosen();
                        break;
                    case R.id.popular_movies_menu_item:
                        popularMoviesChosen();
                        break;
                    case R.id.upcoming_movies_menu_item:
                        upcomingMoviesChosen();
                        break;
                    case R.id.top_rated_movies_menu_item:
                        topratedMoviesChosen();
                        break;
                    case R.id.airing_today_tv_menu_item:
                        airingTodayTvChosen();
                        break;
                    case R.id.on_the_air_tv_menu_item:
                        onTheAirTvChosen();
                        break;
                    case R.id.popular_tv_menu_item:
                        popularTvChosen();
                        break;
                    case R.id.top_rated_tv_menu_item:
                        topRatedTvChosen();
                        break;
                    case R.id.popular_actors_menu_id:
                        popularActorsChosen();
                        break;
                }
                return false;
            }
        });
    }

    @Override
    public void numberOfPagesChanged(int numberOfPages) {
        pageAdapter.numberOfPagesUpdate(numberOfPages);
        //Toast.makeText(this,numberOfPages+"",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("savedstate", pageAdapter.saveState());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (navigationDrawerButton.onOptionsItemSelected(item)) return true;
        return super.onOptionsItemSelected(item);
    }

    public boolean nowPlayingMoviesChosen() {
        Bundle selectionBundle = new Bundle();
        selectionBundle.putInt(RetrofitNetworkUtils.LIST_MAIN_CATEGORY, MainActivity.MOVIES_CATEGORY);
        selectionBundle.putString(RetrofitNetworkUtils.LIST_SUBCATEGORY, "now_playing");
        selectionBundle.putString(RetrofitNetworkUtils.API_KEY, "9b2e111f87ebfd12b22a44d8d7010338");
        selectionBundle.putString(RetrofitNetworkUtils.LANGUAGE, "en-US");
        pageAdapter = new MovieListFragmentsPageadapter(MainActivity.this, fragmentManager, selectionBundle);
        mainBinding.vpShowPages.setAdapter(pageAdapter);
        mainBinding.drawerLayout.closeDrawers();
        return true;
    }

    public boolean popularMoviesChosen() {
        Bundle selectionBundle = new Bundle();
        selectionBundle.putInt(RetrofitNetworkUtils.LIST_MAIN_CATEGORY, MainActivity.MOVIES_CATEGORY);
        selectionBundle.putString(RetrofitNetworkUtils.LIST_SUBCATEGORY, "popular");
        selectionBundle.putString(RetrofitNetworkUtils.API_KEY, "9b2e111f87ebfd12b22a44d8d7010338");
        selectionBundle.putString(RetrofitNetworkUtils.LANGUAGE, "en-US");
        pageAdapter = new MovieListFragmentsPageadapter(MainActivity.this, fragmentManager, selectionBundle);
        mainBinding.vpShowPages.setAdapter(pageAdapter);
        mainBinding.drawerLayout.closeDrawers();
        return true;
    }

    public boolean upcomingMoviesChosen() {
        Bundle selectionBundle = new Bundle();
        selectionBundle.putInt(RetrofitNetworkUtils.LIST_MAIN_CATEGORY, MainActivity.MOVIES_CATEGORY);
        selectionBundle.putString(RetrofitNetworkUtils.LIST_SUBCATEGORY, "upcoming");
        selectionBundle.putString(RetrofitNetworkUtils.API_KEY, "9b2e111f87ebfd12b22a44d8d7010338");
        selectionBundle.putString(RetrofitNetworkUtils.LANGUAGE, "en-US");
        pageAdapter = new MovieListFragmentsPageadapter(MainActivity.this, fragmentManager, selectionBundle);
        mainBinding.vpShowPages.setAdapter(pageAdapter);
        mainBinding.drawerLayout.closeDrawers();
        return true;
    }

    public boolean topratedMoviesChosen() {
        Bundle selectionBundle = new Bundle();
        selectionBundle.putInt(RetrofitNetworkUtils.LIST_MAIN_CATEGORY, MainActivity.MOVIES_CATEGORY);
        selectionBundle.putString(RetrofitNetworkUtils.LIST_SUBCATEGORY, "top_rated");
        selectionBundle.putString(RetrofitNetworkUtils.API_KEY, "9b2e111f87ebfd12b22a44d8d7010338");
        selectionBundle.putString(RetrofitNetworkUtils.LANGUAGE, "en-US");
        pageAdapter = new MovieListFragmentsPageadapter(MainActivity.this, fragmentManager, selectionBundle);
        mainBinding.vpShowPages.setAdapter(pageAdapter);
        mainBinding.drawerLayout.closeDrawers();
        return true;
    }

    public boolean airingTodayTvChosen() {
        Bundle selectionBundle = new Bundle();
        selectionBundle.putInt(RetrofitNetworkUtils.LIST_MAIN_CATEGORY, MainActivity.TV_CATEGORY);
        selectionBundle.putString(RetrofitNetworkUtils.LIST_SUBCATEGORY, "airing_today");
        selectionBundle.putString(RetrofitNetworkUtils.API_KEY, "9b2e111f87ebfd12b22a44d8d7010338");
        selectionBundle.putString(RetrofitNetworkUtils.LANGUAGE, "en-US");
        pageAdapter = new MovieListFragmentsPageadapter(MainActivity.this, fragmentManager, selectionBundle);
        mainBinding.vpShowPages.setAdapter(pageAdapter);
        mainBinding.drawerLayout.closeDrawers();
        return true;
    }

    public boolean onTheAirTvChosen() {
        Bundle selectionBundle = new Bundle();
        selectionBundle.putInt(RetrofitNetworkUtils.LIST_MAIN_CATEGORY, MainActivity.TV_CATEGORY);
        selectionBundle.putString(RetrofitNetworkUtils.LIST_SUBCATEGORY, "on_the_air");
        selectionBundle.putString(RetrofitNetworkUtils.API_KEY, "9b2e111f87ebfd12b22a44d8d7010338");
        selectionBundle.putString(RetrofitNetworkUtils.LANGUAGE, "en-US");
        pageAdapter = new MovieListFragmentsPageadapter(MainActivity.this, fragmentManager, selectionBundle);
        mainBinding.vpShowPages.setAdapter(pageAdapter);
        mainBinding.drawerLayout.closeDrawers();
        return true;
    }

    public boolean popularTvChosen() {
        Bundle selectionBundle = new Bundle();
        selectionBundle.putInt(RetrofitNetworkUtils.LIST_MAIN_CATEGORY, MainActivity.TV_CATEGORY);
        selectionBundle.putString(RetrofitNetworkUtils.LIST_SUBCATEGORY, "popular");
        selectionBundle.putString(RetrofitNetworkUtils.API_KEY, "9b2e111f87ebfd12b22a44d8d7010338");
        selectionBundle.putString(RetrofitNetworkUtils.LANGUAGE, "en-US");
        pageAdapter = new MovieListFragmentsPageadapter(MainActivity.this, fragmentManager, selectionBundle);
        mainBinding.vpShowPages.setAdapter(pageAdapter);
        mainBinding.drawerLayout.closeDrawers();
        return true;
    }

    public boolean topRatedTvChosen() {
        Bundle selectionBundle = new Bundle();
        selectionBundle.putInt(RetrofitNetworkUtils.LIST_MAIN_CATEGORY, MainActivity.TV_CATEGORY);
        selectionBundle.putString(RetrofitNetworkUtils.LIST_SUBCATEGORY, "top_rated");
        selectionBundle.putString(RetrofitNetworkUtils.API_KEY, "9b2e111f87ebfd12b22a44d8d7010338");
        selectionBundle.putString(RetrofitNetworkUtils.LANGUAGE, "en-US");
        pageAdapter = new MovieListFragmentsPageadapter(MainActivity.this, fragmentManager, selectionBundle);
        mainBinding.vpShowPages.setAdapter(pageAdapter);
        mainBinding.drawerLayout.closeDrawers();
        return true;
    }

    public boolean popularActorsChosen() {
        Bundle selectionBundle = new Bundle();
        selectionBundle.putInt(RetrofitNetworkUtils.LIST_MAIN_CATEGORY, MainActivity.ACTORS_CATEGORY);
        selectionBundle.putString(RetrofitNetworkUtils.LIST_SUBCATEGORY, "popular");
        selectionBundle.putString(RetrofitNetworkUtils.API_KEY, "9b2e111f87ebfd12b22a44d8d7010338");
        selectionBundle.putString(RetrofitNetworkUtils.LANGUAGE, "en-US");
        pageAdapter = new MovieListFragmentsPageadapter(MainActivity.this, fragmentManager, selectionBundle);
        mainBinding.vpShowPages.setAdapter(pageAdapter);
        mainBinding.drawerLayout.closeDrawers();
        return true;
    }


}