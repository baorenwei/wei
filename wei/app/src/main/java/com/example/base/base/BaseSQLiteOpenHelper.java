package com.example.base.base;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2016/1/4.
 */
public class BaseSQLiteOpenHelper extends SQLiteOpenHelper{

    private String sql = null;
    private String tableName;

    public BaseSQLiteOpenHelper(Context context,String tableName,int version) {
        super(context, tableName, null,version);
        // TODO Auto-generated constructor stub
        this.tableName = tableName;
    }

    public void setSql(String sql){
        this.sql = sql;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(sql);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("drop table if exists "+ tableName + ";");
        onCreate(db);
    }
}
