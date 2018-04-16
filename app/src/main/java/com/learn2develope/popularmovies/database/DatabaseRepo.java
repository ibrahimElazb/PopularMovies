package com.learn2develope.popularmovies.database;

import android.content.Context;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Ibrahim Elazb on 1/25/2018.
 */

public class DatabaseRepo implements Crud {

    DatabaseHelper databaseHelper;

   public DatabaseRepo(Context context){
        databaseHelper=DatabaseManager.getInstance(context).getDatabaseHelper();
    }

    @Override
    public int create(Object item) {
        int index=-1;
        DatabaseModel model=(DatabaseModel)item;
        try {
            index=databaseHelper.getDataBaseModelDao().create(model);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return index;
    }

    @Override
    public List findById(int movieId) {
        List items=null;
        try {
            items=databaseHelper.getDataBaseModelDao().queryForEq("movieId",movieId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }


    @Override
    public List findByType(String type) {
        List items=null;
        try {
            items=databaseHelper.getDataBaseModelDao().queryForEq("type",type);
            //databaseHelper.getDataBaseModelDao().query
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }

    @Override
    public boolean findByIdType(int movieId, String type) {
       List items=findByType(type);
       for (Object item:items){
           if (((DatabaseModel)item).getMovieId()==movieId)return true;
       }
        return false;
    }

    @Override
    public boolean findAll() {
        List items=null;
        try {
            items=databaseHelper.getDataBaseModelDao().queryForAll();
            if (items.size()>0)return false;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public int deleteById(int movieId) {
        int index=-1;
        try {
            index=databaseHelper.getDataBaseModelDao().delete(findById(movieId));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return index;
    }

    @Override
    public int deleteByType(String type) {
        int index=-1;
        try {
            index=databaseHelper.getDataBaseModelDao().delete(findByType(type));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return index;
    }
}
