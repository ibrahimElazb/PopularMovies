package com.learn2develope.popularmovies;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.learn2develope.popularmovies.databinding.ActivityMainBinding;
import com.learn2develope.popularmovies.model.movies.Movies;
import com.learn2develope.popularmovies.model.movies.Result;
import com.learn2develope.popularmovies.retrofitUtils.MoviesService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding mainBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding=DataBindingUtil.setContentView(this,R.layout.activity_main);
        showLoadingIndication();
        Retrofit retrofit=new Retrofit.Builder().baseUrl("https://api.themoviedb.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        MoviesService mService=retrofit.create(MoviesService.class);
        Call<Movies> moviesListCall=mService
                .getMoviesList("now_playing","9b2e111f87ebfd12b22a44d8d7010338","",1);
        moviesListCall.enqueue(new Callback<Movies>() {
            @Override
            public void onResponse(Call<Movies> call, Response<Movies> response) {
                Movies moviesList=response.body();
                ArrayList<Result> moviesListResult=( ArrayList<Result>)moviesList.results;
                showResults(moviesListResult);
            }

            @Override
            public void onFailure(Call<Movies> call, Throwable t) {
                showErrorIndication();
            }
        });
    }

    public void showLoadingIndication(){
        mainBinding.tvShowJsonData.setVisibility(View.INVISIBLE);
        mainBinding.pbLoadingIndication.setVisibility(View.VISIBLE);
    }

    public void showErrorIndication(){
        mainBinding.pbLoadingIndication.setVisibility(View.INVISIBLE);
        mainBinding.tvShowError.setVisibility(View.VISIBLE);
    }

    public void showResults(ArrayList<Result> results){
        StringBuilder data=new StringBuilder();
        for (Result result:results){
            data.append(result.title+" : ");
        }
        mainBinding.tvShowJsonData.setText(data.toString());
    }
}
