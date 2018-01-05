
package com.learn2develope.popularmovies.model.movies.movieReviews;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MovieReviews implements Serializable
{

    @SerializedName("id")
    @Expose
    public int id;
    @SerializedName("page")
    @Expose
    public int page;
    @SerializedName("results")
    @Expose
    public List<Result> results = null;
    @SerializedName("total_pages")
    @Expose
    public int totalPages;
    @SerializedName("total_results")
    @Expose
    public int totalResults;
    private final static long serialVersionUID = -3369554210899913137L;

}
