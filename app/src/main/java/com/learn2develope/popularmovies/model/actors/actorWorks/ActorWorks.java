
package com.learn2develope.popularmovies.model.actors.actorWorks;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ActorWorks implements Serializable
{

    @SerializedName("cast")
    @Expose
    public List<Cast> cast = null;
    @SerializedName("id")
    @Expose
    public int id;
    private final static long serialVersionUID = 7694964672577822297L;

}
