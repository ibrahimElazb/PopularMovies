
package com.learn2develope.popularmovies.model.movies.movieCast;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MovieCast implements Serializable
{

    @SerializedName("id")
    @Expose
    public int id;
    @SerializedName("cast")
    @Expose
    public List<Cast> cast = null;
    private final static long serialVersionUID = 6263904180837396751L;

}
