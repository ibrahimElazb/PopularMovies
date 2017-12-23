
package com.learn2develope.popularmovies.model.tv;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Network implements Serializable
{

    @SerializedName("id")
    @Expose
    public int id;
    @SerializedName("name")
    @Expose
    public String name;
    private final static long serialVersionUID = 4839250022655352009L;

}
