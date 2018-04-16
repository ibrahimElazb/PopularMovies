
package com.learn2develope.popularmovies.model.tv.tvDetails;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Genre implements Serializable
{

    @SerializedName("id")
    @Expose
    public int id;
    @SerializedName("name")
    @Expose
    public String name;
    private final static long serialVersionUID = 3814397357444515838L;

}
