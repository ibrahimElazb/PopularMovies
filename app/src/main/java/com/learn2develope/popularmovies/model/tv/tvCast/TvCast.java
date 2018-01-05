
package com.learn2develope.popularmovies.model.tv.tvCast;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TvCast implements Serializable
{

    @SerializedName("cast")
    @Expose
    public List<Cast> cast = null;
    @SerializedName("id")
    @Expose
    public int id;
    private final static long serialVersionUID = 6648132850302156853L;

}
