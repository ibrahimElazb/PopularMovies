
package com.learn2develope.popularmovies.model.tv;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Result implements Serializable
{

    @SerializedName("original_name")
    @Expose
    public String originalName;
    @SerializedName("genre_ids")
    @Expose
    public List<Integer> genreIds = null;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("popularity")
    @Expose
    public double popularity;
    @SerializedName("origin_country")
    @Expose
    public List<String> originCountry = null;
    @SerializedName("vote_count")
    @Expose
    public int voteCount;
    @SerializedName("first_air_date")
    @Expose
    public String firstAirDate;
    @SerializedName("backdrop_path")
    @Expose
    public String backdropPath;
    @SerializedName("original_language")
    @Expose
    public String originalLanguage;
    @SerializedName("id")
    @Expose
    public int id;
    @SerializedName("vote_average")
    @Expose
    public double voteAverage;
    @SerializedName("overview")
    @Expose
    public String overview;
    @SerializedName("poster_path")
    @Expose
    public String posterPath;
    private final static long serialVersionUID = 263491555169234847L;

}
