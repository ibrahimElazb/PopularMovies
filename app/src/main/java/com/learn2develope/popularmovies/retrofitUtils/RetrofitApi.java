package com.learn2develope.popularmovies.retrofitUtils;

import com.learn2develope.popularmovies.model.actors.Actors;
import com.learn2develope.popularmovies.model.movies.Movies;
import com.learn2develope.popularmovies.model.tv.TV;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Ibrahim Elazb on 12/23/2017.
 */

public interface RetrofitApi {

    @GET("3/movie/{category}")
    Call<Movies> getMoviesList(@Path("category")String category,
                               @Query("api_key")String apiKey,
                               @Query("language")String language,
                               @Query("page")int page_number);
    @GET("3/tv/{category}")
    Call<TV> getTvList(@Path("category")String category,
                       @Query("api_key")String apiKey,
                       @Query("language")String language,
                       @Query("page")int page_number);

    @GET("3/person/{category}")
    Call<Actors> getActorsList(@Path("category")String category,
                               @Query("api_key")String apiKey,
                               @Query("language")String language,
                               @Query("page")int page_number);
}
