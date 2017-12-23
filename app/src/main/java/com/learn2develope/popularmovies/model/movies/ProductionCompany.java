
package com.learn2develope.popularmovies.model.movies;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductionCompany implements Serializable
{

    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("id")
    @Expose
    public int id;
    private final static long serialVersionUID = -674764907571750570L;

}
