package light;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;

import com.example.base.base.Conn;
import com.example.base.utils.EncryptionUtils;
import com.example.base.utils.HttpUtil;
import com.example.base.utils.HttpUtils;
import com.example.base.utils.LogUtils;
import com.example.base.utils.StringUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import Interface.LightCallBack;
import model.FormFile;

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
        Map<String, File> file = new HashMap<>();
//        parent.put("mobile", "13316584270");
//        parent.put("userName", "aaa");
//        parent.put("password", "123456");
//        parent.put("registerCity", "深圳");
//        parent.put("grant_type", "1");
        parent.put("access_token", "4716663d-78c7-43c2-ab50-e7331520892e");
//        parent.put("mobile", "15986773816");
        File file1 = new File(Uri.parse("file:///storage/sdcard1/temp00.jpg").getPath());
        LogUtils.showLogI(file1.exists()+"");
        file.put(file1.getName(), file1);

        Map<String, byte[]> by = new HashMap<>();

        try {
//            String json = HttpUtil.postType(Conn.USER_LOGIN, parent, null, "POST");
//            JSONObject obj = new JSONObject(json);
//            LogUtils.showLogI(json + "");

//            HttpUtil.postFile(Conn.UPDATE_PICTURE, parent, by);

              String str = EncryptionUtils.getMD5String("123456");
            LogUtils.showLogI(str);
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
