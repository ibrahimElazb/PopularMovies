
package com.learn2develope.popularmovies.model.movies;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Movies implements Serializable
{

    @SerializedName("results")
    @Expose
    public List<Result> results = null;
    @SerializedName("page")
    @Expose
    public int page;
    @SerializedName("total_results")
    @Expose
    public int totalResults;
    @SerializedName("total_pages")
    @Expose
    public int totalPages;
    private final static long serialVersionUID = 8747585346193033101L;

}
