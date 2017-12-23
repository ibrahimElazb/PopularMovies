
package com.learn2develope.popularmovies.model.movies;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Genre implements Serializable
{

    @SerializedName("id")
    @Expose
    public int id;
    @SerializedName("name")
    @Expose
    public String name;
    private final static long serialVersionUID = 8176676699415425995L;

}
