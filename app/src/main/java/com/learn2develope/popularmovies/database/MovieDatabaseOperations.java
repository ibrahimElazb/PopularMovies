package com.learn2develope.popularmovies.database;

import android.content.Context;

import com.learn2develope.popularmovies.model.movies.Result;
import com.learn2develope.popularmovies.NetworkUtils.RetrofitNetworkUtils;

import java.util.List;

/**
 * Created by Ibrahim Elazb on 1/26/2018.
 */

public class MovieDatabaseOperations {


    public static void insertNewDataCollection(Context context, List result, String category, String subCategory) {
        DatabaseRepo databaseRepo = new DatabaseRepo(context);
        DatabaseModel model = new DatabaseModel();

        for (Object item : result) {
            if (category.equals(RetrofitNetworkUtils.MOVIES_CATEGORY)) {
                Result movie = (Result) item;
                model.setMovieId(movie.id);
                model.setMovieName(movie.title);
                model.setType(category + subCategory);
            } else if (category.equals(RetrofitNetworkUtils.TV_CATEGORY)) {
                com.learn2develope.popularmovies.model.tv.Result tv = (com.learn2develope.popularmovies.model.tv.Result) item;
                model.setMovieId(tv.id);
                model.setMovieName(tv.name);
                model.setType(category + subCategory);
            } else if (category.equals(RetrofitNetworkUtils.ACTORS_CATEGORY)) {
                com.learn2develope.popularmovies.model.actors.Result actor = (com.learn2develope.popularmovies.model.actors.Result) item;
                model.setMovieId(actor.id);
                model.setMovieName(actor.name);
                model.setType(category + subCategory);
            }
            databaseRepo.create(model);
        }
    }

    public static void insertNewFavoriteItem(Context context,DatabaseModel model) {
        DatabaseRepo databaseRepo = new DatabaseRepo(context);
            databaseRepo.create(model);
    }

    public static List retrieveData(Context context, String category, String subCategory) {
        List results = new DatabaseRepo(context).findByType(category + subCategory);
        return results;
    }

    public static boolean isFavorite(Context context,int movieId,String type){
        return new DatabaseRepo(context).findByIdType(movieId,type);
    }

    public static boolean isDatabaseEmpty(Context context){
        return new DatabaseRepo(context).findAll();
    }

    public static void deletePreviousCollectionData(Context context, String category, String subCategory){
        new DatabaseRepo(context).deleteByType(category+subCategory);
    }

    public static void deleteFavoriteData(Context context, int movieId){
        new DatabaseRepo(context).deleteById(movieId);
    }
}
