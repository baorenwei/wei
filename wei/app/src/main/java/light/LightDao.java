package light;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.base.base.Conn;
import com.example.base.utils.HttpUtils;
import com.example.base.utils.LogUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import Interface.LightCallBack;

/**
 * Created by Administrator on 2016/1/9.
 * 数据操作类
 */
public class LightDao {

    //将数据保存到数据库
    public static LightBen updateData(Context context, String username, String enail, String data, String sex) {

        ContentResolver mResolver = context.getContentResolver();
        ContentValues values = new ContentValues();
        values.put(LightMeteDate.USERNAME, username);
        values.put(LightMeteDate.EMAIL, enail);
        values.put(LightMeteDate.DATE, data);
        values.put(LightMeteDate.SEX, sex);
        mResolver.insert(LightMeteDate.CONTENT_URI, values);

        Cursor c = mResolver.query(LightMeteDate.CONTENT_URI, null, LightMeteDate._ID + "=?", new String[]{"1"}, null);
        if (c.moveToFirst()) {
            long id = c.getLong(c.getColumnIndex(LightMeteDate._ID));
            mResolver.update(ContentUris.withAppendedId(LightMeteDate.CONTENT_URI, id), values, null, null);
        } else {
            mResolver.insert(LightMeteDate.CONTENT_URI, values);
        }
        String userName = c.getString(c.getColumnIndex(LightMeteDate.USERNAME));
        String sexs = c.getString(c.getColumnIndex(LightMeteDate.SEX));
        String email = c.getString(c.getColumnIndex(LightMeteDate.EMAIL));
        String date = c.getString(c.getColumnIndex(LightMeteDate.DATE));
        c.close();
        return new LightBen(userName, sexs, email, date);
    }

    //解析json数据
    public static LightBen getJsonData(Context context, LightCallBack callback) {

        Map<String, Object> map = new HashMap<>();
        map.put("access_token", "7b38c6c5-b5e4-492b-a699-c7d3c0eaf58f");
        String json = HttpUtils.httpPost(Conn.UPDATE_PICTURE, map, "POST",null);
        try {
            JSONObject obj = new JSONObject(json);
            String access_token = obj.getString("access_token");
            LogUtils.showLogI(obj + "");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        LightBen ben = updateData(context, "bao", "bao", "bao", "bao");
        callback.complete(ben);
        return null;
    }
}
