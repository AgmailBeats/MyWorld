package com.wua.myworld;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDBOpenHelper extends SQLiteOpenHelper {
    public static String DATABASE_NAME = "wua.db";
    public static int DATABASE_VERSION = 1;

    public MyDBOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table user(id INTEGER primary key autoincrement,name VARCHAR(5),pwd VARCHAR(15))");
        db.execSQL("insert into user (name,pwd) values('chenzejia','123456')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
