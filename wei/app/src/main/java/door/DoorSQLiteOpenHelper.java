package door;

import android.content.Context;

import com.example.base.base.BaseSQLiteOpenHelper;

import light.LightMeteDate;


/**
 * Created by Administrator on 2016/1/4.
 */
public class DoorSQLiteOpenHelper extends BaseSQLiteOpenHelper {

    public DoorSQLiteOpenHelper(Context context) {
        super(context, DoorMeteDate.TABLE_NAME,DoorMeteDate.VERSION);
        setSql(SQL);
    }

    public static final
    String SQL = "CREATE TABLE IF NOT EXISTS "
            + DoorMeteDate.TABLE_NAME
            +"("
            + DoorMeteDate._ID +" INTEGER PRIMARY KEY AUTOINCREMENT,"
            + DoorMeteDate.EMAIL + " INTEGER,"
            + DoorMeteDate.USERNAME + " INTEGER,"
            + DoorMeteDate.DATE + " INTEGER,"
            + DoorMeteDate.SEX + "  INTEGER"
            +")";
}
