package com.learn2develope.popularmovies.retrofitUtils;

import android.os.Bundle;
import android.util.Log;

import com.learn2develope.popularmovies.model.actors.Actors;
import com.learn2develope.popularmovies.model.actors.actorDetails.ActorDetails;
import com.learn2develope.popularmovies.model.actors.actorWorks.ActorWorks;
import com.learn2develope.popularmovies.model.movies.Movies;
import com.learn2develope.popularmovies.model.movies.Result;
import com.learn2develope.popularmovies.model.movies.movieCast.MovieCast;
import com.learn2develope.popularmovies.model.movies.movieDetails.MovieDetails;
import com.learn2develope.popularmovies.model.movies.movieReviews.MovieReviews;
import com.learn2develope.popularmovies.model.movies.movieVideos.MovieVideos;
import com.learn2develope.popularmovies.model.tv.TV;
import com.learn2develope.popularmovies.model.tv.tvCast.TvCast;
import com.learn2develope.popularmovies.model.tv.tvDetails.TvDetails;

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

    public static final String MOVIES_CATEGORY = "movie";
    public static final String TV_CATEGORY = "tv";
    public static final String ACTORS_CATEGORY = "person";

    public static final String NOW_PLAYING_MOVIES_SUBCATEGORY = "now_playing";
    public static final String POPULAR_MOVIES_SUBCATEGORY = "popular";
    public static final String TOP_RATED_MOVIES_SUBCATEGORY = "top_rated";
    public static final String UPCOMING_MOVIES_SUBCATEGORY = "upcoming";

    public static final String AIRING_TODAY_TV_SUBCATEGORY = "airing_today";
    public static final String ON_THE_AIR_TV_SUBCATEGORY = "on_the_air";
    public static final String POPULAR_TV_SUBCATEGORY = "popular";
    public static final String TOP_RATED_TV_SUBCATEGORY = "top_rated";

    public static final String POPULAR_ACTORS_SUBCATEGORY = "popular";

    public static final String LIST_MAIN_CATEGORY = "main_category";//movie or tv or actor
    public static final String LIST_SUBCATEGORY = "subcategory"; //every main category has sub-category
    public static final String PAGE_NUMBER = "page_number";

    public static final String API_KEY = "9b2e111f87ebfd12b22a44d8d7010338";
    public static final String LANGUAGE = "en-US";

    public static RetrofitApi getRetrofitApi() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://api.themoviedb.org")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitApi retrofitApi = retrofit.create(RetrofitApi.class);
        return retrofitApi;
    }

    public void getMoviesListInfo(final onLoadingHandler loadingHandler, String subCategory, int pageNumber) {
        Log.i(RetrofitNetworkUtils.class.getName(),"page number "+pageNumber);
        loadingHandler.onLoadingStart();
        final RetrofitApi retrofitApi = getRetrofitApi();
        Call<Movies> moviesListCall = retrofitApi.getMoviesList(subCategory, API_KEY, LANGUAGE, pageNumber);
        moviesListCall.enqueue(new Callback<Movies>() {
            @Override
            public void onResponse(Call<Movies> call, Response<Movies> retrofitResponse) {
                Movies movies = retrofitResponse.body();
                List<Result> movieListResult = movies.results;
                loadingHandler.onLoadCompletedSuccessfully(movieListResult);
            }

            @Override
            public void onFailure(Call<Movies> call, Throwable t) {
                loadingHandler.onLoadFailed(t.getMessage());
            }
        });
    }

    public void getTvListInfo(final onLoadingHandler loadingHandler, String subCategory, int pageNumber) {
        loadingHandler.onLoadingStart();
        final RetrofitApi retrofitApi = getRetrofitApi();
        Call<TV> tvListCall = retrofitApi.getTvList(subCategory, API_KEY, LANGUAGE, pageNumber);
        tvListCall.enqueue(new Callback<TV>() {
            @Override
            public void onResponse(Call<TV> call, Response<TV> retrofitResponse) {
                Log.d(RetrofitNetworkUtils.class.getName(), "tv onresponse");
                TV tv = retrofitResponse.body();
                List<com.learn2develope.popularmovies.model.tv.Result> tvListResult = tv.results;
                loadingHandler.onLoadCompletedSuccessfully(tvListResult);
            }

            @Override
            public void onFailure(Call<TV> call, Throwable t) {
                loadingHandler.onLoadFailed(t.getMessage());
            }
        });

    }

    public void getActorsListInfo(final onLoadingHandler loadingHandler, String subCategory, int pageNumber) {
        loadingHandler.onLoadingStart();
        final RetrofitApi retrofitApi = getRetrofitApi();
        Call<Actors> actorsListCall = retrofitApi.getActorsList(subCategory, API_KEY, LANGUAGE, pageNumber);
        actorsListCall.enqueue(new Callback<Actors>() {
            @Override
            public void onResponse(Call<Actors> call, Response<Actors> retrofitResponse) {
                Actors actors = retrofitResponse.body();
                List<com.learn2develope.popularmovies.model.actors.Result> actorsListResult = actors.results;
                loadingHandler.onLoadCompletedSuccessfully(actorsListResult);
            }

            @Override
            public void onFailure(Call<Actors> call, Throwable t) {
                loadingHandler.onLoadFailed(t.getMessage());
            }
        });
    }

    public void getMovieDetails(final onLoadingHandler loadingHandler,int movieId){
        loadingHandler.onLoadingStart();
        final RetrofitApi retrofitApi = getRetrofitApi();
        Call<MovieDetails> movieDetailsCall=retrofitApi.getMoviesDetails(movieId,API_KEY,LANGUAGE);
        movieDetailsCall.enqueue(new Callback<MovieDetails>() {
            @Override
            public void onResponse(Call<MovieDetails> call, Response<MovieDetails> retrofitResponse) {
                MovieDetails movieDetails=retrofitResponse.body();
                ArrayList<MovieDetails> movieDetailsList=new ArrayList<>();
                movieDetailsList.add(movieDetails);
                loadingHandler.onLoadCompletedSuccessfully(movieDetailsList);
            }

            @Override
            public void onFailure(Call<MovieDetails> call, Throwable t) {
                loadingHandler.onLoadFailed(t.getMessage());
            }
        });
    }

    public void getMovieCast(final onLoadingHandler loadingHandler,int movieId){
        loadingHandler.onLoadingStart();
        final RetrofitApi retrofitApi = getRetrofitApi();
        Call<MovieCast> movieCastCall=retrofitApi.getMoviesCast(movieId,API_KEY);
        movieCastCall.enqueue(new Callback<MovieCast>() {
            @Override
            public void onResponse(Call<MovieCast> call, Response<MovieCast> retrofitResponse) {
                MovieCast movieCast=retrofitResponse.body();
                List castList=movieCast.cast;
                loadingHandler.onLoadCompletedSuccessfully(castList);
            }

            @Override
            public void onFailure(Call<MovieCast> call, Throwable t) {
                loadingHandler.onLoadFailed(t.getMessage());
            }
        });
    }

    public static void getMovieReviews(final onLoadingHandler loadingHandler,int movieId){
        loadingHandler.onLoadingStart();
        final RetrofitApi retrofitApi = getRetrofitApi();
        Call<MovieReviews> movieReviewsCall=retrofitApi.getMoviesReviews(movieId,API_KEY,LANGUAGE,1);
        movieReviewsCall.enqueue(new Callback<MovieReviews>() {
            @Override
            public void onResponse(Call<MovieReviews> call, Response<MovieReviews> retrofitResponse) {
                MovieReviews movieReviews=retrofitResponse.body();
                List reviewList=movieReviews.results;
                loadingHandler.onLoadCompletedSuccessfully(reviewList);
            }

            @Override
            public void onFailure(Call<MovieReviews> call, Throwable t) {
                loadingHandler.onLoadFailed(t.getMessage());
            }
        });
    }

    public static void getMovieVideos(final onLoadingHandler loadingHandler,int movieId){
        loadingHandler.onLoadingStart();
        final RetrofitApi retrofitApi = getRetrofitApi();
        Call<MovieVideos> movieVideosCall=retrofitApi.getMoviesVideos(movieId,API_KEY,LANGUAGE);
        movieVideosCall.enqueue(new Callback<MovieVideos>() {
            @Override
            public void onResponse(Call<MovieVideos> call, Response<MovieVideos> retrofitResponse) {
                MovieVideos movieVideos=retrofitResponse.body();
                List<com.learn2develope.popularmovies.model.movies.movieVideos.Result> videosList=movieVideos.results;
                loadingHandler.onLoadCompletedSuccessfully(videosList);
            }

            @Override
            public void onFailure(Call<MovieVideos> call, Throwable t) {
                loadingHandler.onLoadFailed(t.getMessage());
            }
        });
    }

    public void getTvDetails(final onLoadingHandler loadingHandler,int tvId){
        loadingHandler.onLoadingStart();
        final RetrofitApi retrofitApi = getRetrofitApi();
        Call<TvDetails> tvDetailsCall=retrofitApi.getTvDetails(tvId,API_KEY,LANGUAGE);
        tvDetailsCall.enqueue(new Callback<TvDetails>() {
            @Override
            public void onResponse(Call<TvDetails> call, Response<TvDetails> retrofitResponse) {
                TvDetails tvDetails=retrofitResponse.body();
                ArrayList<TvDetails> tvDetailsList=new ArrayList<>();
                tvDetailsList.add(tvDetails);
                loadingHandler.onLoadCompletedSuccessfully(tvDetailsList);
            }

            @Override
            public void onFailure(Call<TvDetails> call, Throwable t) {
                loadingHandler.onLoadFailed(t.getMessage());
            }
        });
    }

    public void getTvCast(final onLoadingHandler loadingHandler,int tvId){
        loadingHandler.onLoadingStart();
        final RetrofitApi retrofitApi = getRetrofitApi();
        Call<TvCast> tvCastCall=retrofitApi.getTvCast(tvId,API_KEY,LANGUAGE);
        tvCastCall.enqueue(new Callback<TvCast>() {
            @Override
            public void onResponse(Call<TvCast> call, Response<TvCast> retrofitResponse) {
                TvCast tvCast=retrofitResponse.body();
                ArrayList<TvCast> tvCastList=new ArrayList<>();
                tvCastList.add(tvCast);
                loadingHandler.onLoadCompletedSuccessfully(tvCastList);
            }

            @Override
            public void onFailure(Call<TvCast> call, Throwable t) {
                loadingHandler.onLoadFailed(t.getMessage());
            }
        });
    }

    public void getActorDetails(final onLoadingHandler loadingHandler,int actorId){
        loadingHandler.onLoadingStart();
        final RetrofitApi retrofitApi = getRetrofitApi();
        Call<ActorDetails> actorDetailsCall=retrofitApi.getActorDetails(actorId,API_KEY,LANGUAGE);
        actorDetailsCall.enqueue(new Callback<ActorDetails>() {
            @Override
            public void onResponse(Call<ActorDetails> call, Response<ActorDetails> retrofitResponse) {
                ActorDetails actorDetails=retrofitResponse.body();
                ArrayList<ActorDetails> actorDetailsList=new ArrayList<>();
                actorDetailsList.add(actorDetails);
                loadingHandler.onLoadCompletedSuccessfully(actorDetailsList);
            }

            @Override
            public void onFailure(Call<ActorDetails> call, Throwable t) {
                loadingHandler.onLoadFailed(t.getMessage());
            }
        });
    }

    public void getActorWorks(final onLoadingHandler loadingHandler,int actorId){
        loadingHandler.onLoadingStart();
        final RetrofitApi retrofitApi = getRetrofitApi();
        Call<ActorWorks> actorWorksCall=retrofitApi.getActorWorks(actorId,API_KEY,LANGUAGE);
        actorWorksCall.enqueue(new Callback<ActorWorks>() {
            @Override
            public void onResponse(Call<ActorWorks> call, Response<ActorWorks> retrofitResponse) {
                ActorWorks actorWorks=retrofitResponse.body();
                ArrayList<ActorWorks> actorWorksList=new ArrayList<>();
                actorWorksList.add(actorWorks);
                loadingHandler.onLoadCompletedSuccessfully(actorWorksList);
            }

            @Override
            public void onFailure(Call<ActorWorks> call, Throwable t) {
                loadingHandler.onLoadFailed(t.getMessage());
            }
        });
    }

    public interface onLoadingHandler {
        void onLoadingStart();

        void onLoadCompletedSuccessfully(List results);

        void onLoadFailed(String errorMessage);
    }

}
