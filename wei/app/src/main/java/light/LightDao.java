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
    private LightBen updateData(Context context, String username, String enail, String data, String sex) {

        ContentResolver mResolver = context.getContentResolver();
        ContentValues values = new ContentValues();
        values.put(LightMeteDate.USERNAME, username);
        values.put(LightMeteDate.EMAIL, enail);
        values.put(LightMeteDate.DATE, data);
        values.put(LightMeteDate.SEX, sex);
        LogUtils.showLogI("到了這裏");
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
    public  LightBen getJsonData(Context context, LightCallBack callback) {

//        Map<String,String> map = new HashMap<>();
//        map.put("mobile", "13316584270");
//        map.put("userName", "aaa");
//        map.put("registerCity", "深圳");
//        map.put("access_token", "932d9439-00e4-4ec5-84ac-de2e9c6fa5ce&userId");
//        String json = HttpUtils.httpPost(Conn.REGIST,map,"POST");
//        try {
//            JSONObject obj = new JSONObject(json);
//            String access_token =  obj.getString("registerCity");
//            LogUtils.showLogI(access_token);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
        LightBen ben = updateData(context, "bao", "bao", "bao", "bao");
        callback.complete(ben);
        return null;
    }
}
