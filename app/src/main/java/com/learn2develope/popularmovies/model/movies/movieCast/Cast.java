
package com.learn2develope.popularmovies.model.movies.movieCast;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Cast implements Serializable
{

    @SerializedName("cast_id")
    @Expose
    public int castId;
    @SerializedName("character")
    @Expose
    public String character;
    @SerializedName("credit_id")
    @Expose
    public String creditId;
    @SerializedName("gender")
    @Expose
    public int gender;
    @SerializedName("id")
    @Expose
    public int id;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("order")
    @Expose
    public int order;
    @SerializedName("profile_path")
    @Expose
    public String profilePath;
    private final static long serialVersionUID = 4692432635201187252L;

}
