
package com.learn2develope.popularmovies.model.movies.movieVideos;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MovieVideos implements Serializable
{

    @SerializedName("id")
    @Expose
    public int id;
    @SerializedName("results")
    @Expose
    public List<Result> results = null;
    private final static long serialVersionUID = -14605765126271939L;

}
