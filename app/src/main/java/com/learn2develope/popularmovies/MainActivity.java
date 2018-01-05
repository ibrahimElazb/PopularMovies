package com.learn2develope.popularmovies;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.databinding.DataBindingUtil;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;


import com.learn2develope.popularmovies.adapters.MainListAdapter;
import com.learn2develope.popularmovies.databinding.ActivityMainBinding;
import com.learn2develope.popularmovies.retrofitUtils.RetrofitNetworkUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity implements  MainListAdapter.onListItemClickListener {


    ActivityMainBinding mainBinding;
    FragmentManager fragmentManager;
    MainListPageadapter pageAdapter;
    ActionBarDrawerToggle navigationDrawerButton;

    Bundle selectionBundle;

    int mTotalPages = 0;
    String mCurrentSubCategory;
    String mCurrentMainCategory;
    int mCurrentPage = 0;

    final static String TOTAL_PAGES_KEY = "total_pages";
    final static String CURRENT_PAGE_KEY = "current_page";
    final static String CURRENT_SUBCATEGORY_KEY = "current_subcategory";
    final static String CURRENT_MAINCATEGORY_KEY = "current_maincategory";

    public static final String SHOW_ALL_INFORMATION="show_all_info";
    public static final String SHOW_DETAILS="show_details";
    public static final String SHOW_REVIEWS="show_reviews";
    public static final String SHOW_CAST="show_cast";
    public static final String SHOW_VIDEOS="show_videos";
    public static final String SHOW_WORKS="show_works";

    public static final String SELECTED_TYPE_KEY="selected_type";
    public static final String SELECTED_ITEM_ID_KEY="selected_item_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        Log.d(MainActivity.class.getName(), "oncreate");
        navigationDrawerButton = new ActionBarDrawerToggle
                (this, mainBinding.drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mainBinding.drawerLayout.addDrawerListener(navigationDrawerButton);
        navigationDrawerButton.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        fragmentManager = getSupportFragmentManager();
        if (savedInstanceState != null) {
            mTotalPages = savedInstanceState.getInt(TOTAL_PAGES_KEY);
            mCurrentPage = savedInstanceState.getInt(CURRENT_PAGE_KEY);
            mCurrentSubCategory = savedInstanceState.getString(CURRENT_SUBCATEGORY_KEY);
            mCurrentMainCategory = savedInstanceState.getString(CURRENT_MAINCATEGORY_KEY);
            showSubCatgoryAfterRotation();
        } else {
            nowPlayingMoviesChosen();
        }
        mainBinding.nvCategorySelect.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.now_playing_movies_menu_item:
                        mTotalPages=0;
                        mCurrentPage=0;
                        nowPlayingMoviesChosen();
                        break;
                    case R.id.popular_movies_menu_item:
                        mTotalPages=0;
                        mCurrentPage=0;
                        popularMoviesChosen();
                        break;
                    case R.id.upcoming_movies_menu_item:
                        mTotalPages=0;
                        mCurrentPage=0;
                        upcomingMoviesChosen();
                        break;
                    case R.id.top_rated_movies_menu_item:
                        mTotalPages=0;
                        mCurrentPage=0;
                        topratedMoviesChosen();
                        break;
                    case R.id.airing_today_tv_menu_item:
                        mTotalPages=0;
                        mCurrentPage=0;
                        airingTodayTvChosen();
                        break;
                    case R.id.on_the_air_tv_menu_item:
                        mTotalPages=0;
                        mCurrentPage=0;
                        onTheAirTvChosen();
                        break;
                    case R.id.popular_tv_menu_item:
                        mTotalPages=0;
                        mCurrentPage=0;
                        popularTvChosen();
                        break;
                    case R.id.top_rated_tv_menu_item:
                        mTotalPages=0;
                        mCurrentPage=0;
                        topRatedTvChosen();
                        break;
                    case R.id.popular_actors_menu_id:
                        mTotalPages=0;
                        mCurrentPage=0;
                        popularActorsChosen();
                        break;
                }
                return false;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (navigationDrawerButton.onOptionsItemSelected(item)) return true;
        return super.onOptionsItemSelected(item);
    }

    //show loading indicator while downloading information
    public void showLoadingIndicator() {
        mainBinding.pbLoadingIndication.getRoot().setVisibility(View.VISIBLE);
    }

    //to get information about now playing Movies
    public boolean nowPlayingMoviesChosen() {
        Log.d(MainActivity.class.getName(), "nowPlayingMoviesChosen");
        getSupportActionBar().setTitle(getString(R.string.now_playing_movies));
        selectionBundle = new Bundle();
        selectionBundle.putString(RetrofitNetworkUtils.LIST_MAIN_CATEGORY, RetrofitNetworkUtils.MOVIES_CATEGORY);
        selectionBundle.putString(RetrofitNetworkUtils.LIST_SUBCATEGORY, RetrofitNetworkUtils.NOW_PLAYING_MOVIES_SUBCATEGORY);
        mCurrentMainCategory = RetrofitNetworkUtils.MOVIES_CATEGORY;
        mCurrentSubCategory = RetrofitNetworkUtils.NOW_PLAYING_MOVIES_SUBCATEGORY;
        if (mTotalPages == 0)
            getTotalPages(selectionBundle);
        else
            startFragmentPaging();
        return true;
    }

    //to get information about popular Movies
    public boolean popularMoviesChosen() {
        getSupportActionBar().setTitle(getString(R.string.popular_movies));
        selectionBundle = new Bundle();
        selectionBundle.putString(RetrofitNetworkUtils.LIST_MAIN_CATEGORY, RetrofitNetworkUtils.MOVIES_CATEGORY);
        selectionBundle.putString(RetrofitNetworkUtils.LIST_SUBCATEGORY, RetrofitNetworkUtils.POPULAR_MOVIES_SUBCATEGORY);
        mCurrentMainCategory = RetrofitNetworkUtils.MOVIES_CATEGORY;
        mCurrentSubCategory = RetrofitNetworkUtils.POPULAR_MOVIES_SUBCATEGORY;
        if (mTotalPages == 0)
            getTotalPages(selectionBundle);
        else
            startFragmentPaging();
        return true;
    }

    //to get information about upcoming Movies
    public boolean upcomingMoviesChosen() {
        getSupportActionBar().setTitle(getString(R.string.upcoming_movies));
        selectionBundle = new Bundle();
        selectionBundle.putString(RetrofitNetworkUtils.LIST_MAIN_CATEGORY, RetrofitNetworkUtils.MOVIES_CATEGORY);
        selectionBundle.putString(RetrofitNetworkUtils.LIST_SUBCATEGORY, RetrofitNetworkUtils.UPCOMING_MOVIES_SUBCATEGORY);
        mCurrentMainCategory = RetrofitNetworkUtils.MOVIES_CATEGORY;
        mCurrentSubCategory = RetrofitNetworkUtils.UPCOMING_MOVIES_SUBCATEGORY;
        if (mTotalPages == 0)
            getTotalPages(selectionBundle);
        else
            startFragmentPaging();
        return true;
    }

    //to get information about Top rated Movies
    public boolean topratedMoviesChosen() {
        getSupportActionBar().setTitle(getString(R.string.toprated_movies));
        selectionBundle = new Bundle();
        selectionBundle.putString(RetrofitNetworkUtils.LIST_MAIN_CATEGORY, RetrofitNetworkUtils.MOVIES_CATEGORY);
        selectionBundle.putString(RetrofitNetworkUtils.LIST_SUBCATEGORY, RetrofitNetworkUtils.TOP_RATED_MOVIES_SUBCATEGORY);
        mCurrentMainCategory = RetrofitNetworkUtils.MOVIES_CATEGORY;
        mCurrentSubCategory = RetrofitNetworkUtils.TOP_RATED_MOVIES_SUBCATEGORY;
        if (mTotalPages == 0)
            getTotalPages(selectionBundle);
        else
            startFragmentPaging();
        return true;
    }

    //to get information about airing today TV shows
    public boolean airingTodayTvChosen() {
        getSupportActionBar().setTitle(getString(R.string.airing_today_tv));
        selectionBundle = new Bundle();
        selectionBundle.putString(RetrofitNetworkUtils.LIST_MAIN_CATEGORY, RetrofitNetworkUtils.TV_CATEGORY);
        selectionBundle.putString(RetrofitNetworkUtils.LIST_SUBCATEGORY, RetrofitNetworkUtils.AIRING_TODAY_TV_SUBCATEGORY);
        mCurrentMainCategory = RetrofitNetworkUtils.TV_CATEGORY;
        mCurrentSubCategory = RetrofitNetworkUtils.AIRING_TODAY_TV_SUBCATEGORY;
        if (mTotalPages == 0)
            getTotalPages(selectionBundle);
        else
            startFragmentPaging();
        return true;
    }

    //to get information about on the air TV shows
    public boolean onTheAirTvChosen() {
        getSupportActionBar().setTitle(getString(R.string.on_the_air_tv));
        selectionBundle = new Bundle();
        selectionBundle.putString(RetrofitNetworkUtils.LIST_MAIN_CATEGORY, RetrofitNetworkUtils.TV_CATEGORY);
        selectionBundle.putString(RetrofitNetworkUtils.LIST_SUBCATEGORY, RetrofitNetworkUtils.ON_THE_AIR_TV_SUBCATEGORY);
        mCurrentMainCategory = RetrofitNetworkUtils.TV_CATEGORY;
        mCurrentSubCategory = RetrofitNetworkUtils.ON_THE_AIR_TV_SUBCATEGORY;
        if (mTotalPages == 0)
            getTotalPages(selectionBundle);
        else
            startFragmentPaging();
        return true;
    }

    //to get information about Popular TV shows
    public boolean popularTvChosen() {
        getSupportActionBar().setTitle(getString(R.string.popular_tv));
        selectionBundle = new Bundle();
        selectionBundle.putString(RetrofitNetworkUtils.LIST_MAIN_CATEGORY, RetrofitNetworkUtils.TV_CATEGORY);
        selectionBundle.putString(RetrofitNetworkUtils.LIST_SUBCATEGORY, RetrofitNetworkUtils.POPULAR_TV_SUBCATEGORY);
        mCurrentMainCategory = RetrofitNetworkUtils.TV_CATEGORY;
        mCurrentSubCategory = RetrofitNetworkUtils.POPULAR_TV_SUBCATEGORY;
        if (mTotalPages == 0)
            getTotalPages(selectionBundle);
        else
            startFragmentPaging();
        return true;
    }

    //to get information about Top rated TV shows
    public boolean topRatedTvChosen() {
        getSupportActionBar().setTitle(getString(R.string.toprated_tv));
        selectionBundle = new Bundle();
        selectionBundle.putString(RetrofitNetworkUtils.LIST_MAIN_CATEGORY, RetrofitNetworkUtils.TV_CATEGORY);
        selectionBundle.putString(RetrofitNetworkUtils.LIST_SUBCATEGORY, RetrofitNetworkUtils.TOP_RATED_TV_SUBCATEGORY);
        mCurrentMainCategory = RetrofitNetworkUtils.TV_CATEGORY;
        mCurrentSubCategory = RetrofitNetworkUtils.TOP_RATED_TV_SUBCATEGORY;
        if (mTotalPages == 0)
            getTotalPages(selectionBundle);
        else
            startFragmentPaging();
        return true;
    }

    //to get information about popular actors
    public boolean popularActorsChosen() {
        getSupportActionBar().setTitle(getString(R.string.popular_actors));
        selectionBundle = new Bundle();
        selectionBundle.putString(RetrofitNetworkUtils.LIST_MAIN_CATEGORY, RetrofitNetworkUtils.ACTORS_CATEGORY);
        selectionBundle.putString(RetrofitNetworkUtils.LIST_SUBCATEGORY, RetrofitNetworkUtils.POPULAR_ACTORS_SUBCATEGORY);
        mCurrentMainCategory = RetrofitNetworkUtils.ACTORS_CATEGORY;
        mCurrentSubCategory = RetrofitNetworkUtils.POPULAR_ACTORS_SUBCATEGORY;
        if (mTotalPages == 0)
            getTotalPages(selectionBundle);
        else
            startFragmentPaging();
        return true;
    }

    //used to call the TotalPagesAsyncTask call to be executed and send the category and subCategory
    // to it to return the number of pages related to this subcategory
    public void getTotalPages(Bundle bundle) {
        new TotalPagesAsyncTask().execute(bundle);
    }

    //before screen rotation use this method to save the current displayed page of movies ,
    // save subCategory that is displayed and save the total pages of this type.
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(TOTAL_PAGES_KEY, mTotalPages);
        outState.putInt(CURRENT_PAGE_KEY, mainBinding.vpShowPages.getCurrentItem());
        outState.putString(CURRENT_SUBCATEGORY_KEY, mCurrentSubCategory);
        outState.putString(CURRENT_MAINCATEGORY_KEY,mCurrentMainCategory);
    }

    // to start showing the Movies,Tv and actors information
    // after collecting the required data (category,subcategory and total number of pages)
    public void startFragmentPaging() {
        pageAdapter = new MainListPageadapter(fragmentManager, selectionBundle, mTotalPages);
        mainBinding.vpShowPages.setAdapter(pageAdapter);
        mainBinding.vpShowPages.setCurrentItem(mCurrentPage);
        mainBinding.pbLoadingIndication.getRoot().setVisibility(View.INVISIBLE);
    }

    //after screen rotation check the subcategory to display it again
    public void showSubCatgoryAfterRotation() {
        if (mCurrentSubCategory.equals(RetrofitNetworkUtils.NOW_PLAYING_MOVIES_SUBCATEGORY)
                && mCurrentMainCategory.equals(RetrofitNetworkUtils.MOVIES_CATEGORY))
            nowPlayingMoviesChosen();
        else if (mCurrentSubCategory.equals(RetrofitNetworkUtils.POPULAR_MOVIES_SUBCATEGORY)
                && mCurrentMainCategory.equals(RetrofitNetworkUtils.MOVIES_CATEGORY))
            popularMoviesChosen();
        else if (mCurrentSubCategory.equals(RetrofitNetworkUtils.TOP_RATED_MOVIES_SUBCATEGORY)
                && mCurrentMainCategory.equals(RetrofitNetworkUtils.MOVIES_CATEGORY))
            topratedMoviesChosen();
        else if (mCurrentSubCategory.equals(RetrofitNetworkUtils.UPCOMING_MOVIES_SUBCATEGORY)
                && mCurrentMainCategory.equals(RetrofitNetworkUtils.MOVIES_CATEGORY))
            upcomingMoviesChosen();
        else if (mCurrentSubCategory.equals(RetrofitNetworkUtils.AIRING_TODAY_TV_SUBCATEGORY)
                && mCurrentMainCategory.equals(RetrofitNetworkUtils.TV_CATEGORY))
            airingTodayTvChosen();
        else if (mCurrentSubCategory.equals(RetrofitNetworkUtils.ON_THE_AIR_TV_SUBCATEGORY)
                && mCurrentMainCategory.equals(RetrofitNetworkUtils.TV_CATEGORY))
            onTheAirTvChosen();
        else if (mCurrentSubCategory.equals(RetrofitNetworkUtils.TOP_RATED_TV_SUBCATEGORY)
                && mCurrentMainCategory.equals(RetrofitNetworkUtils.TV_CATEGORY))
            topRatedTvChosen();
        else if (mCurrentSubCategory.equals(RetrofitNetworkUtils.POPULAR_TV_SUBCATEGORY)
                && mCurrentMainCategory.equals(RetrofitNetworkUtils.TV_CATEGORY))
            popularTvChosen();
        else if (mCurrentSubCategory.equals(RetrofitNetworkUtils.POPULAR_ACTORS_SUBCATEGORY)
                && mCurrentMainCategory.equals(RetrofitNetworkUtils.ACTORS_CATEGORY))
            popularActorsChosen();

    }

    //when an item in the list (movies,TV,actors) move to the detailed activity
    //to show type(general details , Cast(movies&&TV) , Reviews(movies) , works(actors))
    @Override
    public void onClickListener(int itemId, String detailedType) {
        Intent detailedActivityIntent=new Intent(this,DetailsActivity.class);
        detailedActivityIntent.putExtra(SELECTED_ITEM_ID_KEY,itemId);
        detailedActivityIntent.putExtra(SELECTED_TYPE_KEY,detailedType);
        detailedActivityIntent.putExtra(CURRENT_MAINCATEGORY_KEY,mCurrentMainCategory);
        startActivity(detailedActivityIntent);
    }

    // TotalPagesAsyncTask class is used to get the number of total pages
    // that will be used by FragmentStatePageAdapter class to count
    // the number of pages that will be shown in the viewPage
    class TotalPagesAsyncTask extends AsyncTask<Bundle, Void, Integer> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showLoadingIndicator();
            pageAdapter = new MainListPageadapter(fragmentManager, null, 0);
            mainBinding.vpShowPages.setAdapter(pageAdapter);
            mainBinding.drawerLayout.closeDrawers();

        }

        @Override
        protected Integer doInBackground(Bundle... bundles) {
            Bundle selectedBundle = bundles[0];
            HttpURLConnection urlConnection = null;
            String jsonData = null;
            int totalPages = 0;
            Uri uri = Uri.parse("https://api.themoviedb.org/3")
                    .buildUpon()
                    .appendPath(selectedBundle.getString(RetrofitNetworkUtils.LIST_MAIN_CATEGORY))
                    .appendPath(selectedBundle.getString(RetrofitNetworkUtils.LIST_SUBCATEGORY))
                    .appendQueryParameter("api_key", "9b2e111f87ebfd12b22a44d8d7010338")
                    .appendQueryParameter("language", "en-US")
                    .appendQueryParameter("page", "1")
                    .build();
            try {
                URL url = new URL(uri.toString());
                Log.d(MainActivity.class.getName(), url.toString());
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream in = urlConnection.getInputStream();

                Scanner scanner = new Scanner(in);
                scanner.useDelimiter("\\A");

                boolean hasInput = scanner.hasNext();
                if (hasInput)
                    jsonData = scanner.next();
                JSONObject informationJsonObject = new JSONObject(jsonData);

                if (informationJsonObject.has("total_pages"))
                    totalPages = informationJsonObject.getInt("total_pages");
                Log.d(MainActivity.class.getName(), "total pages " + totalPages);

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                if (urlConnection != null)
                    urlConnection.disconnect();
            }
            return totalPages;
        }

        @Override
        protected void onPostExecute(Integer totalPages) {
            super.onPostExecute(totalPages);
            mTotalPages = totalPages;
            startFragmentPaging();

        }
    }

}