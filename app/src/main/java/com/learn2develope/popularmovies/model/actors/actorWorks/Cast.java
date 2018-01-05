
package com.learn2develope.popularmovies.model.actors.actorWorks;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Cast implements Serializable
{

    @SerializedName("id")
    @Expose
    public int id;
    @SerializedName("character")
    @Expose
    public String character;
    @SerializedName("original_title")
    @Expose
    public String originalTitle;
    @SerializedName("overview")
    @Expose
    public String overview;
    @SerializedName("vote_count")
    @Expose
    public int voteCount;
    @SerializedName("video")
    @Expose
    public boolean video;
    @SerializedName("media_type")
    @Expose
    public String mediaType;
    @SerializedName("release_date")
    @Expose
    public String releaseDate;
    @SerializedName("vote_average")
    @Expose
    public float voteAverage;
    @SerializedName("title")
    @Expose
    public String title;
    @SerializedName("popularity")
    @Expose
    public float popularity;
    @SerializedName("original_language")
    @Expose
    public String originalLanguage;
    @SerializedName("genre_ids")
    @Expose
    public List<Integer> genreIds = null;
    @SerializedName("backdrop_path")
    @Expose
    public String backdropPath;
    @SerializedName("adult")
    @Expose
    public boolean adult;
    @SerializedName("poster_path")
    @Expose
    public String posterPath;
    @SerializedName("credit_id")
    @Expose
    public String creditId;
    @SerializedName("episode_count")
    @Expose
    public int episodeCount;
    @SerializedName("origin_country")
    @Expose
    public List<String> originCountry = null;
    @SerializedName("original_name")
    @Expose
    public String originalName;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("first_air_date")
    @Expose
    public String firstAirDate;
    private final static long serialVersionUID = -8777349883211077457L;

}
