package com.learn2develope.popularmovies.retrofitUtils;

import com.learn2develope.popularmovies.model.actors.Actors;
import com.learn2develope.popularmovies.model.actors.actorDetails.ActorDetails;
import com.learn2develope.popularmovies.model.actors.actorWorks.ActorWorks;
import com.learn2develope.popularmovies.model.movies.Movies;
import com.learn2develope.popularmovies.model.movies.movieCast.MovieCast;
import com.learn2develope.popularmovies.model.movies.movieDetails.MovieDetails;
import com.learn2develope.popularmovies.model.movies.movieReviews.MovieReviews;
import com.learn2develope.popularmovies.model.movies.movieVideos.MovieVideos;
import com.learn2develope.popularmovies.model.tv.TV;
import com.learn2develope.popularmovies.model.tv.tvCast.TvCast;
import com.learn2develope.popularmovies.model.tv.tvDetails.TvDetails;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Ibrahim Elazb on 12/23/2017.
 */

public interface RetrofitApi {

    @GET("3/movie/{subcategory}")
    Call<Movies> getMoviesList(@Path("subcategory")String subCategory,
                               @Query("api_key")String apiKey,
                               @Query("language")String language,
                               @Query("page")int page_number);

    @GET("3/movie/{movie_id}")
    Call<MovieDetails> getMoviesDetails(@Path("movie_id")int movie_id,
                                        @Query("api_key")String apiKey,
                                        @Query("language")String language);

    @GET("3/movie/{movie_id}/credits")
    Call<MovieCast> getMoviesCast(@Path("movie_id")int movie_id,
                                  @Query("api_key")String apiKey);

    @GET("3/movie/{movie_id}/reviews")
    Call<MovieReviews> getMoviesReviews(@Path("movie_id")int movie_id,
                                        @Query("api_key")String apiKey,
                                        @Query("language")String language,
                                        @Query("page")int page_number);
    @GET("3/movie/{movie_id}/videos")
    Call<MovieVideos> getMoviesVideos(@Path("movie_id")int movie_id,
                                      @Query("api_key")String apiKey,
                                      @Query("language")String language);
    @GET("3/tv/{subcategory}")
    Call<TV> getTvList(@Path("subcategory")String subCategory,
                       @Query("api_key")String apiKey,
                       @Query("language")String language,
                       @Query("page")int page_number);

    @GET("3/tv/{tv_id}")
    Call<TvDetails> getTvDetails(@Path("tv_id")int tv_id,
                                 @Query("api_key")String apiKey,
                                 @Query("language")String language);

    @GET("3/tv/{tv_id}/credits")
    Call<TvCast> getTvCast(@Path("tv_id")int tv_id,
                           @Query("api_key")String apiKey,
                           @Query("language")String language);


    @GET("3/person/{subcategory}")
    Call<Actors> getActorsList(@Path("subcategory")String subCategory,
                               @Query("api_key")String apiKey,
                               @Query("language")String language,
                               @Query("page")int page_number);

    @GET("3/person/{actor_id}")
    Call<ActorDetails> getActorDetails(@Path("actor_id")int actor_id,
                                       @Query("api_key")String apiKey,
                                       @Query("language")String language);

    @GET("3/person/{actor_id}/combined_credits")
    Call<ActorWorks> getActorWorks(@Path("actor_id")int actor_id,
                                   @Query("api_key")String apiKey,
                                   @Query("language")String language);
}
