package light;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.base.base.Conn;
import com.example.base.utils.HttpUtil;
import com.example.base.utils.HttpUtils;
import com.example.base.utils.LogUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
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

        Map<String, String> parent = new HashMap<>();
        parent.put("access_token", "7b38c6c5-b5e4-492b-a699-c7d3c0eaf58f");
        parent.put("email", "15986773816@163.com");
        String[] file = {"storage/sdcard1/temp00.jpg"};
        try {
            String json = HttpUtil.postType(Conn.EMAIL_CODE, parent, null,"POST");
            JSONObject obj = new JSONObject(json);
            LogUtils.showLogI(obj+"");
//            String access_token = obj.getString("message");
//            LogUtils.showLogI(access_token + "");
        } catch (Exception e) {
            e.printStackTrace();
        }
//        LightBen ben = updateData(context, "bao", "bao", "bao", "bao");
//        callback.complete(ben);
        return null;
    }
}
