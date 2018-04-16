package com.learn2develope.popularmovies.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

/**
 * Created by Ibrahim Elazb on 1/25/2018.
 */

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    final static String DATABASE_NAME="PopularMoviesAppDb.sqlite";
     static int DATABASE_VERSION=1;

     public Dao<DatabaseModel,Integer> databaseModelDao;

    DatabaseHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource,DatabaseModel.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource,DatabaseModel.class,true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        DATABASE_VERSION=newVersion;
        onCreate(database,connectionSource);
    }

    public Dao<DatabaseModel,Integer> getDataBaseModelDao(){
        if (databaseModelDao==null)
        {
            try {
                databaseModelDao=getDao(DatabaseModel.class);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return databaseModelDao;
    }
}
