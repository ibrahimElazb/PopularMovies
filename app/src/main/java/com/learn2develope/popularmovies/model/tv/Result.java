
package com.learn2develope.popularmovies.model.tv;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Result implements Serializable
{

    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("id")
    @Expose
    public int id;
    @SerializedName("poster_path")
    @Expose
    public String posterPath;
    private final static long serialVersionUID = 263491555169234847L;

}
