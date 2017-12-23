
package com.learn2develope.popularmovies.model.movies;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SpokenLanguage implements Serializable
{

    @SerializedName("iso_639_1")
    @Expose
    public String iso6391;
    @SerializedName("name")
    @Expose
    public String name;
    private final static long serialVersionUID = 7293014226093075800L;

}
