package com.learn2develope.popularmovies;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import com.learn2develope.popularmovies.NetworkUtils.RetrofitNetworkUtils;
import com.learn2develope.popularmovies.adapters.SearchFragmentListAdapter;
import com.learn2develope.popularmovies.databinding.ActivitySearchBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class SearchActivity extends AppCompatActivity implements Button.OnClickListener {

    ActivitySearchBinding activitySearchBinding;
    FragmentManager fragmentManager;
    SearchFragmentListAdapter pageAdapter;

    String query;
    int mTotalPages;
    int mCurrentPage;
    String mCategory = RetrofitNetworkUtils.MOVIES_CATEGORY;//the default category is movie

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activitySearchBinding = DataBindingUtil.setContentView(this, R.layout.activity_search);
        selectTypeConfiguration();
        fragmentManager = getSupportFragmentManager();
        activitySearchBinding.btnSearch.setOnClickListener(this);
    }


    //this is for the configuration of spinner that it contains (movie - tv show - actor)
    // and what happens when I choose one category
    public void selectTypeConfiguration() {
        ArrayAdapter<CharSequence> adapter
                = ArrayAdapter.createFromResource(this, R.array.search_type, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        activitySearchBinding.selectType.setAdapter(adapter);
        activitySearchBinding.selectType.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        if (i == 0) mCategory = RetrofitNetworkUtils.MOVIES_CATEGORY;
                        else if (i == 1) mCategory = RetrofitNetworkUtils.TV_CATEGORY;
                        else if (i == 2) mCategory = RetrofitNetworkUtils.ACTORS_CATEGORY;
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                }
        );
    }

    //start showing pages (fragments) using FragmentPageAdapter after knowing the number of pages (fragments that will be shown)
    //send the query which I'm searching for the category (movie - tv Show - Actors) and the total number of pages
    public void startFragmentPaging() {
        query = activitySearchBinding.etSearchText.getText().toString();
        SearchFragmentListAdapter pageAdapter = new SearchFragmentListAdapter(fragmentManager, query, mCategory, mTotalPages);
        activitySearchBinding.vpShowPages.setAdapter(pageAdapter);
        activitySearchBinding.vpShowPages.setCurrentItem(mCurrentPage);
        activitySearchBinding.pbLoadingIndication.getRoot().setVisibility(View.INVISIBLE);
    }


    //
    @Override
    public void onClick(View view) {
        if (activitySearchBinding.etSearchText.getText().toString() != null) {
            hideTypingWindow();//hide the keyboard which is used to enter data in editText
            //start operation to count number of pages that is used FragmentPageAdapter
            // as a number of items that will be shown by FragmentPageAdapter
            //then start paging by showing the pages (fragments) on by one
            new TotalPagesAsyncTask().execute();
        } else {
            //if there nothing in the editText
            Toast.makeText(this, "search field is empty", Toast.LENGTH_SHORT).show();
        }
    }


    //show loading indicator while loading data from the internet
    public void showLoadingIndicator() {
        activitySearchBinding.pbLoadingIndication.getRoot().setVisibility(View.VISIBLE);
    }

    //to hide the keyboard after finish typing in the editText
    public void hideTypingWindow(){
        InputMethodManager inputManager =
                (InputMethodManager) this.
                        getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(
                this.getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
    }


    // TotalPagesAsyncTask class is used to get the number of total pages
    // that will be used by FragmentStatePageAdapter class to count
    // the number of pages that will be shown in the viewPage
    class TotalPagesAsyncTask extends AsyncTask<Void, Void, Integer> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showLoadingIndicator();
            pageAdapter = new SearchFragmentListAdapter(fragmentManager, query,null, 0);
            activitySearchBinding.vpShowPages.setAdapter(pageAdapter);

        }

        @Override
        protected Integer doInBackground(Void... xx) {
            HttpURLConnection urlConnection = null;
            String jsonData = null;
            int totalPages = 0;
            Uri uri = Uri.parse("https://api.themoviedb.org/3/search/"+mCategory)
                    .buildUpon()
                    .appendQueryParameter("query",query)
                    .appendQueryParameter("api_key", RetrofitNetworkUtils.API_KEY)
                    .appendQueryParameter("language", RetrofitNetworkUtils.LANGUAGE)
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
