package com.learn2develope.popularmovies.database;

import java.util.List;

/**
 * Created by Ibrahim Elazb on 1/25/2018.
 */

public interface Crud {

    public int create(Object item);
    public List findByType(String type);
    public  List findById(int movieId);
    public boolean findByIdType(int movieId,String type);
    public boolean findAll();
    public int deleteById(int movieId);
    public int deleteByType(String type);
}
