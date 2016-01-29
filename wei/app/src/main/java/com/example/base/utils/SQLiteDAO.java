package com.example.base.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.example.base.base.BaseSQLite;


/**
 * Created by Administrator on 2016/1/2.
 * SQLite 工具
 */
public class SQLiteDAO {

    Context mContext;
    BaseSQLite mBaseSQLite;

    SQLiteDAO(Context context, String sql) {
        this.mContext = context;
        mBaseSQLite = new BaseSQLite(mContext, "bao_db");
        mBaseSQLite.setSQL(sql);
        Toast.makeText(mContext, "成功", Toast.LENGTH_SHORT).show();
    }

    public void insert(String tableName, ContentValues values) {
        SQLiteDatabase db = mBaseSQLite.getReadableDatabase();
        db.insert(tableName, null, values);

        Toast.makeText(mContext, "成功", Toast.LENGTH_SHORT).show();
    }

    public Cursor quere(String tableNameint, int id) {

        SQLiteDatabase db = mBaseSQLite.getReadableDatabase();
        Cursor c = db.query(tableNameint, new String[]{"id", "name"}, "id=?", new String[]{String.valueOf(id)}, null, null, null, null);
        return c;
    }

    public void delete() {
        SQLiteDatabase db = mBaseSQLite.getWritableDatabase();
    }

    public void modification() {
        SQLiteDatabase db = mBaseSQLite.getWritableDatabase();
    }
}
