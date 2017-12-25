package com.learn2develope.popularmovies.retrofitUtils;

import android.os.Bundle;

import com.learn2develope.popularmovies.model.actors.Actors;
import com.learn2develope.popularmovies.model.movies.Movies;
import com.learn2develope.popularmovies.model.movies.Result;
import com.learn2develope.popularmovies.model.tv.TV;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Ibrahim Elazb on 12/24/2017.
 */

public class RetrofitNetworkUtils {

    public static final int MOVIES_CATEGORY = 0;
    public static final int TV_CATEGORY = 1;
    public static final int ACTORS_CATEGORY = 2;

    public static final String LIST_MAIN_CATEGORY = "main_category";//movie or tv or actor
    public static final String LIST_SUBCATEGORY="subcategory"; //every main category has sub-category
    public static final String API_KEY="api_key";
    public static final String LANGUAGE="language";
    public static final String PAGE_NUMBER="page_number";

    public interface onDownloadCompleteHandler {
        void onDownloadCompletedSuccessfully(List results, int numberOfPages);
        void onDownloadFailed(String errorMessage);
    }

    public static RetrofitApi getRetrofitApi() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://api.themoviedb.org")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitApi retrofitApi = retrofit.create(RetrofitApi.class);
        return retrofitApi;
    }

    public static void startDownloadingDataFromIntenet(onDownloadCompleteHandler downloadHandler, Bundle bundle) {

        int mainCategory=bundle.getInt(LIST_MAIN_CATEGORY);
        String subCategory=bundle.getString(LIST_SUBCATEGORY);
        String apiKey=bundle.getString(API_KEY);
        String language=bundle.getString(LANGUAGE);
        int pageNumber=bundle.getInt(PAGE_NUMBER);
        RetrofitApi retrofitApiService = getRetrofitApi();
        if (mainCategory == MOVIES_CATEGORY) {
            Call<Movies> moviesListCall = retrofitApiService
                    .getMoviesList(subCategory, apiKey, language, pageNumber);
            if (moviesListCall != null) {
                getMoviesInformation(moviesListCall, downloadHandler);
            }
        } else if (mainCategory == TV_CATEGORY) {
            Call<TV> tvListCall = retrofitApiService
                    .getTvList(subCategory, apiKey, language, pageNumber);
            if (tvListCall != null) {
                getTvInformation(tvListCall, downloadHandler);
            }

        } else if (mainCategory == ACTORS_CATEGORY) {
            Call<Actors> actorsListCall = retrofitApiService
                    .getActorsList(subCategory, apiKey, language, pageNumber);
            if (actorsListCall != null) {
                getActorsInformation(actorsListCall, downloadHandler);
            }
        } else {
            throw new IllegalArgumentException("invalid argument");
        }
    }


    public static void getMoviesInformation(Call<Movies> moviesListCall, final onDownloadCompleteHandler downloadHandler) {
        moviesListCall.enqueue(new Callback<Movies>() {
            @Override
            public void onResponse(Call<Movies> call, Response<Movies> response) {
                Movies moviesList = response.body();
                ArrayList<Result> moviesListResult = (ArrayList<Result>) moviesList.results;
                downloadHandler.onDownloadCompletedSuccessfully( moviesListResult,moviesList.totalPages);
        }

            @Override
            public void onFailure(Call<Movies> call, Throwable t) {
                downloadHandler.onDownloadFailed(t.getMessage());
            }
        });
    }

    public static void getTvInformation(Call<TV> tvListCall, final onDownloadCompleteHandler downloadHandler) {
        tvListCall.enqueue(new Callback<TV>() {
            @Override
            public void onResponse(Call<TV> call, Response<TV> response) {
                TV tvList = response.body();
                List<com.learn2develope.popularmovies.model.tv.Result> tvListResult = tvList.results;
                downloadHandler.onDownloadCompletedSuccessfully(tvListResult,tvList.totalPages);
            }

            @Override
            public void onFailure(Call<TV> call, Throwable t) {
                downloadHandler.onDownloadFailed(t.getMessage());
            }
        });
    }

    public static void getActorsInformation(Call<Actors> actorsListCall, final onDownloadCompleteHandler downloadHandler) {
        actorsListCall.enqueue(new Callback<Actors>() {
            @Override
            public void onResponse(Call<Actors> call, Response<Actors> response) {
                Actors actorsList = response.body();
                List<com.learn2develope.popularmovies.model.actors.Result> actorsListResult = actorsList.results;
                downloadHandler.onDownloadCompletedSuccessfully( actorsListResult,actorsList.totalPages);
            }

            @Override
            public void onFailure(Call<Actors> call, Throwable t) {
                downloadHandler.onDownloadFailed(t.getMessage());
            }
        });
    }
}
