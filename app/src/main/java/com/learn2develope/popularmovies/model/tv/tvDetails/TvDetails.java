
package com.learn2develope.popularmovies.model.tv.tvDetails;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TvDetails implements Serializable
{

    @SerializedName("backdrop_path")
    @Expose
    public String backdropPath;
    @SerializedName("episode_run_time")
    @Expose
    public List<Integer> episodeRunTime = null;
    @SerializedName("first_air_date")
    @Expose
    public String firstAirDate;
    @SerializedName("genres")
    @Expose
    public List<Genre> genres = null;
    @SerializedName("homepage")
    @Expose
    public String homepage;
    @SerializedName("id")
    @Expose
    public int id;

    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("number_of_episodes")
    @Expose
    public int numberOfEpisodes;
    @SerializedName("number_of_seasons")
    @Expose
    public int numberOfSeasons;
    @SerializedName("overview")
    @Expose
    public String overview;
    @SerializedName("poster_path")
    @Expose
    public String posterPath;
    @SerializedName("seasons")
    @Expose
    public List<Season> seasons = null;
    @SerializedName("status")
    @Expose
    public String status;
    @SerializedName("type")
    @Expose
    public String type;
    @SerializedName("vote_average")
    @Expose
    public float voteAverage;
    @SerializedName("vote_count")
    @Expose
    public int voteCount;
    private final static long serialVersionUID = 4275714008599212627L;

}
