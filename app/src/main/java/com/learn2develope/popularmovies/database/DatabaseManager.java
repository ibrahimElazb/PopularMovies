package com.learn2develope.popularmovies.database;

import android.content.Context;

/**
 * Created by Ibrahim Elazb on 1/25/2018.
 */

public class DatabaseManager {

    private static DatabaseManager databaseManagerInstance;
    private DatabaseHelper databaseHelper;


    private DatabaseManager(Context context){
        databaseHelper=new DatabaseHelper(context);
    }

    public static DatabaseManager getInstance(Context context){
        if (databaseManagerInstance==null)
            databaseManagerInstance=new DatabaseManager(context);
        return databaseManagerInstance;
    }

    public DatabaseHelper getDatabaseHelper(){
        return databaseHelper;
    }


}
