
package com.learn2develope.popularmovies.model.actors;
import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Result implements Serializable
{
    @SerializedName("id")
    @Expose
    public int id;
    @SerializedName("profile_path")
    @Expose
    public String profilePath;
    @SerializedName("name")
    @Expose
    public String name;
    private final static long serialVersionUID = -1155117822792173117L;

}
