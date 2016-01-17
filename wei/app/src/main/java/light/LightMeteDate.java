package light;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Administrator on 2016/1/10.
 */
public class LightMeteDate implements BaseColumns {

    public static final String TABLE_NAME = "light";
    public static final int VERSION = 1;

    public static final String JID = "jid";
    /**
     * 开关
     */
    public static final String USERNAME = "_on";

    public static final String SEX = "boil";//烧水

    public static final String EMAIL = "feed";//加水

    public static final String DATE = "ster";//消毒


    public static final String AUTHORITY = "com.emample.light.provider";
    public static final Uri AUTHORITY_URI = Uri.parse("content://" + AUTHORITY);
    public static final Uri CONTENT_URI = Uri.withAppendedPath(AUTHORITY_URI, TABLE_NAME);
}
