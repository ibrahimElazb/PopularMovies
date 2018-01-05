
package com.learn2develope.popularmovies.model.actors.actorDetails;
import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ActorDetails implements Serializable
{

    @SerializedName("birthday")
    @Expose
    public String birthday;
    @SerializedName("deathday")
    @Expose
    public String deathday;
    @SerializedName("id")
    @Expose
    public int id;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("gender")
    @Expose
    public int gender;
    @SerializedName("biography")
    @Expose
    public String biography;
    @SerializedName("popularity")
    @Expose
    public double popularity;
    @SerializedName("place_of_birth")
    @Expose
    public String placeOfBirth;
    @SerializedName("profile_path")
    @Expose
    public String profilePath;
    @SerializedName("adult")
    @Expose
    public boolean adult;
    @SerializedName("imdb_id")
    @Expose
    public String imdbId;
    @SerializedName("homepage")
    @Expose
    public Object homepage;
    private final static long serialVersionUID = -7601957322008258204L;

}
