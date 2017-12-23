
package com.learn2develope.popularmovies.model.movies;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductionCountry implements Serializable
{

    @SerializedName("iso_3166_1")
    @Expose
    public String iso31661;
    @SerializedName("name")
    @Expose
    public String name;
    private final static long serialVersionUID = 8503028454888201062L;

}
