
package com.learn2develope.popularmovies.model.actors;
import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class KnownFor implements Serializable
{

    @SerializedName("vote_average")
    @Expose
    public double voteAverage;
    @SerializedName("vote_count")
    @Expose
    public int voteCount;
    @SerializedName("id")
    @Expose
    public int id;
    @SerializedName("video")
    @Expose
    public boolean video;
    @SerializedName("media_type")
    @Expose
    public String mediaType;
    @SerializedName("title")
    @Expose
    public String title;
    @SerializedName("popularity")
    @Expose
    public double popularity;
    @SerializedName("poster_path")
    @Expose
    public String posterPath;
    @SerializedName("original_language")
    @Expose
    public String originalLanguage;
    @SerializedName("original_title")
    @Expose
    public String originalTitle;
    @SerializedName("genre_ids")
    @Expose
    public List<Integer> genreIds = null;
    @SerializedName("backdrop_path")
    @Expose
    public Object backdropPath;
    @SerializedName("adult")
    @Expose
    public boolean adult;
    @SerializedName("overview")
    @Expose
    public String overview;
    @SerializedName("release_date")
    @Expose
    public String releaseDate;
    @SerializedName("original_name")
    @Expose
    public String originalName;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("first_air_date")
    @Expose
    public String firstAirDate;
    @SerializedName("origin_country")
    @Expose
    public List<String> originCountry = null;
    private final static long serialVersionUID = 2851310390117743941L;

}
