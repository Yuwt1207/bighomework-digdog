package com.example.dig_dog.db;

import android.provider.BaseColumns;

public class DbContract {
    private DbContract(){}

    public static final String SQL_CREATE_PASSWORD="CREATE TABLE IF NOT EXISTS "+DIGDOGEntry.TABLE_NAME_PASSWORD+"("+
            DIGDOGEntry._ID+" INTEGER  PRIMARY KEY AUTOINCREMENT,"+
            DIGDOGEntry.COLUMN_NAME_USERNAME+" VARCHAR NOT NULL,"+
            DIGDOGEntry.COLUMN_NAME_PASSWORD+" VARCHAR NOT NULL)";//建表语句

    public static final String SQL_CREATE_LIKED="CREATE TABLE IF NOT EXISTS "+DIGDOGEntry.TABLE_NAME_LIKED+"("+
            DIGDOGEntry.COLUMN_NAME_USERNAME+" VARCHAR NOT NULL,"+
            DIGDOGEntry.COLUMN_NAME_LIKED+" VARCHAR NOT NULL)";//建表语句

    public static final String SQL_CREATE_STARED="CREATE TABLE IF NOT EXISTS "+DIGDOGEntry.TABLE_NAME_STARED+"("+
            DIGDOGEntry.COLUMN_NAME_USERNAME+" VARCHAR NOT NULL,"+
            DIGDOGEntry.COLUMN_NAME_STARED+" VARCHAR NOT NULL)";//建表语句

    public static final String SQL_DELETE_PASSWORD = "DROP TABLE IF EXISTS " + DIGDOGEntry.TABLE_NAME_PASSWORD;
    public static final String SQL_DELETE_LIKED= "DROP TABLE IF EXISTS " + DIGDOGEntry.TABLE_NAME_LIKED;
    public static final String SQL_DELETE_STARTED = "DROP TABLE IF EXISTS " + DIGDOGEntry.TABLE_NAME_STARED;


    public static class DIGDOGEntry implements BaseColumns{
        public static final String TABLE_NAME_PASSWORD="PASSWORD";
        public static final String TABLE_NAME_LIKED="LIKED";
        public static final String TABLE_NAME_STARED="STARED";
        public static final String COLUMN_NAME_USERNAME="USERNAME";
        public static final String COLUMN_NAME_PASSWORD="PASSWORD";
        public static final String COLUMN_NAME_LIKED="LIKED";//放url，url唯一
        public static final String COLUMN_NAME_STARED="STARED";//放url，url唯一
    }
}
