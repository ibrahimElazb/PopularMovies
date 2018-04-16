
package com.learn2develope.popularmovies.model.search.tvShow;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Result implements Serializable
{
    @SerializedName("id")
    @Expose
    public int id;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("poster_path")
    @Expose
    public String posterPath;

    private final static long serialVersionUID = -4149788988283614176L;

}
