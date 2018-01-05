
package com.learn2develope.popularmovies.model.movies;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Result implements Serializable {

    @SerializedName("id")
    @Expose
    public int id;
    @SerializedName("title")
    @Expose
    public String title;
    @SerializedName("poster_path")
    @Expose
    public String posterPath;

    private final static long serialVersionUID = 7756300412282020086L;

}
