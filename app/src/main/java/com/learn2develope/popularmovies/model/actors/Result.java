
package com.learn2develope.popularmovies.model.actors;
import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Result implements Serializable
{

    @SerializedName("popularity")
    @Expose
    public double popularity;
    @SerializedName("id")
    @Expose
    public int id;
    @SerializedName("profile_path")
    @Expose
    public String profilePath;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("known_for")
    @Expose
    public List<KnownFor> knownFor = null;
    @SerializedName("adult")
    @Expose
    public boolean adult;
    private final static long serialVersionUID = -1155117822792173117L;

}
