package door;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import light.LightBen;
import light.LightMeteDate;

/**
 * Created by Administrator on 2016/1/9.
 * 数据操作类
 */
public class DoorDao {

    //将数据保存到数据库
    private static DoorBen updateData(Context context, String username, String enail, String data, String sex) {

        ContentResolver mResolver = context.getContentResolver();
        ContentValues values = new ContentValues();
        values.put(DoorMeteDate.USERNAME, username);
        values.put(DoorMeteDate.EMAIL, enail);
        values.put(DoorMeteDate.DATE, data);
        values.put(DoorMeteDate.SEX, sex);
        mResolver.insert(DoorMeteDate.CONTENT_URI, values);

        Cursor c = mResolver.query(DoorMeteDate.CONTENT_URI, null, DoorMeteDate._ID + "=?", new String[]{"1"}, null);
        if (c.moveToFirst()) {
            long id = c.getLong(c.getColumnIndex(DoorMeteDate._ID));
            mResolver.update(ContentUris.withAppendedId(DoorMeteDate.CONTENT_URI, id), values, null, null);

        } else {
            mResolver.insert(DoorMeteDate.CONTENT_URI, values);
        }
            String userName = c.getString(c.getColumnIndex(DoorMeteDate.USERNAME));
            String sexs = c.getString(c.getColumnIndex(DoorMeteDate.SEX));
            String email = c.getString(c.getColumnIndex(DoorMeteDate.EMAIL));
            String date = c.getString(c.getColumnIndex(DoorMeteDate.DATE));
        c.close();
        return new DoorBen(userName,sexs,email,date);
    }

    //解析json数据
    public static DoorBen getJsonData(Context context, String ip, String str) {
        DoorBen ben = updateData(context, "a", "a", "a", "a");
        return ben;
    }
}
