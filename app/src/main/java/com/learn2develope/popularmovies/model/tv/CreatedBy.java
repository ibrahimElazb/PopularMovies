
package com.learn2develope.popularmovies.model.tv;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CreatedBy implements Serializable
{

    @SerializedName("id")
    @Expose
    public int id;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("gender")
    @Expose
    public int gender;
    @SerializedName("profile_path")
    @Expose
    public String profilePath;
    private final static long serialVersionUID = -2793159149269589845L;

}
