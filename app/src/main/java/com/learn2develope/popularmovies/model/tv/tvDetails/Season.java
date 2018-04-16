
package com.learn2develope.popularmovies.model.tv.tvDetails;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Season implements Serializable
{

    @SerializedName("air_date")
    @Expose
    public String airDate;
    @SerializedName("episode_count")
    @Expose
    public int episodeCount;
    @SerializedName("id")
    @Expose
    public int id;
    @SerializedName("poster_path")
    @Expose
    public String posterPath;
    @SerializedName("season_number")
    @Expose
    public int seasonNumber;
    private final static long serialVersionUID = -5133799427315039996L;

}
