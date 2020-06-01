package com.example.dig_dog.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.example.dig_dog.db.DbContract.SQL_DELETE_LIKED;
import static com.example.dig_dog.db.DbContract.SQL_DELETE_PASSWORD;
import static com.example.dig_dog.db.DbContract.SQL_DELETE_STARTED;


public class Dbhelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME="digdog.db";
    private static final int DATABASE_VERSION=2;

    public Dbhelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DbContract.SQL_CREATE_PASSWORD);
        db.execSQL(DbContract.SQL_CREATE_LIKED);
        db.execSQL(DbContract.SQL_CREATE_STARED);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_PASSWORD);
        db.execSQL(SQL_DELETE_LIKED);
        db.execSQL(SQL_DELETE_STARTED);
        onCreate(db);
    }
}
