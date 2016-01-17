package com.example.base.base;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2016/1/2.
 */
public class BaseSQLite extends SQLiteOpenHelper {

    private static  final int VERSION = 1;
    private String SQL = null;
    private String mDatabase;

    public BaseSQLite(Context context, String name, SQLiteDatabase.CursorFactory factory,
                      int version) {
        super(context, name, factory, version);
    }
    public BaseSQLite(Context context,String name){
        this(context,name,VERSION);
        this.mDatabase = name;
    }
    public BaseSQLite(Context context,String name,int version){
        this(context, name, null, version);
    }

    public void setSQL(String sql){
        this.SQL = sql;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql = SQL;
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists "+ mDatabase + ";");
        onCreate(db);
    }
}
