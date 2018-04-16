package com.learn2develope.popularmovies.database;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Ibrahim Elazb on 1/25/2018.
 */

@DatabaseTable (tableName = "movie_app_table")
public class DatabaseModel {

    @DatabaseField (generatedId = true)
    public int id;

    @DatabaseField
    public int movieId;

    @DatabaseField
    public String movieName;

    @DatabaseField
    public String type;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
